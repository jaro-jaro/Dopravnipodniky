package cz.jaro.dopravnipodniky.ui.malovani

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import cz.jaro.dopravnipodniky.shared.curbColor
import cz.jaro.dopravnipodniky.shared.curbWidth
import cz.jaro.dopravnipodniky.shared.markingWidth
import cz.jaro.dopravnipodniky.shared.sidewalkColor
import cz.jaro.dopravnipodniky.shared.sidewalkWidth
import cz.jaro.dopravnipodniky.shared.stopEntryLength
import cz.jaro.dopravnipodniky.shared.stopLength
import cz.jaro.dopravnipodniky.shared.stopMarkingColor
import cz.jaro.dopravnipodniky.shared.stopMarkingsOffset
import cz.jaro.dopravnipodniky.shared.stopOffset
import cz.jaro.dopravnipodniky.shared.stopPostOffset
import cz.jaro.dopravnipodniky.shared.stopPostThickness
import cz.jaro.dopravnipodniky.shared.stopPostWidth
import cz.jaro.dopravnipodniky.shared.stopWidth
import cz.jaro.dopravnipodniky.shared.streetColor
import kotlin.math.sqrt

// Směr vpravo, počátek na spodku ulice
context(drawScope: DrawScope)
fun drawStop() = with(drawScope) {
    val sidewalkWidth = sidewalkWidth.toPx()
    val curbWidth = curbWidth.toPx()
    val markingWidth = markingWidth.toPx()
    val stopOffset = stopOffset.toPx()
    val stopLength = stopLength.toPx()
    val stopMarkingsOffset = stopMarkingsOffset.toPx()
    val stopPostOffset = stopPostOffset.toPx()
    val stopPostWidth = stopPostWidth.toPx()
    val stopPostThickness = stopPostThickness.toPx()
    val stopWidth = stopWidth.toPx()
    val stopEntryLength = stopEntryLength.toPx()
    translate(
        left = stopOffset,
    ) {
        drawRect(
            color = streetColor,
            topLeft = cz.jaro.dopravnipodniky.shared.Offset(x = -stopEntryLength),
            size = Size(stopLength + stopEntryLength * 2, stopWidth),
        )
        // Obrubník + chodník pod zastávkou
        drawRect(
            color = curbColor,
            topLeft = cz.jaro.dopravnipodniky.shared.Offset(y = stopWidth),
            size = Size(stopLength, curbWidth),
        )
        drawRect(
            color = sidewalkColor,
            topLeft = cz.jaro.dopravnipodniky.shared.Offset(y = stopWidth + curbWidth),
            size = Size(stopLength, sidewalkWidth),
        )
        // Vjezd + výjezd
        translate(
            left = -stopEntryLength,
        ) {
            drawStopEntry(curbWidth, stopEntryLength, stopWidth, sidewalkWidth)
        }
        translate(
            left = stopLength + stopEntryLength,
        ) {
            scale(
                scaleX = -1F,
                scaleY = 1F,
                pivot = cz.jaro.dopravnipodniky.shared.Offset(),
            ) {
                drawStopEntry(curbWidth, stopEntryLength, stopWidth, sidewalkWidth)
            }
        }
        // Čáry
        translate(
            left = markingWidth / 2 + stopMarkingsOffset,
            top = markingWidth / 2 + stopMarkingsOffset,
        ) {
            val delkaMalovaneZastavky = stopLength - markingWidth - stopMarkingsOffset * 2
            val sirkaMalovaneZastavky = stopWidth - markingWidth - stopMarkingsOffset * 2
            drawRect(
                color = stopMarkingColor,
                size = Size(delkaMalovaneZastavky, sirkaMalovaneZastavky),
                style = Stroke(
                    width = markingWidth,
                )
            )
            drawLine(
                color = stopMarkingColor,
                start = Offset.Zero,
                end = Offset(delkaMalovaneZastavky, sirkaMalovaneZastavky),
                strokeWidth = markingWidth
            )
            drawLine(
                color = stopMarkingColor,
                start = Offset(0F, sirkaMalovaneZastavky),
                end = Offset(delkaMalovaneZastavky, 0F),
                strokeWidth = markingWidth
            )
        }
        // Sloupek
        translate(
            left = stopLength - stopPostOffset - stopPostThickness,
            top = stopWidth + curbWidth + stopPostOffset
        ) {
            drawRect(
                color = Color.Black,
                size = Size(stopPostThickness, stopPostWidth)
            )
        }
    }
}

private fun DrawScope.drawStopEntry(
    sirkaObrubniku: Float,
    delkaVyjezduZeZastavky: Float,
    sirkaZastavky: Float,
    sirkaChodniku: Float
) {
    drawPath(
        path = StopEntry(sirkaObrubniku, delkaVyjezduZeZastavky, sirkaZastavky),
        color = curbColor,
    )
    translate(
        top = sirkaObrubniku,
    ) {
        drawPath(
            path = StopEntry(sirkaChodniku, delkaVyjezduZeZastavky, sirkaZastavky),
            color = sidewalkColor,
        )
    }
}

@Suppress("FunctionName")
fun StopEntry(
    width: Float,
    length: Float,
    offset: Float,
) = Path().apply {
    val d = width * offset / (sqrt(offset * offset + length * length) + length)
    relativeLineTo(dx = length, dy = offset)
    relativeLineTo(dx = 1F, dy = 0F)
    relativeLineTo(dx = 0F, dy = width)
    relativeLineTo(dx = -1F, dy = 0F)
    relativeLineTo(dx = -d, dy = 0F)
    relativeLineTo(dx = -length, dy = -offset)
    relativeLineTo(dx = d, dy = -width)
    close()
}