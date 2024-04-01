package cz.jaro.dopravnipodniky.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditRoad
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toOffset
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import cz.jaro.compose_dialog.show
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.Vse
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Krizovatka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.TypKrizovatky
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Zastavka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.krizovatkyNaLince
import cz.jaro.dopravnipodniky.data.dopravnipodnik.maZastavku
import cz.jaro.dopravnipodniky.data.dopravnipodnik.rohyMesta
import cz.jaro.dopravnipodniky.data.dopravnipodnik.seznamKrizovatek
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.zasebevrazdujZastavku
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.data.dosahlosti.DosahlostCallback
import cz.jaro.dopravnipodniky.dialogState
import cz.jaro.dopravnipodniky.shared.Menic
import cz.jaro.dopravnipodniky.shared.SharedViewModel
import cz.jaro.dopravnipodniky.shared.StavHry
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.cenaKruhace
import cz.jaro.dopravnipodniky.shared.cenaTroleje
import cz.jaro.dopravnipodniky.shared.cenaZastavky
import cz.jaro.dopravnipodniky.shared.composeString
import cz.jaro.dopravnipodniky.shared.je
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.shared.jednotky.asString
import cz.jaro.dopravnipodniky.shared.jednotky.minus
import cz.jaro.dopravnipodniky.shared.jednotky.plus
import cz.jaro.dopravnipodniky.shared.jednotky.toDpSKrizovatkama
import cz.jaro.dopravnipodniky.shared.jednotky.ulicovychBloku
import cz.jaro.dopravnipodniky.shared.maximalniOddaleni
import cz.jaro.dopravnipodniky.shared.najitObdelnikVeKteremJe
import cz.jaro.dopravnipodniky.shared.odNulaNula
import cz.jaro.dopravnipodniky.shared.oddalenyRezim
import cz.jaro.dopravnipodniky.shared.predsazeniKrizovatky
import cz.jaro.dopravnipodniky.shared.replaceBy
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.shared.stavHry
import cz.jaro.dopravnipodniky.shared.toOffsetSPriblizenim
import cz.jaro.dopravnipodniky.shared.ulicovyBlok
import cz.jaro.dopravnipodniky.snackbarHostState
import cz.jaro.dopravnipodniky.ui.destinations.GarazScreenDestination
import cz.jaro.dopravnipodniky.ui.destinations.LinkyScreenDestination
import cz.jaro.dopravnipodniky.ui.malovani.Mesto
import cz.jaro.dopravnipodniky.ui.theme.DpTheme
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import kotlin.math.min
import kotlin.reflect.KClass

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
        dosahni = viewModel.dosahni,
    )
    else LinearProgressIndicator(Modifier.fillMaxWidth())
}

