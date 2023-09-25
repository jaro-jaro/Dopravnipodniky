package cz.jaro.dopravnipodniky.ui.linky

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FormatLineSpacing
import androidx.compose.material.icons.filled.GridGoldenratio
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Bus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.busy
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ulice
import cz.jaro.dopravnipodniky.dialogState
import cz.jaro.dopravnipodniky.shared.SharedViewModel
import cz.jaro.dopravnipodniky.shared.Smer
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.cenaTroleje
import cz.jaro.dopravnipodniky.shared.delkaUlice
import cz.jaro.dopravnipodniky.shared.je
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz
import cz.jaro.dopravnipodniky.shared.jednotky.asString
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.snackbarHostState
import cz.jaro.dopravnipodniky.ui.destinations.VytvareniLinkyScreenDestination
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt
import kotlin.reflect.KFunction1


@Composable
@Destination
fun LinkyScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = koinViewModel<SharedViewModel>()

    val linky by viewModel.linky.collectAsStateWithLifecycle()
    val ulicove by viewModel.ulice.collectAsStateWithLifecycle()
    val prachy by viewModel.prachy.collectAsStateWithLifecycle()
    val busy by viewModel.busy.collectAsStateWithLifecycle()
    val tutorial by viewModel.tutorial.collectAsStateWithLifecycle()

    if (
        linky != null &&
        ulicove != null &&
        prachy != null &&
        busy != null &&
        tutorial != null
    ) LinkyScreen(
        linky = linky!!,
        ulicove = ulicove!!,
        busy = busy!!,
        tutorial = tutorial!!,
        prachy = prachy!!,
        zmenitPrachy = viewModel::zmenitPrachy,
        zmenitLinky = viewModel::zmenitLinky,
        zmenitBusy = viewModel::zmenitBusy,
        zmenitUlice = viewModel::zmenitUlice,
        navigate = navigator::navigate,
        navigateBack = navigator::navigateUp
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LinkyScreen(
    linky: List<Linka>,
    ulicove: List<Ulice>,
    busy: List<Bus>,
    tutorial: StavTutorialu,
    prachy: Peniz,
    zmenitPrachy: KFunction1<(Peniz) -> Peniz, Unit>,
    zmenitLinky: (MutableList<Linka>.() -> Unit) -> Unit,
    zmenitBusy: (MutableList<Bus>.() -> Unit) -> Unit,
    zmenitUlice: (MutableList<Ulice>.() -> Unit) -> Unit,
    navigate: (Direction) -> Unit,
    navigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.linky))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigateBack()
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
                    Text(stringResource(R.string.nova_linka))
                },
                icon = {
                    Icon(Icons.Default.Add, null)
                },
                onClick = {
                    navigate(VytvareniLinkyScreenDestination)
                },
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { paddingValues ->
        LazyColumn(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (linky.isEmpty()) item {
                Text(stringResource(R.string.zadna_linka), Modifier.padding(8.dp))
            }
            items(linky, key = { it.cislo }) { linka ->
                Column(
                    Modifier
                        .animateItemPlacement()
                        .animateContentSize(),
                ) {
                    ListItem(
                        headlineContent = {
                            Text(linka.cislo)
                        },
                        trailingContent = {
                            val ctx = LocalContext.current
                            val scope = rememberCoroutineScope()
                            if (
                                !(tutorial je StavTutorialu.Tutorialujeme.Uvod) &&
                                !(tutorial je StavTutorialu.Tutorialujeme.Linky) &&
                                !(tutorial je StavTutorialu.Tutorialujeme.Zastavky) &&
                                !(tutorial je StavTutorialu.Tutorialujeme.Garaz) &&
                                !(tutorial je StavTutorialu.Tutorialujeme.Obchod)
                            ) Row {
                                IconButton(
                                    onClick = {
                                        val ulicNaLince = linka.ulice.size
                                        val delkaLinky = ulicNaLince * (delkaUlice + sirkaUlice)
                                        val pocetBusu = linka.busy(busy).size

                                        if (pocetBusu == 0) {
                                            scope.launch {
                                                snackbarHostState.showSnackbar(
                                                    ctx.getString(R.string.pridejte_vozidla_na_linku),
                                                    duration = SnackbarDuration.Short,
                                                    withDismissAction = true,
                                                )
                                            }
                                            return@IconButton
                                        }

                                        val odstupy = (2 * delkaLinky) / pocetBusu.toFloat()

                                        zmenitBusy {
                                            linka.busy(busy).forEachIndexed { i, bus ->
                                                val poziceOdZacatkuLinky = odstupy * i

                                                val jeDruhySmer = poziceOdZacatkuLinky / delkaLinky >= 1
                                                val indexUlice = (poziceOdZacatkuLinky % delkaLinky) / (delkaUlice + sirkaUlice)
                                                val poziceVUlici = poziceOdZacatkuLinky % (delkaUlice + sirkaUlice)

                                                val index = indexOfFirst { it.id == bus.id }

                                                this[index] = this[index].copy(
                                                    smerNaLince = if (!jeDruhySmer) Smer.Pozitivni else Smer.Negativni,
                                                    poziceNaLince = indexUlice.roundToInt() % linka.ulice.size,
                                                    poziceVUlici = poziceVUlici,
                                                )
                                            }
                                        }
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                ctx.getString(R.string.uspesne_rozmisteno),
                                                duration = SnackbarDuration.Short,
                                                withDismissAction = true,
                                            )
                                        }
                                        dialogState.hideTopMost()
                                    }
                                ) {
                                    Icon(Icons.Default.FormatLineSpacing, null)
                                }
                                IconButton(
                                    onClick = {
                                        dialogState.show(
                                            confirmButton = {
                                                TextButton(
                                                    onClick = {
                                                        if (prachy < cenaTroleje * linka.ulice(ulicove).count { !it.maTrolej }) {
                                                            scope.launch {
                                                                snackbarHostState.showSnackbar(
                                                                    ctx.getString(R.string.malo_penez),
                                                                    duration = SnackbarDuration.Short,
                                                                    withDismissAction = true,
                                                                )
                                                            }
                                                            dialogState.hideTopMost()
                                                            return@TextButton
                                                        }

                                                        zmenitPrachy {
                                                            it - cenaTroleje * linka.ulice(ulicove).count { !it.maTrolej }
                                                        }

                                                        zmenitUlice {
                                                            linka.ulice(ulicove).forEach { ulice ->
                                                                if (!ulice.maTrolej) {
                                                                    val i = this.indexOfFirst { it.id == ulice.id }
                                                                    this[i] = this[i].copy(
                                                                        maTrolej = true
                                                                    )
                                                                }
                                                            }
                                                        }
                                                        scope.launch {
                                                            snackbarHostState.showSnackbar(
                                                                ctx.getString(R.string.uspesne_pridany_troleje),
                                                                duration = SnackbarDuration.Short,
                                                                withDismissAction = true,
                                                            )
                                                        }
                                                        dialogState.hideTopMost()
                                                    }
                                                ) {
                                                    Text(stringResource(android.R.string.ok))
                                                }
                                            },
                                            text = {
                                                Text(
                                                    stringResource(
                                                        R.string.pridat_troleje_nebo_zastavky_linka_dialog,
                                                        (cenaTroleje * linka.ulice(ulicove).count { !it.maTrolej }).asString(),
                                                        stringResource(R.string.troleje)
                                                    )
                                                )
                                            },
                                            title = {
                                                Text(stringResource(R.string.pridat_troleje_linka))
                                            },
                                        )
                                    }
                                ) {
                                    Icon(Icons.Default.GridGoldenratio, null)
                                }
                                IconButton(
                                    onClick = {
                                        zmenitBusy {
                                            forEachIndexed { i, it ->
                                                if (it.linka == linka.id) this[i] = it.copy(
                                                    linka = null
                                                )
                                            }
                                        }
                                        zmenitLinky {
                                            remove(linka)
                                        }
                                    }
                                ) {
                                    Icon(Icons.Default.Delete, null)
                                }
                            }
                        },
                        leadingContent = {
                            Icon(
                                Icons.Default.Timeline,
                                stringResource(R.string.ikonka_busiku),
                                Modifier
                                    .width(40.dp)
                                    .height(40.dp),
                                tint = linka.barvicka.barva,
                            )
                        },
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

private operator fun Dp.rem(other: Dp) = Dp(value % other.value)
