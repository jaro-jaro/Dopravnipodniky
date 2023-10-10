package cz.jaro.dopravnipodniky.ui.dopravnipodniky

import android.widget.NumberPicker
import android.widget.Toast
import androidx.annotation.FloatRange
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.Vse
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Trakce
import cz.jaro.dopravnipodniky.data.dopravnipodnik.dobaOdPoslednihoHrani
import cz.jaro.dopravnipodniky.data.dopravnipodnik.jsouVsechnyZatrolejovane
import cz.jaro.dopravnipodniky.data.dopravnipodnik.linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.maZastavku
import cz.jaro.dopravnipodniky.data.dopravnipodnik.nevyzvednuto
import cz.jaro.dopravnipodniky.data.dopravnipodnik.plocha
import cz.jaro.dopravnipodniky.data.dopravnipodnik.typMesta
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.urovenMesta
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.dialogState
import cz.jaro.dopravnipodniky.shared.LongPressButton
import cz.jaro.dopravnipodniky.shared.Menic
import cz.jaro.dopravnipodniky.shared.SharedViewModel
import cz.jaro.dopravnipodniky.shared.composeString
import cz.jaro.dopravnipodniky.shared.formatovat
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz
import cz.jaro.dopravnipodniky.shared.jednotky.asString
import cz.jaro.dopravnipodniky.shared.jednotky.penez
import cz.jaro.dopravnipodniky.shared.kremze
import cz.jaro.dopravnipodniky.shared.minimumInvestice
import cz.jaro.dopravnipodniky.shared.minutes
import cz.jaro.dopravnipodniky.shared.prodejniCenaCloveka
import cz.jaro.dopravnipodniky.shared.vecne
import cz.jaro.dopravnipodniky.snackbarHostState
import cz.jaro.dopravnipodniky.ui.destinations.NovyDopravniPodnikScreenDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random
import kotlin.reflect.KClass

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
    var loading by rememberSaveable { mutableStateOf(null as (@FloatRange(0.0, 3.0) Float)?) }
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
                    dialogState.show(
                        confirmButton = {
                            val ctx = LocalContext.current
                            val scope = rememberCoroutineScope()
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
                                            snackbarHostState.showSnackbar(
                                                message = ctx.getString(R.string.nigdo_nebyl_ochoten),
                                                duration = SnackbarDuration.Indefinite
                                            )
                                        }

                                        menic.zmenitPrachy {
                                            it - i / 10
                                        }

                                        dialogState.hideTopMost()
                                        return@TextButton
                                    }

                                    menic.zmenitPrachy {
                                        it - i
                                    }

                                    dialogState.hideTopMost()
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
                                            navigate(NovyDopravniPodnikScreenDestination)
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

        val res = LocalContext.current.resources
        val scope = rememberCoroutineScope()

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
                    if (loading!! != 3F) {
                        Text("${loading!!.toInt() + 1}: ${((loading!! % 1) * 100).formatovat(2).composeString()} %")
                        LinearProgressIndicator(
                            progress = loading!! % 1,
                            Modifier.fillMaxWidth()
                        )
                    } else {
                        Text("Hotovo!")
                        LinearProgressIndicator(Modifier.fillMaxWidth())
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
                            .animateItemPlacement()
                            .animateContentSize()
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
                                    Icon(if (Random.nextFloat() < .001F) Icons.Default.ShoppingCartCheckout else Icons.AutoMirrored.Filled.Login, null)
                                }
                            },
                        )
                        AnimatedVisibility(expanded) {
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                if (tentoDP.info.id != dp.info.id) Text(
                                    text = stringResource(R.string.nevyzvednuto, dp.info.nevyzvednuto.asString()),
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(all = 8.dp),
                                )
                                if (tentoDP.info.id != dp.info.id) Text(
                                    text = stringResource(
                                        R.string.doba_od_posledniho_otevreni,
                                        dp.info.dobaOdPoslednihoHrani.minutes.formatovat(0).composeString()
                                    ),
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(all = 8.dp),
                                )
                                Text(
                                    text = stringResource(
                                        R.string.zisk,
                                        dp.info.zisk.asString(),
                                    ),
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(all = 8.dp),
                                )
                                Text(
                                    text = stringResource(
                                        R.string.typ_mesta,
                                        dp.typMesta.composeString(),
                                    ),
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(all = 8.dp),
                                )
                                Text(
                                    text = stringResource(
                                        R.string.uroven_mesta,
                                        dp.urovenMesta.formatovat().composeString(),
                                    ),
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(all = 8.dp),
                                )
                                Text(
                                    text = "Potenciál města: ${dp.ulice.sumOf { it.potencial }.formatovat(0).composeString()}",
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(all = 8.dp),
                                )
                                Text(
                                    text = stringResource(
                                        R.string.jedoucich_vozidel,
                                        dp.busy.count {
                                            it.linka != null && (it.typBusu.trakce !is Trakce.Trolejbus || dp.linka(it.linka)
                                                .ulice(dp).jsouVsechnyZatrolejovane())
                                        },
                                        dp.busy.count(),
                                    ),
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(all = 8.dp),
                                )
                                Text(
                                    text = stringResource(
                                        R.string.rozloha,
                                        dp.plocha.value.formatovat().composeString(),
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(all = 8.dp),
                                )
                                Text(
                                    text = stringResource(
                                        R.string.pocet_cloveku,
                                        dp.cloveci.formatovat().composeString(),
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(all = 8.dp),
                                )
                                Text(
                                    text = stringResource(
                                        R.string.pocet_ulic,
                                        dp.ulice.count().formatovat().composeString(),
                                        dp.ulice.count { it.maTrolej }.formatovat().composeString(),
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(all = 8.dp),
                                )
                                Text(
                                    text = stringResource(
                                        R.string.pocet_domu,
                                        dp.ulice.sumOf { it.baraky.size }.formatovat().composeString(),
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(all = 8.dp),
                                )
                                Text(
                                    text = stringResource(
                                        R.string.pocet_zastavek,
                                        dp.ulice.count { it.maZastavku }.formatovat().composeString(),
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(all = 8.dp),
                                )

                                val oddelavaciCena = prodejniCenaCloveka * dp.cloveci + dp.busy.sumOf { it.prodejniCena.value }.penez

                                var vybraneJizdne by rememberSaveable { mutableIntStateOf(0) }

                                LaunchedEffect(dp.info.jizdne) {
                                    vybraneJizdne = dp.info.jizdne.value.toInt()
                                }

                                Row(
                                    Modifier.fillMaxWidth(),
                                ) {
                                    if (tentoDP.info.id != dp.info.id) LongPressButton(
                                        Modifier
                                            .padding(all = 8.dp),
                                        onClick = {
                                            dialogState.show(
                                                confirmButton = {
                                                    TextButton(
                                                        onClick = {
                                                            val actualCena = oddelavaciCena * Random.nextDouble(.5, 1.5)
                                                            MainScope().launch {

                                                                menic.zmenitOstatniDopravniPodniky {
                                                                    removeAt(indexOfFirst { it.info.id == dp.info.id })
                                                                }

                                                                menic.zmenitPrachy {
                                                                    it + actualCena
                                                                }

                                                                dialogState.hideTopMost()

                                                                with(res) {
                                                                    if (dp.info.jmenoMesta == vecne) snackbarHostState.showSnackbar(
                                                                        message = getString(R.string.vecne_neni_vecne),
                                                                        duration = SnackbarDuration.Long,
                                                                        withDismissAction = true,
                                                                    )
                                                                    snackbarHostState.showSnackbar(
                                                                        message = getString(
                                                                            R.string.prodali_jste_dp,
                                                                            dp.info.jmenoMesta,
                                                                            getString(
                                                                                R.string.kc,
                                                                                actualCena.value.formatovat(0).asString()
                                                                            )
                                                                        ),
                                                                        duration = SnackbarDuration.Indefinite,
                                                                        withDismissAction = true,
                                                                    )
                                                                }
                                                            }
                                                        }
                                                    ) {
                                                        Text(stringResource(R.string.prodat_dp))
                                                    }
                                                },
                                                dismissButton = {
                                                    TextButton(
                                                        onClick = {
                                                            dialogState.hideTopMost()
                                                        }
                                                    ) {
                                                        Text(stringResource(R.string.zrusit))
                                                    }
                                                },
                                                title = {
                                                    Text(stringResource(R.string.prodat_dp))
                                                },
                                                content = {
                                                    Text(stringResource(R.string.za_prodej_dp_dostanete, oddelavaciCena.asString()))
                                                },
                                            )
                                        },
                                        onLongPress = {
                                            scope.launch {
                                                if (dp.info.jmenoMesta == kremze)
                                                    menic.zmenitOstatniDopravniPodniky {
                                                        val i = indexOfFirst { it.info.id == dp.info.id }
                                                        this[i] = this[i].copy(
                                                            info = this[i].info.copy(
                                                                jmenoMesta = vecne
                                                            )
                                                        )
                                                    }
                                            }
                                        },
                                    ) {
                                        Text(
                                            text = stringResource(R.string.prodat)
                                        )
                                    }
                                    Spacer(modifier = Modifier.weight(1F))
                                    OutlinedButton(
                                        onClick = {
                                            dialogState.show(
                                                confirmButton = {
                                                    TextButton(
                                                        onClick = {
                                                            scope.launch {
                                                                menic.zmenitOstatniDopravniPodniky {
                                                                    val i = indexOfFirst { it.info.id == dp.info.id }
                                                                    this[i] = this[i].copy(
                                                                        info = this[i].info.copy(
                                                                            jizdne = vybraneJizdne.penez,
                                                                        )
                                                                    )
                                                                }
                                                            }
                                                            dialogState.hideTopMost()
                                                        }
                                                    ) {
                                                        Text(stringResource(R.string.potvrdit))
                                                    }
                                                },
                                                dismissButton = {
                                                    TextButton(
                                                        onClick = {

                                                        }
                                                    ) {
                                                        Text(stringResource(R.string.pruzkum_mineni))
                                                    }
                                                },
                                                title = {
                                                    Text(stringResource(R.string.vyberte_linku))
                                                },
                                                content = {
                                                    AndroidView(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        factory = { context ->
                                                            NumberPicker(context).apply {
                                                                minValue = 0
                                                                maxValue = 100
                                                                setOnValueChangedListener { _, _, noveJizdne ->
                                                                    vybraneJizdne = noveJizdne
                                                                }
                                                            }
                                                        },
                                                        update = {
                                                            it.value = vybraneJizdne
                                                        },
                                                    )
                                                },
                                            )
                                        },
                                        Modifier
                                            .padding(all = 8.dp),
                                    ) {
                                        Text(
                                            text = stringResource(R.string.zmenit_jizdne)
                                        )
                                    }
                                }
                            }
                        }
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}