var DEBUG_MODE by mutableStateOf(false)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    dp: DopravniPodnik,
    vse: Vse,
    menic: Menic,
    navigate: (Direction) -> Unit,
    ziskatDP: () -> DopravniPodnik,
    dosahni: (KClass<out Dosahlost>) -> Unit,
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
                DosahlostCallback { dosahlost, dosahlosti ->
                    MainScope().launch {
                        val result = snackbarHostState.showSnackbar(
                            message = "Splněno ${res.getString(dosahlost.jmeno)}",
                            actionLabel = "Zobrazit",
                            duration = SnackbarDuration.Long,
                            withDismissAction = true,
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            zobrazitDosahlosti(dosahlosti)
                        }
                    }
                }
            }
        })
    }
    Scaffold(
        topBar = {
            MainAppBar(dp, dosahni, menic, navigate, vse)
        }
    ) { paddingValues ->
        Surface(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            val malovatBusy = stavHry != StavHry.Hra || !editor && priblizeni > oddalenyRezim
            val mestoModifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { off ->
                        val pitomyDP = ziskatDP()
                        if (!editor) return@detectTapGestures
                        pitomyDP.seznamKrizovatek
                            .toList()
                            .najitObdelnikVeKteremJe(
                                offset = off,
                                tx = tx, ty = ty, priblizeni = priblizeni
                            ) { (pozice, _) ->
                                DpRect(
                                    left = pozice.x.toDpSKrizovatkama() - predsazeniKrizovatky - sirkaUlice / 2,
                                    top = pozice.y.toDpSKrizovatkama() - predsazeniKrizovatky - sirkaUlice / 2,
                                    right = pozice.x.toDpSKrizovatkama() + predsazeniKrizovatky + sirkaUlice * 3 / 2,
                                    bottom = pozice.y.toDpSKrizovatkama() + predsazeniKrizovatky + sirkaUlice * 3 / 2,
                                )
                            }
                            ?.let { (pozice, krizovatka) ->
                                kliklNaKrizovatku(krizovatka, menic, dp, pozice)
                                return@detectTapGestures
                            }

                        pitomyDP.ulice
                            .najitObdelnikVeKteremJe(
                                offset = off,
                                tx = tx, ty = ty, priblizeni = priblizeni
                            ) { ulice ->
                                DpRect(
                                    left = ulice.zacatekX,
                                    top = ulice.zacatekY,
                                    right = ulice.konecX,
                                    bottom = ulice.konecY,
                                )
                            }
                            ?.let { staraUlice ->
                                kliklNaUlici(menic, staraUlice, vse)
                                return@detectTapGestures
                            }
                    }
                }
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        val pitomyDP = ziskatDP()
                        scope.launch {
                            // t - posunuti, p - prostředek, x - max, i - min

                            val (start, stop) = pitomyDP.rohyMesta
                            val m = start
                                .toDpSKrizovatkama()
                                .minus(ulicovyBlok * 2)
                                .toOffsetSPriblizenim(priblizeni)
                            val i = stop
                                .toDpSKrizovatkama()
                                .plus(ulicovyBlok * 2)
                                .toOffsetSPriblizenim(priblizeni)
                                .minus(
                                    IntOffset(
                                        this@pointerInput.size.width,
                                        this@pointerInput.size.height
                                    ).toOffset()
                                )
                            val p = (i + m) / 2F

                            val ti = i.odNulaNula(priblizeni)
                            val tm = m.odNulaNula(priblizeni)
                            val pt = p.odNulaNula(priblizeni)

                            tx = if (ti.x > tm.x) pt.x else tx
                                .minus(pan.odNulaNula(priblizeni).x)
                                .coerceIn(ti.x, tm.x)
                            ty = if (ti.y > tm.y) pt.y else ty
                                .minus(pan.odNulaNula(priblizeni).y)
                                .coerceIn(ti.y, tm.y)
                            priblizeni = (priblizeni * zoom).coerceAtLeast(maximalniOddaleni)
                        }
//                                println("onGesture(centroid=$centroid, pan=$pan, zoom=$zoom, rotation=$rotation)")
//                                println("tx=${tx}, ty=${ty}, priblizeni=${priblizeni}")
                    }
                }
                .onSizeChanged {
                    size = it
                }
            Mesto(
                malovatBusy = malovatBusy,
                malovatLinky = !malovatBusy,
                malovatTroleje = priblizeni > oddalenyRezim,
                kliklyKrizovatky = null,
                dp = dp,
                tx = tx,
                ty = ty,
                priblizeni = priblizeni,
                modifier = mestoModifier,
            )
            Column(
                Modifier.fillMaxSize(),
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
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
                    ) IconButton(
                        onClick = {
                            dialogState.show(
                                content = {
                                    DpTheme(
                                        useDynamicColor = false,
                                        theme = dp.info.tema,
                                    ) {
                                        Scaffold(
                                            topBar = {
                                                TopAppBar(
                                                    title = {
                                                        Text(stringResource(R.string.podrobnosti_o_zisku))
                                                    },
                                                    navigationIcon = {
                                                        IconButton(
                                                            onClick = {
                                                                hide()
                                                            }
                                                        ) {
                                                            Icon(
                                                                Icons.AutoMirrored.Filled.ArrowBack,
                                                                null
                                                            )
                                                        }
                                                    }
                                                )
                                            }
                                        ) {
                                            Text(
                                                dp.info.detailZisku.composeString(),
                                                Modifier
                                                    .verticalScroll(rememberScrollState())
                                                    .padding(it)
                                                    .padding(all = 16.dp)
                                            )
                                        }
                                    }
                                },
                                properties = DialogProperties(
                                    usePlatformDefaultWidth = false,
                                )
                            )
                        }
                    ) {
                        Icon(Icons.Default.Info, stringResource(R.string.podrobnosti_o_zisku))
                    }

                    if (
                        !(vse.tutorial je StavTutorialu.Tutorialujeme.Uvod) &&
                        !(vse.tutorial je StavTutorialu.Tutorialujeme.Linky) &&
                        !(vse.tutorial je StavTutorialu.Tutorialujeme.Zastavky) &&
                        !(vse.tutorial je StavTutorialu.Tutorialujeme.Garaz) &&
                        !(vse.tutorial je StavTutorialu.Tutorialujeme.Obchod)
                    ) Text(
                        text = dp.info.zisk.asString(),
                        Modifier
                            .padding(top = 16.dp, end = 16.dp, bottom = 16.dp)
                            .padding(start = 0.dp),
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
                                    Icon(
                                        Icons.Default.Edit,
                                        null,
                                        Modifier.size(ButtonDefaults.IconSize)
                                    )
                                else
                                    Icon(
                                        Icons.Default.Done,
                                        null,
                                        Modifier.size(ButtonDefaults.IconSize)
                                    )
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

private fun kliklNaUlici(
    menic: Menic,
    staraUlice: Ulice,
    vse: Vse
) {
    dialogState.show(
        confirmButton = {},
        dismissButton = {
            TextButton(
                onClick = {
                    hide()
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
                    hide()
                },
                Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(),
            ) {

                Text(
                    text = if (!staraUlice.maZastavku)
                        stringResource(
                            R.string.vytvorit_zastavku,
                            cenaZastavky.asString()
                        )
                    else stringResource(
                        R.string.odstranit_zastavku,
                        (cenaZastavky / 5).asString()
                    ),
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
                    hide()
                },
                Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = if (!staraUlice.maTrolej)
                        stringResource(
                            R.string.postavit_troleje,
                            cenaTroleje.asString()
                        )
                    else stringResource(
                        R.string.odstranit_troleje,
                        (cenaTroleje / 5).asString()
                    ),
                    textAlign = TextAlign.Justify
                )
            }
        }
    )
    return
}

