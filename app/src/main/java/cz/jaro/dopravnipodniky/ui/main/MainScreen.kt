package cz.jaro.dopravnipodniky.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessible
import androidx.compose.material.icons.filled.AccessibleForward
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditRoad
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toOffset
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import cz.jaro.dopravnipodniky.BuildConfig
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.Nastaveni
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Bus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DPInfo
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Zastavka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.maZastavku
import cz.jaro.dopravnipodniky.data.dopravnipodnik.rohyMesta
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.zasebevrazdujZastavku
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.data.dosahlosti.DosahlostCallback
import cz.jaro.dopravnipodniky.shared.SharedViewModel
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.UliceID
import cz.jaro.dopravnipodniky.shared.barvaDosahnuteDosahlosti
import cz.jaro.dopravnipodniky.shared.barvaSecretDosahlosti
import cz.jaro.dopravnipodniky.shared.cenaTroleje
import cz.jaro.dopravnipodniky.shared.cenaZastavky
import cz.jaro.dopravnipodniky.shared.composeString
import cz.jaro.dopravnipodniky.shared.formatovat
import cz.jaro.dopravnipodniky.shared.je
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz
import cz.jaro.dopravnipodniky.shared.jednotky.asString
import cz.jaro.dopravnipodniky.shared.jednotky.minus
import cz.jaro.dopravnipodniky.shared.jednotky.penez
import cz.jaro.dopravnipodniky.shared.jednotky.plus
import cz.jaro.dopravnipodniky.shared.jednotky.toDpSKrizovatkama
import cz.jaro.dopravnipodniky.shared.jednotky.ulicovychBloku
import cz.jaro.dopravnipodniky.shared.maximalniOddaleni
import cz.jaro.dopravnipodniky.shared.najitObdelnikVeKteremJe
import cz.jaro.dopravnipodniky.shared.odNulaNula
import cz.jaro.dopravnipodniky.shared.oddalenyRezim
import cz.jaro.dopravnipodniky.shared.replaceBy
import cz.jaro.dopravnipodniky.shared.toOffsetSPriblizenim
import cz.jaro.dopravnipodniky.shared.ulicovyBlok
import cz.jaro.dopravnipodniky.shared.zrychlovacHry
import cz.jaro.dopravnipodniky.snackbarHostState
import cz.jaro.dopravnipodniky.ui.destinations.DopravniPodnikyScreenDestination
import cz.jaro.dopravnipodniky.ui.destinations.GarazScreenDestination
import cz.jaro.dopravnipodniky.ui.destinations.LinkyScreenDestination
import cz.jaro.dopravnipodniky.ui.destinations.MainScreenDestination
import cz.jaro.dopravnipodniky.ui.malovani.Mesto
import cz.jaro.dopravnipodniky.ui.theme.Theme
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import java.time.LocalDate
import kotlin.math.min
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
    val dosahlosti: List<Dosahlost.NormalniDosahlost>? by viewModel.dosahlosti.collectAsStateWithLifecycle()
    val ulicove by viewModel.ulice.collectAsStateWithLifecycle()
    val linky by viewModel.linky.collectAsStateWithLifecycle()
    val busy by viewModel.busy.collectAsStateWithLifecycle()
    val prachy by viewModel.prachy.collectAsStateWithLifecycle()

    if (
        dpInfo != null &&
        nastaveni != null &&
        tutorial != null &&
        dosahlosti != null &&
        ulicove != null &&
        linky != null &&
        busy != null &&
        prachy != null
    ) MainScreen(
//        zmenitPodniky = viewModel::zmenitOstatniDopravnikyPodniky,
//        zmenitDP = viewModel::zmenitDopravnikyPodnik,
        dpInfo = dpInfo!!,
        nastaveni = nastaveni!!,
        tutorial = tutorial!!,
        dosahlosti = dosahlosti!!,
        ulicove = ulicove!!,
        linky = linky!!,
        busy = busy!!,
        prachy = prachy!!,
        zmenitTutorial = viewModel::zmenitTutorial,
        zmenitDPInfo = viewModel::zmenitDPInfo,
        zmenitPrachy = viewModel::zmenitPrachy,
        zmenitNastaveni = viewModel::zmenitNastaveni,
        zmenitUlice = viewModel::zmenitUlice,
        navigate = navigator::navigate,
        ziskatUlice = {
            ulicove!!
        },
    )
}

