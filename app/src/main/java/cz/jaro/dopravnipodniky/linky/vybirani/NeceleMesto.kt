package cz.jaro.dopravnipodniky.linky.vybirani

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import cz.jaro.dopravnipodniky.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.dopravnipodnik.seznamKrizovatek
import cz.jaro.dopravnipodniky.malovani.draw
import cz.jaro.dopravnipodniky.malovani.getNamalovatLinky
import cz.jaro.dopravnipodniky.malovani.nakreslitTroleje
import cz.jaro.dopravnipodniky.malovani.nakreslitTrolejeNaKrizovatku
import cz.jaro.dopravnipodniky.malovani.namalovatKrizovatku
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.shared.oddalenyRezim

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
        dp.linky, dp.ulicove
    ) {
        getNamalovatLinky(dp.linky, dp.ulicove)
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

                dp.ulicove.forEach { ulice ->
                    ulice.draw()
                }

                dp.ulicove.forEach { ulice ->
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
                    dp.ulicove.forEach { ulice ->
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