package cz.jaro.dopravnipodniky.ui.malovani

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Krizovatka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.TypKrizovatky
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.shared.Orientace
import cz.jaro.dopravnipodniky.shared.Quintuple
import cz.jaro.dopravnipodniky.shared.barvaTroleje
import cz.jaro.dopravnipodniky.shared.drawArc
import cz.jaro.dopravnipodniky.shared.existuje
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.shared.jednotky.toDpSKrizovatkama
import cz.jaro.dopravnipodniky.shared.maTrolej
import cz.jaro.dopravnipodniky.shared.neexistuje
import cz.jaro.dopravnipodniky.shared.nemaTrolej
import cz.jaro.dopravnipodniky.shared.odsazeniTroleji
import cz.jaro.dopravnipodniky.shared.predsazeniTrolejiL
import cz.jaro.dopravnipodniky.shared.predsazeniTrolejiS
import cz.jaro.dopravnipodniky.shared.sirkaTroleje
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.shared.stavTroleje
import kotlin.math.sqrt

private const val DEBUG_BARVY = false

context (DrawScope)
fun Ulice.nakreslitTroleje() {

    val delkaUlice = delka.toPx()
    val sirka = sirkaTroleje.toPx()
    val predsazeniS = predsazeniTrolejiS.toPx()
    val troleje = odsazeniTroleji.map { it.toPx() }

    translate(
        left = zacatekX.toPx(),
        top = zacatekY.toPx(),
    ) {
        when (orientace) {
            Orientace.Vodorovne -> {
                troleje.forEach { trolej ->
                    drawLine(
                        color = barvaTroleje,
                        start = Offset(predsazeniS, trolej),
                        end = Offset(delkaUlice - predsazeniS, trolej),
                        strokeWidth = sirka,
                    )
                }
            }

            Orientace.Svisle -> {
                troleje.forEach { trolej ->
                    drawLine(
                        color = barvaTroleje,
                        start = Offset(trolej, predsazeniS),
                        end = Offset(trolej, delkaUlice - predsazeniS),
                        strokeWidth = sirka,
                    )
                }
            }
        }
    }

}

