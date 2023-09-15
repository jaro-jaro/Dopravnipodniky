package cz.jaro.dopravnipodniky.ui.malovani

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.shared.Orientace
import cz.jaro.dopravnipodniky.shared.Quintuple
import cz.jaro.dopravnipodniky.shared.barvaChodniku
import cz.jaro.dopravnipodniky.shared.barvaPozadi
import cz.jaro.dopravnipodniky.shared.barvaUlice
import cz.jaro.dopravnipodniky.shared.drawArc
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.shared.jednotky.toDpSUlicema
import cz.jaro.dopravnipodniky.shared.predsazeniKrizovatky
import cz.jaro.dopravnipodniky.shared.sirkaChodniku
import cz.jaro.dopravnipodniky.shared.sirkaUlice

context(DrawScope)
fun namalovatKrizovatku(
    ulice: List<Ulice>,
    krizovatka: Pozice<UlicovyBlok>,
) {
    val (x, y) = krizovatka

    val zacatekX = x.toDpSUlicema()
    val zacatekY = y.toDpSUlicema()

    val sirkaUlice = sirkaUlice.toPx()
    val sirkaChodniku = sirkaChodniku.toPx()
    val predsazeniKrizovatky = predsazeniKrizovatky.toPx()

    val sousedVpravo = ulice.find {
        it.orientace == Orientace.Vodorovne && it.zacatek == krizovatka
    } != null
    val sousedDole = ulice.find {
        it.orientace == Orientace.Svisle && it.zacatek == krizovatka
    } != null
    val sousedVlevo = ulice.find {
        it.orientace == Orientace.Vodorovne && it.konec == krizovatka
    } != null
    val sousedNahore = ulice.find {
        it.orientace == Orientace.Svisle && it.konec == krizovatka
    } != null

    translate(
        left = zacatekX.toPx(),
        top = zacatekY.toPx(),
    ) {
        drawRoundRect(
            color = barvaUlice,
            topLeft = Offset(),
            size = Size(sirkaUlice, sirkaUlice),
            cornerRadius = CornerRadius(sirkaChodniku),
        )

        val sousediUhly = listOf(
            Quintuple(sousedNahore, sousedVpravo, sousedDole, sousedVlevo, 0F),
            Quintuple(sousedVpravo, sousedDole, sousedVlevo, sousedNahore, 90F),
            Quintuple(sousedDole, sousedVlevo, sousedNahore, sousedVpravo, 180F),
            Quintuple(sousedVlevo, sousedNahore, sousedVpravo, sousedDole, 270F),
        )

        sousediUhly.filter { (soused1, _, _, _, _) ->
            soused1
        }.forEach { (_, _, _, _, uhel) ->
            rotate(
                degrees = uhel,
                pivot = Offset(sirkaUlice / 2, sirkaUlice / 2),
            ) {
                drawRoundRect(
                    color = barvaUlice,
                    topLeft = Offset(y = -predsazeniKrizovatky - 1F),
                    size = Size(sirkaUlice, predsazeniKrizovatky + 2),
                )
            }
        }

        sousediUhly.filter { (soused1, soused2, soused3, soused4, _) ->
            !soused1 && !soused3 || !soused1 && soused2 == soused4
        }.forEach { (_, soused2, _, soused4, uhel) ->
            rotate(
                degrees = uhel,
                pivot = Offset(sirkaUlice / 2, sirkaUlice / 2),
            ) {
                drawRoundRect(
                    color = barvaChodniku,
                    topLeft = Offset(),
                    size = Size(sirkaUlice, sirkaChodniku),
                    cornerRadius = CornerRadius(sirkaChodniku),
                )
                if (soused4) drawRoundRect(
                    color = barvaChodniku,
                    topLeft = Offset(x = -predsazeniKrizovatky),
                    size = Size(predsazeniKrizovatky + sirkaChodniku, sirkaChodniku),
                )
                if (soused2) drawRoundRect(
                    color = barvaChodniku,
                    topLeft = Offset(x = sirkaUlice - sirkaChodniku),
                    size = Size(predsazeniKrizovatky + sirkaChodniku, sirkaChodniku),
                )
            }
        }

        sousediUhly
            .filter { (soused1, soused2, soused3, soused4, _) ->
                soused4 && soused1 && !soused2 && !soused3
            }
            .forEach { (_, _, _, _, uhel) ->
                rotate(
                    degrees = uhel,
                    pivot = Offset(sirkaUlice / 2F, sirkaUlice / 2F),
                ) {
                    drawRect(
                        topLeft = Offset(x = -predsazeniKrizovatky + 1F, y = -predsazeniKrizovatky + 1F),
                        size = Size(predsazeniKrizovatky + sirkaUlice, predsazeniKrizovatky + sirkaUlice),
                        color = barvaPozadi,
                        style = Fill,
                    )
                    drawArc(
                        useCenter = true,
                        style = Fill,
                        color = barvaUlice,
                        center = Offset(x = -predsazeniKrizovatky + 0.5F, y = -predsazeniKrizovatky + 0.5F),
                        quadSize = Size(
                            width = predsazeniKrizovatky + sirkaUlice - 0.5F,
                            height = predsazeniKrizovatky + sirkaUlice - 0.5F
                        ),
                        startAngle = 0F,
                        sweepAngle = 90F,
                    )
                    drawArc(
                        useCenter = false,
                        style = Stroke(
                            width = sirkaChodniku,
                        ),
                        color = barvaChodniku,
                        center = Offset(x = -predsazeniKrizovatky, y = -predsazeniKrizovatky),
                        quadSize = Size(
                            width = predsazeniKrizovatky + sirkaUlice - sirkaChodniku / 2,
                            height = predsazeniKrizovatky + sirkaUlice - sirkaChodniku / 2,
                        ),
                        startAngle = 0F,
                        sweepAngle = 90F,
                    )
                }
            }

        sousediUhly
            .filter { (soused1, _, _, soused4, _) ->
                soused1 && soused4
            }
            .forEach { (_, _, _, _, uhel) ->
                rotate(
                    degrees = uhel,
                    pivot = Offset(sirkaUlice / 2F, sirkaUlice / 2F),
                ) {
                    drawRoundRect(
                        color = barvaUlice,
                        topLeft = Offset(-predsazeniKrizovatky + 2, -predsazeniKrizovatky + 2),
                        size = Size(predsazeniKrizovatky, predsazeniKrizovatky),
                    )
                    drawArc(
                        useCenter = true,
                        style = Fill,
                        color = barvaPozadi,
                        center = Offset(x = -predsazeniKrizovatky, y = -predsazeniKrizovatky),
                        quadSize = Size(width = predsazeniKrizovatky, height = predsazeniKrizovatky),
                        startAngle = 0F,
                        sweepAngle = 90F,
                    )
                    drawArc(
                        useCenter = false,
                        style = Stroke(
                            width = sirkaChodniku,
                        ),
                        color = barvaChodniku,
                        center = Offset(x = -predsazeniKrizovatky, y = -predsazeniKrizovatky),
                        quadSize = Size(
                            width = predsazeniKrizovatky + sirkaChodniku / 2,
                            height = predsazeniKrizovatky + sirkaChodniku / 2
                        ),
                        startAngle = 0F,
                        sweepAngle = 90F,
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
    val path = Path()
    path.moveTo(nodes.first().x, nodes.first().y)
    nodes.drop(1).forEach {
        path.lineTo(it.x, it.y)
        path.moveTo(it.x, it.y)
    }
    path.lineTo(nodes.first().x, nodes.first().y)
    return path
}