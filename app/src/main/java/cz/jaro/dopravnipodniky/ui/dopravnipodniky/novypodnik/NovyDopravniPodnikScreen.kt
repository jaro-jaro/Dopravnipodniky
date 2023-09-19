package cz.jaro.dopravnipodniky.ui.dopravnipodniky.novypodnik

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.toOffset
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Route
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.rohyMesta
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.shared.DPID
import cz.jaro.dopravnipodniky.shared.SharedViewModel
import cz.jaro.dopravnipodniky.shared.jednotky.minus
import cz.jaro.dopravnipodniky.shared.jednotky.plus
import cz.jaro.dopravnipodniky.shared.jednotky.toDpSKrizovatkama
import cz.jaro.dopravnipodniky.shared.jednotky.ulicovychBloku
import cz.jaro.dopravnipodniky.shared.novePodniky
import cz.jaro.dopravnipodniky.shared.odNulaNula
import cz.jaro.dopravnipodniky.shared.toOffsetSPriblizenim
import cz.jaro.dopravnipodniky.shared.ulicovyBlok
import cz.jaro.dopravnipodniky.ui.destinations.MainScreenDestination
import cz.jaro.dopravnipodniky.ui.malovani.Mesto
import org.koin.androidx.compose.koinViewModel
import kotlin.math.min
import kotlin.reflect.KFunction1

@Composable
@Destination
fun NovyDopravniPodnikScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = koinViewModel<SharedViewModel>()

    val nastaveni by viewModel.nastaveni.collectAsStateWithLifecycle()
    val tutorial by viewModel.tutorial.collectAsStateWithLifecycle()
    val dosahlosti: List<Dosahlost.NormalniDosahlost>? by viewModel.dosahlosti.collectAsStateWithLifecycle()
    val prachy by viewModel.prachy.collectAsStateWithLifecycle()
    val podniky by novePodniky.collectAsStateWithLifecycle()

    if (
        nastaveni != null &&
        tutorial != null &&
        dosahlosti != null &&
        prachy != null &&
        podniky != null
    ) NovyDopravniPodnikScreen(
        podniky = podniky!!,
        potvrdit = viewModel::vybratMesto,
        pop = { direction: Route, inclusive: Boolean -> navigator.popBackStack(direction.route, inclusive) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NovyDopravniPodnikScreen(
    podniky: Triple<DopravniPodnik, DopravniPodnik, DopravniPodnik>,
    potvrdit: KFunction1<DPID, Unit>,
    pop: (Route, Boolean) -> Unit,
) {
    var dpId by rememberSaveable { mutableStateOf(podniky.first.info.id.toString()) }
    val dp by remember(dpId) { derivedStateOf { podniky.toList().single { it.info.id.toString() == dpId } } }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(R.string.novy_dp))
                }
            )
        }
    ) { paddingValues ->
        Surface(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            var tx by remember { mutableStateOf(null as Float?) }
            var ty by remember { mutableStateOf(null as Float?) }
            var priblizeni by remember { mutableStateOf(null as Float?) }
            Column(
                Modifier.fillMaxSize(),
            ) {
                Text(
                    text = dp.info.jmenoMesta,
                    Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                )
                val density = LocalDensity.current
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F)
                        .onSizeChanged { size ->
                            with(density) {

                                val (start, stop) = dp.ulice.rohyMesta
                                val m = start
                                    .toDpSKrizovatkama()
                                    .minus(ulicovyBlok * 2)
                                    .toOffsetSPriblizenim(priblizeni ?: 1F, size.center.toOffset())
                                val i = stop
                                    .toDpSKrizovatkama()
                                    .plus(ulicovyBlok * 2)
                                    .toOffsetSPriblizenim(priblizeni ?: 1F, size.center.toOffset())
                                    .minus(IntOffset(size.width, size.height).toOffset())
                                val p = (i + m) / 2F

                                tx = p.odNulaNula(priblizeni ?: 1F).x
                                ty = p.odNulaNula(priblizeni ?: 1F).y

                                val sirkaMesta = stop.x - start.x
                                val vyskaMesta = stop.y - start.y
                                val priblizeniPodleSirky = size.width / (sirkaMesta + 4.ulicovychBloku)
                                    .toDpSKrizovatkama()
                                    .toPx()
                                val priblizeniPodleVysky = size.height / (vyskaMesta + 4.ulicovychBloku)
                                    .toDpSKrizovatkama()
                                    .toPx()
                                priblizeni = min(priblizeniPodleSirky, priblizeniPodleVysky)
                            }
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    if (tx != null && ty != null && priblizeni != null) Mesto(
                        malovatBusy = false,
                        malovatLinky = false,
                        malovatTroleje = false,
                        kliklyKrizovatky = emptyList(),
                        ulice = dp.ulice,
                        linky = listOf(),
                        busy = listOf(),
                        tx = tx!!,
                        ty = ty!!,
                        dpInfo = dp.info,
                        priblizeni = priblizeni!!,
                        modifier = Modifier.fillMaxSize()
                    )
                    else CircularProgressIndicator()
                }
                Row(
                    Modifier.fillMaxWidth(),
                ) {
                    IconButton(
                        onClick = {
                            tx = null
                            ty = null
                            priblizeni = null
                            val i = podniky.toList().indexOfFirst { it.info.id == dp.info.id }
                            dpId = podniky.toList()[(i - 1 + 3) % 3].info.id.toString()
                        },
                    ) {
                        Icon(Icons.Default.KeyboardArrowLeft, "Vlevo")
                    }
                    Spacer(Modifier.weight(1F))
                    Button(
                        onClick = {
                            potvrdit(dp.info.id)
                            pop(MainScreenDestination, false)
                        }
                    ) {
                        Text(
                            stringResource(R.string.vybrat_mesto)
                        )
                    }
                    Spacer(Modifier.weight(1F))
                    IconButton(
                        onClick = {
                            tx = null
                            ty = null
                            priblizeni = null
                            val i = podniky.toList().indexOfFirst { it.info.id == dp.info.id }
                            dpId = podniky.toList()[(i + 1) % 3].info.id.toString()
                        },
                    ) {
                        Icon(Icons.Default.KeyboardArrowRight, "Vpravo")
                    }
                }
            }
        }
    }
}