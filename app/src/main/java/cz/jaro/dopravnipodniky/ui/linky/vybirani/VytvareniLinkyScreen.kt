package cz.jaro.dopravnipodniky.ui.linky.vybirani

import android.widget.Toast
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculateCentroid
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChanged
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toOffset
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.Vse
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.rohyMesta
import cz.jaro.dopravnipodniky.data.dopravnipodnik.seznamKrizovatek
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ulice
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.dialogState
import cz.jaro.dopravnipodniky.shared.LinkaID
import cz.jaro.dopravnipodniky.shared.Menic
import cz.jaro.dopravnipodniky.shared.SharedViewModel
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.je
import cz.jaro.dopravnipodniky.shared.jednotky.minus
import cz.jaro.dopravnipodniky.shared.jednotky.plus
import cz.jaro.dopravnipodniky.shared.jednotky.sousedi
import cz.jaro.dopravnipodniky.shared.jednotky.toDpOffset
import cz.jaro.dopravnipodniky.shared.jednotky.toDpSKrizovatkama
import cz.jaro.dopravnipodniky.shared.jednotky.ulicovychBloku
import cz.jaro.dopravnipodniky.shared.maximalniOddaleni
import cz.jaro.dopravnipodniky.shared.najitObdelnikVeKteremJe
import cz.jaro.dopravnipodniky.shared.odNulaNula
import cz.jaro.dopravnipodniky.shared.oddalenyRezim
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.shared.toOffsetSPriblizenim
import cz.jaro.dopravnipodniky.shared.ulicovyBlok
import cz.jaro.dopravnipodniky.ui.malovani.Mesto
import cz.jaro.dopravnipodniky.ui.theme.Barvicka
import org.koin.androidx.compose.koinViewModel
import java.util.UUID
import kotlin.math.min
import kotlin.reflect.KClass

@Composable
@Destination
fun VytvareniLinkyScreen(
    upravovani: LinkaID? = null,
    navigator: DestinationsNavigator,
) {
    val viewModel = koinViewModel<SharedViewModel>()

    LaunchedEffect(Unit) {
        viewModel.menic.zmenitTutorial {
            if (it je StavTutorialu.Tutorialujeme.Uvod)
                StavTutorialu.Tutorialujeme.Linky
            else it
        }
    }

    val dp by viewModel.dp.collectAsStateWithLifecycle()
    val vse by viewModel.vse.collectAsStateWithLifecycle()

    if (
        dp != null &&
        vse != null
    ) VytvareniLinkyScreen(
        upravovani = upravovani,
        dp = dp!!,
        vse = vse!!,
        menic = viewModel.menic,
        navigateUp = navigator::navigateUp,
        dosahni = viewModel.dosahni,
    )
}

