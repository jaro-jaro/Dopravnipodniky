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
import cz.jaro.dopravnipodniky.data.Vse
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Zastavka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.maZastavku
import cz.jaro.dopravnipodniky.data.dopravnipodnik.rohyMesta
import cz.jaro.dopravnipodniky.data.dopravnipodnik.zasebevrazdujZastavku
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.data.dosahlosti.DosahlostCallback
import cz.jaro.dopravnipodniky.dialogState
import cz.jaro.dopravnipodniky.shared.Menic
import cz.jaro.dopravnipodniky.shared.SharedViewModel
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.barvaDosahnuteDosahlosti
import cz.jaro.dopravnipodniky.shared.barvaSecretDosahlosti
import cz.jaro.dopravnipodniky.shared.cenaTroleje
import cz.jaro.dopravnipodniky.shared.cenaZastavky
import cz.jaro.dopravnipodniky.shared.composeString
import cz.jaro.dopravnipodniky.shared.formatovat
import cz.jaro.dopravnipodniky.shared.je
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
        viewModel.menic.zmenitTutorial {
            if (it je StavTutorialu.Tutorialujeme.Linky)
                StavTutorialu.Tutorialujeme.Zastavky
            else it
        }
    }
    val dp by viewModel.dp.collectAsStateWithLifecycle()
    val vse by viewModel.vse.collectAsStateWithLifecycle()

    if (
        dp != null &&
        vse != null
    ) MainScreen(
        dp = dp!!,
        vse = vse!!,
        menic = viewModel.menic,
        navigate = navigator::navigate,
        ziskatDP = {
            dp!!
        },
    )
}

