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
import cz.jaro.dopravnipodniky.shared.TurnType.Left
import cz.jaro.dopravnipodniky.shared.TurnType.Right
import cz.jaro.dopravnipodniky.shared.TurnType.Straight
import cz.jaro.dopravnipodniky.shared.delkaUlice
import cz.jaro.dopravnipodniky.shared.indexOfFirstOrElse
import cz.jaro.dopravnipodniky.shared.lengthsOfTurnParts
import cz.jaro.dopravnipodniky.shared.odsazeniBusu
import cz.jaro.dopravnipodniky.shared.predsazeniKrizovatky
import cz.jaro.dopravnipodniky.shared.reversedIfNegative
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.shared.times
import cz.jaro.dopravnipodniky.shared.translate
import cz.jaro.dopravnipodniky.shared.turnType
import cz.jaro.dopravnipodniky.shared.zip
import cz.jaro.dopravnipodniky.ui.main.DEBUG_MODE
import kotlin.math.sqrt

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

    val turnsParts = when (turn) {
        Right -> listOf(0F, 90F)
        Straight -> listOf(0F, 0F)
        Left -> listOf(0F, -90F)
        TurnType.Return180 -> listOf(0F, -180F)
        TurnType.RoundaboutRight -> listOf(0F, 45F, 45F, 90F)
        TurnType.RoundaboutStraight -> listOf(0F, 45F, -45F, 0F)
        TurnType.RoundaboutLeft -> listOf(0F, 45F, -135F, -90F)
        TurnType.RoundaboutReturn -> listOf(0F, 45F, -225F, -180F)
    }.windowed(2).map { it[0] to it[1] }

    val lengthsOfTurnParts = turn.lengthsOfTurnParts(bus.typBusu.sirka)
    val accumulativeTurnPartLengths = lengthsOfTurnParts.runningReduce { acc, length -> length + acc }
    val turnLength = accumulativeTurnPartLengths.last()

    val busSegmentOffset =
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
        }

    return {
        val zacatekX = street.zacatekX.toPx()
        val zacatekY = street.zacatekY.toPx()

        val clanky = bus.typBusu.clanky.map { it.toPx() }
        val sirkaBusu = bus.typBusu.sirka.toPx()
        val odsazeni = odsazeniBusu.toPx()
        val sirkaUlice = sirkaUlice.toPx()
        val delkaUlice = delkaUlice.toPx()

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
                    val malovat: DrawScope.(clanek: Float, natoceni: Float, i: Int) -> Unit = { clanek, natoceni, i ->
                        rotate(
                            degrees = natoceni,
                            pivot = Offset()
                        ) {
                            val jePrvni = i == 0
                            val jePosledni = i == busSegmentOffset.lastIndex
                            val zaobleni = sirkaBusu / 4
                            drawRoundRect(
                                color = line.barvicka.barva,
                                topLeft = Offset(
                                    x = when {
                                        !jePosledni -> -clanek * .25F - zaobleni
                                        else -> -clanek * .25F + 0F
                                    },
                                    y = -sirkaBusu / 2,
                                ),
                                size = Size(
                                    width = when {
                                        jePrvni && jePosledni -> clanek
                                        jePrvni || jePosledni -> clanek + zaobleni
                                        else /*!jePrvni && !jePosledni*/ -> clanek + zaobleni * 2
                                    },
                                    height = sirkaBusu,
                                ),
                                cornerRadius = CornerRadius(zaobleni * 2),
                            )
//                            drawRoundRect(
//                                color = Color.Green,
//                                topLeft = Offset(
//                                    x = -1.dp.toPx(),
//                                    y = -1.dp.toPx(),
//                                ),
//                                size = Size(
//                                    width = 2.dp.toPx(),
//                                    height = 2.dp.toPx(),
//                                ),
//                                cornerRadius = CornerRadius(3F.dp.toPx()),
//                            )
                            /*if (jePrvni) drawIntoCanvas {
                                it.nativeCanvas.drawText(
//                                    uhelCastiZatoceni[0].joinToString { it.toDouble().zaokrouhlit(1).toString() },
//                                    castiZatoceni.joinToString { it.joinToString { it.toDouble().zaokrouhlit(1).toString() } },
//                                    poziceVCasti.joinToString { it.value.toDouble().zaokrouhlit(1).toString() },
//                                    posunutiCastiZatoceni.joinToString { it.value.toDouble().zaokrouhlit(1).toString() },
//                                    delkyCastiZatoceni.runningReduce { acc, delka -> delka + acc }.joinToString { it.value.toDouble().zaokrouhlit(1).toString() },
//                                    delkyCastiZatoceni.joinToString { it.value.toDouble().zaokrouhlit(1).toString() },
                                    "${poziceClankuVKrizovatce[0].value.toDouble().zaokrouhlit(1)} ${natoceniClanku[0].toDouble().zaokrouhlit(1)} $indexCasti",
//                                    delkyCastiZatoceni.runningReduce { acc, delka -> delka + acc }.joinToString { it.value.toDouble().zaokrouhlit(1).toString() },
                                    0F,
                                    5.dp.toPx(),
                                    Paint().apply {
                                        color = android.graphics.Color.WHITE
                                    }
                                )
                            }*/
                            if (jePrvni && DEBUG_MODE) drawIntoCanvas {
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
                    zip(
                        segmentEndOffsetInIntersection,
                        segmentRotation,
                        clanky,
                        turnPartIndexPerSegment,
                        turnPartOffsetPerSegment
                    ).forEachIndexed { i, (pozice, natoceni, clanek, index, delka) ->
                        when {
                            pozice < 0.dp -> translate(
                                left = delkaUlice - predsazeniKrizovatky.toPx() + pozice.toPx(),
                                top = sirkaUlice - odsazeni - sirkaBusu / 2,
                            ) {
                                malovat(clanek, natoceni, i)
                            }

                            turn == Straight -> translate(
                                left = delkaUlice - predsazeniKrizovatky.toPx() + pozice.toPx(),
                                top = sirkaUlice - odsazeni - sirkaBusu / 2,
                            ) {
                                malovat(clanek, natoceni, i)
                            }

                            pozice > turnLength && turn == TurnType.RoundaboutStraight -> translate(
                                left = delkaUlice + sirkaUlice + predsazeniKrizovatky.toPx(),
                                top = sirkaUlice + predsazeniKrizovatky.toPx(),
                            ) {
                                val r = predsazeniKrizovatky.toPx() + odsazeni + sirkaBusu / 2
                                translate(
                                    left = pozice.toPx() - turnLength.toPx(),
                                    top = -r,
                                ) {
                                    malovat(clanek, natoceni, i)
                                }
                            }

                            pozice > turnLength && turn in listOf(Right, TurnType.RoundaboutRight) -> translate(
                                left = delkaUlice - predsazeniKrizovatky.toPx(),
                                top = sirkaUlice + predsazeniKrizovatky.toPx(),
                            ) {
                                val r = predsazeniKrizovatky.toPx() + odsazeni + sirkaBusu / 2
                                translate(
                                    left = r,
                                    top = pozice.toPx() - turnLength.toPx(),
                                ) {
                                    malovat(clanek, natoceni, i)
                                }
                            }

                            turn == Right -> translate(
                                pivot = Offset(
                                    x = delkaUlice - predsazeniKrizovatky.toPx(),
                                    y = sirkaUlice + predsazeniKrizovatky.toPx(),
                                ),
                                radius = predsazeniKrizovatky.toPx() + odsazeni + sirkaBusu / 2,
                                degrees = natoceni,
                                isTurnRight = true,
                            ) {
                                malovat(clanek, natoceni, i)
                            }

                            pozice > turnLength && turn in listOf(Left, TurnType.RoundaboutLeft) -> translate(
                                left = delkaUlice - predsazeniKrizovatky.toPx(),
                                top = -predsazeniKrizovatky.toPx(),
                            ) {
                                val r = predsazeniKrizovatky.toPx() + sirkaUlice - odsazeni - sirkaBusu / 2
                                translate(
                                    left = r,
                                    top = -(pozice.toPx() - turnLength.toPx()),
                                ) {
                                    malovat(clanek, natoceni, i)
                                }
                            }

                            turn == Left -> translate(
                                pivot = Offset(
                                    x = delkaUlice - predsazeniKrizovatky.toPx(),
                                    y = -predsazeniKrizovatky.toPx(),
                                ),
                                radius = predsazeniKrizovatky.toPx() + sirkaUlice - odsazeni - sirkaBusu / 2,
                                degrees = natoceni,
                                isTurnRight = false,
                            ) {
                                malovat(clanek, natoceni, i)
                            }

                            pozice > turnLength && turn == TurnType.RoundaboutReturn -> translate(
                                left = delkaUlice - predsazeniKrizovatky.toPx(),
                                top = sirkaUlice / 2,
                            ) {
                                val r = sirkaUlice / 2 - odsazeni - sirkaBusu / 2
                                translate(
                                    left = -(pozice.toPx() - turnLength.toPx()),
                                    top = -r,
                                ) {
                                    malovat(clanek, natoceni, i)
                                }
                            }

                            turn is TurnType.Roundabout && index == 0 -> translate(
                                pivot = Offset(
                                    x = delkaUlice - predsazeniKrizovatky.toPx(),
                                    y = sirkaUlice + predsazeniKrizovatky.toPx(),
                                ),
                                radius = predsazeniKrizovatky.toPx() + odsazeni + sirkaBusu / 2,
                                degrees = natoceni,
                                isTurnRight = true,
                            ) {
                                malovat(clanek, natoceni, i)
                            }

                            turn is TurnType.Roundabout && index == 1 -> translate(
                                pivot = Offset(
                                    x = delkaUlice + sirkaUlice / 2,
                                    y = sirkaUlice / 2,
                                ),
                                radius = .5F * sqrt(2F) * sirkaUlice + predsazeniKrizovatky.toPx() * sqrt(2F) - predsazeniKrizovatky.toPx() - odsazeni - sirkaBusu / 2,
                                degrees = natoceni,
                                isTurnRight = false,
                            ) {
                                malovat(clanek, natoceni, i)
                            }

                            turn == TurnType.RoundaboutRight && index == 2 -> translate(
                                pivot = Offset(
                                    x = delkaUlice - predsazeniKrizovatky.toPx(),
                                    y = sirkaUlice + predsazeniKrizovatky.toPx(),
                                ),
                                radius = predsazeniKrizovatky.toPx() + odsazeni + sirkaBusu / 2,
                                degrees = natoceni,
                                isTurnRight = true,
                            ) {
                                malovat(clanek, natoceni, i)
                            }

                            turn == TurnType.RoundaboutStraight && index == 2 -> translate(
                                pivot = Offset(
                                    x = delkaUlice + sirkaUlice + predsazeniKrizovatky.toPx(),
                                    y = sirkaUlice + predsazeniKrizovatky.toPx(),
                                ),
                                radius = predsazeniKrizovatky.toPx() + odsazeni + sirkaBusu / 2,
                                degrees = natoceni,
                                isTurnRight = true,
                            ) {
                                malovat(clanek, natoceni, i)
                            }

                            turn == TurnType.RoundaboutLeft && index == 2 -> translate(
                                pivot = Offset(
                                    x = delkaUlice + sirkaUlice + predsazeniKrizovatky.toPx(),
                                    y = -predsazeniKrizovatky.toPx(),
                                ),
                                radius = predsazeniKrizovatky.toPx() + odsazeni + sirkaBusu / 2,
                                degrees = natoceni,
                                isTurnRight = true,
                            ) {
                                malovat(clanek, natoceni, i)
                            }

                            turn == TurnType.RoundaboutReturn && index == 2 -> translate(
                                pivot = Offset(
                                    x = delkaUlice - predsazeniKrizovatky.toPx(),
                                    y = -predsazeniKrizovatky.toPx(),
                                ),
                                radius = predsazeniKrizovatky.toPx() + odsazeni + sirkaBusu / 2,
                                degrees = natoceni,
                                isTurnRight = true,
                            ) {
                                malovat(clanek, natoceni, i)
                            }
                        }
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
