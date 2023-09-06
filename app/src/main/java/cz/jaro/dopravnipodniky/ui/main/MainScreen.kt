package cz.jaro.dopravnipodniky.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditRoad
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toOffset
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.Nastaveni
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Bus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DPInfo
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Zastavka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.maZastavku
import cz.jaro.dopravnipodniky.data.dopravnipodnik.stred
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.velikostMesta
import cz.jaro.dopravnipodniky.data.dopravnipodnik.zasebevrazdujZastavku
import cz.jaro.dopravnipodniky.shared.SharedViewModel
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.UliceID
import cz.jaro.dopravnipodniky.shared.cenaTroleje
import cz.jaro.dopravnipodniky.shared.cenaZastavky
import cz.jaro.dopravnipodniky.shared.je
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz
import cz.jaro.dopravnipodniky.shared.jednotky.asString
import cz.jaro.dopravnipodniky.shared.jednotky.minus
import cz.jaro.dopravnipodniky.shared.jednotky.penez
import cz.jaro.dopravnipodniky.shared.jednotky.plus
import cz.jaro.dopravnipodniky.shared.jednotky.to
import cz.jaro.dopravnipodniky.shared.jednotky.toDpSUlicema
import cz.jaro.dopravnipodniky.shared.jednotky.toPx
import cz.jaro.dopravnipodniky.shared.maximalniOddaleni
import cz.jaro.dopravnipodniky.shared.oddalenyRezim
import cz.jaro.dopravnipodniky.shared.pocatecniPriblizeni
import cz.jaro.dopravnipodniky.shared.replaceBy
import cz.jaro.dopravnipodniky.shared.ulicovyBlok
import cz.jaro.dopravnipodniky.ui.destinations.GarazScreenDestination
import cz.jaro.dopravnipodniky.ui.destinations.LinkyScreenDestination
import cz.jaro.dopravnipodniky.ui.malovani.Mesto
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.math.pow

@Composable
@Destination(start = true)
fun MainScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = koinViewModel<SharedViewModel>()

    LaunchedEffect(Unit) {
        viewModel.zmenitTutorial {
            if (it je StavTutorialu.Tutorialujeme.Linky)
                StavTutorialu.Tutorialujeme.Zastavky
            else it
        }
    }
    val dpInfo by viewModel.dpInfo.collectAsStateWithLifecycle()
    val nastaveni by viewModel.nastaveni.collectAsStateWithLifecycle()
    val tutorial by viewModel.tutorial.collectAsStateWithLifecycle()
    val ulicove by viewModel.ulice.collectAsStateWithLifecycle()
    val linky by viewModel.linky.collectAsStateWithLifecycle()
    val busy by viewModel.busy.collectAsStateWithLifecycle()
    val prachy by viewModel.prachy.collectAsStateWithLifecycle()

    if (
        dpInfo != null &&
        nastaveni != null &&
        tutorial != null &&
        ulicove != null &&
        linky != null &&
        busy != null &&
        prachy != null
    ) MainScreen(
        dpInfo = dpInfo!!,
        nastaveni = nastaveni!!,
        tutorial = tutorial!!,
        ulicove = ulicove!!,
        linky = linky!!,
        busy = busy!!,
        prachy = prachy!!,
        zmenitTutorial = viewModel::zmenitTutorial,
        zmenitPrachy = viewModel::zmenitPrachy,
        zmenitNastaveni = viewModel::zmenitNastaveni,
        zmenitUlice = viewModel::zmenitUlice,
        navigatate = navigator::navigate,
    )
}