var DEBUG_TEXT by mutableStateOf(false)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    dp: DopravniPodnik,
    vse: Vse,
    menic: Menic,
    navigate: (Direction) -> Unit,
    ziskatDP: () -> DopravniPodnik,
) {
    var size by remember { mutableStateOf(null as IntSize?) }
    val density = LocalDensity.current
    var tx by remember { mutableFloatStateOf(0F) }
    var ty by remember { mutableFloatStateOf(0F) }
    var priblizeni by remember { mutableFloatStateOf(1F) }
    var editor by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val res = LocalContext.current.resources

    LaunchedEffect(dp.ulice.map { it.zacatek to it.konec }, size) {
        if (size == null) return@LaunchedEffect
        with(density) {
            val (start, stop) = dp.rohyMesta
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
                    MainScope().launch {
                        val result = snackbarHostState.showSnackbar(
                            message = "Splněno ${res.getString(it.jmeno)}",
                            actionLabel = "Zobrazit",
                            duration = SnackbarDuration.Long,
                            withDismissAction = true,
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            zobrazitDosahlosti(vse.dosahlosti)
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
//                    Text("${dp.info.jmenoMesta} — $seed")
                    Text(dp.info.jmenoMesta)
                },
                Modifier.combinedClickable(onLongClick = {
                    menic.zmenitPrachy {
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
                        !(vse.tutorial je StavTutorialu.Tutorialujeme.Uvod) &&
                        !(vse.tutorial je StavTutorialu.Tutorialujeme.Linky) &&
                        !(vse.tutorial je StavTutorialu.Tutorialujeme.Zastavky) &&
                        !(vse.tutorial je StavTutorialu.Tutorialujeme.Garaz) &&
                        !(vse.tutorial je StavTutorialu.Tutorialujeme.Obchod)
                    ) IconButton(
                        onClick = {
                            zobrazitDosahlosti(vse.dosahlosti)
                        }
                    ) {
                        Icon(Icons.Default.EmojiEvents, stringResource(R.string.uspechy))
                    }
//                    if (BuildConfig.DEBUG) IconButton(
//                        onClick = {
//                            CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
//                                seed = Random.nextInt()
//                                val novyDP = Generator.vygenerujMiPrvniMesto()
//                                menic.zmenitPodniky {
//                                    add(novyDP)
//                                }
//                                delay(500)
//                                menic.zmenitDP(novyDP.info.id)
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
                    if (vse.tutorial je StavTutorialu.Tutorialujeme.Uvod) IconButton(
                        onClick = {
                            menic.zmenitTutorial {
                                StavTutorialu.Tutorialujeme.Uvod
                            }
                        }
                    ) {
                        Icon(Icons.Default.Help, stringResource(R.string.tutorial))
                    }
                    if (vse.tutorial je StavTutorialu.Tutorialujeme.Zastavky) IconButton(
                        onClick = {
                            menic.zmenitTutorial {
                                StavTutorialu.Tutorialujeme.Zastavky
                            }
                        }
                    ) {
                        Icon(Icons.Default.Help, stringResource(R.string.tutorial))
                    }
                    if (vse.tutorial je StavTutorialu.Tutorialujeme.Garaz) IconButton(
                        onClick = {
                            menic.zmenitTutorial {
                                StavTutorialu.Tutorialujeme.Garaz
                            }
                        }
                    ) {
                        Icon(Icons.Default.Help, stringResource(R.string.tutorial))
                    }
                    var show by remember { mutableStateOf(false) }
                    var debug by remember { mutableStateOf(DEBUG_TEXT) }
                    var evc by remember { mutableStateOf(vse.nastaveni.automatickyUdelovatEvC) }
                    var multi by remember { mutableStateOf(vse.nastaveni.vicenasobnyKupovani) }
                    var tema by remember { mutableStateOf(dp.info.tema) }
                    IconButton(
                        onClick = {
                            show = !show
                        }
                    ) {
                        Icon(Icons.Default.MoreVert, null)
                    }
                    DropdownMenu(
                        expanded = show,
                        onDismissRequest = {
                            show = false
                        }
                    ) {
                        if (
                            !(vse.tutorial je StavTutorialu.Tutorialujeme.Uvod) &&
                            !(vse.tutorial je StavTutorialu.Tutorialujeme.Linky) &&
                            !(vse.tutorial je StavTutorialu.Tutorialujeme.Zastavky) &&
                            !(vse.tutorial je StavTutorialu.Tutorialujeme.Garaz) &&
                            !(vse.tutorial je StavTutorialu.Tutorialujeme.Obchod)
                        ) DropdownMenuItem(
                            text = {
                                Text(stringResource(R.string.nastaveni))
                            },
                            onClick = {
                                dialogState.show(
                                    confirmButton = {
                                        TextButton(
                                            onClick = {
                                                dialogState.hideTopMost()
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
                                    content = {
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
                                                value = stringResource(tema.jmeno),
                                                leadingIcon = {
                                                    Icon(Icons.Default.Circle, null, tint = tema.barva)
                                                },
                                                onValueChange = {},
                                                label = { Text(stringResource(R.string.tema_aplikace)) },
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
                                                            menic.zmenitDPInfo {
                                                                it.copy(
                                                                    tema = tohleTema
                                                                )
                                                            }
                                                            tema = tohleTema
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
                                                checked = evc,
                                                onCheckedChange = {
                                                    menic.zmenitNastaveni { n ->
                                                        n.copy(
                                                            automatickyUdelovatEvC = it
                                                        )
                                                    }
                                                    evc = it
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
                                                checked = multi,
                                                onCheckedChange = {
                                                    menic.zmenitNastaveni { n ->
                                                        n.copy(
                                                            vicenasobnyKupovani = it
                                                        )
                                                    }
                                                    multi = it
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
                                                checked = debug,
                                                onCheckedChange = {
                                                    DEBUG_TEXT = !DEBUG_TEXT
                                                    debug = DEBUG_TEXT
                                                }
                                            )
                                        }
                                    }
                                )
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
                                dialogState.show(
                                    confirmButton = {
                                        TextButton(
                                            onClick = {
                                                dialogState.hideTopMost()
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
                                    content = {
                                        Text("Verze aplikace: ${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})")
                                        Text("2021-${LocalDate.now().year} RO studios, člen skupiny JARO")
                                        Text("2019-${LocalDate.now().year} JARO")
                                        Text("Všechna data o busech byla ukradena bez svolení majitelů. Na naše data není v žádném případě spolehnutí,")
                                        Text("Simulate crash...", Modifier.clickable {
                                            throw RuntimeException("Test exception")
                                        }, fontSize = 10.sp)
                                    }
                                )
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
            fun PointerInputScope.onClick(it: Offset) {
                val pitomyDP = ziskatDP()
                if (editor) {
                    val staraUlice = pitomyDP.ulice.najitObdelnikVeKteremJe(
                        offset = it,
                        tx = tx, ty = ty, priblizeni = priblizeni
                    ) { ulice ->
                        DpRect(
                            left = ulice.zacatekX,
                            top = ulice.zacatekY,
                            right = ulice.konecX,
                            bottom = ulice.konecY,
                        )
                    } ?: return

                    dialogState.show(
                        confirmButton = {},
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    dialogState.hideTopMost()
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
                        content = {
                            OutlinedButton(
                                onClick = {
                                    menic.zmenitUlice {
                                        if (staraUlice.maZastavku)
                                            replaceBy(staraUlice.zasebevrazdujZastavku()) { it.id }
                                        else
                                            replaceBy(staraUlice.copy(zastavka = Zastavka())) { it.id }
                                    }
                                    menic.zmenitPrachy {
                                        it - if (!staraUlice.maZastavku) cenaZastavky else cenaZastavky / 5
                                    }
                                    dialogState.hideTopMost()
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
                                !(vse.tutorial je StavTutorialu.Tutorialujeme.Uvod) &&
                                !(vse.tutorial je StavTutorialu.Tutorialujeme.Linky) &&
                                !(vse.tutorial je StavTutorialu.Tutorialujeme.Zastavky) &&
                                !(vse.tutorial je StavTutorialu.Tutorialujeme.Garaz) &&
                                !(vse.tutorial je StavTutorialu.Tutorialujeme.Obchod)
                            ) OutlinedButton(
                                onClick = {
                                    menic.zmenitUlice {
                                        if (staraUlice.maTrolej)
                                            replaceBy(staraUlice.copy(maTrolej = false)) { it.id }
                                        else
                                            replaceBy(staraUlice.copy(maTrolej = true)) { it.id }
                                    }
                                    menic.zmenitPrachy {
                                        it - if (!staraUlice.maTrolej) cenaTroleje else cenaTroleje / 5
                                    }
                                    dialogState.hideTopMost()
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
                    )
                }
            }

            @Suppress("UNUSED_PARAMETER")
            fun PointerInputScope.onTransform(p0: Offset, pan: Offset, zoom: Float, p3: Float) {
                val pitomyDP = ziskatDP()
                scope.launch {
                    // t - posunuti, p - prostředek, x - max, i - min

                    val (start, stop) = pitomyDP.rohyMesta
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
                dp = dp,
                tx = tx,
                ty = ty,
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
                        !(vse.tutorial je StavTutorialu.Tutorialujeme.Uvod)
                    ) Text(
                        text = vse.prachy.asString(),
                        Modifier
                            .weight(1F)
                            .padding(all = 16.dp),
                        textAlign = TextAlign.Start,
                    )

                    if (
                        !(vse.tutorial je StavTutorialu.Tutorialujeme.Uvod) &&
                        !(vse.tutorial je StavTutorialu.Tutorialujeme.Linky) &&
                        !(vse.tutorial je StavTutorialu.Tutorialujeme.Zastavky) &&
                        !(vse.tutorial je StavTutorialu.Tutorialujeme.Garaz) &&
                        !(vse.tutorial je StavTutorialu.Tutorialujeme.Obchod)
                    ) Text(
                        text = dp.info.zisk.asString(),
                        Modifier
                            .weight(1F)
                            .padding(all = 16.dp),
                        textAlign = TextAlign.End,
                    )
                }
                Spacer(modifier = Modifier.weight(1F))
                if (
                    !(vse.tutorial je StavTutorialu.Tutorialujeme.Uvod) &&
                    !(vse.tutorial je StavTutorialu.Tutorialujeme.Linky) &&
                    !(vse.tutorial je StavTutorialu.Tutorialujeme.Garaz) &&
                    !(vse.tutorial je StavTutorialu.Tutorialujeme.Obchod)
                ) Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(
                        onClick = {
                            editor = !editor

                            if (!editor) menic.zmenitTutorial {
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
                        !(vse.tutorial je StavTutorialu.Tutorialujeme.Linky) &&
                        !(vse.tutorial je StavTutorialu.Tutorialujeme.Zastavky) &&
                        !(vse.tutorial je StavTutorialu.Tutorialujeme.Garaz) &&
                        !(vse.tutorial je StavTutorialu.Tutorialujeme.Obchod)
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
                        !(vse.tutorial je StavTutorialu.Tutorialujeme.Uvod) &&
                        !(vse.tutorial je StavTutorialu.Tutorialujeme.Linky) &&
                        !(vse.tutorial je StavTutorialu.Tutorialujeme.Zastavky)
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

fun zobrazitDosahlosti(dosahlosti: List<Dosahlost.NormalniDosahlost>) = dialogState.show(
    confirmButton = {
        TextButton(
            onClick = {
                dialogState.hideTopMost()
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
    content = {
        Dosahlosti(dosahlosti)
    },
)

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