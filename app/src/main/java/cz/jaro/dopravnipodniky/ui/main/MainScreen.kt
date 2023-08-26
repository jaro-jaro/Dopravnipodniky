package cz.jaro.dopravnipodniky.ui.main

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
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
import cz.jaro.dopravnipodniky.data.dopravnipodnik.stred
import cz.jaro.dopravnipodniky.data.dopravnipodnik.velikostMesta
import cz.jaro.dopravnipodniky.shared.SharedViewModel
import cz.jaro.dopravnipodniky.shared.jednotky.asString
import cz.jaro.dopravnipodniky.shared.jednotky.dpSUlicema
import cz.jaro.dopravnipodniky.shared.jednotky.minus
import cz.jaro.dopravnipodniky.shared.jednotky.plus
import cz.jaro.dopravnipodniky.shared.jednotky.toPx
import cz.jaro.dopravnipodniky.shared.maximalniOddaleni
import cz.jaro.dopravnipodniky.shared.pocatecniPriblizeni
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
        upravitVse = viewModel::zmenitVse,
        navigatate = navigator::navigate,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    dp: DopravniPodnik,
    vse: Vse,
//    upravitDp: ((DopravniPodnik) -> DopravniPodnik) -> Unit,
    upravitVse: ((Vse) -> Vse) -> Unit,
    navigatate: (Direction) -> Unit,
) {
    val density = LocalDensity.current
    val stred = remember { dp.stred.dpSUlicema }
    val tx = remember { Animatable(with(density) { -stred.x.toPx() * pocatecniPriblizeni }) }
    val ty = remember { Animatable(with(density) { -stred.y.toPx() * pocatecniPriblizeni }) }
    val priblizeni = remember { Animatable(pocatecniPriblizeni) }
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(dp.jmenoMesta)
                },
                actions = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                tx.animateTo(with(density) { -stred.x.toPx() * pocatecniPriblizeni })
                            }
                            scope.launch {
                                ty.animateTo(with(density) { -stred.y.toPx() * pocatecniPriblizeni })
                            }
                            scope.launch {
                                priblizeni.animateTo(pocatecniPriblizeni)
                            }
                        }
                    ) {
                        Icon(Icons.Default.Home, stringResource(R.string.kalibrovat))
                    }
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
            CeleMesto(
                dp = dp,
                tx = tx.value,
                ty = ty.value,
                priblizeni = priblizeni.value,
                modifier = Modifier
                    .fillMaxSize()
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
                                        .times(priblizeni.value)
                                        .plus(size.center.toOffset())
                                        .plus(
                                            size.center
                                                .times(priblizeni.value)
                                                .toOffset()
                                        )
                                    val i = stop.dpSUlicema
                                        .plus(ulicovyBlok * 2)
                                        .toPx()
                                        .minus(size.center.toOffset())
                                        .times(priblizeni.value)
                                        .plus(size.center.toOffset())
                                        .plus(
                                            size.center
                                                .times(priblizeni.value)
                                                .toOffset()
                                        )
                                        .minus(IntOffset(size.width, size.height).toOffset())
                                    val p = (i + m) / 2F

                                    val t = Offset(
                                        tx.value.plus(pan.x / priblizeni.value),
                                        ty.value.plus(pan.y / priblizeni.value),
                                    )

                                    val ti = -i / priblizeni.value
                                    val tm = -m / priblizeni.value
                                    val pt = -p / priblizeni.value
                                    val txc = if (ti.x > tm.x) pt.x else t.x.coerceIn(ti.x, tm.x)
                                    val tyc = if (ti.y > tm.y) pt.y else t.y.coerceIn(ti.y, tm.y)

                                    tx.snapTo(txc)
                                    ty.snapTo(tyc)
                                    priblizeni.snapTo((priblizeni.value * zoom).coerceAtLeast(maximalniOddaleni))
                                }
//                                println("onGesture(centroid=$centroid, pan=$pan, zoom=$zoom, rotation=$rotation)")
//                                println("tx=${tx.value}, ty=${ty.value}, priblizeni=${priblizeni.value}")
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
                    Icon(Icons.Default.Edit, null)
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