var DEBUG_TEXT by mutableStateOf(false)
var ukazatDosahlosti by mutableStateOf(false)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
//    zmenitPodniky: suspend (suspend MutableList<DopravniPodnik>.() -> Unit) -> Unit,
//    zmenitDP: suspend (DPID) -> Unit,
    dpInfo: DPInfo,
    nastaveni: Nastaveni,
    tutorial: StavTutorialu,
    dosahlosti: List<Dosahlost.NormalniDosahlost>,
    ulicove: List<Ulice>,
    linky: List<Linka>,
    busy: List<Bus>,
    prachy: Peniz,
    zmenitDPInfo: ((DPInfo) -> DPInfo) -> Unit,
    zmenitTutorial: ((StavTutorialu) -> StavTutorialu) -> Unit,
    zmenitPrachy: ((Peniz) -> Peniz) -> Unit,
    zmenitNastaveni: ((Nastaveni) -> Nastaveni) -> Unit,
    zmenitUlice: (MutableList<Ulice>.() -> Unit) -> Unit,
    navigate: (Direction) -> Unit,
    ziskatUlice: () -> List<Ulice>,
) {
    var size by remember { mutableStateOf(null as IntSize?) }
    val density = LocalDensity.current
    var tx by remember { mutableFloatStateOf(0F) }
    var ty by remember { mutableFloatStateOf(0F) }
    var priblizeni by remember { mutableFloatStateOf(1F) }
    var editor by remember { mutableStateOf(false) }
    var upravitUlici by remember { mutableStateOf(null as UliceID?) }
    val scope = rememberCoroutineScope()
    val res = LocalContext.current.resources

    LaunchedEffect(ulicove, size) {
        if (size == null) return@LaunchedEffect
        with(density) {
            val (start, stop) = ulicove.rohyMesta
            val m = start
                .toDpSKrizovatkama()
                .minus(ulicovyBlok)
                .toOffsetSPriblizenim(priblizeni, size!!.center.toOffset())
            val i = stop
                .toDpSKrizovatkama()
                .plus(ulicovyBlok)
                .toOffsetSPriblizenim(priblizeni, size!!.center.toOffset())
                .minus(IntOffset(size!!.width, size!!.height).toOffset())
            val p = (i + m) / 2F

            tx = p.odNulaNula(priblizeni).x
            ty = p.odNulaNula(priblizeni).y

            val sirkaMesta = stop.x - start.x
            val vyskaMesta = stop.y - start.y
            val priblizeniPodleSirky = size!!.width / (sirkaMesta + 2.ulicovychBloku)
                .toDpSKrizovatkama()
                .toPx()
            val priblizeniPodleVysky = size!!.height / (vyskaMesta + 2.ulicovychBloku)
                .toDpSKrizovatkama()
                .toPx()
            priblizeni = min(priblizeniPodleSirky, priblizeniPodleVysky)
        }
    }

    LaunchedEffect(Unit) {
        loadKoinModules(module {
            single {
                DosahlostCallback {
                    if (
                        !(tutorial je StavTutorialu.Tutorialujeme.Uvod) &&
                        !(tutorial je StavTutorialu.Tutorialujeme.Linky) &&
                        !(tutorial je StavTutorialu.Tutorialujeme.Zastavky) &&
                        !(tutorial je StavTutorialu.Tutorialujeme.Garaz) &&
                        !(tutorial je StavTutorialu.Tutorialujeme.Obchod)
                    ) MainScope().launch {
                        val result = snackbarHostState.showSnackbar(
                            message = "Splněno ${res.getString(it.jmeno)}",
                            actionLabel = "Zobrazit",
                            duration = SnackbarDuration.Long,
                            withDismissAction = true,
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            navigate(MainScreenDestination)
                            ukazatDosahlosti = true
                        }
                    }
                }
            }
        })
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
//                    Text("${dpInfo.jmenoMesta} — $seed")
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
                    navigate(DopravniPodnikyScreenDestination)
                },
                actions = {
                    if (
                        !(tutorial je StavTutorialu.Tutorialujeme.Uvod) &&
                        !(tutorial je StavTutorialu.Tutorialujeme.Linky) &&
                        !(tutorial je StavTutorialu.Tutorialujeme.Zastavky) &&
                        !(tutorial je StavTutorialu.Tutorialujeme.Garaz) &&
                        !(tutorial je StavTutorialu.Tutorialujeme.Obchod)
                    ) IconButton(
                        onClick = {
                            ukazatDosahlosti = true
                        }
                    ) {
                        Icon(Icons.Default.EmojiEvents, stringResource(R.string.uspechy))
                    }
//                    if (BuildConfig.DEBUG) IconButton(
//                        onClick = {
//                            CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
//                                seed = Random.nextInt()
//                                val novyDP = Generator.vygenerujMiPrvniMesto()
//                                zmenitPodniky {
//                                    add(novyDP)
//                                }
//                                delay(500)
//                                zmenitDP(novyDP.info.id)
//                            }
//                        }
//                    ) {
//                        Icon(Icons.Default.Refresh, stringResource(R.string.uspechy))
//                    }
                    if (BuildConfig.DEBUG) IconButton(
                        onClick = {
                            zrychlovacHry = if (zrychlovacHry == 1F) 60F else 1F
                        }
                    ) {
                        Icon(
                            if (zrychlovacHry == 1F) Icons.Default.Accessible else Icons.Default.AccessibleForward,
                            stringResource(R.string.uspechy)
                        )
                    }
                    if (tutorial je StavTutorialu.Tutorialujeme.Uvod) IconButton(
                        onClick = {
                            zmenitTutorial {
                                StavTutorialu.Tutorialujeme.Uvod
                            }
                        }
                    ) {
                        Icon(Icons.Default.Help, stringResource(R.string.tutorial))
                    }
                    if (tutorial je StavTutorialu.Tutorialujeme.Zastavky) IconButton(
                        onClick = {
                            zmenitTutorial {
                                StavTutorialu.Tutorialujeme.Zastavky
                            }
                        }
                    ) {
                        Icon(Icons.Default.Help, stringResource(R.string.tutorial))
                    }
                    if (tutorial je StavTutorialu.Tutorialujeme.Garaz) IconButton(
                        onClick = {
                            zmenitTutorial {
                                StavTutorialu.Tutorialujeme.Garaz
                            }
                        }
                    ) {
                        Icon(Icons.Default.Help, stringResource(R.string.tutorial))
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
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    zobrazitNastaveni = false
                                }
                            ) {
                                Text(stringResource(android.R.string.ok))
                            }
                        },
                        icon = {
                            Icon(Icons.Default.Settings, null)
                        },
                        title = {
                            Text(stringResource(R.string.nastaveni))
                        },
                        text = {
                            Column(
                                Modifier.fillMaxWidth()
                            ) {
                                var expanded by remember { mutableStateOf(false) }
                                ExposedDropdownMenuBox(
                                    expanded = expanded,
                                    onExpandedChange = { expanded = !expanded },
                                ) {
                                    OutlinedTextField(
                                        modifier = Modifier
                                            .menuAnchor()
                                            .padding(vertical = 4.dp)
                                            .fillMaxWidth(),
                                        readOnly = true,
                                        value = stringResource(dpInfo.tema.jmeno),
                                        leadingIcon = {
                                            Icon(Icons.Default.Circle, null, tint = dpInfo.tema.barva)
                                        },
                                        onValueChange = {},
                                        label = { Text(stringResource(R.string.barva_linky)) },
                                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Number,
                                        )
                                    )
                                    ExposedDropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false },
                                    ) {
                                        Theme.entries.forEach { tohleTema ->
                                            DropdownMenuItem(
                                                text = { Text(stringResource(tohleTema.jmeno)) },
                                                onClick = {
                                                    zmenitDPInfo {
                                                        it.copy(
                                                            tema = tohleTema
                                                        )
                                                    }
                                                    expanded = false
                                                },
                                                leadingIcon = {
                                                    Icon(Icons.Default.Circle, null, tint = tohleTema.barva)
                                                },
                                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                            )
                                        }
                                    }
                                }
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    Text(stringResource(R.string.automaticky_prirazovat_ev_c), Modifier.weight(1F))
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
                                }
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    Text(stringResource(R.string.vicenasobne_kupovani), Modifier.weight(1F))
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
                                }
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    Text("DEBUG MÓD", Modifier.weight(1F))
                                    Switch(
                                        checked = DEBUG_TEXT,
                                        onCheckedChange = {
                                            DEBUG_TEXT = !DEBUG_TEXT
                                        }
                                    )
                                }
                            }
                        }
                    )

                    var zobrazitTuDruhouKravinu by remember { mutableStateOf(false) }
                    if (zobrazitTuDruhouKravinu) AlertDialog(
                        onDismissRequest = {
                            zobrazitTuDruhouKravinu = false
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    zobrazitTuDruhouKravinu = false
                                }
                            ) {
                                Text(stringResource(android.R.string.ok))
                            }
                        },
                        icon = {
                            Icon(Icons.Default.Info, null)
                        },
                        title = {
                            Text(stringResource(R.string.o_aplikaci))
                        },
                        text = {
                            Column {
                                Text("Verze aplikace: ${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})")
                                Text("2021-${LocalDate.now().year} RO studios, člen skupiny JARO")
                                Text("2019-${LocalDate.now().year} JARO")
                                Text("Všechna data o busech byla ukradena bez svolení majitelů. Na naše data není v žádném případě spolehnutí,")
                                Text("Simulate crash...", Modifier.clickable {
                                    throw RuntimeException("Test exception")
                                }, fontSize = 10.sp)
                            }
                        }
                    )
                    DropdownMenu(
                        expanded = show,
                        onDismissRequest = {
                            show = false
                        }
                    ) {
                        if (
                            !(tutorial je StavTutorialu.Tutorialujeme.Uvod) &&
                            !(tutorial je StavTutorialu.Tutorialujeme.Linky) &&
                            !(tutorial je StavTutorialu.Tutorialujeme.Zastavky) &&
                            !(tutorial je StavTutorialu.Tutorialujeme.Garaz) &&
                            !(tutorial je StavTutorialu.Tutorialujeme.Obchod)
                        ) DropdownMenuItem(
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
                                zobrazitTuDruhouKravinu = true
                                show = false
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
            if (ukazatDosahlosti) AlertDialog(
                onDismissRequest = {
                    ukazatDosahlosti = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            ukazatDosahlosti = false
                        }
                    ) {
                        Text(stringResource(android.R.string.ok))
                    }
                },
                title = {
                    Text(stringResource(R.string.uspechy))
                },
                icon = {
                    Icon(Icons.Default.EmojiEvents, null)
                },
                text = {
                    Dosahlosti(dosahlosti)
                }
            )
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

            fun PointerInputScope.onClick(it: Offset) {
                val pitomyUlice = ziskatUlice()
                if (editor) {
                    val ulice = pitomyUlice.najitObdelnikVeKteremJe(
                        offset = it,
                        tx = tx, ty = ty, priblizeni = priblizeni
                    ) { ulice ->
                        DpRect(
                            left = ulice.zacatekX,
                            top = ulice.zacatekY,
                            right = ulice.konecX,
                            bottom = ulice.konecY,
                        )
                    }

                    upravitUlici = ulice?.id
                }
            }

            @Suppress("UNUSED_PARAMETER")
            fun PointerInputScope.onTransform(p0: Offset, pan: Offset, zoom: Float, p3: Float) {
                val pitomyUlice = ziskatUlice()
                scope.launch {
                    // t - posunuti, p - prostředek, x - max, i - min

                    val (start, stop) = pitomyUlice.rohyMesta
                    val m = start.toDpSKrizovatkama()
                        .minus(ulicovyBlok * 2)
                        .toOffsetSPriblizenim(priblizeni)
                    val i = stop.toDpSKrizovatkama()
                        .plus(ulicovyBlok * 2)
                        .toOffsetSPriblizenim(priblizeni)
                        .minus(IntOffset(this@onTransform.size.width, this@onTransform.size.height).toOffset())
                    val p = (i + m) / 2F

                    val ti = i.odNulaNula(priblizeni)
                    val tm = m.odNulaNula(priblizeni)
                    val pt = p.odNulaNula(priblizeni)

                    tx = if (ti.x > tm.x) pt.x else tx.minus(pan.odNulaNula(priblizeni).x).coerceIn(ti.x, tm.x)
                    ty = if (ti.y > tm.y) pt.y else ty.minus(pan.odNulaNula(priblizeni).y).coerceIn(ti.y, tm.y)
                    priblizeni = (priblizeni * zoom).coerceAtLeast(maximalniOddaleni)
                }
//                                println("onGesture(centroid=$centroid, pan=$pan, zoom=$zoom, rotation=$rotation)")
//                                println("tx=${tx}, ty=${ty}, priblizeni=${priblizeni}")
            }

            val malovatBusy = !editor && priblizeni > oddalenyRezim
            Mesto(
                malovatBusy = malovatBusy,
                malovatLinky = !malovatBusy,
                malovatTroleje = priblizeni > oddalenyRezim,
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
                        detectTapGestures(onTap = ::onClick)
                    }
                    .pointerInput(Unit) {
                        detectTransformGestures(onGesture = ::onTransform)
                    }
                    .onSizeChanged {
                        size = it
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
                            navigate(LinkyScreenDestination)
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
                            navigate(GarazScreenDestination)
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

@Composable
fun Dosahlosti(
    dosahlosti: List<Dosahlost.NormalniDosahlost>
) {
    val razeneDosahlosti = remember(dosahlosti) {
        Dosahlost.dosahlosti().map { baseDosahlost ->
            dosahlosti.find { it::class == baseDosahlost::class } ?: baseDosahlost
        }.sortedByDescending {
            (it.stav as? Dosahlost.Stav.Splneno)?.kdy
        }.sortedBy {
            when {
                it.stav is Dosahlost.Stav.Splneno -> 0
                it is Dosahlost.Secret -> 2
                else -> 1
            }
        }
    }
    LazyColumn {
        items(razeneDosahlosti) { dosahlost ->
            OutlinedCard(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = when {
                    dosahlost.stav is Dosahlost.Stav.Splneno -> CardDefaults.outlinedCardColors(
                        containerColor = barvaDosahnuteDosahlosti,
                        contentColor = Color.White,
                    )

                    dosahlost is Dosahlost.Secret -> CardDefaults.outlinedCardColors(
                        containerColor = barvaSecretDosahlosti,
                        contentColor = Color.White,
                    )

                    else -> CardDefaults.outlinedCardColors()
                }
            ) {
                Column(
                    Modifier.fillMaxWidth()
                ) {
                    val jmeno = when {
                        dosahlost is Dosahlost.Secret && dosahlost.napoveda == null && dosahlost.stav !is Dosahlost.Stav.Splneno -> "???"
                        else -> stringResource(dosahlost.jmeno)
                    }
                    Text(jmeno, Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp))
                    val popis = when {
                        dosahlost is Dosahlost.Secret && dosahlost.napoveda == null && dosahlost.stav !is Dosahlost.Stav.Splneno -> "?????????"
                        dosahlost is Dosahlost.Secret && dosahlost.stav !is Dosahlost.Stav.Splneno -> stringResource(dosahlost.napoveda!!)
                        else -> stringResource(dosahlost.popis)
                    }
                    Text(popis, Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp))
                    if (dosahlost !is Dosahlost.Secret || dosahlost.stav is Dosahlost.Stav.Splneno) Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                    ) {
                        Text(dosahlost.odmena.asString())
                        Spacer(Modifier.weight(1F))
                        if (dosahlost !is Dosahlost.Pocetni) Unit
                        else {
                            val splneno = when (dosahlost.stav) {
                                is Dosahlost.Stav.Nesplneno -> 0
                                is Dosahlost.Stav.Pocetni -> (dosahlost.stav as Dosahlost.Stav.Pocetni).pocet
                                is Dosahlost.Stav.Splneno -> dosahlost.cil
                            }
                            Text(
                                "${splneno.formatovat(0).composeString()}/${dosahlost.cil.formatovat(0).composeString()}",
                                textAlign = TextAlign.End
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}