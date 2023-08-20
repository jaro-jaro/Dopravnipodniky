package cz.jaro.dopravnipodniky

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
import cz.jaro.dopravnipodniky.classes.DopravniPodnik
import cz.jaro.dopravnipodniky.classes.jsouVsechnyZatrolejovane
import cz.jaro.dopravnipodniky.classes.ulice
import cz.jaro.dopravnipodniky.destinations.ObchodScreenDestination
import cz.jaro.dopravnipodniky.jednotky.asString
import cz.jaro.dopravnipodniky.other.Trakce
import cz.jaro.dopravnipodniky.other.ikonka
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination
fun GarazScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = koinViewModel<MainViewModel>()

    val dp by viewModel.dp.collectAsStateWithLifecycle()
    val vse by viewModel.vse.collectAsStateWithLifecycle()

    if (dp != null && vse != null) GarazScreen(
        dp = dp!!,
        vse = vse!!,
        upravitDp = viewModel::zmenitDp,
        upravitVse = viewModel::zmenitVse,
        navigatate = navigator::navigate,
        navigatateBack = navigator::navigateUp,
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GarazScreen(
    dp: DopravniPodnik,
    vse: Vse,
    upravitDp: ((DopravniPodnik) -> DopravniPodnik) -> Unit,
    upravitVse: ((Vse) -> Vse) -> Unit,
    navigatate: (Direction) -> Unit,
    navigatateBack: () -> Unit,
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
            items(dp.busy.sortedBy { it.evCislo }, key = { it.evCislo }) { bus ->
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
                                if (bus.linka != null) append(stringResource(R.string.linka_tohle, bus.linka))
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
                                colorFilter = ColorFilter.tint(color = vse.tema.barva),
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
                                val linky = when (bus.typBusu.trakce) {
                                    is Trakce.Trolejbus -> dp.linky.filter { it.ulice(dp).jsouVsechnyZatrolejovane() }
                                    else -> dp.linky
                                }
                                LaunchedEffect(linky) {
                                    if (linky.isEmpty()) {
                                        Toast.makeText(ctx, R.string.nejprve_si_vytvorte_linku, Toast.LENGTH_SHORT).show()
                                    }
                                }

                                if (linky.isNotEmpty()) AlertDialog(
                                    onDismissRequest = {
                                        vybratLinku = false
                                    },
                                    confirmButton = { },
                                    title = {
                                        Text(stringResource(R.string.vyberte_linku))
                                    },
                                    text = {
                                        LazyColumn(
                                            Modifier.fillMaxWidth()
                                        ) LazyColumn2@{
                                            items(linky) { linka ->
                                                ListItem(
                                                    headlineContent = {
                                                        Text(linka.cislo.toString())
                                                    },
                                                    Modifier.clickable {
                                                        upravitDp { dopravniPodnik ->
                                                            dopravniPodnik.copy(
                                                                busy = dopravniPodnik.busy.mutate {
                                                                    this[indexOfFirst { it.id == bus.id }] = bus.copy(
                                                                        linka = linka.id
                                                                    )
                                                                }
                                                            )
                                                        }
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
                                        upravitDp { dp ->
                                            dp.copy(
                                                busy = dp.busy.filterNot { it.id == bus.id }
                                            )
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