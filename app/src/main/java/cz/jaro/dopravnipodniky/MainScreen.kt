package cz.jaro.dopravnipodniky

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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import cz.jaro.dopravnipodniky.classes.DopravniPodnik
import cz.jaro.dopravnipodniky.destinations.GarazScreenDestination
import cz.jaro.dopravnipodniky.jednotky.asString
import cz.jaro.dopravnipodniky.sketches.CeleMesto
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination(start = true)
fun MainScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = koinViewModel<MainViewModel>()

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
    val tx = remember { Animatable(with(density) { pocatecniPosunutiX.toPx() }) }
    val ty = remember { Animatable(with(density) { pocatecniPosunutiY.toPx() }) }
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
                                tx.animateTo(with(density) { pocatecniPosunutiX.toPx() })
                            }
                            scope.launch {
                                ty.animateTo(with(density) { pocatecniPosunutiY.toPx() })
                            }
                            scope.launch {
                                priblizeni.animateTo(pocatecniPriblizeni)
                            }
                        }
                    ) {
                        Icon(Icons.Default.Home, stringResource(R.string.kalibrovat))
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
                vse = vse,
                tx = tx.value,
                ty = ty.value,
                priblizeni = priblizeni.value,
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTransformGestures(
                            onGesture = { _, pan, zoom, _ ->
//                        val (x, y) = dp.oblastiPodniku
//                        println("${tx.toDp()}, ${tx.toDp().div(priblizeni)}, ${x.start.dpSUlicema}, ${x.endInclusive.dpSUlicema}")
                                scope.launch {
                                    tx.snapTo(tx.value + pan.x / priblizeni.value)/*.div(priblizeni).coerceIn(-x.endInclusive.dpSUlicema.toPx(), -x.start.dpSUlicema.toPx()*//* + (size.width)*//*).times(priblizeni)*/
                                    ty.snapTo(ty.value + pan.y / priblizeni.value)/*.div(priblizeni).coerceIn(-y.endInclusive.dpSUlicema.toPx(), -y.start.dpSUlicema.toPx()*//* + (size.height)*//*).times(priblizeni)*/
                                    // TODO
                                    priblizeni.snapTo((priblizeni.value * zoom).coerceAtLeast(maximalniOddaleni))
                                }
//                        println("onGesture(centroid=$centroid, pan=$pan, zoom=$zoom, rotation=$rotation)")
                                println("tx=$tx, ty=$ty, priblizeni=$priblizeni")
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