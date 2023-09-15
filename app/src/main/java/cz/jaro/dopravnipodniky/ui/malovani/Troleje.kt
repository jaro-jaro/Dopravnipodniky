package cz.jaro.dopravnipodniky.ui.malovani

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.shared.Orientace
import cz.jaro.dopravnipodniky.shared.barvaTroleje
import cz.jaro.dopravnipodniky.shared.drawArc
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.shared.jednotky.toDpSUlicema
import cz.jaro.dopravnipodniky.shared.odsazeniTroleji
import cz.jaro.dopravnipodniky.shared.predsazeniTrolejiL
import cz.jaro.dopravnipodniky.shared.predsazeniTrolejiS
import cz.jaro.dopravnipodniky.shared.sirkaTroleje
import cz.jaro.dopravnipodniky.shared.sirkaUlice

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
    krizovatka: Pozice<UlicovyBlok>,
) {

    val sousedVpravo = ulice.find {
        it.orientace == Orientace.Vodorovne && it.zacatek == krizovatka && it.maTrolej
    }
    val sousedDole = ulice.find {
        it.orientace == Orientace.Svisle && it.zacatek == krizovatka && it.maTrolej
    }
    val sousedVlevo = ulice.find {
        it.orientace == Orientace.Vodorovne && it.konec == krizovatka && it.maTrolej
    }
    val sousedNahore = ulice.find {
        it.orientace == Orientace.Svisle && it.konec == krizovatka && it.maTrolej
    }

    val ctyrKrizovatka = sousedDole != null && sousedNahore != null && sousedVlevo != null && sousedVpravo != null

    val zacatekX = krizovatka.x.toDpSUlicema().toPx()
    val zacatekY = krizovatka.y.toDpSUlicema().toPx()

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

        val rovinkyUhly = listOf(
            Triple(sousedNahore, sousedDole, 0F), // ║
            Triple(sousedVpravo, sousedVlevo, 90F), // ═
        )

        rovinkyUhly
            .filter { (soused1, soused2, _) ->
                soused1 != null && soused2 != null
            }
            .forEach { (_, _, uhel) ->
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

        val zatackyUhly = listOf(
            Triple(sousedDole, sousedVpravo, 180F), // ╔
            Triple(sousedDole, sousedVlevo, 270F), // ╗
            Triple(sousedNahore, sousedVlevo, 0F), // ╝
            Triple(sousedNahore, sousedVpravo, 90F), // ╚
        )

        zatackyUhly
            .filter { (soused1, soused2, _) ->
                soused1 != null && soused2 != null
            }
            .forEach { (_, _, uhel) ->
                rotate(
                    degrees = uhel,
                    pivot = Offset(sirkaUlice / 2F, sirkaUlice / 2F),
                ) {
                    val predsazeniM = if (ctyrKrizovatka) predsazeniL else predsazeniS
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

        val sousediUhly = listOfNotNull(
            if (sousedVpravo != null) 90F else null,
            if (sousedDole != null) 180F else null,
            if (sousedVlevo != null) 270F else null,
            if (sousedNahore != null) 0F else null,
        )
        if (sousediUhly.size == 1) {
            val uhel = sousediUhly.first()

            rotate(
                degrees = uhel,
                pivot = Offset(sirkaUlice / 2F, sirkaUlice / 2F),
            ) {
                troleje.forEach { trolej ->
                    drawLine(
                        color = if (DEBUG_BARVY) Color.Yellow else barvaTroleje,
                        start = Offset(x = trolej, y = -predsazeniS),
                        end = Offset(x = trolej, y = sirkaUlice / 2F),
                        strokeWidth = sirka,
                    )
                }
                troleje.dropLast(2).forEach { trolej ->
                    drawArc(
                        color = if (DEBUG_BARVY) Color.White else barvaTroleje,
                        startAngle = 0F,
                        sweepAngle = 180F,
                        useCenter = false,
                        center = Offset(sirkaUlice / 2F, sirkaUlice / 2F),
                        quadSize = Size(sirkaUlice / 2 - trolej, sirkaUlice / 2 - trolej),
                        style = Stroke(
                            width = sirka,
                        ),
                    )
                }
            }
        }
    }
}
