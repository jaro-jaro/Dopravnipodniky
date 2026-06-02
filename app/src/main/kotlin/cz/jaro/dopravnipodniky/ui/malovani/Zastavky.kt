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
import cz.jaro.dopravnipodniky.shared.delkaUlice
import cz.jaro.dopravnipodniky.shared.delkaZastavky
import cz.jaro.dopravnipodniky.shared.odsazeniCarZastavky
import cz.jaro.dopravnipodniky.shared.odsazeniSloupku
import cz.jaro.dopravnipodniky.shared.posunutiZastavky
import cz.jaro.dopravnipodniky.shared.sirkaCary
import cz.jaro.dopravnipodniky.shared.sirkaSloupku
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.shared.sirkaZastavky
import cz.jaro.dopravnipodniky.shared.tloustkaSloupku

context(drawScope: DrawScope)
fun Ulice.namalovatZastavku() = with(drawScope) {
    translate(
        left = zacatekX.toPx(),
        top = zacatekY.toPx(),
    ) {
        when (orientace) {
            Orientace.Svisle -> {
                zastavka()
                rotate(
                    degrees = 180F,
                    pivot = Offset(sirkaUlice.toPx() / 2, delkaUlice.toPx() / 2)
                ) {
                    zastavka()
                }
            }
            Orientace.Vodorovne -> rotate(
                degrees = -90F,
                pivot = Offset.Zero
            ) {
                translate(
                    left = -sirkaUlice.toPx(),
                ) {
                    zastavka()
                    rotate(
                        degrees = 180F,
                        pivot = Offset(sirkaUlice.toPx() / 2, delkaUlice.toPx() / 2)
                    ) {
                        zastavka()
                    }
                }
            }
        }
    }
}

// Směr dolů
private fun DrawScope.zastavka() {
    translate(
        top = posunutiZastavky.toPx() + delkaZastavky.toPx(),
        left = odsazeniSloupku.toPx()
    ) {
        drawRect(
            color = Color.Black,
            size = Size(width = sirkaSloupku.toPx(), height = tloustkaSloupku.toPx())
        )
    }
    translate(
        top = posunutiZastavky.toPx(),
        left = odsazeniCarZastavky.toPx(),
    ) {
        drawRect(
            color = Color.Yellow,
            size = Size(sirkaZastavky.toPx(), delkaZastavky.toPx()),
            style = Stroke(
                width = sirkaCary.toPx(),
            )
        )
        drawLine(
            color = Color.Yellow,
            start = Offset.Zero,
            end = Offset(sirkaZastavky.toPx(), delkaZastavky.toPx()),
            strokeWidth = sirkaCary.toPx()
        )
        drawLine(
            color = Color.Yellow,
            start = Offset(sirkaZastavky.toPx(), 0F),
            end = Offset(0F, delkaZastavky.toPx()),
            strokeWidth = sirkaCary.toPx()
        )
    }
}