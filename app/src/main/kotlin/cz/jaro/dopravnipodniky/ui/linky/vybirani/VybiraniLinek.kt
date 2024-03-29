package cz.jaro.dopravnipodniky.ui.linky.vybirani

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import cz.jaro.dopravnipodniky.shared.drawRoundRect
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.shared.jednotky.toDpSKrizovatkama
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.ui.malovani.Offset
import kotlin.math.sqrt

fun DrawScope.namalovatVybiraniLinky(
    kliklyKrizovatky: List<Pozice<UlicovyBlok>>,
    barva: Color,
) {
    if (kliklyKrizovatky.isEmpty()) return

    val fill = Color(1 - barva.red, 1 - barva.green, 1 - barva.blue)

    val posledniKrizovatka = kliklyKrizovatky.last()

    translate(
        left = posledniKrizovatka.x.toDpSKrizovatkama().toPx(),
        top = posledniKrizovatka.y.toDpSKrizovatkama().toPx(),
    ) {
        drawCircle(
            color = fill.copy(alpha = 2 / 3F),
            radius = (sirkaUlice * sqrt(2F)).toPx(),
            center = Offset(
                x = sirkaUlice.toPx() / 2,
                y = sirkaUlice.toPx() / 2,
            )
        )
    }

    kliklyKrizovatky.windowed(2).forEach { (prvniKrizovatka, druhaKrizovatka) ->

        val zacatekXPrvni = prvniKrizovatka.x.toDpSKrizovatkama().toPx()
        val zacatekYPrvni = prvniKrizovatka.y.toDpSKrizovatkama().toPx()
        val konecXPrvni = (prvniKrizovatka.x.toDpSKrizovatkama() + sirkaUlice).toPx()
        val konecYPrvni = (prvniKrizovatka.y.toDpSKrizovatkama() + sirkaUlice).toPx()

        val zacatekXDruhy = druhaKrizovatka.x.toDpSKrizovatkama().toPx()
        val zacatekYDruhy = druhaKrizovatka.y.toDpSKrizovatkama().toPx()
        val konecXDruhy = (druhaKrizovatka.x.toDpSKrizovatkama() + sirkaUlice).toPx()
        val konecYDruhy = (druhaKrizovatka.y.toDpSKrizovatkama() + sirkaUlice).toPx()

        if (zacatekXPrvni < zacatekXDruhy || zacatekYPrvni < zacatekYDruhy) {
            drawRoundRect(
                color = fill.copy(alpha = 4 / 5F),
                topLeft = Offset(zacatekXPrvni, zacatekYPrvni),
                bottomRight = Offset(konecXDruhy, konecYDruhy),
                cornerRadius = CornerRadius(sirkaUlice.toPx() / 3)
            )
        } else {
            drawRoundRect(
                color = fill.copy(alpha = 4 / 5F),
                topLeft = Offset(zacatekXDruhy, zacatekYDruhy),
                bottomRight = Offset(konecXPrvni, konecYPrvni),
                cornerRadius = CornerRadius(sirkaUlice.toPx() / 3)
            )
        }
    }
}
