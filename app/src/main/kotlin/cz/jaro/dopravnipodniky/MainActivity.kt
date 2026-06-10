package cz.jaro.dopravnipodniky

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Euro
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.jaro.better_dialog.AlertDialogManager
import cz.jaro.dopravnipodniky.data.Hodiny
import cz.jaro.dopravnipodniky.data.PreferencesDataSource
import cz.jaro.dopravnipodniky.data.Updater
import cz.jaro.dopravnipodniky.data.dopravnipodnik.dobaOdPoslednihoHrani
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlovac
import cz.jaro.dopravnipodniky.migrations.BarakyNemajiBarvuMigration
import cz.jaro.dopravnipodniky.migrations.NoveKrizovatkyMigration
import cz.jaro.dopravnipodniky.migrations.NovySystemGeneratoruMigration
import cz.jaro.dopravnipodniky.shared.SharedViewModel
import cz.jaro.dopravnipodniky.shared.StavHry
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.composeString
import cz.jaro.dopravnipodniky.shared.formatovat
import cz.jaro.dopravnipodniky.shared.je
import cz.jaro.dopravnipodniky.shared.stavHry
import cz.jaro.dopravnipodniky.shared.vychoziStavHry
import cz.jaro.dopravnipodniky.shared.zpomalit
import cz.jaro.dopravnipodniky.ui.Loading
import cz.jaro.dopravnipodniky.ui.nav.NavDisplay
import cz.jaro.dopravnipodniky.ui.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.koinConfiguration
import org.koin.dsl.module
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