private fun kliklNaKrizovatku(
    krizovatka: Krizovatka?,
    menic: Menic,
    dp: DopravniPodnik,
    pozice: Pozice<UlicovyBlok>
) {
    dialogState.show(
        confirmButton = {},
        dismissButton = {
            TextButton(
                onClick = {
                    hide()
                }
            ) {
                Text(stringResource(R.string.zrusit))
            }
        },
        icon = {
            Icon(Icons.Default.EditRoad, null)
        },
        title = {
            Text(stringResource(R.string.upravit_krizovatku))
        },
        content = {
            OutlinedButton(
                onClick = {
                    fun zmenit() {
                        menic.zmenitKrizovatky {
                            if (krizovatka == null)
                                add(
                                    Krizovatka(
                                        pozice = pozice,
                                        typ = TypKrizovatky.Kruhac
                                    )
                                )
                            else if (krizovatka.typ != TypKrizovatky.Kruhac)
                                replaceBy(krizovatka.copy(typ = TypKrizovatky.Kruhac)) { it.id }
                            else
                                remove(krizovatka)
                        }
                        menic.zmenitPrachy {
                            it - if (krizovatka == null || krizovatka.typ != TypKrizovatky.Kruhac) cenaZastavky else cenaZastavky / 5
                        }
                        hide()
                    }

                    val spatneLinky = dp.linky.filter { linka ->
                        linka.ulice(dp).krizovatkyNaLince().let {
                            it.first() == pozice || it.last() == pozice
                        }
                    }
                    if (krizovatka != null && krizovatka.typ == TypKrizovatky.Kruhac && spatneLinky.isNotEmpty()) dialogState.show(
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    hide()
                                    zmenit()
                                },
                            ) {
                                Text(stringResource(android.R.string.ok))
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    hide()
                                },
                            ) {
                                Text(stringResource(R.string.zrusit))
                            }
                        },
                        icon = {
                            Icon(Icons.Default.WarningAmber, null)
                        },
                        title = {
                            Text(stringResource(R.string.opravdu_chcete_odstranit_kruhovy_objezd))
                        },
                        content = {
                            Text(stringResource(
                                R.string.jeliko_na_t_to_k_i_ovatce_kon,
                                pluralStringResource(R.plurals.linka1p, spatneLinky.count()),
                                spatneLinky.joinToString(", ") { it.cislo }.reversed().replaceFirst(" ,", " a ").reversed(),
                                pluralStringResource(R.plurals.tato_linka6p, spatneLinky.count()),
                                pluralStringResource(R.plurals.linka2p, spatneLinky.count()),
                            ))
                        },
                    )
                    else zmenit()
                },
                Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(),
            ) {

                Text(
                    text = if (krizovatka == null || krizovatka.typ != TypKrizovatky.Kruhac)
                        stringResource(
                            R.string.vytvorit_kruhac,
                            cenaKruhace.asString()
                        )
                    else stringResource(
                        R.string.odstranit_kruhac,
                        (cenaKruhace / 5).asString()
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    )
    return
}

