package cz.jaro.dopravnipodniky.sketches

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import cz.jaro.dopravnipodniky.Orientace
import cz.jaro.dopravnipodniky.classes.DopravniPodnik
import cz.jaro.dopravnipodniky.classes.Ulice
import cz.jaro.dopravnipodniky.jednotky.Pozice
import cz.jaro.dopravnipodniky.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.jednotky.blokuSUlicema
import cz.jaro.dopravnipodniky.jednotky.toDp
import cz.jaro.dopravnipodniky.odsazeniTroleji
import cz.jaro.dopravnipodniky.predsazeniTrolejiL
import cz.jaro.dopravnipodniky.predsazeniTrolejiS
import cz.jaro.dopravnipodniky.sedTroleje
import cz.jaro.dopravnipodniky.sedUlice
import cz.jaro.dopravnipodniky.sirkaTroleje
import cz.jaro.dopravnipodniky.sirkaUlice

private const val DEBUG_BARVY = false

context (DrawScope)
fun Ulice.nakreslitTroleje() {

    val delkaUlice = delka.toDp(priblizeni).toPx()
    val sirka = sirkaTroleje.toDp(priblizeni.coerceAtLeast(1F)).toPx()
    val predsazeniS = predsazeniTrolejiS.toDp(priblizeni).toPx()
    val troleje = odsazeniTroleji.map { it.toDp(priblizeni).toPx() }

    translate(
        left = zacatekX.toDp(priblizeni).toPx(),
        top = zacatekY.toDp(priblizeni).toPx(),
    ) {
        when (orietace) {
            Orientace.VODOROVNE -> {
                troleje.forEach { trolej ->
                    drawLine(
                        color = sedTroleje,
                        start = Offset(predsazeniS, trolej),
                        end = Offset(delkaUlice - predsazeniS, trolej),
                        strokeWidth = sirka,
                    )
                }
            }

            Orientace.SVISLE -> {
                troleje.forEach { trolej ->
                    drawLine(
                        color = sedTroleje,
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
    dp: DopravniPodnik,
    krizovatka: Pozice<UlicovyBlok>,
) {

    val sousedVpravo = dp.ulicove.find {
        it.orietace == Orientace.VODOROVNE && it.zacatek == krizovatka
    }
    val sousedDole = dp.ulicove.find {
        it.orietace == Orientace.SVISLE && it.zacatek == krizovatka
    }
    val sousedVlevo = dp.ulicove.find {
        it.orietace == Orientace.VODOROVNE && it.konec == krizovatka
    }
    val sousedNahore = dp.ulicove.find {
        it.orietace == Orientace.SVISLE && it.konec == krizovatka
    }

    val ctyrKrizovatka = sousedDole != null && sousedNahore != null && sousedVlevo != null && sousedVpravo != null

    val zacatekX = krizovatka.x.blokuSUlicema.toDp(priblizeni).toPx()
    val zacatekY = krizovatka.y.blokuSUlicema.toDp(priblizeni).toPx()

    val sirka = sirkaTroleje.toDp(priblizeni.coerceAtLeast(1F)).toPx()
    val sirkaUlice = sirkaUlice.toDp(priblizeni).toPx()
    val predsazeniS = predsazeniTrolejiS.toDp(priblizeni).toPx()
    val predsazeniL = predsazeniTrolejiL.toDp(priblizeni).toPx()
    val troleje = odsazeniTroleji.map { it.toDp(priblizeni).toPx() }

    translate(
        left = zacatekX,
        top = zacatekY,
    ) {
        drawRect(
            color = if (DEBUG_BARVY) Color.Red else sedUlice,
            topLeft = Offset.Zero,
            size = Size(sirkaUlice, sirkaUlice)
        )

        val rovinkyUhly = listOf(
            Triple(sousedNahore, sousedDole, 0F), // ║
            Triple(sousedVpravo, sousedVlevo, 90F), // ═
        )

        rovinkyUhly
            .filter { (soused1, soused2, _) ->
                soused1?.maTrolej == true && soused2?.maTrolej == true
            }
            .forEach { (_, _, uhel) ->
                rotate(
                    degrees = uhel,
                    pivot = Offset(sirkaUlice / 2F, sirkaUlice / 2F),
                ) {
                    troleje.forEach { trolej ->
                        drawLine(
                            color = if (DEBUG_BARVY) Color.Green else sedTroleje,
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
                soused1?.maTrolej == true && soused2?.maTrolej == true
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
                            color = if (DEBUG_BARVY) Color.Cyan else sedTroleje,
                            center = Offset(x = -predsazeni, y = -predsazeni),
                            quadSize = Size(width = predsazeni + trolej, height = predsazeni + trolej),
                            startAngle = 0F,
                            sweepAngle = 90F,
                        )
                    }
                }
            }

        val sousediUhly = listOfNotNull(
            if (sousedVpravo?.maTrolej == true) 90F else null,
            if (sousedDole?.maTrolej == true) 180F else null,
            if (sousedVlevo?.maTrolej == true) 270F else null,
            if (sousedNahore?.maTrolej == true) 0F else null,
        )
        if (sousediUhly.size == 1) {
            val uhel = sousediUhly.first()

            rotate(
                degrees = uhel,
                pivot = Offset(sirkaUlice / 2F, sirkaUlice / 2F),
            ) {
                troleje.forEach { trolej ->
                    drawLine(
                        color = if (DEBUG_BARVY) Color.Yellow else sedTroleje,
                        start = Offset(x = trolej, y = -predsazeniS),
                        end = Offset(x = trolej, y = sirkaUlice / 2F),
                        strokeWidth = sirka,
                    )
                }
                troleje.dropLast(2).forEach { trolej ->
                    drawArc(
                        color = if (DEBUG_BARVY) Color.White else sedTroleje,
                        startAngle = 0F,
                        sweepAngle = 180F,
                        useCenter = false,
                        center = Offset(sirkaUlice / 2F, sirkaUlice / 2F),
                        quadSize = Size(trolej, trolej),
                        style = Stroke(
                            width = sirka,
                        ),
                    )
                }
            }
        }
    }
}
