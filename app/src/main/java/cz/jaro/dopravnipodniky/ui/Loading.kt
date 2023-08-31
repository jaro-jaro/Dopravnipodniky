package cz.jaro.dopravnipodniky.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.shared.jednotky.kilometruZaHodinu
import cz.jaro.dopravnipodniky.shared.jednotky.metru
import cz.jaro.dopravnipodniky.shared.jednotky.tiku
import cz.jaro.dopravnipodniky.shared.jednotky.toDp
import cz.jaro.dopravnipodniky.shared.jednotky.toDuration
import cz.jaro.dopravnipodniky.shared.millisPerTik
import cz.jaro.dopravnipodniky.shared.odsazeniBusu
import cz.jaro.dopravnipodniky.shared.sedChodniku
import cz.jaro.dopravnipodniky.shared.sedUlice
import cz.jaro.dopravnipodniky.shared.sirkaChodniku
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.ui.main.Offset
import cz.jaro.dopravnipodniky.ui.theme.Barvicka
import cz.jaro.dopravnipodniky.ui.theme.DpTheme
import cz.jaro.dopravnipodniky.ui.theme.Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Composable
fun Loading() = DpTheme(
    useDynamicColor = true,
    theme = Theme.Zlute
) {
    Surface {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            CircularProgressIndicator()
            Text("Hra se načítá...", Modifier.padding(all = 8.dp))

            var sirka by remember { mutableStateOf(Float.MAX_VALUE.dp) }
            val delkaBusu = remember { 12.metru }
            val sirkaBusu = remember { 2.5.metru }
            var poloha by remember { mutableStateOf(-delkaBusu.toDp()) }
            var poloha2 by remember { mutableStateOf(-delkaBusu.toDp()) }
            val rychlostBusu = remember { 200.kilometruZaHodinu }
            LaunchedEffect(Unit) {
                launch(Dispatchers.IO) {
                    flow {
                        while (currentCoroutineContext().isActive) {
                            emit(System.currentTimeMillis())
                            delay(0)
                        }
                    }
                        .flowOn(Dispatchers.IO)
                        .distinctUntilChanged()
                        .map { millis ->
                            millis / (millisPerTik / 2)
                        }
                        .distinctUntilChanged()
                        .collect {
                            poloha += rychlostBusu * 1.tiku.toDuration()
                            if (poloha > sirka) {
                                poloha = -delkaBusu.toDp()
                            }
                            poloha2 += rychlostBusu * 1.tiku.toDuration()
                            if (poloha2 > sirka) {
                                poloha2 = -delkaBusu.toDp()
                            }
                        }
                }
            }
//            val density = LocalDensity.current
            Canvas(
                Modifier
                    .fillMaxWidth()
                    .height(sirkaUlice)
//                    .onPlaced {
//                        with(density) {
//                            sirka = it.size.width.toDp()
//                        }
//                    }
            ) {
                if (sirka == Float.MAX_VALUE.dp) {
                    sirka = size.width.toDp() / 2
                    poloha2 = sirka / 2 - delkaBusu.toDp()
                }
                scale(
                    2F
                ) {
                    drawRect(color = sedUlice)
                    drawRect(
                        color = sedChodniku,
                        size = Size(
                            width = size.width,
                            height = sirkaChodniku.toPx()
                        )
                    )
                    drawRect(
                        color = sedChodniku,
                        topLeft = Offset(
                            y = sirkaUlice.toPx() - sirkaChodniku.toPx()
                        ),
                        size = Size(
                            width = size.width,
                            height = sirkaChodniku.toPx()
                        )
                    )
                    drawRoundRect(
                        color = Barvicka.Zluta.barva,
                        topLeft = Offset(
                            x = poloha.toPx() + sirka.toPx() / 2,
                            y = sirkaUlice.toPx() - odsazeniBusu.toPx() - sirkaBusu.toDp().toPx()
                        ),
                        size = Size(
                            width = delkaBusu.toDp().toPx(),
                            height = sirkaBusu.toDp().toPx(),
                        ),
                        cornerRadius = CornerRadius(3F.dp.toPx())
                    )
                    drawRoundRect(
                        color = Barvicka.Cervena.barva,
                        topLeft = Offset(
                            x = poloha2.toPx() + sirka.toPx() / 2,
                            y = sirkaUlice.toPx() - odsazeniBusu.toPx() - sirkaBusu.toDp().toPx()
                        ),
                        size = Size(
                            width = delkaBusu.toDp().toPx(),
                            height = sirkaBusu.toDp().toPx(),
                        ),
                        cornerRadius = CornerRadius(3F.dp.toPx())
                    )
                }
            }
        }
    }
}