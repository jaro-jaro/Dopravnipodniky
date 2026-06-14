package cz.jaro.dopravnipodniky.ui.malovani

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import cz.jaro.dopravnipodniky.data.dopravnipodnik.IntersectionType
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Krizovatka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.shared.Offset
import cz.jaro.dopravnipodniky.shared.Orientace
import cz.jaro.dopravnipodniky.shared.Quintuple
import cz.jaro.dopravnipodniky.shared.Size
import cz.jaro.dopravnipodniky.shared.backgroundColor
import cz.jaro.dopravnipodniky.shared.curbColor
import cz.jaro.dopravnipodniky.shared.curbWidth
import cz.jaro.dopravnipodniky.shared.drawAnnulus
import cz.jaro.dopravnipodniky.shared.drawAnnulusSector
import cz.jaro.dopravnipodniky.shared.drawArc
import cz.jaro.dopravnipodniky.shared.helpers.countTrue
import cz.jaro.dopravnipodniky.shared.intersectionOffset
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.shared.jednotky.Vector
import cz.jaro.dopravnipodniky.shared.jednotky.deg
import cz.jaro.dopravnipodniky.shared.jednotky.toDpSKrizovatkama
import cz.jaro.dopravnipodniky.shared.jednotky.toPx
import cz.jaro.dopravnipodniky.shared.lineMarkingColor
import cz.jaro.dopravnipodniky.shared.markingWidth
import cz.jaro.dopravnipodniky.shared.rotate
import cz.jaro.dopravnipodniky.shared.roundaboutIslandRadius
import cz.jaro.dopravnipodniky.shared.roundaboutStreetOuterRadius
import cz.jaro.dopravnipodniky.shared.sidewalkColor
import cz.jaro.dopravnipodniky.shared.sidewalkWidth
import cz.jaro.dopravnipodniky.shared.streetColor
import cz.jaro.dopravnipodniky.shared.streetWidth
import cz.jaro.dopravnipodniky.shared.translate
import cz.jaro.dopravnipodniky.shared.turnMarkingPartLength

