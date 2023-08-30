package cz.jaro.dopravnipodniky.ui.malovani

import android.graphics.Paint
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.shared.Orientace.Svisle
import cz.jaro.dopravnipodniky.shared.Orientace.Vodorovne
import cz.jaro.dopravnipodniky.shared.delkaUlice
import cz.jaro.dopravnipodniky.shared.sedChodniku
import cz.jaro.dopravnipodniky.shared.sedUlice
import cz.jaro.dopravnipodniky.shared.sirkaChodniku
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.ui.main.DEBUG_TEXT
import cz.jaro.dopravnipodniky.ui.main.Offset

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
        when (orientace) {
            Svisle -> {
                drawRect(
                    color = sedUlice,
                    topLeft = Offset(y = -1F),
                    size = Size(sirkaUlice, delkaUlice + 2F)
                )
                drawRoundRect(
                    color = sedChodniku,
                    topLeft = Offset(y = -sirkaChodniku),
                    size = Size(sirkaChodniku, delkaUlice + sirkaChodniku * 2),
                    cornerRadius = CornerRadius(sirkaChodniku),
                ) // vlevo
                drawRoundRect(
                    color = sedChodniku,
                    topLeft = Offset(x = sirkaUlice - sirkaChodniku, y = -sirkaChodniku),
                    size = Size(sirkaChodniku, delkaUlice + sirkaChodniku * 2),
                    cornerRadius = CornerRadius(sirkaChodniku),
                ) // vpravo
            }

            Vodorovne -> {
                drawRect(
                    color = sedUlice,
                    topLeft = Offset(x = -1F),
                    size = Size(delkaUlice + 2F, sirkaUlice)
                )
                drawRoundRect(
                    color = sedChodniku,
                    topLeft = Offset(x = -sirkaChodniku),
                    size = Size(delkaUlice + sirkaChodniku * 2, sirkaChodniku),
                    cornerRadius = CornerRadius(sirkaChodniku),
                ) // nahore
                drawRoundRect(
                    color = sedChodniku,
                    topLeft = Offset(x = -sirkaChodniku, y = sirkaUlice - sirkaChodniku),
                    size = Size(delkaUlice + sirkaChodniku * 2, sirkaChodniku),
                    cornerRadius = CornerRadius(sirkaChodniku),
                ) // dole
            }
        }
        if (DEBUG_TEXT) drawIntoCanvas {
            it.nativeCanvas.drawText(
                cloveci.toString(),
                0F,
                5.dp.toPx(),
                Paint()
            )
        }
        if (DEBUG_TEXT) drawIntoCanvas {
            it.nativeCanvas.drawText(
                zastavka?.cloveci.toString(),
                0F,
                10.dp.toPx(),
                Paint()
            )
        }
    }

    zastavka?.let {
        namalovatZastavku()
    }
}