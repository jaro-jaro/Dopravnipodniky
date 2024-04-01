package cz.jaro.dopravnipodniky.ui.malovani

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.kapacitaZastavky
import cz.jaro.dopravnipodniky.shared.Orientace.Svisle
import cz.jaro.dopravnipodniky.shared.Orientace.Vodorovne
import cz.jaro.dopravnipodniky.shared.barvaChodniku
import cz.jaro.dopravnipodniky.shared.barvaUlice
import cz.jaro.dopravnipodniky.shared.delkaUlice
import cz.jaro.dopravnipodniky.shared.drawText
import cz.jaro.dopravnipodniky.shared.predsazeniKrizovatky
import cz.jaro.dopravnipodniky.shared.sirkaChodniku
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.ui.main.DEBUG_MODE

context(DrawScope)
fun Ulice.draw() {

    //fill(BARVICKY[ulice.potencial])
    //fill(ulice.potencial * 20)obrubnik

    translate(
        left = zacatekX.toPx(),
        top = zacatekY.toPx(),
    ) {
        val sirkaChodniku = sirkaChodniku.toPx()
        val sirkaUlice = sirkaUlice.toPx()
        val delkaUlice = delkaUlice.toPx()
        val predsazeniKrizovatky = predsazeniKrizovatky.toPx()
        fun debugText() {
            if (DEBUG_MODE) drawText(
                text = "$cloveci/$kapacita",
                position = Offset(
                    x = 5.dp.toPx(),
                    y = 4.3.dp.toPx(),
                ),
            )
            if (DEBUG_MODE) drawText(
                text = "${zastavka?.cloveci}/${kapacitaZastavky()}",
                position = Offset(
                    x = 5.dp.toPx(),
                    y = 8.3.dp.toPx(),
                ),
            )
        }

        when (orientace) {
            Svisle -> {
                drawRect(
                    color = barvaUlice,
                    topLeft = Offset(y = predsazeniKrizovatky),
                    size = Size(sirkaUlice, delkaUlice - predsazeniKrizovatky * 2)
                )
                drawRoundRect(
                    color = barvaChodniku,
                    topLeft = Offset(y = predsazeniKrizovatky - 1),
                    size = Size(sirkaChodniku, delkaUlice - predsazeniKrizovatky * 2 + 2),
                ) // vlevo
                drawRoundRect(
                    color = barvaChodniku,
                    topLeft = Offset(x = sirkaUlice - sirkaChodniku, y = predsazeniKrizovatky - 1),
                    size = Size(sirkaChodniku, delkaUlice - predsazeniKrizovatky * 2 + 2),
                ) // vpravo
                rotate(
                    degrees = 90F,
                    pivot = Offset(sirkaUlice / 2, sirkaUlice / 2)
                ) {
                    debugText()
                }
            }

            Vodorovne -> {
                drawRect(
                    color = barvaUlice,
                    topLeft = Offset(x = predsazeniKrizovatky),
                    size = Size(delkaUlice - predsazeniKrizovatky * 2, sirkaUlice)
                )
                drawRoundRect(
                    color = barvaChodniku,
                    topLeft = Offset(x = predsazeniKrizovatky - 1),
                    size = Size(delkaUlice - predsazeniKrizovatky * 2 + 2, sirkaChodniku),
                    cornerRadius = CornerRadius(1F),
                ) // nahore
                drawRoundRect(
                    color = barvaChodniku,
                    topLeft = Offset(x = predsazeniKrizovatky - 1, y = sirkaUlice - sirkaChodniku),
                    size = Size(delkaUlice - predsazeniKrizovatky * 2 + 2, sirkaChodniku),
                    cornerRadius = CornerRadius(1F),
                ) // dole
                debugText()
            }
        }
    }

    zastavka?.let {
        namalovatZastavku()
    }
}