context(drawScope: DrawScope)
fun namalovatKrizovatku(
    ulice: List<Ulice>,
    pozice: Vector<UlicovyBlok>,
    krizovatka: Krizovatka?,
) = with(drawScope) {
    val (x, y) = pozice

    val origin = pozice.toDpSKrizovatkama()

    val streetWidth = streetWidth.toPx()
    val curbWidth = curbWidth.toPx()
    val sidewalkWidth = sidewalkWidth.toPx()
    val intersectionOffset = intersectionOffset.toPx()
    val roundaboutStreetOuterRadius = roundaboutStreetOuterRadius.toPx()
    val roundaboutIslandRadius = roundaboutIslandRadius.toPx()
    val markingWidth = markingWidth.toPx()
    val turnMarkingPartLength = turnMarkingPartLength.toPx()

    val sousedVpravo = ulice.find {
        it.orientace == Orientace.Vodorovne && it.zacatek == pozice
    } != null
    val sousedDole = ulice.find {
        it.orientace == Orientace.Svisle && it.zacatek == pozice
    } != null
    val sousedVlevo = ulice.find {
        it.orientace == Orientace.Vodorovne && it.konec == pozice
    } != null
    val sousedNahore = ulice.find {
        it.orientace == Orientace.Svisle && it.konec == pozice
    } != null

    val baseIntersectionSize = Size(streetWidth)
    val intersectionSizeOffset = Offset(intersectionOffset + streetWidth + intersectionOffset)
    translate(
        offset = origin.toPx() - Offset(intersectionOffset),
    ) {
        drawRect(
            color = streetColor,
            topLeft = Offset(intersectionOffset),
            size = baseIntersectionSize,
        )

        // kreslení chodníku okolo kruháče
        if (krizovatka?.type == IntersectionType.Roundabout) translate(
            offset = intersectionSizeOffset / 2F,
        ) {
            drawAnnulus(
                color = curbColor,
                innerRadius = roundaboutStreetOuterRadius,
                width = curbWidth,
            )
            drawAnnulus(
                color = sidewalkColor,
                innerRadius = roundaboutStreetOuterRadius + curbWidth,
                width = sidewalkWidth,
            )
        }

        val sousediUhly = listOf(
            Quintuple(sousedNahore, sousedVpravo, sousedDole, sousedVlevo, 0.deg),
            Quintuple(sousedVpravo, sousedDole, sousedVlevo, sousedNahore, 90.deg),
            Quintuple(sousedDole, sousedVlevo, sousedNahore, sousedVpravo, 180.deg),
            Quintuple(sousedVlevo, sousedNahore, sousedVpravo, sousedDole, 270.deg),
        )

        // Napojení křižovatky na sousedy
        sousediUhly.filter { [soused1, _, _, _, _] ->
            soused1
        }.forEach { [_, _, _, _, uhel] ->
            rotate(
                angle = uhel,
                pivot = intersectionSizeOffset / 2F,
            ) {
                drawRect(
                    color = streetColor,
                    topLeft = Offset(x = intersectionOffset, y = -1F),
                    size = Size(streetWidth, intersectionOffset + 2),
                )
            }
        }

        // Kreslení rovných chodníků
        if (krizovatka?.type != IntersectionType.Roundabout) sousediUhly.filter { [soused1, soused2, soused3, soused4, _] ->
            !soused1 && !soused3 || !soused1 && soused2 == soused4
        }.forEach { [_, soused2, _, soused4, uhel] ->
            rotate(
                angle = uhel,
                pivot = intersectionSizeOffset / 2F,
            ) {
                val left = if (soused4) 0F else intersectionOffset
                val width = streetWidth + intersectionOffset * listOf(soused2, soused4).countTrue()
                drawRect(
                    color = curbColor,
                    topLeft = Offset(x = left, y = intersectionOffset - curbWidth),
                    size = Size(width, curbWidth),
                )
                drawRect(
                    color = sidewalkColor,
                    topLeft = Offset(x = left, y = intersectionOffset - curbWidth - sidewalkWidth),
                    size = Size(width, sidewalkWidth),
                )
            }
        }

        // Kreslení vnějšího oblouku u křižovatky typu L
        if (krizovatka?.type != IntersectionType.Roundabout) sousediUhly
            .filter { [soused1, soused2, soused3, soused4, _] ->
                soused4 && soused1 && !soused2 && !soused3
            }
            .forEach { [_, _, _, _, uhel] ->
                rotate(
                    angle = uhel,
                    pivot = intersectionSizeOffset / 2F,
                ) {
                    drawRect(
                        topLeft = Offset(1F),
                        size = Size(intersectionOffset + streetWidth),
                        color = backgroundColor,
                        style = Fill,
                    )
                    drawArc(
                        useCenter = true,
                        style = Fill,
                        color = streetColor,
                        center = Offset(.5F),
                        radius = intersectionOffset + streetWidth - .5F,
                        startAngle = 0F,
                        sweepAngle = 90F,
                    )
                    drawAnnulusSector(
                        width = curbWidth,
                        color = curbColor,
                        innerRadius = intersectionOffset + streetWidth,
                        startAngle = 0F,
                        sweepAngle = 90F,
                    )
                    drawAnnulusSector(
                        width = sidewalkWidth,
                        color = sidewalkColor,
                        innerRadius = intersectionOffset + streetWidth + curbWidth,
                        startAngle = 0F,
                        sweepAngle = 90F,
                    )
                }
            }

        // Kreslení vnitřního oblouku
        sousediUhly
            .filter { [soused1, _, _, soused4, _] ->
                soused1 && soused4
            }
            .forEach { [_, _, _, _, uhel] ->
                rotate(
                    angle = uhel,
                    pivot = intersectionSizeOffset / 2F,
                ) {
                    drawRoundRect(
                        color = streetColor,
                        topLeft = Offset(2F),
                        size = Size(intersectionOffset, intersectionOffset),
                    )
                    drawArc(
                        useCenter = true,
                        style = Fill,
                        color = backgroundColor,
                        center = Offset(),
                        radius = intersectionOffset,
                        startAngle = 0F,
                        sweepAngle = 90F,
                    )
                    drawAnnulusSector(
                        width = curbWidth,
                        color = curbColor,
                        innerRadius = intersectionOffset - curbWidth,
                        startAngle = 0F,
                        sweepAngle = 90F,
                    )
                    drawAnnulusSector(
                        width = sidewalkWidth,
                        color = sidewalkColor,
                        innerRadius = intersectionOffset - curbWidth - sidewalkWidth,
                        startAngle = 0F,
                        sweepAngle = 90F,
                    )
                }
            }

        // Kreslení napojení na kruháč
        if (krizovatka?.type == IntersectionType.Roundabout) sousediUhly
            .filter { [soused1, _, _, _, _] ->
                soused1
            }
            .forEach { [_, _, _, _, uhel] ->
                rotate(
                    angle = uhel,
                    pivot = intersectionSizeOffset / 2F,
                ) {
                    drawPath(
                        path = Triangle(
                            a = Offset(2F, 2F),
                            b = Offset(2F + intersectionOffset, 2F + intersectionOffset),
                            c = Offset(2F + intersectionOffset, 2F),
                        ),
                        color = streetColor,
                    )
                    drawArc(
                        useCenter = true,
                        style = Fill,
                        color = backgroundColor,
                        center = Offset(),
                        radius = intersectionOffset,
                        startAngle = 0F,
                        sweepAngle = 45F,
                    )
                    drawAnnulusSector(
                        width = curbWidth,
                        color = curbColor,
                        innerRadius = intersectionOffset - curbWidth,
                        startAngle = 0F,
                        sweepAngle = 45F,
                    )
                    drawAnnulusSector(
                        width = sidewalkWidth,
                        color = sidewalkColor,
                        innerRadius = intersectionOffset - curbWidth - sidewalkWidth,
                        startAngle = 0F,
                        sweepAngle = 45F,
                    )
                }
            }

        // Kreslení napojení na kruháč
        if (krizovatka?.type == IntersectionType.Roundabout) sousediUhly
            .filter { [_, _, _, soused4, _] ->
                soused4
            }
            .forEach { [_, _, _, _, uhel] ->
                rotate(
                    angle = uhel,
                    pivot = intersectionSizeOffset / 2F,
                ) {
                    drawPath(
                        path = Triangle(
                            a = Offset(2F, 2F),
                            b = Offset(2F + intersectionOffset, 2F + intersectionOffset),
                            c = Offset(2F, 2F + intersectionOffset),
                        ),
                        color = streetColor,
                    )
                    drawArc(
                        useCenter = true,
                        style = Fill,
                        color = backgroundColor,
                        center = Offset(),
                        radius = intersectionOffset,
                        startAngle = 45F,
                        sweepAngle = 45F,
                    )
                    drawAnnulusSector(
                        width = curbWidth,
                        color = curbColor,
                        innerRadius = intersectionOffset - curbWidth,
                        startAngle = 45F,
                        sweepAngle = 45F,
                    )
                    drawAnnulusSector(
                        width = sidewalkWidth,
                        color = sidewalkColor,
                        innerRadius = intersectionOffset - curbWidth - sidewalkWidth,
                        startAngle = 45F,
                        sweepAngle = 45F,
                    )
                }
            }

        // Kreslení kruháče
        if (krizovatka?.type == IntersectionType.Roundabout) {
            translate(
                offset = intersectionSizeOffset / 2F,
            ) {
                drawCircle(
                    color = streetColor,
                    radius = roundaboutStreetOuterRadius,
                    style = Fill,
                    center = Offset(),
                )
                drawCircle(
                    color = backgroundColor,
                    radius = roundaboutIslandRadius,
                    style = Fill,
                    center = Offset(),
                )
            }
        }

        // Kreslení čáry na ulici u křižovatky typu L
        if (krizovatka?.type != IntersectionType.Roundabout) sousediUhly
            .filter { [soused1, soused2, soused3, soused4, _] ->
                soused4 && soused1 && !soused2 && !soused3
            }
            .forEach { [_, _, _, _, uhel] ->
                rotate(
                    angle = uhel,
                    pivot = intersectionSizeOffset / 2F,
                ) {
                    drawAnnulusSector(
                        width = markingWidth,
                        color = lineMarkingColor,
                        innerRadius = intersectionOffset + streetWidth / 2 - markingWidth / 2,
                        startAngle = 0F,
                        sweepAngle = 90F,
                        pathEffect = PathEffect.dashPathEffect(
                            intervals = floatArrayOf(turnMarkingPartLength, turnMarkingPartLength),
                            phase = turnMarkingPartLength,
                        )
                    )
                }
            }

    }
}

@Suppress("FunctionName")
fun Triangle(
    a: Offset,
    b: Offset,
    c: Offset,
) = Polygon(listOf(a, b, c))

@Suppress("FunctionName")
fun Polygon(
    nodes: List<Offset>,
): Path {
    require(nodes.size >= 3)
    return Path().apply {
        moveTo(nodes.first().x, nodes.first().y)
        nodes.drop(1).forEach {
            lineTo(it.x, it.y)
        }
        close()
    }
}