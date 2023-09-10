package cz.jaro.dopravnipodniky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Help
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.DestinationsNavHost
import cz.jaro.dopravnipodniky.data.Hodiny
import cz.jaro.dopravnipodniky.data.PreferencesDataSource
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlovac
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.composeString
import cz.jaro.dopravnipodniky.shared.formatovat
import cz.jaro.dopravnipodniky.shared.je
import cz.jaro.dopravnipodniky.shared.jednotky.asString
import cz.jaro.dopravnipodniky.shared.jednotky.times
import cz.jaro.dopravnipodniky.shared.nasobitelZiskuPoOffline
import cz.jaro.dopravnipodniky.ui.Loading
import cz.jaro.dopravnipodniky.ui.NavGraphs
import cz.jaro.dopravnipodniky.ui.theme.DpTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

var zobrazitLoading by mutableStateOf(true)
var uplnePoprve = true
var snackbarHostState = SnackbarHostState()

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dosahlovac = get<Dosahlovac>()
        val hodiny = get<Hodiny>()
        val dataSource = get<PreferencesDataSource>()
        val dpInfoFlow = dataSource.dpInfo
        val temaFlow = dpInfoFlow.map { it.tema }
        val loading = temaFlow.map {
            if (zobrazitLoading) {
                delay(if (uplnePoprve) 5.seconds else 1.seconds)
                uplnePoprve = false
                zobrazitLoading = false
            }
        }

        setContent {
            val dpInfo by dpInfoFlow.collectAsStateWithLifecycle(null)
            val tema by temaFlow.collectAsStateWithLifecycle(null)
            loading.collectAsStateWithLifecycle(Unit)
            val tutorial by dataSource.tutorial.collectAsStateWithLifecycle(null)

            if (tema != null && dpInfo != null) DpTheme(
                useDynamicColor = false,
                theme = tema!!
            ) {
                val scope = rememberCoroutineScope()
                var vyuctovani by remember { mutableStateOf(null as Duration?) }
                LaunchedEffect(Unit) {
                    hodiny.registerListener(1.seconds) {
                        // zistovani jestli nejses moc dlouho pryc

                        val dobaOdPosledniNavstevy =
                            (System.currentTimeMillis() - dpInfo!!.casPosledniNavstevy).milliseconds.coerceAtMost(8.hours)

                        if (dobaOdPosledniNavstevy < 0.milliseconds) {
                            dosahlovac.dosahni(Dosahlost.Citer::class)
                        }

                        if (dobaOdPosledniNavstevy > 10.seconds) {

                            dataSource.upravitBusy {
                                forEachIndexed { i, bus ->
                                    this[i] = bus.copy(
                                        najeto = bus.najeto + dobaOdPosledniNavstevy
                                    )
                                }
                            }

                            dataSource.upravitPrachy {
                                it + dpInfo!!.zisk * dobaOdPosledniNavstevy
                            }

                            if (
                                tutorial != null &&
                                !(tutorial!! je StavTutorialu.Tutorialujeme.Uvod) &&
                                !(tutorial!! je StavTutorialu.Tutorialujeme.Linky) &&
                                !(tutorial!! je StavTutorialu.Tutorialujeme.Zastavky) &&
                                !(tutorial!! je StavTutorialu.Tutorialujeme.Garaz) &&
                                !(tutorial!! je StavTutorialu.Tutorialujeme.Obchod)
                            ) vyuctovani = dobaOdPosledniNavstevy
                        }
                        dataSource.upravitDPInfo {
                            it.copy(
                                casPosledniNavstevy = System.currentTimeMillis()
                            )
                        }
                    }
                }

                if (tutorial is StavTutorialu.Tutorialujeme && !zobrazitLoading) AlertDialog(
                    onDismissRequest = {
                        scope.launch {
                            dataSource.upravitTutorial {
                                StavTutorialu.Odkliknuto(tutorial as StavTutorialu.Tutorialujeme)
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                scope.launch {
                                    dataSource.upravitTutorial {
                                        StavTutorialu.Odkliknuto(tutorial as StavTutorialu.Tutorialujeme)
                                    }
                                }
                            },
                        ) {
                            if (tutorial == StavTutorialu.Tutorialujeme.Uvod)
                                Text(stringResource(R.string.pojdme_na_to))
                            else
                                Text(stringResource(android.R.string.ok))
                        }
                    },
                    dismissButton = {
                        if (tutorial == StavTutorialu.Tutorialujeme.Uvod) TextButton(
                            onClick = {
                                scope.launch {
                                    dataSource.upravitTutorial {
                                        StavTutorialu.Nic
                                    }
                                }
                            },
                        ) {
                            Text(stringResource(R.string.preskocit_tutorial))
                        }
                    },
                    icon = {
                        Icon(Icons.Default.Help, null)
                    },
                    title = {
                        Text(stringResource(R.string.tutorial))
                    },
                    text = {
                        Text(
                            stringResource((tutorial as StavTutorialu.Tutorialujeme).text),
                            Modifier.verticalScroll(rememberScrollState())
                        )
                    },
                )

                if (vyuctovani != null && !zobrazitLoading) AlertDialog(
                    onDismissRequest = {
                        vyuctovani = null
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                vyuctovani = null
                            }
                        ) {
                            Text(stringResource(android.R.string.ok))
                        }
                    },
                    icon = {
                        Icon(Icons.Default.AttachMoney, null)
                    },
                    title = {
                        Text(stringResource(R.string.slovo_vyuctovani))
                    },
                    text = {
                        Text(
                            stringResource(
                                R.string.vyuctovani,
                                if (vyuctovani!! < 2.hours)
                                    pluralStringResource(
                                        R.plurals.min,
                                        vyuctovani!!.inWholeMinutes.toInt(),
                                        vyuctovani!!.inWholeMinutes,
                                    )
                                else
                                    pluralStringResource(
                                        R.plurals.hod,
                                        vyuctovani!!.inWholeHours.toInt(),
                                        vyuctovani!!.inWholeHours,
                                    ),
                                (dpInfo!!.zisk * nasobitelZiskuPoOffline * vyuctovani!!).asString(),
                                (dpInfo!!.zisk).asString(),
                                vyuctovani!!.inWholeMinutes.formatovat(0).composeString(),
                                nasobitelZiskuPoOffline.formatovat().composeString(),
                                (dpInfo!!.zisk * nasobitelZiskuPoOffline * vyuctovani!!).asString(),
                                if (vyuctovani!! >= 8.hours)
                                    getString(R.string.bohuzel_dlouho_neaktivni)
                                else ""
                            )
                        )
                    }
                )

                Scaffold(
                    snackbarHost = {
                        SnackbarHost(snackbarHostState)
                    }
                ) { paddingValues ->
                    DestinationsNavHost(navGraph = NavGraphs.root, Modifier.padding(paddingValues))
                }
            }
            AnimatedVisibility(
                zobrazitLoading,
                enter = slideInVertically(animationSpec = tween(300), initialOffsetY = { it }),
                exit = slideOutVertically(animationSpec = tween(300), targetOffsetY = { it }),
            ) {
                Loading()
            }
        }
    }
}
