package cz.jaro.dopravnipodniky.ui.malovani

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.kapacitaZastavky
import cz.jaro.dopravnipodniky.shared.Orientace.Svisle
import cz.jaro.dopravnipodniky.shared.Orientace.Vodorovne
import cz.jaro.dopravnipodniky.shared.barvaChodniku
import cz.jaro.dopravnipodniky.shared.barvaUlice
import cz.jaro.dopravnipodniky.shared.delkaUlice
import cz.jaro.dopravnipodniky.shared.predsazeniKrizovatky
import cz.jaro.dopravnipodniky.shared.sirkaChodniku
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.ui.main.DEBUG_TEXT

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
                ) // nahore
                drawRoundRect(
                    color = barvaChodniku,
                    topLeft = Offset(x = predsazeniKrizovatky - 1, y = sirkaUlice - sirkaChodniku),
                    size = Size(delkaUlice - predsazeniKrizovatky * 2 + 2, sirkaChodniku),
                ) // dole
            }
        }
        if (DEBUG_TEXT) drawIntoCanvas {
            it.nativeCanvas.drawText(
                "$cloveci/$kapacita",
                0F,
                5.dp.toPx(),
                Paint().apply {
                    color = Color.WHITE
                }
            )
        }
        if (DEBUG_TEXT) drawIntoCanvas {
            it.nativeCanvas.drawText(
                "${zastavka?.cloveci}/${kapacitaZastavky()}",
                0F,
                10.dp.toPx(),
                Paint().apply {
                    color = Color.WHITE
                }
            )
        }
    }

    zastavka?.let {
        namalovatZastavku()
    }
}