context(DrawScope)
fun nakreslitTrolejeNaKrizovatku(
    ulice: List<Ulice>,
    pozice: Pozice<UlicovyBlok>,
    krizovatka: Krizovatka?,
) {

    val sousedVpravo = ulice.find {
        it.orientace == Orientace.Vodorovne && it.zacatek == pozice
    }.stavTroleje
    val sousedDole = ulice.find {
        it.orientace == Orientace.Svisle && it.zacatek == pozice
    }.stavTroleje
    val sousedVlevo = ulice.find {
        it.orientace == Orientace.Vodorovne && it.konec == pozice
    }.stavTroleje
    val sousedNahore = ulice.find {
        it.orientace == Orientace.Svisle && it.konec == pozice
    }.stavTroleje

    val zacatekX = pozice.x.toDpSKrizovatkama().toPx()
    val zacatekY = pozice.y.toDpSKrizovatkama().toPx()

    val sirka = sirkaTroleje.toPx()
    val sirkaUlice = sirkaUlice.toPx()
    val predsazeniS = predsazeniTrolejiS.toPx()
    val predsazeniL = predsazeniTrolejiL.toPx()
    val troleje = odsazeniTroleji.map { it.toPx() }

    translate(
        left = zacatekX,
        top = zacatekY,
    ) {
        drawRect(
            color = if (DEBUG_BARVY) Color.Red else Color.Transparent,
            topLeft = Offset.Zero,
            size = Size(sirkaUlice, sirkaUlice)
        )

        val sousediUhly = listOf(
            Quintuple(sousedNahore, sousedVpravo, sousedDole, sousedVlevo, 0F),
            Quintuple(sousedVpravo, sousedDole, sousedVlevo, sousedNahore, 90F),
            Quintuple(sousedDole, sousedVlevo, sousedNahore, sousedVpravo, 180F),
            Quintuple(sousedVlevo, sousedNahore, sousedVpravo, sousedDole, 270F),
        )

        // Kreslení rovných trolejí
        if (krizovatka == null || krizovatka.typ != TypKrizovatky.Kruhac) sousediUhly
            .filter { (soused1, _, soused3, _, _) ->
                soused1.maTrolej && soused3.maTrolej
            }
            .forEach { (_, _, _, _, uhel) ->
                rotate(
                    degrees = uhel,
                    pivot = Offset(sirkaUlice / 2F, sirkaUlice / 2F),
                ) {
                    troleje.forEach { trolej ->
                        drawLine(
                            color = if (DEBUG_BARVY) Color.Green else barvaTroleje,
                            start = Offset(trolej, -predsazeniS),
                            end = Offset(trolej, sirkaUlice + predsazeniS),
                            strokeWidth = sirka,
                        )
                    }
                }
            }

        // Kreslení oblouků
        if (krizovatka == null || krizovatka.typ != TypKrizovatky.Kruhac) sousediUhly
            .filter { (soused1, _, _, soused4, _) ->
                soused1.maTrolej && soused4.maTrolej
            }
            .forEach { (_, soused2, soused3, _, uhel) ->
                val zatacka = soused2.nemaTrolej && soused3.nemaTrolej
                rotate(
                    degrees = uhel,
                    pivot = Offset(sirkaUlice / 2F, sirkaUlice / 2F),
                ) {
                    val predsazeniM = if (zatacka) predsazeniS else predsazeniL
                    troleje.zip(listOf(predsazeniS, predsazeniS, predsazeniM, predsazeniM)).forEach { (trolej, predsazeni) ->
                        drawArc(
                            useCenter = false,
                            style = Stroke(
                                width = sirka,
                            ),
                            color = if (DEBUG_BARVY) Color.Cyan else barvaTroleje,
                            center = Offset(x = -predsazeni, y = -predsazeni),
                            quadSize = Size(width = predsazeni + trolej, height = predsazeni + trolej),
                            startAngle = 0F,
                            sweepAngle = 90F,
                        )
                    }
                }
            }

        // Kreslení otočky
        if (krizovatka == null || krizovatka.typ != TypKrizovatky.Kruhac) sousediUhly
            .singleOrNull { (soused1, _, _, _, _) ->
                soused1.maTrolej
            }
            ?.let { (_, soused2, soused3, soused4, uhel) ->
                val posunuti = if (soused3.neexistuje && soused2.existuje == soused4.neexistuje) -predsazeniS else sirkaUlice / 2

                rotate(
                    degrees = uhel,
                    pivot = Offset(sirkaUlice / 2F, sirkaUlice / 2F),
                ) {
                    troleje.forEach { trolej ->
                        drawLine(
                            color = if (DEBUG_BARVY) Color.Yellow else barvaTroleje,
                            start = Offset(x = trolej, y = -predsazeniS),
                            end = Offset(x = trolej, y = posunuti),
                            strokeWidth = sirka,
                        )
                    }
    //                troleje.dropLast(2).forEach { trolej ->
    //                    drawArc(
    //                        color = if (DEBUG_BARVY) Color.White else barvaTroleje,
    //                        startAngle = 0F,
    //                        sweepAngle = 180F,
    //                        useCenter = false,
    //                        center = Offset(sirkaUlice / 2, posunuti),
    //                        quadSize = Size(sirkaUlice / 2 - trolej, sirkaUlice / 2 - trolej),
    //                        style = Stroke(
    //                            width = sirka,
    //                        ),
    //                    )
    //                }
                }
            }

        // Kreslení trolejí na kruháči
        if (krizovatka != null && krizovatka.typ == TypKrizovatky.Kruhac && sousediUhly.any { it.first.maTrolej }) {
            val rVnejsi =
                .5 * sqrt(2.0) * sirkaUlice + predsazeniS * sqrt(2.0) - predsazeniS

            troleje.dropLast(2).forEach { trolej ->
                val r = rVnejsi - trolej

                drawCircle(
                    color = barvaTroleje,
                    radius = r.toFloat(),
                    center = Offset(x = sirkaUlice / 2, y = sirkaUlice / 2),
                    style = Stroke(
                        width = sirka,
                    )
                )
            }
        }

        // Kreslení napojení na kruháč
        if (krizovatka != null && krizovatka.typ == TypKrizovatky.Kruhac) sousediUhly
            .filter { (soused1, _, _, _, _) ->
                soused1.maTrolej
            }
            .forEach { (_, _, _, _, uhel) ->
                rotate(
                    degrees = uhel,
                    pivot = Offset(sirkaUlice / 2F, sirkaUlice / 2F),
                ) {
                    troleje.dropLast(2).forEach { trolej ->
                        drawArc(
                            useCenter = false,
                            style = Stroke(
                                width = sirka,
                            ),
                            color = if (DEBUG_BARVY) Color.Red else barvaTroleje,
                            center = Offset(x = -predsazeniS, y = -predsazeniS),
                            quadSize = Size(width = predsazeniS + trolej, height = predsazeniS + trolej),
                            startAngle = 0F,
                            sweepAngle = 45F,
                        )
                    }
                }
            }

        // Kreslení napojení na kruháč
        if (krizovatka != null && krizovatka.typ == TypKrizovatky.Kruhac) sousediUhly
            .filter { (_, _, _, soused4, _) ->
                soused4.maTrolej
            }
            .forEach { (_, soused2, soused3, _, uhel) ->
                rotate(
                    degrees = uhel,
                    pivot = Offset(sirkaUlice / 2F, sirkaUlice / 2F),
                ) {
                    troleje.dropLast(2).forEach { trolej ->
                        drawArc(
                            useCenter = false,
                            style = Stroke(
                                width = sirka,
                            ),
                            color = if (DEBUG_BARVY) Color.Cyan else barvaTroleje,
                            center = Offset(x = -predsazeniS, y = -predsazeniS),
                            quadSize = Size(width = predsazeniS + trolej, height = predsazeniS + trolej),
                            startAngle = 45F,
                            sweepAngle = 45F,
                        )
                    }
                }
            }
    }
}
