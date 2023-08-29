package cz.jaro.dopravnipodniky.ui.linky.vybirani

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.seznamKrizovatek
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.shared.oddalenyRezim
import cz.jaro.dopravnipodniky.ui.malovani.draw
import cz.jaro.dopravnipodniky.ui.malovani.getNamalovatLinky
import cz.jaro.dopravnipodniky.ui.malovani.nakreslitTroleje
import cz.jaro.dopravnipodniky.ui.malovani.nakreslitTrolejeNaKrizovatku
import cz.jaro.dopravnipodniky.ui.malovani.namalovatKrizovatku

@Composable
fun NeceleMesto(
    dp: DopravniPodnik,
    modifier: Modifier,
    tx: Float,
    ty: Float,
    priblizeni: Float,
    kliklyKrizovatky: List<Pozice<UlicovyBlok>>
) {

    val nakreslitLinky = remember(
        dp.linky, dp.ulice
    ) {
        getNamalovatLinky(dp.linky, dp.ulice)
    }

    Canvas(
        modifier = modifier,
    ) {
//        drawCircle(
//            color = Color.Yellow,
//            center = pos.value,
//            radius = 4.dp.toPx(),
//        )
//        dp.seznamKrizovatek.forEach { krizovatka ->
//            drawRect(
//                color = Color.Green,
//                topLeft = (krizovatka.dpSUlicema.toPx() - sirkaUlice.toPx())
//                    .minus(size.center)
//                    .times(priblizeni)
//                    .plus(size.center)
//                    .plus(Offset(tx, ty).times(priblizeni)),
//                size = Size(sirkaUlice.toPx(), sirkaUlice.toPx())
//                    .times(2F)
//                    .times(2F)
//                    .times(priblizeni),
//            )
//        }
        scale(
            scale = priblizeni,
        ) {
            translate(
                left = tx + size.center.x,
                top = ty + size.center.y,
            ) {

                dp.ulice.forEach { ulice ->
                    ulice.draw()
                }

                dp.ulice.forEach { ulice ->
                    ulice.baraky.forEach { barak ->
                        barak.draw(dp.tema, ulice)
                    }
                }

                dp.seznamKrizovatek.forEach { krizovatka ->
                    namalovatKrizovatku(dp, krizovatka)
                }

                nakreslitLinky.forEach { nakreslitKousekLinky ->
                    nakreslitKousekLinky()
                }

                namalovatVybiraniLinky(
                    kliklyKrizovatky = kliklyKrizovatky,
                    barva = dp.tema.barva,
                )

                if (priblizeni > oddalenyRezim) {
                    dp.ulice.forEach { ulice ->
                        if (ulice.maTrolej) ulice.nakreslitTroleje()
                    }
                    dp.seznamKrizovatek.forEach { krizovatka ->
                        nakreslitTrolejeNaKrizovatku(dp, krizovatka)
                    }
                }
            }
        }
    }
}