package cz.jaro.dopravnipodniky.sketches

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import cz.jaro.dopravnipodniky.Vse
import cz.jaro.dopravnipodniky.classes.DopravniPodnik
import cz.jaro.dopravnipodniky.classes.seznamKrizovatek
import cz.jaro.dopravnipodniky.maximalniOddaleni
import cz.jaro.dopravnipodniky.oddalenyRezim
import cz.jaro.dopravnipodniky.sedPozadi
import kotlin.math.roundToInt

@Composable
fun Sketch(
    dp: DopravniPodnik,
    modifier: Modifier = Modifier,
    vykreslitBusy: Boolean? = null,
    vykreslitLinky: Boolean? = null,
    vybiraniLinky: Boolean = false,
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
            }
            .pointerInput(Unit) {
                detectTransformGestures(
                    onGesture = { _, pan, zoom, _ ->
                        tx += pan.x.toDp()
                        ty += pan.y.toDp() //todo locknout

                        priblizeni = (priblizeni * zoom).coerceAtLeast(maximalniOddaleni)
//                        println("onGesture(centroid=$centroid, pan=$pan, zoom=$zoom, rotation=$rotation)")
                        println("tx=$tx, ty=$ty, priblizeni=$priblizeni")
                    }
                )
            },
    ) {

        drawRect(
            color = sedPozadi,
            topLeft = Offset(),
            size = size
        )

        translate(
            left = tx.toPx(),
            top = ty.toPx(),
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
                    barak.draw(Vse(dp).tema, ulice) // todo vse
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

            ulozit()
        }
    }
}

fun Offset(x: Float = 0F, y: Float = 0F) = Offset(x, y)

//fun touchEnded() {
//    super.touchEnded()
//
//    dp.sledovanejBus = null
//
//    s = 0F
//
//    x1 = -1F
//    y1 = -1F
//
//    if (!vybiraniLinky && editor) moznaChcesUdelatZastavku()
//
//    ulozit()
//}


fun jeVObdelniku(x: Int, y: Int, x1: Float, y1: Float, x2: Float, y2: Float): Boolean {
    return (x in x1.roundToInt()..x2.roundToInt()) && (y in y1.roundToInt()..y2.roundToInt())
}

fun ulozit() {

}

//override fun touchMoved() {
//    super.touchMoved()
//
//    dp.sledovanejBus = null
//
//    if (vybiraniLinky) {
//        if (touches.size == 1) poKliknutiPriVybiraniLinky()
//        if (touches.size == 2) sunout()
//        if (touches.size == 2) zachovavatPodobnost()
//    } else {
//        sunout()
//        if (touches.size == 2) zachovavatPodobnost()
//    }
//    ulozit()
//}

private var puvodniDx = 0F
private var puvodniDy = 0F
private var puvodniDz = 0F

//private fun kalibrovat() {
//
//    if (dp.kalibrovat == -1) {
//        dp.kalibrovat = dobaKalibraceT.value.toInt()
//        puvodniDz = velikostBloku - pocatecniPriblizeni
//        puvodniDx = tx - pocatecniPosunutiX
//        puvodniDy = ty - pocatecniPosunutiY
//    }
//
//    velikostBloku -= puvodniDz / dobaKalibraceT.value.toInt()
//    tx -= puvodniDx / dobaKalibraceT.value.toInt()
//    ty -= puvodniDy / dobaKalibraceT.value.toInt()
//
//    dp.kalibrovat--
//}
