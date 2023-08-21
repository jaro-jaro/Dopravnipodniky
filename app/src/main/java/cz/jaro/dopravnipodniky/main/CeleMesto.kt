package cz.jaro.dopravnipodniky.main

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import cz.jaro.dopravnipodniky.data.serializers.DpSerializer
import cz.jaro.dopravnipodniky.data.Vse
import cz.jaro.dopravnipodniky.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.dopravnipodnik.seznamKrizovatek
import cz.jaro.dopravnipodniky.malovani.draw
import cz.jaro.dopravnipodniky.malovani.nakreslitTroleje
import cz.jaro.dopravnipodniky.malovani.nakreslitTrolejeNaKrizovatku
import cz.jaro.dopravnipodniky.malovani.namalovatKrizovatku
import cz.jaro.dopravnipodniky.shared.oddalenyRezim
import cz.jaro.dopravnipodniky.shared.sedPozadi
import kotlinx.serialization.Serializable

typealias SerializableDp = @Serializable(with = DpSerializer::class) Dp

@Composable
fun CeleMesto(
    dp: DopravniPodnik,
    vse: Vse,
//    upravitDp: ((DopravniPodnik) -> DopravniPodnik) -> Unit,
//    upravitVse: ((Vse) -> Vse) -> Unit,
    modifier: Modifier,
    tx: Float,
    ty: Float,
    priblizeni: Float,
    vykreslitBusy: Boolean? = null,
    vykreslitLinky: Boolean? = null,
//    vybiraniLinky: Boolean = false,
) {

//    println(dp.ulicove.map { "(${it.zacatek.x.value} ${it.zacatek.y.value} - ${it.konec.x.value} ${it.konec.y.value})" })

    val nezajimaMeVykreslovani = vykreslitBusy == null && vykreslitLinky == null
    val opravduVykreslitBusy = if (nezajimaMeVykreslovani) priblizeni > oddalenyRezim else vykreslitBusy ?: false
    val opravduVykreslitLinky = if (nezajimaMeVykreslovani) priblizeni < oddalenyRezim else vykreslitLinky ?: false

//    if (vybiraniLinky) pripravitNaVybiraniLinky()
    Canvas(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures {
                    println("onTap(offset=$it)")
                }
            },
    ) {

        drawRect(
//            color = vse.tema.darkColorScheme.surface,
            color = sedPozadi,
            topLeft = Offset(),
            size = size
        )
        scale(
            scale = priblizeni,
        ) {
            translate(
                left = tx,
                top = ty,
            ) {

//    if (!vybiraniLinky) {
//        vykreslitLinky = vse.zobrazitLinky
//        vykreslitBusy = !vse.zobrazitLinky
//    } else {
//        vykreslitLinky = true
//        vykreslitBusy = false
//    )

                dp.ulicove.forEach { ulice ->
                    ulice.draw()
                }

                dp.ulicove.forEach { ulice ->
                    ulice.baraky.forEach { barak ->
                        barak.draw(vse.tema, ulice)
                    }
                }

                dp.seznamKrizovatek.forEach { krizovatka ->
                    namalovatKrizovatku(dp, krizovatka)
                }

                if (opravduVykreslitBusy) dp.busy.forEach { bus ->
//                bus.draw(dp)
                }

                if (opravduVykreslitLinky) {
//                namalovatLinky()
                }

                if (priblizeni > oddalenyRezim) {
                    dp.ulicove.forEach { ulice ->
                        if (ulice.maTrolej) ulice.nakreslitTroleje()
                    }
                    dp.seznamKrizovatek.forEach { krizovatka ->
                        nakreslitTrolejeNaKrizovatku(dp, krizovatka)
                    }
                }

//            if (vybiraniLinky) namalovatVybiraniLinky()
            }
        }
    }
}

fun Offset(x: Float = 0F, y: Float = 0F) = Offset(x, y)