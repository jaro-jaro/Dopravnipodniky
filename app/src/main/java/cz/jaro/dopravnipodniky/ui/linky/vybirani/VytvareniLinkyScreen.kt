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
import androidx.compose.material3.AlertDialog
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toOffset
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DPInfo
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.rohyMesta
import cz.jaro.dopravnipodniky.data.dopravnipodnik.seznamKrizovatek
import cz.jaro.dopravnipodniky.data.dopravnipodnik.stred
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.shared.SharedViewModel
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.je
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.shared.jednotky.minus
import cz.jaro.dopravnipodniky.shared.jednotky.plus
import cz.jaro.dopravnipodniky.shared.jednotky.sousedi
import cz.jaro.dopravnipodniky.shared.jednotky.toDpOffset
import cz.jaro.dopravnipodniky.shared.jednotky.toDpSKrizovatkama
import cz.jaro.dopravnipodniky.shared.maximalniOddaleni
import cz.jaro.dopravnipodniky.shared.najitObdelnikVeKteremJe
import cz.jaro.dopravnipodniky.shared.odNulaNula
import cz.jaro.dopravnipodniky.shared.oddalenyRezim
import cz.jaro.dopravnipodniky.shared.pocatecniPriblizeni
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.shared.toOffsetSPriblizenim
import cz.jaro.dopravnipodniky.shared.ulicovyBlok
import cz.jaro.dopravnipodniky.ui.malovani.Mesto
import cz.jaro.dopravnipodniky.ui.theme.Barvicka
import org.koin.androidx.compose.koinViewModel
import java.util.UUID
import kotlin.reflect.KClass

@Composable
@Destination
fun VytvareniLinkyScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = koinViewModel<SharedViewModel>()

    LaunchedEffect(Unit) {
        viewModel.zmenitTutorial {
            if (it je StavTutorialu.Tutorialujeme.Uvod)
                StavTutorialu.Tutorialujeme.Linky
            else it
        }
    }

    val dpInfo by viewModel.dpInfo.collectAsStateWithLifecycle()
    val linky by viewModel.linky.collectAsStateWithLifecycle()
    val ulicove by viewModel.ulice.collectAsStateWithLifecycle()
    val tutorial by viewModel.tutorial.collectAsStateWithLifecycle()

    if (
        dpInfo != null &&
        linky != null &&
        ulicove != null &&
        tutorial != null
    ) VytvareniLinkyScreen(
        dpInfo = dpInfo!!,
        linky = linky!!,
        ulicove = ulicove!!,
        tutorial = tutorial!!,
        zmenitTutorial = viewModel::zmenitTutorial,
        zmenitLinky = viewModel::zmenitLinky,
        navigateUp = navigator::navigateUp,
        dosahni = viewModel.dosahni,
    )
}

var pos by mutableStateOf(Offset.Zero)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VytvareniLinkyScreen(
    dpInfo: DPInfo,
    tutorial: StavTutorialu,
    zmenitTutorial: ((StavTutorialu) -> StavTutorialu) -> Unit,
    linky: List<Linka>,
    ulicove: List<Ulice>,
    zmenitLinky: (MutableList<Linka>.() -> Unit) -> Unit,
    navigateUp: () -> Unit,
    dosahni: (KClass<out Dosahlost>) -> Unit,
) {
    var waitForExit by remember { mutableStateOf(null as UUID?) }
    val density = LocalDensity.current
    val stred = remember { ulicove.stred.toDpSKrizovatkama() }
    var tx by remember { mutableFloatStateOf(with(density) { -stred.x.toPx() * pocatecniPriblizeni }) }
    var ty by remember { mutableFloatStateOf(with(density) { -stred.y.toPx() * pocatecniPriblizeni }) }
    var priblizeni by remember { mutableFloatStateOf(pocatecniPriblizeni) }
//    val realPriblizeni = animateFloatAsState(priblizeni.first, label = "zoom").value to priblizeni.second
    var kliklyKrizovatky by remember { mutableStateOf(emptyList<Pozice<UlicovyBlok>>()) }

    fun PointerInputScope.delejNeco(centroid: Offset, listSize: Int, zoom: Float, pan: Offset) {

        pos = centroid

        if (listSize == 1) run {

            val k = ulicove.seznamKrizovatek.najitObdelnikVeKteremJe(
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
                if (ulicove.any { it.zacatek == minOf(k, l) && it.konec == maxOf(k, l) }) {
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

                val (start, stop) = ulicove.rohyMesta
                val m = start.toDpSKrizovatkama()
                    .minus(ulicovyBlok * 2)
                    .toOffsetSPriblizenim(priblizeni)
                val i = stop.toDpSKrizovatkama()
                    .plus(ulicovyBlok * 2)
                    .toOffsetSPriblizenim(priblizeni)
                    .minus(IntOffset(size.width, size.height).toOffset())
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

    LaunchedEffect(linky, waitForExit) {
        if (waitForExit != null && linky.any { it.id == waitForExit }) {
            navigateUp()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.nova_linka))
                },
                actions = {
                    if (tutorial je StavTutorialu.Tutorialujeme.Linky) IconButton(
                        onClick = {
                            zmenitTutorial {
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
                ulice = ulicove,
                linky = linky,
                busy = emptyList(),
                dpInfo = dpInfo,
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
                    var zobrazit by remember { mutableStateOf(false) }
                    var expanded by remember { mutableStateOf(false) }
                    var cisloLinky by remember { mutableStateOf("") }
                    var barva by remember { mutableStateOf(Barvicka.Cervena) }
                    Button(
                        onClick = {
                            zobrazit = true
                        },
                        Modifier
                            .padding(all = 8.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.hotovo)
                        )
                    }
                    val scope = rememberCoroutineScope()
                    val ctx = LocalContext.current
                    if (zobrazit) AlertDialog(
                        onDismissRequest = {
                            zobrazit = false
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    if (cisloLinky.isEmpty()) {
                                        Toast.makeText(ctx, R.string.spatne_cislo_linky, Toast.LENGTH_LONG).show()
                                        return@TextButton
                                    }
                                    if (linky.any { it.cislo == cisloLinky }) {
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
                                            ulicove.first { it.zacatek == minOf(prvni, druha) && it.konec == maxOf(prvni, druha) }.id
                                        },
                                        barvicka = barva
                                    )

                                    dosahni(Dosahlost.SkupinovaDosahlost.Linka::class)

                                    if (linky.any { it.ulice == linka.ulice }) dosahni(Dosahlost.StejneLinky::class)

                                    zmenitLinky {
                                        add(linka)
                                    }
                                    waitForExit = linka.id
                                }
                            ) {
                                Text(stringResource(android.R.string.ok))
                            }
                        },
                        title = {
                            Text(stringResource(id = R.string.vytvorit_linku))
                        },
                        text = {
                            Column(
                                Modifier.fillMaxWidth()
                            ) {
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
                            }
                        },
                    )
                }
            }
        }
    }
}

operator fun Offset.plus(other: Float) = Offset(x + other, y + other)
operator fun Offset.minus(other: Float) = Offset(x - other, y - other)

private fun <E> List<E>.beforeLast(): E = get(lastIndex - 1)