var DEBUG_TEXT by mutableStateOf(false)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    dpInfo: DPInfo,
    nastaveni: Nastaveni,
    tutorial: StavTutorialu,
    ulicove: List<Ulice>,
    linky: List<Linka>,
    busy: List<Bus>,
    prachy: Peniz,
    zmenitTutorial: ((StavTutorialu) -> StavTutorialu) -> Unit,
    zmenitPrachy: ((Peniz) -> Peniz) -> Unit,
    zmenitNastaveni: ((Nastaveni) -> Nastaveni) -> Unit,
    zmenitUlice: (MutableList<Ulice>.() -> Unit) -> Unit,
    navigatate: (Direction) -> Unit,
) {
    val density = LocalDensity.current
    val stred = remember(ulicove.stred) { ulicove.stred.toDpSUlicema() }
    var tx by remember(stred) { mutableFloatStateOf(with(density) { -stred.x.toPx() * pocatecniPriblizeni }) }
    var ty by remember(stred) { mutableFloatStateOf(with(density) { -stred.y.toPx() * pocatecniPriblizeni }) }
    var priblizeni by remember { mutableFloatStateOf(pocatecniPriblizeni) }
    var editor by remember { mutableStateOf(false) }
    var upravitUlici by remember { mutableStateOf(null as UliceID?) }
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(dpInfo.jmenoMesta)
                },
                Modifier.combinedClickable(onLongClick = {
                    zmenitPrachy {
                        if (it < Double.POSITIVE_INFINITY.penez)
                            Double.POSITIVE_INFINITY.penez
                        else
                            12.0.pow(6).penez
                    }
                }) {
                    // todo podniky
                },
                actions = {
                    IconButton(
                        onClick = {
                        }
                    ) {
                        Icon(Icons.Default.EmojiEvents, stringResource(R.string.kalibrovat))
                    }
                    var show by remember { mutableStateOf(false) }
                    IconButton(
                        onClick = {
                            show = !show
                        }
                    ) {
                        Icon(Icons.Default.MoreVert, null)
                    }
                    var zobrazitNastaveni by remember { mutableStateOf(false) }
                    if (zobrazitNastaveni) AlertDialog(
                        onDismissRequest = {
                            zobrazitNastaveni = false
                        }
                    ) {
                        Column {
                            Text("Provizorní nastavení")
                            Text(stringResource(R.string.automaticky_prirazovat_ev_c))
                            Switch(
                                checked = nastaveni.automatickyUdelovatEvC,
                                onCheckedChange = {
                                    zmenitNastaveni { n ->
                                        n.copy(
                                            automatickyUdelovatEvC = it
                                        )
                                    }
                                }
                            )
                            Text(stringResource(R.string.vicenasobne_kupovani))
                            Switch(
                                checked = nastaveni.vicenasobnyKupovani,
                                onCheckedChange = {
                                    zmenitNastaveni { n ->
                                        n.copy(
                                            vicenasobnyKupovani = it
                                        )
                                    }
                                }
                            )
                            Text("DEBUG MÓD")
                            Switch(
                                checked = DEBUG_TEXT,
                                onCheckedChange = {
                                    DEBUG_TEXT = !DEBUG_TEXT
                                }
                            )
                        }
                    }
                    DropdownMenu(
                        expanded = show,
                        onDismissRequest = {
                            show = false
                        }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(stringResource(R.string.nastaveni))
                            },
                            onClick = {
                                zobrazitNastaveni = true
                                show = false
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Settings, null)
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(stringResource(R.string.o_aplikaci))
                            },
                            onClick = {

                            },
                            leadingIcon = {
                                Icon(Icons.Default.Info, null)
                            }
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Surface(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (upravitUlici != null) {
                val staraUlice = remember { ulicove.ulice(upravitUlici!!) }

                AlertDialog(
                    onDismissRequest = {
                        upravitUlici = null
                    },
                    confirmButton = {},
                    dismissButton = {
                        TextButton(
                            onClick = {
                                upravitUlici = null
                            }
                        ) {
                            Text(stringResource(R.string.zrusit))
                        }
                    },
                    icon = {
                        Icon(Icons.Default.EditRoad, null)
                    },
                    title = {
                        Text(stringResource(R.string.upravit_ulici))
                    },
                    text = {
                        Column(
                            Modifier.fillMaxWidth()
                        ) {
                            OutlinedButton(
                                onClick = {
                                    zmenitUlice {
                                        if (staraUlice.maZastavku)
                                            replaceBy(staraUlice.zasebevrazdujZastavku()) { it.id }
                                        else
                                            replaceBy(staraUlice.copy(zastavka = Zastavka())) { it.id }
                                    }
                                    zmenitPrachy {
                                        it - if (!staraUlice.maZastavku) cenaZastavky else cenaZastavky / 5
                                    }
                                    upravitUlici = null
                                },
                                Modifier
                                    .padding(vertical = 4.dp)
                                    .fillMaxWidth(),
                            ) {

                                Text(
                                    text = if (!staraUlice.maZastavku)
                                        stringResource(R.string.vytvorit_zastavku, cenaZastavky.asString())
                                    else stringResource(R.string.odstranit_zastavku, (cenaZastavky / 5).asString()),
                                    textAlign = TextAlign.Center
                                )
                            }
                            if (
                                !(tutorial je StavTutorialu.Tutorialujeme.Uvod) &&
                                !(tutorial je StavTutorialu.Tutorialujeme.Linky) &&
                                !(tutorial je StavTutorialu.Tutorialujeme.Zastavky) &&
                                !(tutorial je StavTutorialu.Tutorialujeme.Garaz) &&
                                !(tutorial je StavTutorialu.Tutorialujeme.Obchod)
                            ) OutlinedButton(
                                onClick = {
                                    zmenitUlice {
                                        if (staraUlice.maTrolej)
                                            replaceBy(staraUlice.copy(maTrolej = false)) { it.id }
                                        else
                                            replaceBy(staraUlice.copy(maTrolej = true)) { it.id }
                                    }
                                    zmenitPrachy {
                                        it - if (!staraUlice.maTrolej) cenaTroleje else cenaTroleje / 5
                                    }
                                    upravitUlici = null
                                },
                                Modifier
                                    .padding(vertical = 4.dp)
                                    .fillMaxWidth(),
                            ) {
                                Text(
                                    text = if (!staraUlice.maTrolej)
                                        stringResource(R.string.postavit_troleje, cenaTroleje.asString())
                                    else stringResource(R.string.odstranit_troleje, (cenaTroleje / 5).asString()),
                                    textAlign = TextAlign.Justify
                                )
                            }
                        }
                    }
                )
            }
            val malovatBusy = !editor && priblizeni > oddalenyRezim
            Mesto(
                malovatBusy = malovatBusy,
                malovatLinky = !malovatBusy,
                kliklyKrizovatky = emptyList(),
                ulice = ulicove,
                linky = linky,
                busy = busy,
                tx = tx,
                ty = ty,
                dpInfo = dpInfo,
                priblizeni = priblizeni,
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures {
                            if (editor) {
                                val ulice = ulicove.find { ulice ->
                                    Rect(
                                        topLeft = (ulice.zacatekX to ulice.zacatekY)
                                            .toPx()
                                            .minus(size.center.toOffset())
                                            .times(priblizeni)
                                            .plus(size.center.toOffset())
                                            .plus(
                                                androidx.compose.ui.geometry
                                                    .Offset(tx, ty)
                                                    .times(priblizeni)
                                            )
                                            .plus(
                                                size.center
                                                    .times(priblizeni)
                                                    .toOffset()
                                            ),
                                        /*.also(::println)*/
                                        bottomRight = (ulice.konecX to ulice.konecY)
                                            .toPx()
                                            .minus(size.center.toOffset())
                                            .times(priblizeni)
                                            .plus(size.center.toOffset())
                                            .plus(
                                                androidx.compose.ui.geometry
                                                    .Offset(tx, ty)
                                                    .times(priblizeni)
                                            )
                                            .plus(
                                                size.center
                                                    .times(priblizeni)
                                                    .toOffset()
                                            ),
                                        /*.also(::println)*/
                                    ).contains(it)
                                }
                                upravitUlici = ulice?.id
                            }
                        }
                    }
                    .pointerInput(Unit) {
                        detectTransformGestures(
                            onGesture = { _, pan, zoom, _ ->
                                scope.launch {
                                    // t - posunuti, c - coercovaný, p - prostředek, x - max, i - min

                                    val (start, stop) = ulicove.velikostMesta
                                    val m = start.toDpSUlicema()
                                        .minus(ulicovyBlok * 2)
                                        .toPx()
                                        .minus(size.center.toOffset())
                                        .times(priblizeni)
                                        .plus(size.center.toOffset())
                                        .plus(
                                            size.center
                                                .times(priblizeni)
                                                .toOffset()
                                        )
                                    val i = stop.toDpSUlicema()
                                        .plus(ulicovyBlok * 2)
                                        .toPx()
                                        .minus(size.center.toOffset())
                                        .times(priblizeni)
                                        .plus(size.center.toOffset())
                                        .plus(
                                            size.center
                                                .times(priblizeni)
                                                .toOffset()
                                        )
                                        .minus(IntOffset(size.width, size.height).toOffset())
                                    val p = (i + m) / 2F

                                    val ti = -i / priblizeni
                                    val tm = -m / priblizeni
                                    val pt = -p / priblizeni
                                    tx = if (ti.x > tm.x) pt.x else tx
                                        .plus(pan.x / priblizeni)
                                        .coerceIn(ti.x, tm.x)
                                    ty = if (ti.y > tm.y) pt.y else ty
                                        .plus(pan.y / priblizeni)
                                        .coerceIn(ti.y, tm.y)
                                    priblizeni = (priblizeni * zoom).coerceAtLeast(maximalniOddaleni)
                                }
//                                println("onGesture(centroid=$centroid, pan=$pan, zoom=$zoom, rotation=$rotation)")
//                                println("tx=${tx}, ty=${ty}, priblizeni=${priblizeni}")
                            }
                        )
                    },
            )
            Column(
                Modifier.fillMaxSize(),
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                ) {
                    if (
                        !(tutorial je StavTutorialu.Tutorialujeme.Uvod)
                    ) Text(
                        text = prachy.asString(),
                        Modifier
                            .weight(1F)
                            .padding(all = 16.dp),
                        textAlign = TextAlign.Start,
                    )

                    if (
                        !(tutorial je StavTutorialu.Tutorialujeme.Uvod) &&
                        !(tutorial je StavTutorialu.Tutorialujeme.Linky) &&
                        !(tutorial je StavTutorialu.Tutorialujeme.Zastavky) &&
                        !(tutorial je StavTutorialu.Tutorialujeme.Garaz) &&
                        !(tutorial je StavTutorialu.Tutorialujeme.Obchod)
                    ) Text(
                        text = dpInfo.zisk.asString(),
                        Modifier
                            .weight(1F)
                            .padding(all = 16.dp),
                        textAlign = TextAlign.End,
                    )
                }
                Spacer(modifier = Modifier.weight(1F))
                if (
                    !(tutorial je StavTutorialu.Tutorialujeme.Uvod) &&
                    !(tutorial je StavTutorialu.Tutorialujeme.Linky) &&
                    !(tutorial je StavTutorialu.Tutorialujeme.Garaz) &&
                    !(tutorial je StavTutorialu.Tutorialujeme.Obchod)
                ) Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(
                        onClick = {
                            editor = !editor

                            if (!editor) zmenitTutorial {
                                if (it je StavTutorialu.Tutorialujeme.Zastavky)
                                    StavTutorialu.Tutorialujeme.Garaz
                                else it
                            }
                        },
                        Modifier
                            .padding(end = 8.dp)
                            .animateContentSize(),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Crossfade(
                                targetState = editor,
                                label = "Editor",
                            ) {
                                if (!it)
                                    Icon(Icons.Default.Edit, null, Modifier.size(ButtonDefaults.IconSize))
                                else
                                    Icon(Icons.Default.Done, null, Modifier.size(ButtonDefaults.IconSize))
                            }
                            Spacer(
                                Modifier
                                    .width(ButtonDefaults.IconSpacing)
                                    .height(ButtonDefaults.IconSpacing)
                            )
                            AnimatedVisibility(
                                visible = editor,
                            ) {
                                Text(stringResource(R.string.hotovo), Modifier)
                            }
                        }
                    }
                }
                Row(
                    Modifier.fillMaxWidth(),
                ) {
                    if (
                        !(tutorial je StavTutorialu.Tutorialujeme.Linky) &&
                        !(tutorial je StavTutorialu.Tutorialujeme.Zastavky) &&
                        !(tutorial je StavTutorialu.Tutorialujeme.Garaz) &&
                        !(tutorial je StavTutorialu.Tutorialujeme.Obchod)
                    ) TextButton(
                        onClick = {
                            navigatate(LinkyScreenDestination)
                        },
                        Modifier
                            .weight(1F)
                            .padding(all = 8.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.linky)
                        )
                    } else Spacer(
                        Modifier
                            .weight(1F)
                            .padding(all = 8.dp)
                    )
                    if (
                        !(tutorial je StavTutorialu.Tutorialujeme.Uvod) &&
                        !(tutorial je StavTutorialu.Tutorialujeme.Linky) &&
                        !(tutorial je StavTutorialu.Tutorialujeme.Zastavky)
                    ) TextButton(
                        onClick = {
                            navigatate(GarazScreenDestination)
                        },
                        Modifier
                            .weight(1F)
                            .padding(all = 8.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.garaz)
                        )
                    } else Spacer(
                        Modifier
                            .weight(1F)
                            .padding(all = 8.dp)
                    )
                }
            }
        }
    }
}