var zobrazitLoading by mutableStateOf(true)
var uplnePoprve = true
var snackbarHostState = SnackbarHostState()
var lifecycleState: StateFlow<Lifecycle.State>? = null

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        stavHry

        lifecycleState = lifecycle.currentStateFlow

        setContent {
            KoinApp(this) {
                val dosahlovac = koinInject<Dosahlovac>()
                val hodiny = koinInject<Hodiny>()
                val dataSource = koinInject<PreferencesDataSource>()
                val dpInfoFlow = remember { dataSource.dpInfo }
                val temaFlow = remember { dpInfoFlow.map { it.tema } }
                val loading = remember {
                    temaFlow.map {
                        if (zobrazitLoading && uplnePoprve) {
                            delay(5.seconds)
                            uplnePoprve = false
                            zobrazitLoading = false
                        }
                    }
                }

                val dpInfo by dpInfoFlow.collectAsStateWithLifecycle(null)
                val tema by temaFlow.collectAsStateWithLifecycle(null)
                loading.collectAsStateWithLifecycle(Unit)
                val tutorial by dataSource.tutorial.collectAsStateWithLifecycle(null)

                if (tema != null && dpInfo != null) AppTheme(
                    useDynamicColor = false,
                    theme = tema!!
                ) {
                    val scope = rememberCoroutineScope()
                    var vyuctovani by remember { mutableStateOf(null as Duration?) }
                    LaunchedEffect(Unit) {
                        hodiny.registerListener(.1.seconds) { ubehlo ->
                            // zistovani jestli nejses moc dlouho pryc
                            val posledniId = dpInfo!!.id

                            val dobaOdPosledniNavstevy = dpInfo!!.dobaOdPoslednihoHrani
//                        println(ubehlo)
//                        println(dobaOdPosledniNavstevy)
//                        println(stavHry)

                            when (val stav = stavHry) {
                                is StavHry.Hra, is StavHry.PomalaHra -> {
                                    if (dobaOdPosledniNavstevy < 0.milliseconds) {
//                                        dosahlovac.dosahni(Dosahlost.Citer::class) TODO
//                                    stavHry = StavHry.Dohaneni(zbyva = -dobaOdPosledniNavstevy)

                                        dataSource.upravitDPInfo { info ->
                                            if (posledniId != info.id) info
                                            else info.copy(
                                                casPosledniNavstevy = Clock.System.now(),
                                            )
                                        }
                                        return@registerListener
                                    } else if (dobaOdPosledniNavstevy > 250.seconds) {
                                        stavHry =
                                            StavHry.RychlaSimulace(zbyva = dobaOdPosledniNavstevy)
                                        if (
                                            tutorial != null &&
                                            !(tutorial!! je StavTutorialu.Tutorialujeme.Uvod) &&
                                            !(tutorial!! je StavTutorialu.Tutorialujeme.Linky) &&
                                            !(tutorial!! je StavTutorialu.Tutorialujeme.Zastavky) &&
                                            !(tutorial!! je StavTutorialu.Tutorialujeme.Garaz) &&
                                            !(tutorial!! je StavTutorialu.Tutorialujeme.Obchod)
                                        ) vyuctovani =
                                            dobaOdPosledniNavstevy.coerceAtMost(8.hours)
                                    } else if (dobaOdPosledniNavstevy > 10.seconds) {
                                        stavHry =
                                            StavHry.PomalaSimulace(zbyva = dobaOdPosledniNavstevy)
                                        if (
                                            tutorial != null &&
                                            !(tutorial!! je StavTutorialu.Tutorialujeme.Uvod) &&
                                            !(tutorial!! je StavTutorialu.Tutorialujeme.Linky) &&
                                            !(tutorial!! je StavTutorialu.Tutorialujeme.Zastavky) &&
                                            !(tutorial!! je StavTutorialu.Tutorialujeme.Garaz) &&
                                            !(tutorial!! je StavTutorialu.Tutorialujeme.Obchod)
                                        ) vyuctovani = dobaOdPosledniNavstevy
                                    }
                                }

                                is StavHry.RychlaSimulace -> {
                                    stavHry = stav.copy(zbyva = stav.zbyva - ubehlo)
                                    if (stav.zbyva < 100.seconds) {
                                        stavHry = stav.zpomalit()
                                    }
                                }

                                is StavHry.PomalaSimulace -> {
                                    stavHry = stav.copy(zbyva = stav.zbyva - ubehlo)
                                    if (stav.zbyva < 10.seconds) {
                                        vyuctovani = null
                                        stavHry = vychoziStavHry
                                    }
                                }

//                            is StavHry.Dohaneni -> {
//                                stavHry = stav.copy(zbyva = stav.zbyva - ubehlo / stavHry.zrychleni.toDouble())
//                                if (stav.zbyva < 1.seconds) {
//                                    stavHry = StavHry.Hra
//                                }
//                            }
                            }
                            dataSource.upravitDPInfo { info ->
                                if (posledniId != info.id) info
                                else info.copy(
                                    casPosledniNavstevy = info.casPosledniNavstevy + ubehlo.let {
                                        if (stavHry is StavHry.Simulace) it// / stavHry.zrychleni.toDouble()
                                        else it
                                    } + if (dobaOdPosledniNavstevy > 8.hours) (dobaOdPosledniNavstevy - 8.hours) else 0.hours
                                )
                            }
                        }
                    }

                    cz.jaro.better_dialog.AlertDialog(AlertDialogManager.Global)

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
                            Icon(Icons.AutoMirrored.Filled.Help, null)
                        },
                        title = {
                            Text(stringResource(R.string.tutorial))
                        },
                        text = {
                            Text(
                                stringResource((tutorial as StavTutorialu.Tutorialujeme).text),
                                Modifier.verticalScroll(rememberScrollState()),
                            )
                        },
                    )

                    if (stavHry is StavHry.Simulace && vyuctovani != null && !zobrazitLoading) AlertDialog(
                        onDismissRequest = {},
                        confirmButton = {},
                        icon = { Icon(Icons.Default.Euro, null) },
                        title = { Text(stringResource(R.string.slovo_vyuctovani)) },
                        text = {
                            if (stavHry is StavHry.Simulace && vyuctovani != null && !zobrazitLoading) Column {
                                val stav = stavHry as StavHry.Simulace
                                val celkem = vyuctovani!!
                                Text(
                                    stringResource(
                                        R.string.vyuctovani,
                                        if (celkem < 2.hours)
                                            pluralStringResource(
                                                R.plurals.min,
                                                celkem.inWholeMinutes.toInt(),
                                                celkem.inWholeMinutes,
                                            )
                                        else
                                            pluralStringResource(
                                                R.plurals.hod,
                                                celkem.inWholeHours.toInt(),
                                                celkem.inWholeHours,
                                            ),
                                        if ((celkem - stav.zbyva) < 2.hours)
                                            pluralStringResource(
                                                R.plurals.min,
                                                (celkem - stav.zbyva).inWholeMinutes.toInt(),
                                                (celkem - stav.zbyva).inWholeMinutes,
                                            )
                                        else
                                            pluralStringResource(
                                                R.plurals.hod,
                                                (celkem - stav.zbyva).inWholeHours.toInt(),
                                                (celkem - stav.zbyva).inWholeHours,
                                            ),
                                        (1 - stav.zbyva / celkem).times(100).formatovat(0).composeString(),
                                    ),
                                    Modifier.verticalScroll(rememberScrollState()),
                                )
                                LinearProgressIndicator(progress = {
                                    (1 - stav.zbyva / celkem).toFloat()
                                })
                            }
                        },
                    )

//                if (stavHry is StavHry.Dohaneni && !zobrazitLoading) AlertDialog(
//                    onDismissRequest = { },
//                    confirmButton = { },
//                    icon = {
//                        Icon(Icons.Default.WarningAmber, null)
//                    },
//                    title = {
//                        Text(stringResource(R.string.citer))
//                    },
//                    text = { },
//                )

                    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
                    Scaffold(
                        snackbarHost = {
                            SnackbarHost(snackbarHostState)
                        }
                    ) {

                        /*val view = LocalView.current
                        val bg = MaterialTheme.colorScheme.background
                        TODO
                        SideEffect {
                            navController.addOnDestinationChangedListener { _, _, _ ->
                                if (!view.isInEditMode) {
                                    val window = (view.context as? Activity)?.window
                                        ?: return@addOnDestinationChangedListener
                                    window.statusBarColor = bg.toArgb()
                                    WindowCompat.getInsetsController(
                                        window,
                                        view
                                    ).isAppearanceLightStatusBars = false
                                }
                            }
                        }*/

                        NavDisplay(Modifier)
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
}

@Composable
private fun KoinApp(
    context: Context,
    content: @Composable () -> Unit,
) = KoinApplication(
    configuration = koinConfiguration {
        androidContext(context)
        androidLogger()
        modules(module {
            single {
                PreferenceDataStoreFactory.create(
                    migrations = listOf(
                        NovySystemGeneratoruMigration,
                        BarakyNemajiBarvuMigration,
                        NoveKrizovatkyMigration,
                    )
                ) {
                    get<Context>().preferencesDataStoreFile("DopravniPodniky_DataStore")
                }
            }
        })
        modules(module {
            single { Hodiny() }
            single { PreferencesDataSource(get(), get()) }
            single { Dosahlovac(get()) }
            single(createdAtStart = true) { Updater(get(), get(), get()) }
            viewModel { SharedViewModel(get(), get()) }
        })
    },
    content = content,
)
