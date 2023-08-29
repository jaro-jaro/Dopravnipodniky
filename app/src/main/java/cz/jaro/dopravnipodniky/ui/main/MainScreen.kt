package cz.jaro.dopravnipodniky.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
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
import androidx.compose.material.icons.filled.RestartAlt
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
import cz.jaro.dopravnipodniky.BuildConfig
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.Generator
import cz.jaro.dopravnipodniky.data.Vse
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Zastavka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.maZastavku
import cz.jaro.dopravnipodniky.data.dopravnipodnik.stred
import cz.jaro.dopravnipodniky.data.dopravnipodnik.velikostMesta
import cz.jaro.dopravnipodniky.data.dopravnipodnik.zasebevrazdujZastavku
import cz.jaro.dopravnipodniky.shared.SharedViewModel
import cz.jaro.dopravnipodniky.shared.UliceID
import cz.jaro.dopravnipodniky.shared.cenaTroleje
import cz.jaro.dopravnipodniky.shared.cenaZastavky
import cz.jaro.dopravnipodniky.shared.jednotky.asString
import cz.jaro.dopravnipodniky.shared.jednotky.dpSUlicema
import cz.jaro.dopravnipodniky.shared.jednotky.minus
import cz.jaro.dopravnipodniky.shared.jednotky.plus
import cz.jaro.dopravnipodniky.shared.jednotky.to
import cz.jaro.dopravnipodniky.shared.jednotky.toPx
import cz.jaro.dopravnipodniky.shared.maximalniOddaleni
import cz.jaro.dopravnipodniky.shared.pocatecniPriblizeni
import cz.jaro.dopravnipodniky.shared.replaceBy
import cz.jaro.dopravnipodniky.shared.ulicovyBlok
import cz.jaro.dopravnipodniky.ui.destinations.GarazScreenDestination
import cz.jaro.dopravnipodniky.ui.destinations.LinkyScreenDestination
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination(start = true)
fun MainScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = koinViewModel<SharedViewModel>()

    val dp by viewModel.dp.collectAsStateWithLifecycle()
    val vse by viewModel.vse.collectAsStateWithLifecycle()

    if (dp != null && vse != null) MainScreen(
        dp = dp!!,
        vse = vse!!,
        upravitDp = viewModel::zmenitDp,
        upravitVse = viewModel::zmenitVse,
        navigatate = navigator::navigate,
    )
}

var DEBUG_TEXT by mutableStateOf(false)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    dp: DopravniPodnik,
    vse: Vse,
    upravitDp: ((DopravniPodnik) -> DopravniPodnik) -> Unit,
    upravitVse: ((Vse) -> Vse) -> Unit,
    navigatate: (Direction) -> Unit,
) {
    val density = LocalDensity.current
    val stred = remember(dp.stred) { dp.stred.dpSUlicema }
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
                    Text(dp.jmenoMesta)
                },
                actions = {
                    if (BuildConfig.DEBUG) IconButton(
                        onClick = {
                            upravitVse { Vse(prvniDp = Generator.vygenerujMiPrvniMesto()) }
                        }
                    ) {
                        Icon(Icons.Default.RestartAlt, null)
                    }
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
                    var nastaveni by remember { mutableStateOf(false) }
                    if (nastaveni) AlertDialog(
                        onDismissRequest = {
                            nastaveni = false
                        }
                    ) {
                        Column {
                            Text("Provizorní nastavení")
                            Text(stringResource(R.string.automaticky_prirazovat_ev_c))
                            Switch(
                                checked = vse.automatickyUdelovatEvC,
                                onCheckedChange = {
                                    upravitVse { vse ->
                                        vse.copy(
                                            automatickyUdelovatEvC = it
                                        )
                                    }
                                }
                            )
                            Text(stringResource(R.string.vicenasobne_kupovani))
                            Switch(
                                checked = vse.vicenasobnyKupovani,
                                onCheckedChange = {
                                    upravitVse { vse ->
                                        vse.copy(
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
                                nastaveni = true
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
                val ulice = remember { dp.ulice(upravitUlici!!) }

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
                                    upravitDp { dp ->
                                        if (ulice.maZastavku)
                                            dp.copy(ulice = dp.ulice.replaceBy(ulice.zasebevrazdujZastavku()) { it.id })
                                        else
                                            dp.copy(ulice = dp.ulice.replaceBy(ulice.copy(zastavka = Zastavka())) { it.id })
                                    }
                                    upravitVse {
                                        it.copy(
                                            prachy = it.prachy - if (!ulice.maZastavku) cenaZastavky else cenaZastavky / 5
                                        )
                                    }
                                    upravitUlici = null
                                },
                                Modifier
                                    .padding(vertical = 4.dp)
                                    .fillMaxWidth(),
                            ) {

                                Text(
                                    text = if (!ulice.maZastavku)
                                        stringResource(R.string.vytvorit_zastavku, cenaZastavky.asString())
                                    else stringResource(R.string.odstranit_zastavku, (cenaZastavky / 5).asString()),
                                    textAlign = TextAlign.Center
                                )
                            }
                            OutlinedButton(
                                onClick = {
                                    upravitDp { dp ->
                                        if (ulice.maTrolej)
                                            dp.copy(ulice = dp.ulice.replaceBy(ulice.copy(maTrolej = false)) { it.id })
                                        else
                                            dp.copy(ulice = dp.ulice.replaceBy(ulice.copy(maTrolej = true)) { it.id })
                                    }
                                    upravitVse {
                                        it.copy(
                                            prachy = it.prachy - if (!ulice.maTrolej) cenaTroleje else cenaTroleje / 5
                                        )
                                    }
                                    upravitUlici = null
                                },
                                Modifier
                                    .padding(vertical = 4.dp)
                                    .fillMaxWidth(),
                            ) {
                                Text(
                                    text = if (!ulice.maTrolej)
                                        stringResource(R.string.postavit_troleje, cenaTroleje.asString())
                                    else stringResource(R.string.odstranit_troleje, (cenaTroleje / 5).asString()),
                                    textAlign = TextAlign.Justify
                                )
                            }
                        }
                    }
                )
            }
            CeleMesto(
                dp = dp,
                tx = tx,
                ty = ty,
                priblizeni = priblizeni,
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures {
                            if (editor) {
                                val ulice = dp.ulice.find { ulice ->
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
                                            )
                                            /*.also(::println)*/,
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
                                            )
                                            /*.also(::println)*/,
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

                                    val (start, stop) = dp.velikostMesta
                                    val m = start.dpSUlicema
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
                                    val i = stop.dpSUlicema
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
                Text(
                    text = vse.prachy.asString(),
                    Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp),
                    textAlign = TextAlign.Center,
                )
                Row(
                    Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = dp.zisk.asString(),
                        Modifier
                            .weight(1F)
                            .padding(all = 8.dp),
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = stringResource(R.string.pocet_busu, dp.busy.count { it.linka != null }, dp.busy.size),
                        Modifier
                            .weight(1F)
                            .padding(all = 8.dp),
                        textAlign = TextAlign.End,
                    )
                }
                Spacer(modifier = Modifier.weight(1F))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(
                        onClick = {
                            editor = !editor
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
                    TextButton(
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
                    }
                    TextButton(
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
                    }
                }
            }
        }
    }
}