package cz.jaro.dopravnipodniky.ui.malovani

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.kapacitaZastavky
import cz.jaro.dopravnipodniky.data.dopravnipodnik.origin
import cz.jaro.dopravnipodniky.shared.Offset
import cz.jaro.dopravnipodniky.shared.Orientace
import cz.jaro.dopravnipodniky.shared.bareStreetLength
import cz.jaro.dopravnipodniky.shared.curbColor
import cz.jaro.dopravnipodniky.shared.curbWidth
import cz.jaro.dopravnipodniky.shared.drawText
import cz.jaro.dopravnipodniky.shared.intersectionOffset
import cz.jaro.dopravnipodniky.shared.jednotky.toPx
import cz.jaro.dopravnipodniky.shared.lineMarkingColor
import cz.jaro.dopravnipodniky.shared.markingPartLength
import cz.jaro.dopravnipodniky.shared.markingWidth
import cz.jaro.dopravnipodniky.shared.sidewalkColor
import cz.jaro.dopravnipodniky.shared.sidewalkWidth
import cz.jaro.dopravnipodniky.shared.streetColor
import cz.jaro.dopravnipodniky.shared.streetWidth
import cz.jaro.dopravnipodniky.shared.translate
import cz.jaro.dopravnipodniky.ui.main.DEBUG_MODE

context(drawScope: DrawScope)
fun Ulice.draw() = with(drawScope) {
    //fill(BARVICKY[ulice.potencial])
    //fill(ulice.potencial * 20)obrubnik

    val sidewalkWidth = sidewalkWidth.toPx()
    val curbWidth = curbWidth.toPx()
    val markingWidth = markingWidth.toPx()
    val markingPartLength = markingPartLength.toPx()
    val streetWidth = streetWidth.toPx()
    val bareStreetLength = bareStreetLength.toPx()
    val intersectionOffset = intersectionOffset.toPx()

    fun debugText() {
        drawText(
            text = "$cloveci/$kapacita",
            position = Offset(
                x = 5.dp.toPx(),
                y = 4.3.dp.toPx(),
            ),
        )
        drawText(
            text = "${zastavka?.cloveci}/${kapacitaZastavky()}",
            position = Offset(
                x = 5.dp.toPx(),
                y = 8.3.dp.toPx(),
            ),
        )
    }

    fun DrawScope.drawStreetSide() {
        drawRoundRect(
            color = curbColor,
            topLeft = Offset(x = -1F),
            size = Size(bareStreetLength + 2F, curbWidth),
        )
        drawRoundRect(
            color = sidewalkColor,
            topLeft = Offset(x = -1F, y = curbWidth),
            size = Size(bareStreetLength + 2F, sidewalkWidth),
        )

        zastavka?.let {
            translate(
                left = -intersectionOffset,
            ) {
                drawStop()
            }
        }
    }

    withTransform({
        translate(
            offset = with(drawScope) { origin.toPx() },
        )
        if (orientace == Orientace.Svisle) rotate(
            degrees = 90F,
            pivot = Offset(streetWidth / 2, streetWidth / 2),
        )
        translate(
            left = intersectionOffset,
        )
    }) {
        drawRect(
            color = streetColor,
            size = Size(bareStreetLength, streetWidth)
        )
        drawLine(
            color = lineMarkingColor,
            start = Offset(y = streetWidth / 2),
            end = Offset(x = bareStreetLength, y = streetWidth / 2),
            strokeWidth = markingWidth,
            pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(markingPartLength, markingPartLength))
        )
        translate(
            top = streetWidth,
        ) {
            drawStreetSide()
            rotate(
                degrees = 180F,
                pivot = Offset(
                    x = bareStreetLength / 2,
                    y = -streetWidth / 2,
                ),
            ) {
                drawStreetSide()
            }
        }

        if (DEBUG_MODE) debugText()
    }
}