var pos by mutableStateOf(Offset.Zero)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VytvareniLinkyScreen(
    upravovani: LinkaID?,
    dp: DopravniPodnik,
    vse: Vse,
    menic: Menic,
    navigateUp: () -> Unit,
    dosahni: (KClass<out Dosahlost>) -> Unit,
) {
    var size by remember { mutableStateOf(null as IntSize?) }
    var waitForExit by remember { mutableStateOf(null as UUID?) }
    val density = LocalDensity.current
    var tx by remember { mutableFloatStateOf(0F) }
    var ty by remember { mutableFloatStateOf(0F) }
    var priblizeni by remember { mutableFloatStateOf(1F) }
    var kliklyKrizovatky by remember {
        mutableStateOf(upravovani?.let { linkaID ->
            dp.linka(linkaID).ulice.flatMap { uliceID -> dp.ulice(uliceID).let { listOf(it.zacatek, it.konec) } }.distinct()
        } ?: emptyList())
    }

    LaunchedEffect(dp.rohyMesta, size) {
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

    fun PointerInputScope.delejNeco(centroid: Offset, listSize: Int, zoom: Float, pan: Offset) {

        pos = centroid

        if (listSize == 1) run {

            val k = dp.seznamKrizovatek.najitObdelnikVeKteremJe(
                offset = centroid,
                tx = tx, ty = ty, priblizeni = priblizeni
            ) {
                DpRect(
                    origin = (it.toDpSKrizovatkama() - sirkaUlice).toDpOffset(),
                    size = DpSize(sirkaUlice * 3, sirkaUlice * 3)
                )
            } ?: return@run

            if (kliklyKrizovatky.isEmpty()) {
                kliklyKrizovatky += k
                return@run
            }
            val l = kliklyKrizovatky.last()
            if (kliklyKrizovatky.size > 1 && k == kliklyKrizovatky.beforeLast()) {
                kliklyKrizovatky -= l
                return@run
            }
            if (k in l.sousedi()) {
                if (dp.ulice.any { it.zacatek == minOf(k, l) && it.konec == maxOf(k, l) }) {
                    if (k !in kliklyKrizovatky/* || BuildConfig.DEBUG*/) {
                        kliklyKrizovatky += k
                    }
                }
            }
        }
        else {
            if (zoom != 1f ||
                pan != Offset.Zero
            ) {
                // t - posunuti, p - prostředek, x - max, i - min

                val (start, stop) = dp.rohyMesta
                val m = start.toDpSKrizovatkama()
                    .minus(ulicovyBlok * 2)
                    .toOffsetSPriblizenim(priblizeni)
                val i = stop.toDpSKrizovatkama()
                    .plus(ulicovyBlok * 2)
                    .toOffsetSPriblizenim(priblizeni)
                    .minus(IntOffset(this.size.width, this.size.height).toOffset())
                val p = (i + m) / 2F

                val ti = i.odNulaNula(priblizeni)
                val tm = m.odNulaNula(priblizeni)
                val pt = p.odNulaNula(priblizeni)

                tx = if (ti.x > tm.x) pt.x else tx.minus(pan.odNulaNula(priblizeni).x).coerceIn(ti.x, tm.x)
                ty = if (ti.y > tm.y) pt.y else ty.minus(pan.odNulaNula(priblizeni).y).coerceIn(ti.y, tm.y)
                priblizeni = (priblizeni * zoom).coerceAtLeast(maximalniOddaleni)
            }
        }
    }

    LaunchedEffect(dp.linky, waitForExit) {
        if (waitForExit != null && dp.linky.any { it.id == waitForExit }) {
            navigateUp()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (upravovani == null) Text(stringResource(R.string.nova_linka))
                    else Text(stringResource(R.string.upravit_vedeni))
                },
                actions = {
                    if (vse.tutorial je StavTutorialu.Tutorialujeme.Linky) IconButton(
                        onClick = {
                            menic.zmenitTutorial {
                                StavTutorialu.Tutorialujeme.Linky
                            }
                        }
                    ) {
                        Icon(Icons.Default.Help, stringResource(R.string.tutorial))
                    }
                    IconButton(
                        onClick = {
                            kliklyKrizovatky = emptyList()
                        }
                    ) {
                        Icon(Icons.Default.Refresh, stringResource(R.string.vyresetovat))
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigateUp()
                        }
                    ) {
                        Icon(Icons.Default.ArrowBack, stringResource(R.string.zpet))
                    }
                },
            )
        }
    ) { paddingValues ->
        Surface(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Mesto(
                malovatBusy = false,
                malovatLinky = true,
                malovatTroleje = priblizeni > oddalenyRezim,
                dp = dp,
                tx = tx,
                ty = ty,
                priblizeni = priblizeni,
                modifier = Modifier
                    .fillMaxSize(1F)
                    .pointerInput(Unit) {
                        detectTapGestures {
                            delejNeco(it, 1, 1F, Offset.Zero)
                        }
                    }
                    .pointerInput(Unit) {
                        awaitEachGesture {
//                            var zoom = 1f
//                            var pan = Offset.Zero
//                            var pastTouchSlop = false
//                            val touchSlop = viewConfiguration.touchSlop
                            awaitFirstDown(requireUnconsumed = false)
                            do {
                                val event = awaitPointerEvent()
                                val canceled = event.changes.any { it.isConsumed }
                                if (!canceled) {
                                    val zoomChange = event.calculateZoom()
                                    val panChange = event.calculatePan()

//                                    if (!pastTouchSlop) {
//                                        zoom *= zoomChange
//                                        pan += panChange
//
//                                        val centroidSize = event.calculateCentroidSize(useCurrent = false)
//                                        val zoomMotion = abs(1 - zoom) * centroidSize
//                                        val panMotion = pan.getDistance()
//
//                                        if (zoomMotion > touchSlop ||
//                                            panMotion > touchSlop
//                                        ) {
//                                            pastTouchSlop = true
//                                        }
//                                    }
//                                    if (pastTouchSlop) {
                                    val centroid = event.calculateCentroid()
                                    if (centroid != Offset.Unspecified) delejNeco(
                                        event.calculateCentroid(),
                                        event.changes.size,
                                        zoomChange,
                                        panChange
                                    )
//                                    }
                                    event.changes.forEach {
                                        if (it.positionChanged()) {
                                            it.consume()
                                        }
                                    }
                                }
                            } while (!canceled && event.changes.any { it.pressed })
                        }
                    }
                    .onSizeChanged {
                        size = it
                    },
                kliklyKrizovatky = kliklyKrizovatky,
            )
            Column(
                Modifier.fillMaxSize(),
            ) {
                Spacer(modifier = Modifier.weight(1F))
                Row(
                    Modifier.fillMaxWidth(),
                ) {
                    Spacer(modifier = Modifier.weight(1F))
                    var expanded by remember { mutableStateOf(false) }
                    var cisloLinky by remember { mutableStateOf("") }
                    var barva by remember { mutableStateOf(Barvicka.Cervena) }
                    val ctx = LocalContext.current
                    Button(
                        onClick = {
                            if (upravovani != null) {

                                if (kliklyKrizovatky.size < 2) {
                                    Toast.makeText(ctx, R.string.linka_kratka, Toast.LENGTH_LONG).show()
                                    return@Button
                                }

                                menic.zmenitBusy {
                                    val novyPocetUlic = kliklyKrizovatky.size - 1

                                    forEachIndexed { i, bus ->
                                        if (bus.linka != upravovani) return@forEachIndexed
                                        this[i] = this[i].copy(
                                            poziceNaLince = this[i].poziceNaLince.coerceAtMost(novyPocetUlic - 1)
                                        )
                                    }
                                }
                                menic.zmenitLinky {
                                    val i = indexOfFirst { it.id == upravovani }
                                    this[i] = this[i].copy(
                                        ulice = kliklyKrizovatky.windowed(2).map { (prvni, druha) ->
                                            dp.ulice.first {
                                                it.zacatek == minOf(prvni, druha) && it.konec == maxOf(prvni, druha)
                                            }.id
                                        },
                                    )
                                }
                                navigateUp()
                            }
                            else dialogState.show(
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            if (cisloLinky.isEmpty()) {
                                                Toast.makeText(ctx, R.string.spatne_cislo_linky, Toast.LENGTH_LONG).show()
                                                return@TextButton
                                            }
                                            if (dp.linky.any { it.cislo == cisloLinky }) {
                                                Toast.makeText(ctx, R.string.linka_existuje, Toast.LENGTH_LONG).show()
                                                return@TextButton
                                            }
                                            if (kliklyKrizovatky.size < 2) {
                                                Toast.makeText(ctx, R.string.linka_kratka, Toast.LENGTH_LONG).show()
                                                return@TextButton
                                            }

                                            val linka = Linka(
                                                cislo = cisloLinky,
                                                ulice = kliklyKrizovatky.windowed(2).map { (prvni, druha) ->
                                                    dp.ulice.first {
                                                        it.zacatek == minOf(prvni, druha) && it.konec == maxOf(
                                                            prvni,
                                                            druha
                                                        )
                                                    }.id
                                                },
                                                barvicka = barva
                                            )

                                            dosahni(Dosahlost.SkupinovaDosahlost.Linka::class)

                                            if (dp.linky.any { it.ulice == linka.ulice }) dosahni(Dosahlost.StejneLinky::class)

                                            menic.zmenitLinky {
                                                add(linka)
                                            }
                                            waitForExit = linka.id
                                            dialogState.hideTopMost()
                                        }
                                    ) {
                                        Text(stringResource(android.R.string.ok))
                                    }
                                },
                                title = {
                                    Text(stringResource(id = R.string.vytvorit_linku))
                                },
                                content = {
                                    OutlinedTextField(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        value = cisloLinky,
                                        onValueChange = {
                                            cisloLinky = it
                                        },
                                        label = { Text(stringResource(R.string.zadejte_cislo_linky)) },
                                    )
                                    ExposedDropdownMenuBox(
                                        expanded = expanded,
                                        onExpandedChange = { expanded = !expanded },
                                    ) {
                                        OutlinedTextField(
                                            modifier = Modifier
                                                .menuAnchor()
                                                .padding(top = 8.dp)
                                                .fillMaxWidth(),
                                            readOnly = true,
                                            value = stringResource(barva.jmeno),
                                            leadingIcon = {
                                                Icon(Icons.Default.Circle, null, tint = barva.barva)
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
                                            Barvicka.entries.forEach { tahleBarva ->
                                                DropdownMenuItem(
                                                    text = { Text(stringResource(tahleBarva.jmeno)) },
                                                    onClick = {
                                                        barva = tahleBarva
                                                        expanded = false
                                                    },
                                                    leadingIcon = {
                                                        Icon(Icons.Default.Circle, null, tint = tahleBarva.barva)
                                                    },
                                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                                )
                                            }
                                        }
                                    }
                                },
                            )
                        },
                        Modifier
                            .padding(all = 8.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.hotovo)
                        )
                    }
                }
            }
        }
    }
}

operator fun Offset.plus(other: Float) = Offset(x + other, y + other)
operator fun Offset.minus(other: Float) = Offset(x - other, y - other)

private fun <E> List<E>.beforeLast(): E = get(lastIndex - 1)
