package cz.jaro.dopravnipodniky.ui.malovani

import android.graphics.Paint
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Bus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.directionInLine
import cz.jaro.dopravnipodniky.data.dopravnipodnik.getStreets
import cz.jaro.dopravnipodniky.data.dopravnipodnik.linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.orientedInLine
import cz.jaro.dopravnipodniky.shared.Orientace
import cz.jaro.dopravnipodniky.shared.Smer
import cz.jaro.dopravnipodniky.shared.TurnType
import cz.jaro.dopravnipodniky.shared.delkaUlice
import cz.jaro.dopravnipodniky.shared.jednotky.degrees
import cz.jaro.dopravnipodniky.shared.odsazeniBusu
import cz.jaro.dopravnipodniky.shared.predsazeniKrizovatky
import cz.jaro.dopravnipodniky.shared.reversedIfNegative
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.shared.turnType
import cz.jaro.dopravnipodniky.ui.main.DEBUG_MODE
import cz.jaro.dopravnipodniky.ui.theme.orange600

fun getNamalovatBus(bus: Bus, dp: DopravniPodnik): DrawScope.() -> Unit {
    if (bus.linka == null) return {}
    val line = dp.linka(bus.linka)

    val streetIDs = line.ulice.reversedIfNegative(bus.smerNaLince)
    val streets = dp.getStreets(streetIDs)

    val street = streets[bus.poziceNaLince]
    val nextStreet = streets.getOrNull(bus.poziceNaLince + 1)

    val streetAndBusDirectionInLine = street.directionInLine(streets)
    val orientedStreet = street.orientedInLine(streetAndBusDirectionInLine)

    val cornerRotation = when (street.orientace) {
        Orientace.Vodorovne -> 0F
        Orientace.Svisle -> 90F
    }

    val centerRotation = when (streetAndBusDirectionInLine) {
        Smer.Pozitivni -> 0F
        Smer.Negativni -> 180F
    }

    val nextIntersection = dp.krizovatky.find { it.pozice == orientedStreet.second }

    val turn = turnType(nextIntersection, street, nextStreet)

    /*    val busSegmentOffset =
            bus.typBusu.clanky.scan(0.dp to 0.dp) { (acc, lastSegment), segment ->
                acc + lastSegment to segment
            }.drop(1)

        val rearOffsetInIntersection = bus.poziceVUlici - (delkaUlice - predsazeniKrizovatky)
        val frontOffsetInIntersection = rearOffsetInIntersection + bus.typBusu.clanky.first()

        val segmentEndOffsetInIntersection = busSegmentOffset.map { (offset, segment) ->
            frontOffsetInIntersection - (offset + segment * .75)
        }

        val turnPartIndexPerSegment = segmentEndOffsetInIntersection.map { offset ->
            accumulativeTurnPartLengths.indexOfFirstOrElse(defaultValue = { turnsParts.lastIndex }) { offset < it }
        }

        val turnPartAnglesPerSegment = turnPartIndexPerSegment.map { i ->
            turnsParts[i]
        }

        val turnPartLengthPerSegment = turnPartIndexPerSegment.map { i ->
            lengthsOfTurnParts[i]
        }

        val turnPartOffsetPerSegment = turnPartIndexPerSegment.map { i ->
            accumulativeTurnPartLengths.getOrElse(i - 1, defaultValue = { 0.dp })
        }

        val segmentEndOffsetInTurnPart =
            turnPartOffsetPerSegment.zip(segmentEndOffsetInIntersection) { turnPart, segment ->
                segment - turnPart
            }

        val segmentEndPositionInTurnPart =
            segmentEndOffsetInTurnPart.zip(turnPartLengthPerSegment) { offset, length ->
                offset.coerceIn(0.dp, length) / length
            }

        val segmentRotation =
            segmentEndPositionInTurnPart.zip(turnPartAnglesPerSegment) { position, angle ->
                position * (angle.second - angle.first) + angle.first
            }*/

    return {
        val zacatekX = street.zacatekX.toPx()
        val zacatekY = street.zacatekY.toPx()

        val clanky = bus.typBusu.clanky.map { it.toPx() }
        val sirkaBusu = bus.typBusu.sirka.toPx()
        val odsazeni = odsazeniBusu.toPx()
        val sirkaUlice = sirkaUlice.toPx()
        val delkaUlice = delkaUlice.toPx()
        val delka = bus.typBusu.delka.toPx()
        val pozice = bus.poziceVUlici.toPx() - (delkaUlice - predsazeniKrizovatky.toPx())

        translate(
            left = zacatekX,
            top = zacatekY,
        ) {
            rotate(
                degrees = cornerRotation,
                pivot = Offset(x = sirkaUlice / 2, y = sirkaUlice / 2),
            ) {
                rotate(
                    degrees = centerRotation,
                    pivot = Offset(x = delkaUlice / 2, y = sirkaUlice / 2),
                ) {
                    val malovat: DrawScope.() -> Unit = {
                        rotate(
                            degrees = bus.rotation.degrees.toFloat(),
                            pivot = Offset()
                        ) {
                            val zaobleni = sirkaBusu / 4
                            drawRoundRect(
                                color = line.barvicka.barva,
                                topLeft = Offset(
                                    x = -delka * .25F + 0F,
                                    y = -sirkaBusu / 2,
                                ),
                                size = Size(
                                    width = delka,
                                    height = sirkaBusu,
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
                                        color = android.graphics.Color.WHITE
                                    }
                                )
                            }
                        }
                    }
                    // --->
                    when {
                        pozice >= 0F && turn == TurnType.Straight -> translate(
                            left = delkaUlice - predsazeniKrizovatky.toPx() + pozice,
                            top = sirkaUlice - odsazeni - sirkaBusu / 2,
                        ) {
                            malovat()
                        }

                        else -> translate(
                            left = bus.position.x.toPx(),
                            top = bus.position.y.toPx(),
                        ) {
                            malovat()
                        }

//                        pozice > turnLength.toPx() && turn == TurnType.RoundaboutStraight -> translate(
//                            left = delkaUlice + sirkaUlice + predsazeniKrizovatky.toPx(),
//                            top = sirkaUlice + predsazeniKrizovatky.toPx(),
//                        ) {
//                            val r = predsazeniKrizovatky.toPx() + odsazeni + sirkaBusu / 2
//                            translate(
//                                left = pozice - turnLength.toPx(),
//                                top = -r,
//                            ) {
//                                malovat()
//                            }
//                        }
//
//                        pozice > turnLength.toPx() && turn in listOf(TurnType.Right, TurnType.RoundaboutRight) -> translate(
//                            left = delkaUlice - predsazeniKrizovatky.toPx(),
//                            top = sirkaUlice + predsazeniKrizovatky.toPx(),
//                        ) {
//                            val r = predsazeniKrizovatky.toPx() + odsazeni + sirkaBusu / 2
//                            translate(
//                                left = r,
//                                top = pozice - turnLength.toPx(),
//                            ) {
//                                malovat()
//                            }
//                        }
//
//                        turn == TurnType.Right -> translate(
//                            pivot = Offset(
//                                x = delkaUlice - predsazeniKrizovatky.toPx(),
//                                y = sirkaUlice + predsazeniKrizovatky.toPx(),
//                            ),
//                            radius = predsazeniKrizovatky.toPx() + odsazeni + sirkaBusu / 2,
//                            degrees = bus.rotation.degrees.toFloat(),
//                            isTurnRight = true,
//                        ) {
//                            malovat()
//                        }
//
//                        pozice > turnLength.toPx() && turn in listOf(TurnType.Left, TurnType.RoundaboutLeft) -> translate(
//                            left = delkaUlice - predsazeniKrizovatky.toPx(),
//                            top = -predsazeniKrizovatky.toPx(),
//                        ) {
//                            val r = predsazeniKrizovatky.toPx() + sirkaUlice - odsazeni - sirkaBusu / 2
//                            translate(
//                                left = r,
//                                top = -(pozice - turnLength.toPx()),
//                            ) {
//                                malovat()
//                            }
//                        }
//
//                        turn == TurnType.Left -> translate(
//                            pivot = Offset(
//                                x = delkaUlice - predsazeniKrizovatky.toPx(),
//                                y = -predsazeniKrizovatky.toPx(),
//                            ),
//                            radius = predsazeniKrizovatky.toPx() + sirkaUlice - odsazeni - sirkaBusu / 2,
//                            degrees = bus.rotation.degrees.toFloat(),
//                            isTurnRight = false,
//                        ) {
//                            malovat()
//                        }
//
//                        pozice > turnLength.toPx() && turn == TurnType.RoundaboutReturn -> translate(
//                            left = delkaUlice - predsazeniKrizovatky.toPx(),
//                            top = sirkaUlice / 2,
//                        ) {
//                            val r = sirkaUlice / 2 - odsazeni - sirkaBusu / 2
//                            translate(
//                                left = -(pozice - turnLength.toPx()),
//                                top = -r,
//                            ) {
//                                malovat()
//                            }
//                        }
//
//                        turn is TurnType.Roundabout -> translate(
//                            pivot = Offset(
//                                x = delkaUlice - predsazeniKrizovatky.toPx(),
//                                y = sirkaUlice + predsazeniKrizovatky.toPx(),
//                            ),
//                            radius = predsazeniKrizovatky.toPx() + odsazeni + sirkaBusu / 2,
//                            degrees = bus.rotation.degrees.toFloat(),
//                            isTurnRight = true,
//                        ) {
//                            malovat()
//                        }
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
