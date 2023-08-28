package cz.jaro.dopravnipodniky.ui.main

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.Dp
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.seznamKrizovatek
import cz.jaro.dopravnipodniky.data.serializers.DpSerializer
import cz.jaro.dopravnipodniky.shared.oddalenyRezim
import cz.jaro.dopravnipodniky.shared.sedPozadi
import cz.jaro.dopravnipodniky.ui.malovani.draw
import cz.jaro.dopravnipodniky.ui.malovani.getNamalovatLinky
import cz.jaro.dopravnipodniky.ui.malovani.nakreslitTroleje
import cz.jaro.dopravnipodniky.ui.malovani.nakreslitTrolejeNaKrizovatku
import cz.jaro.dopravnipodniky.ui.malovani.namalovatKrizovatku
import kotlinx.serialization.Serializable

typealias SerializableDp = @Serializable(with = DpSerializer::class) Dp

@Composable
fun CeleMesto(
    dp: DopravniPodnik,
//    vse: Vse,
//    upravitDp: ((DopravniPodnik) -> DopravniPodnik) -> Unit,
//    upravitVse: ((Vse) -> Vse) -> Unit,
    modifier: Modifier,
    tx: Float,
    ty: Float,
    priblizeni: Float,
) {

//    println(dp.ulicove.map { "(${it.zacatek.x.value} ${it.zacatek.y.value} - ${it.konec.x.value} ${it.konec.y.value})" })

    val nakreslitLinky = remember(
        dp.linky, dp.ulicove
    ) {
        getNamalovatLinky(dp.linky, dp.ulicove)
    }

    Canvas(
        modifier = modifier,
    ) {
        drawRect(
//            color = dp.tema.darkColorScheme.surface,
//            color = dp.tema.darkColorScheme.surfaceVariant,
            color = sedPozadi,
            topLeft = Offset(),
            size = size
        )

        scale(
            scale = priblizeni,
        ) {
            translate(
                left = tx + size.center.x,
                top = ty + size.center.y,
            ) {

                dp.seznamKrizovatek.forEach { krizovatka ->
                    namalovatKrizovatku(dp, krizovatka)
                }

                dp.ulicove.forEach { ulice ->
                    ulice.draw()
                }

                dp.ulicove.forEach { ulice ->
                    ulice.baraky.forEach { barak ->
                        barak.draw(dp.tema, ulice)
                    }
                }

                if (priblizeni > oddalenyRezim) dp.busy.forEach { bus ->
                bus.draw(dp)
                }

                if (priblizeni < oddalenyRezim) nakreslitLinky.forEach { nakreslitKousekLinky ->
                    nakreslitKousekLinky()
                }

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

fun Offset(x: Float = 0F, y: Float = 0F) = Offset(x, y)