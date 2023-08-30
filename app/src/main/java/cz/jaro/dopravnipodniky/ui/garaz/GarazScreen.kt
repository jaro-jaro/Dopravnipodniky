package cz.jaro.dopravnipodniky.ui.garaz

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationSearching
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Bus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DPInfo
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Trakce
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ikonka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.jsouVsechnyZatrolejovane
import cz.jaro.dopravnipodniky.data.dopravnipodnik.linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ulice
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.shared.SharedViewModel
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.composeString
import cz.jaro.dopravnipodniky.shared.formatovat
import cz.jaro.dopravnipodniky.shared.je
import cz.jaro.dopravnipodniky.shared.jednotky.asString
import cz.jaro.dopravnipodniky.shared.replaceBy
import cz.jaro.dopravnipodniky.ui.destinations.ObchodScreenDestination
import org.koin.androidx.compose.koinViewModel
import kotlin.reflect.KClass

@Composable
@Destination
fun GarazScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = koinViewModel<SharedViewModel>()

    LaunchedEffect(Unit) {
        viewModel.zmenitTutorial {
            if (it je StavTutorialu.Tutorialujeme.Obchod)
                StavTutorialu.Tutorialujeme.Vypraveni
            else it
        }
    }

    val dpInfo by viewModel.dpInfo.collectAsStateWithLifecycle()
    val ulicove by viewModel.ulice.collectAsStateWithLifecycle()
    val linky by viewModel.linky.collectAsStateWithLifecycle()
    val busy by viewModel.busy.collectAsStateWithLifecycle()

    if (
        dpInfo != null &&
        ulicove != null &&
        linky != null &&
        busy != null
    )  GarazScreen(
        dpInfo = dpInfo!!,
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
fun GarazScreen(
    dpInfo: DPInfo,
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
                    Text(stringResource(R.string.garaz))
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
                    Text(stringResource(R.string.garaz_koupit_vozidlo))
                },
                icon = {
                    Icon(Icons.Default.Add, null)
                },
                onClick = {
                    navigatate(ObchodScreenDestination)
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { paddingValues ->
        var otevreno by rememberSaveable { mutableStateOf(null as Int?) }
        LazyColumn(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (busy.isEmpty()) item {
                Text(stringResource(R.string.zadny_bus), Modifier.padding(8.dp))
            }
            items(busy.sortedBy { it.evCislo }, key = { it.evCislo }) { bus ->
                val expanded = otevreno == bus.evCislo
                Column(
                    Modifier
                        .animateItemPlacement()
                        .animateContentSize()
                        .clickable {
                            otevreno = if (expanded) null else bus.evCislo
                        },
                ) {
                    ListItem(
                        headlineContent = {
                            Text(buildString {
                                append(stringResource(R.string.ev_c, bus.evCislo))
                                append(" ")
                                if (bus.linka != null) append(stringResource(R.string.linka_tohle, linky.linka(bus.linka).cislo))
                            })
                        },
                        trailingContent = {
                            Icon(Icons.Default.LocationSearching, null)
                        },
                        leadingContent = {
                            Image(
                                painterResource(bus.typBusu.trakce.ikonka),
                                stringResource(R.string.ikonka_busiku),
                                Modifier
                                    .width(40.dp)
                                    .height(40.dp),
                                colorFilter = ColorFilter.tint(color = dpInfo.tema.barva),
                            )
                        },
                    )
                    AnimatedVisibility(expanded) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = stringResource(bus.typBusu.trakce.jmeno),
                                Modifier
                                    .fillMaxWidth()
                                    .padding(all = 8.dp),
                            )
                            Text(
                                text = bus.typBusu.model,
                                Modifier
                                    .fillMaxWidth()
                                    .padding(all = 8.dp),
                            )
                            Text(
                                text = stringResource(R.string.ponicenost, bus.ponicenost.times(100).formatovat().composeString()),
                                Modifier
                                    .fillMaxWidth()
                                    .padding(all = 8.dp),
                            )
                            Text(
                                text = stringResource(R.string.naklady, bus.naklady.asString()),
                                Modifier
                                    .fillMaxWidth()
                                    .padding(all = 8.dp),
                            )
                            var vybratLinku by rememberSaveable { mutableStateOf(false) }
                            val ctx = LocalContext.current

                            if (vybratLinku) {
                                val pouzitelneLinky = when (bus.typBusu.trakce) {
                                    is Trakce.Trolejbus -> linky.filter { it.ulice(ulicove).jsouVsechnyZatrolejovane() }
                                    else -> linky
                                }
                                LaunchedEffect(pouzitelneLinky) {
                                    if (pouzitelneLinky.isEmpty()) {
                                        Toast.makeText(ctx, R.string.nejprve_si_vytvorte_linku, Toast.LENGTH_SHORT).show()
                                    }
                                }

                                if (pouzitelneLinky.isNotEmpty()) AlertDialog(
                                    onDismissRequest = {
                                        vybratLinku = false
                                    },
                                    confirmButton = { },
                                    dismissButton = {
                                        TextButton(
                                            onClick = {
                                                zmenitBusy {
                                                    replaceBy(bus.copy(linka = null)) { it.id }
                                                }
                                                vybratLinku = false
                                            }
                                        ) {
                                            Text(stringResource(R.string.odebrat_bus_z_linek))
                                        }
                                    },
                                    title = {
                                        Text(stringResource(R.string.vyberte_linku))
                                    },
                                    text = {
                                        LazyColumn(
                                            Modifier.fillMaxWidth()
                                        ) LazyColumn2@{
                                            items(pouzitelneLinky) { linka ->
                                                ListItem(
                                                    headlineContent = {
                                                        Text(linka.cislo.toString())
                                                    },
                                                    Modifier.clickable {
                                                        zmenitBusy {
                                                            replaceBy(bus.copy(linka = linka.id)) { it.id }
                                                        }
                                                        dosahni(Dosahlost.BusNaLince::class)
                                                        vybratLinku = false
                                                    },
                                                )
                                            }
                                        }
                                    },
                                )
                            }

                            Row(
                                Modifier.fillMaxWidth(),
                            ) {
                                Button(
                                    onClick = {
                                        vybratLinku = true
                                    },
                                    Modifier
                                        .padding(all = 8.dp),
                                ) {
                                    Text(
                                        text = stringResource(if (bus.linka == null) R.string.vypravit else R.string.zmenit_linku)
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1F))
                                OutlinedButton(
                                    onClick = {
                                        zmenitUlice {

                                            var cloveci = bus.cloveci
                                            val noveUlice = map { ulice ->
                                                var cloveciVUlici = ulice.cloveci
                                                while (cloveciVUlici <= ulice.kapacita && cloveci > 0) {

                                                    cloveci--
                                                    cloveciVUlici++
                                                }

                                                ulice.copy(
                                                    cloveci = cloveciVUlici
                                                )
                                            }

                                            clear()
                                            addAll(noveUlice)
                                        }
                                        zmenitBusy {
                                            val i = indexOfFirst { it.id == bus.id }
                                            removeAt(i)
                                        }
                                    },
                                    Modifier
                                        .padding(all = 8.dp),
                                ) {
                                    Text(
                                        text = stringResource(R.string.prodat_za, bus.prodejniCena.asString())
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