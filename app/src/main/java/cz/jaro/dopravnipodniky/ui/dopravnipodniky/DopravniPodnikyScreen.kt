package cz.jaro.dopravnipodniky.ui.dopravnipodniky

import android.widget.NumberPicker
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Bus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DPInfo
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Trakce
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
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
import cz.jaro.dopravnipodniky.shared.DPID
import cz.jaro.dopravnipodniky.shared.SharedViewModel
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.composeString
import cz.jaro.dopravnipodniky.shared.formatovat
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz
import cz.jaro.dopravnipodniky.shared.jednotky.asString
import cz.jaro.dopravnipodniky.shared.jednotky.penez
import cz.jaro.dopravnipodniky.shared.minutes
import cz.jaro.dopravnipodniky.shared.prodejniCenaCloveka
import cz.jaro.dopravnipodniky.snackbarHostState
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

    val prachy by viewModel.prachy.collectAsStateWithLifecycle()
    val dpInfo by viewModel.dpInfo.collectAsStateWithLifecycle()
    val ulicove by viewModel.ulice.collectAsStateWithLifecycle()
    val linky by viewModel.linky.collectAsStateWithLifecycle()
    val busy by viewModel.busy.collectAsStateWithLifecycle()
    val tutorial by viewModel.tutorial.collectAsStateWithLifecycle()
    val podniky by viewModel.podniky.collectAsStateWithLifecycle()

    if (
        prachy != null &&
        dpInfo != null &&
        ulicove != null &&
        podniky != null &&
        tutorial != null &&
        linky != null &&
        busy != null
    ) DopravniPodnikyScreen(
        prachy = prachy!!,
        zmenitPodniky = viewModel::zmenitOstatniDopravnikyPodniky,
        zmenitDP = viewModel::zmenitDopravnikyPodnik,
        zmenitPrachy = viewModel::zmenitPrachy,
        tutorial = tutorial!!,
        dpInfo = dpInfo!!,
        podniky = podniky!!,
        ulicove = ulicove!!,
        linky = linky!!,
        busy = busy!!,
        zmenitBusy = viewModel::zmenitBusy,
        zmenitUlice = viewModel::zmenitUlice,
        navigatate = navigator::navigate,
        navigatateBack = navigator::navigateUp,
        dosahni = viewModel.dosahni
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DopravniPodnikyScreen(
    podniky: List<DopravniPodnik>,
    zmenitPodniky: (suspend MutableList<DopravniPodnik>.() -> Unit) -> Unit,
    zmenitDP: suspend (DPID) -> Unit,
    zmenitPrachy: ((Peniz) -> Peniz) -> Unit,
    dpInfo: DPInfo,
    prachy: Peniz,
    tutorial: StavTutorialu,
    ulicove: List<Ulice>,
    linky: List<Linka>,
    busy: List<Bus>,
    zmenitBusy: (MutableList<Bus>.() -> Unit) -> Unit,
    zmenitUlice: (MutableList<Ulice>.() -> Unit) -> Unit,
    navigatate: (Direction) -> Unit,
    navigatateBack: () -> Unit,
    dosahni: (KClass<out Dosahlost>) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.dopravni_podniky))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigatateBack()
                        }
                    ) {
                        Icon(Icons.Default.ArrowBack, stringResource(R.string.zpet))
                    }
                },
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(stringResource(R.string.najit_nove_mesto))
                },
                icon = {
                    Icon(Icons.Default.Search, null)
                },
                onClick = {
//                    navigatate(ObchodScreenDestination) TODO novej dp
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { paddingValues ->
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
                    text = prachy.asString(),
                    Modifier
                        .weight(1F)
                        .padding(all = 16.dp),
                    textAlign = TextAlign.Start,
                )
            }
            LazyColumn(
                Modifier.weight(1F)
            ) {
                items(podniky, key = { it.info.id }) { dp ->
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
                                if (dpInfo.id != dp.info.id) IconButton(
                                    onClick = {
                                        CoroutineScope(Dispatchers.Main).launch {
                                            zmenitDP(dp.info.id)
                                            navigatateBack()
                                        }
                                    }
                                ) {
                                    Icon(if (Random.nextFloat() < .001F) Icons.Default.ShoppingCartCheckout else Icons.Default.Login, null)
                                }
                            },
                        )
                        AnimatedVisibility(expanded) {
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                if (dpInfo.id != dp.info.id) Text(
                                    text = stringResource(R.string.nevyzvednuto, dp.info.nevyzvednuto.asString()),
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(all = 8.dp),
                                )
                                if (dpInfo.id != dp.info.id) Text(
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
                                            it.linka != null && (it.typBusu.trakce !is Trakce.Trolejbus || dp.linky.linka(it.linka)
                                                .ulice(dp.ulice).jsouVsechnyZatrolejovane())
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

                                var novejDp by rememberSaveable { mutableStateOf(false) }
                                var novejDPLogaritmicky by rememberSaveable { mutableStateOf(false) }
                                var oddelatDP by rememberSaveable { mutableStateOf(false) }
                                var smenitJizdne by rememberSaveable { mutableStateOf(false) }
                                val res = LocalContext.current.resources
                                val scope = rememberCoroutineScope()

                                if (novejDp) AlertDialog(
                                    onDismissRequest = {
                                        novejDp = false
                                    },
                                    confirmButton = { },
                                    dismissButton = {
                                        TextButton(
                                            onClick = {
                                                novejDp = false
                                            }
                                        ) {
                                            Text(stringResource(R.string.odebrat_bus_z_linek))
                                        }
                                    },
                                    title = {
                                        Text(stringResource(R.string.vyberte_linku))
                                    },
                                    text = {

                                    },
                                )
                                if (novejDPLogaritmicky) AlertDialog(
                                    onDismissRequest = {
                                        novejDPLogaritmicky = false
                                    },
                                    confirmButton = { },
                                    dismissButton = {
                                        TextButton(
                                            onClick = {
                                                novejDPLogaritmicky = false
                                            }
                                        ) {
                                            Text(stringResource(R.string.odebrat_bus_z_linek))
                                        }
                                    },
                                    title = {
                                        Text(stringResource(R.string.vyberte_linku))
                                    },
                                    text = {

                                    },
                                )

                                val oddelavaciCena = prodejniCenaCloveka * dp.cloveci + dp.busy.sumOf { it.prodejniCena.value }.penez

                                if (oddelatDP) AlertDialog(
                                    onDismissRequest = {
                                        oddelatDP = false
                                    },
                                    confirmButton = {
                                        TextButton(
                                            onClick = {
                                                val actualCena = oddelavaciCena * Random.nextDouble(.5, 1.5)

                                                zmenitPodniky {
                                                    removeAt(indexOfFirst { it.info.id == dp.info.id })
                                                }

                                                zmenitPrachy {
                                                    it + actualCena
                                                }

                                                MainScope().launch {
                                                    with(res) {
                                                        snackbarHostState.showSnackbar(
                                                            message = getString(
                                                                R.string.prodali_jste_dp,
                                                                dp.info.jmenoMesta,
                                                                getString(R.string.kc, actualCena.value.formatovat(0).asString())
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
                                                oddelatDP = false
                                            }
                                        ) {
                                            Text(stringResource(R.string.zrusit))
                                        }
                                    },
                                    title = {
                                        Text(stringResource(R.string.prodat_dp))
                                    },
                                    text = {
                                        Text(stringResource(R.string.za_prodej_dp_dostanete, oddelavaciCena.asString()))
                                    },
                                )
                                var vybraneJizdne by rememberSaveable { mutableIntStateOf(0) }

                                LaunchedEffect(dp.info.jizdne) {
                                    vybraneJizdne = dp.info.jizdne.value.toInt()
                                }

                                if (smenitJizdne) AlertDialog(
                                    onDismissRequest = {
                                        smenitJizdne = false
                                    },
                                    confirmButton = {
                                        TextButton(
                                            onClick = {
                                                zmenitPodniky {
                                                    val i = indexOfFirst { it.info.id == dp.info.id }
                                                    this[i] = this[i].copy(
                                                        info = this[i].info.copy(
                                                            jizdne = vybraneJizdne.penez,
                                                        )
                                                    )
                                                }
                                                smenitJizdne = false
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
                                    text = {
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
                                            }
                                        )
                                    },
                                )


                                Row(
                                    Modifier.fillMaxWidth(),
                                ) {
                                    if (dpInfo.id != dp.info.id) Button(
                                        onClick = {
                                            oddelatDP = true
                                        },
                                        Modifier
                                            .padding(all = 8.dp),
                                    ) {
                                        Text(
                                            text = stringResource(R.string.prodat)
                                        )
                                    }
                                    Spacer(modifier = Modifier.weight(1F))
                                    OutlinedButton(
                                        onClick = {
                                            smenitJizdne = true
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