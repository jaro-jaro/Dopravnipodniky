package cz.jaro.dopravnipodniky.ui.dopravnipodniky

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import cz.jaro.compose_dialog.show
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.Vse
import cz.jaro.dopravnipodniky.data.generace.DetailGenerace
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.data.generace.Generator
import cz.jaro.dopravnipodniky.dialogState
import cz.jaro.dopravnipodniky.shared.Menic
import cz.jaro.dopravnipodniky.shared.SharedViewModel
import cz.jaro.dopravnipodniky.shared.composeString
import cz.jaro.dopravnipodniky.shared.formatovat
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz
import cz.jaro.dopravnipodniky.shared.jednotky.asString
import cz.jaro.dopravnipodniky.shared.jednotky.penez
import cz.jaro.dopravnipodniky.shared.minimumInvestice
import cz.jaro.dopravnipodniky.snackbarHostState
import cz.jaro.dopravnipodniky.ui.destinations.NovyDopravniPodnikScreenDestination
import cz.jaro.dopravnipodniky.ui.main.DEBUG_TEXT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random
import kotlin.reflect.KClass
import kotlin.time.Duration.Companion.seconds

@Composable
@Destination
fun DopravniPodnikyScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = koinViewModel<SharedViewModel>()

    val dp by viewModel.dp.collectAsStateWithLifecycle()
    val vse by viewModel.vse.collectAsStateWithLifecycle()

    if (
        dp != null &&
        vse != null
    ) DopravniPodnikyScreen(
        tentoDP = dp!!,
        vse = vse!!,
        menic = viewModel.menic,
        novaMesta = viewModel::najit3NovaMesta,
        navigate = navigator::navigate,
        navigateBack = navigator::navigateUp,
        dosahni = viewModel.dosahni,
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DopravniPodnikyScreen(
    tentoDP: DopravniPodnik,
    vse: Vse,
    menic: Menic,
    navigate: (Direction) -> Unit,
    novaMesta: (Peniz, (Float) -> Unit, () -> Unit) -> Unit,
    dosahni: (KClass<out Dosahlost>) -> Unit,
    navigateBack: () -> Unit,
) {
    var loading by rememberSaveable { mutableStateOf(null as Float?) }
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.dopravni_podniky))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigateBack()
                        }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.zpet))
                    }
                },
            )
        },
        floatingActionButton = {
            var investice by rememberSaveable { mutableStateOf("") }
            ExtendedFloatingActionButton(
                text = {
                    Text(stringResource(R.string.najit_nove_mesto))
                },
                icon = {
                    Icon(Icons.Default.Search, null)
                },
                onClick = {
                    if (DEBUG_TEXT) dialogState.show(
                        confirmButton = {
                            val ctx = LocalContext.current
                            TextButton(
                                onClick = {
                                    val detailGenerace = try {
                                        Json.decodeFromString<DetailGenerace>(investice)
                                    } catch (_: IllegalArgumentException) {
                                        Toast.makeText(ctx, "Toto není dobře", Toast.LENGTH_LONG).show()
                                        return@TextButton
                                    }
                                    hide()

                                    scope.launch {
                                        val dp = Generator(detailGenerace) {
                                            loading = it
                                        }

                                        loading = null

                                        navigateBack()

                                        CoroutineScope(Dispatchers.IO).launch {
                                            menic.zmenitOstatniDopravniPodniky {
                                                add(dp)
                                            }
                                            menic.zmenitDopravniPodnik(dp.info.id)
                                        }
                                    }
                                }
                            ) {
                                Text(stringResource(android.R.string.ok))
                            }
                        },
                        dismissButton = { },
                        title = {
                            Text(stringResource(R.string.novy_dp))
                        },
                        content = {
                            TextField(
                                value = investice,
                                onValueChange = {
                                    investice = it
                                },
                                Modifier.fillMaxWidth(),
                                label = {
                                    Text("Detail generace")
                                }
                            )
                        },
                    )
                    else dialogState.show(
                        confirmButton = {
                            val ctx = LocalContext.current
                            TextButton(
                                onClick = {
                                    val i = investice.toLongOrNull()?.penez ?: run {
                                        Toast.makeText(ctx, R.string.zadejte_validni_pocet, Toast.LENGTH_LONG).show()
                                        return@TextButton
                                    }

                                    if (i > vse.prachy) {
                                        Toast.makeText(ctx, R.string.malo_penez, Toast.LENGTH_LONG).show()
                                        return@TextButton
                                    }

                                    if (i < minimumInvestice && i != 69_420.penez) {

                                        scope.launch {
                                            loading = 4F

                                            delay(3.seconds)

                                            loading = null

                                            snackbarHostState.showSnackbar(
                                                message = ctx.getString(R.string.nigdo_nebyl_ochoten),
                                                withDismissAction = true,
                                            )
                                        }

                                        menic.zmenitPrachy {
                                            it - i / 10
                                        }

                                        hide()
                                        return@TextButton
                                    }

                                    menic.zmenitPrachy {
                                        it - i
                                    }

                                    hide()
                                    loading = 0F

                                    if (i == 69_420.penez) {
                                        dosahni(Dosahlost.JostoMesto::class)

                                        novaMesta(
                                            i * 100,
                                            {
                                                loading = it
                                            }
                                        ) {
                                            loading = null
                                            navigate(NovyDopravniPodnikScreenDestination)
                                        }

                                    } else {
                                        novaMesta(
                                            i,
                                            {
                                                loading = it
                                            }
                                        ) {
                                            loading = null
                                            dialogState.show(
                                                confirmButton = {
                                                    TextButton(
                                                        onClick = {
                                                            hide()
                                                            navigate(NovyDopravniPodnikScreenDestination)
                                                        }
                                                    ) {
                                                        Text(stringResource(android.R.string.ok))
                                                    }
                                                },
                                                onDismissed = {
                                                    navigate(NovyDopravniPodnikScreenDestination)
                                                },
                                                content = {
                                                    Text(stringResource(R.string.tri_mesta_se_nasla))
                                                },
                                                title = {
                                                    Text(stringResource(R.string.novy_dp))
                                                }
                                            )
                                        }
                                    }
                                }
                            ) {
                                Text(stringResource(android.R.string.ok))
                            }
                        },
                        dismissButton = { },
                        title = {
                            Text(stringResource(R.string.novy_dp))
                        },
                        content = {
                            TextField(
                                value = investice,
                                onValueChange = {
                                    investice = it
                                },
                                Modifier.fillMaxWidth(),
                                label = {
                                    Text(stringResource(R.string.vyse_investice))
                                }
                            )
                        },
                    )
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { paddingValues ->

        if (loading != null) AlertDialog(
            onDismissRequest = { },
            confirmButton = { },
            dismissButton = { },
            title = {
                Text(stringResource(R.string.generace_mesta))
            },
            text = {
                Column(
                    Modifier.fillMaxWidth()
                ) {
                    when {
                        loading!! == 4F -> {
                            LinearProgressIndicator(Modifier.fillMaxWidth())
                        }
                        loading!! != 3F -> {
                            Text("${loading!!.toInt() + 1}: ${((loading!! % 1) * 100).formatovat(2).composeString()} %")
                            LinearProgressIndicator(
                                progress = loading!! % 1,
                                Modifier.fillMaxWidth()
                            )
                        }
                        else -> {
                            Text("Hotovo!")
                            LinearProgressIndicator(Modifier.fillMaxWidth())
                        }
                    }
                }
            },
        )

        var otevreno by rememberSaveable { mutableStateOf(null as String?) }
        Column(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Row(
                Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = vse.prachy.asString(),
                    Modifier
                        .weight(1F)
                        .padding(all = 16.dp),
                    textAlign = TextAlign.Start,
                )
            }
            LazyColumn(
                Modifier.weight(1F)
            ) {
                items(vse.podniky.distinctBy { it.info.id }, key = { it.info.id }) { dp ->
                    val expanded = otevreno == dp.info.id.toString()
                    Column(
                        Modifier
                            .clickable {
                                otevreno = if (expanded) null else dp.info.id.toString()
                            },
                    ) {
                        ListItem(
                            headlineContent = {
                                Text(dp.info.jmenoMesta)
                            },
                            trailingContent = {
                                if (tentoDP.info.id != dp.info.id) IconButton(
                                    onClick = {
                                        CoroutineScope(Dispatchers.Main).launch {
                                            menic.zmenitDopravniPodnik(dp.info.id)
                                            navigateBack()
                                        }
                                    }
                                ) {
                                    Icon(if (Random.nextFloat() <= .01F) Icons.Default.ShoppingCartCheckout else Icons.AutoMirrored.Filled.Login, null)
                                }
                            },
                            leadingContent = {
                                Icon(Icons.Default.Circle, null, tint = dp.info.tema.barva)
                            },
                        )
//                        AnimatedVisibility(expanded) {
//                            Column(
//                                Modifier
//                                    .fillMaxWidth()
//                                    .padding(8.dp)
//                            ) {
                                DopravniPodnik(tentoDP, dp, menic)
//                            }
//                        }
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}