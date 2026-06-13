package cz.jaro.dopravnipodniky.ui.malovani

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Bus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.directionInLine
import cz.jaro.dopravnipodniky.data.dopravnipodnik.firstSegmentLength
import cz.jaro.dopravnipodniky.data.dopravnipodnik.getStreets
import cz.jaro.dopravnipodniky.data.dopravnipodnik.linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.origin
import cz.jaro.dopravnipodniky.shared.Orientace
import cz.jaro.dopravnipodniky.shared.Smer
import cz.jaro.dopravnipodniky.shared.busRearAxlePosition
import cz.jaro.dopravnipodniky.shared.delkaUlice
import cz.jaro.dopravnipodniky.shared.jednotky.Angle
import cz.jaro.dopravnipodniky.shared.jednotky.Vector
import cz.jaro.dopravnipodniky.shared.jednotky.toPx
import cz.jaro.dopravnipodniky.shared.reversedIfNegative
import cz.jaro.dopravnipodniky.shared.rotate
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.shared.translate
import cz.jaro.dopravnipodniky.ui.main.DEBUG_MODE
import cz.jaro.dopravnipodniky.ui.theme.orange600

fun getBusDrawFunction(bus: Bus, dp: DopravniPodnik): DrawScope.() -> Unit {
    if (bus.linka == null) return {}
    val line = dp.linka(bus.linka)

    val streetIDs = line.ulice.reversedIfNegative(bus.smerNaLince).asSequence()
    val streets = dp.getStreets(streetIDs).toList()

    val street = streets[bus.poziceNaLince]

    val streetAndBusDirectionInLine = street.directionInLine(streets)

    val cornerRotation = when (street.orientace) {
        Orientace.Vodorovne -> 0F
        Orientace.Svisle -> 90F
    }

    val centerRotation = when (streetAndBusDirectionInLine) {
        Smer.Pozitivni -> 0F
        Smer.Negativni -> 180F
    }

    data class DrawSegmentInfo(val position: Vector<Dp>, val rotation: Angle, val length: Dp, val fractionBehind: Float)

    val segments = listOf(
        DrawSegmentInfo(bus.position, bus.rotation, bus.firstSegmentLength, busRearAxlePosition),
        *bus.segmentEndsPosition.zip(bus.typBusu.clanky.drop(1)) { [position, rotation], length ->
            DrawSegmentInfo(position, rotation, length, .5F)
        }.toTypedArray()
    )

    return {
        val busWidth = bus.typBusu.sirka.toPx()
        val streetWidth = sirkaUlice.toPx()
        val streetLength = delkaUlice.toPx()
        val streetOrigin = street.origin.toPx()

        withTransform({
            translate(streetOrigin)
            rotate(
                degrees = cornerRotation,
                pivot = Offset(x = streetWidth / 2, y = streetWidth / 2),
            )
            rotate(
                degrees = centerRotation,
                pivot = Offset(x = streetLength / 2, y = streetWidth / 2),
            )
        }) {
            segments.forEach {
                val offset = it.position.toPx()
                withTransform({
                    translate(offset)
                    rotate(it.rotation)
                }) {
                    val zaobleni = busWidth / 4
                    drawRoundRect(
                        color = line.color.color,
                        topLeft = Offset(
                            x = -it.length.toPx() * it.fractionBehind,
                            y = -busWidth / 2,
                        ),
                        size = Size(
                            width = it.length.toPx(),
                            height = busWidth,
                        ),
                        cornerRadius = CornerRadius(zaobleni * 2),
                    )
                    drawCircle(orange600, radius = 1.dp.toPx(), center = Offset())
                    if (DEBUG_MODE) drawIntoCanvas {
                        it.nativeCanvas.drawText(
                            "${bus.cloveci}/${bus.typBusu.kapacita}",
                            0F,
                            7.dp.toPx(),
                            Paint().apply {
                                this.color = Color.WHITE
                            }
                        )
                    }
                }
            }
        }
    }

//        if (dp.sledovanejBus == this@draw) sleduj()
}

/*
context (DrawScope)
fun Bus.sleduj() {
    tx = -pozice.first + pocatecniPosunutiX
    ty = -pozice.second + pocatecniPosunutiY
    if (velikostBloku <= 1.9) {
        velikostBloku *= (TPS + .1F) / TPS.toFloat()
    }
}
*/
