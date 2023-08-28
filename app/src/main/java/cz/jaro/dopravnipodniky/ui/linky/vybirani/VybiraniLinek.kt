package cz.jaro.dopravnipodniky.ui.linky.vybirani

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import cz.jaro.dopravnipodniky.shared.drawRoundRect
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.shared.jednotky.dpSUlicema
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.ui.main.Offset
import kotlin.math.sqrt

fun DrawScope.namalovatVybiraniLinky(
    kliklyKrizovatky: List<Pozice<UlicovyBlok>>,
    barva: Color,
) {
    if (kliklyKrizovatky.isEmpty()) return

    val fill = Color(1 - barva.red, 1 - barva.green, 1 - barva.blue)

    val posledniKrizovatka = kliklyKrizovatky.last()

    translate(
        left = posledniKrizovatka.x.dpSUlicema.toPx(),
        top = posledniKrizovatka.y.dpSUlicema.toPx(),
    ) {
        drawCircle(
            color = fill,
            radius = (sirkaUlice * sqrt(2F)).toPx(),
            center = Offset(
                x = sirkaUlice.toPx() / 2,
                y = sirkaUlice.toPx() / 2,
            )
        )
    }

    kliklyKrizovatky.windowed(2).forEach { (prvniKrizovatka, druhaKrizovatka) ->

        val zacatekXPrvni = prvniKrizovatka.x.dpSUlicema.toPx()
        val zacatekYPrvni = prvniKrizovatka.y.dpSUlicema.toPx()
        val konecXPrvni = (prvniKrizovatka.x.dpSUlicema + sirkaUlice).toPx()
        val konecYPrvni = (prvniKrizovatka.y.dpSUlicema + sirkaUlice).toPx()

        val zacatekXDruhy = druhaKrizovatka.x.dpSUlicema.toPx()
        val zacatekYDruhy = druhaKrizovatka.y.dpSUlicema.toPx()
        val konecXDruhy = (druhaKrizovatka.x.dpSUlicema + sirkaUlice).toPx()
        val konecYDruhy = (druhaKrizovatka.y.dpSUlicema + sirkaUlice).toPx()

        if (zacatekXPrvni < zacatekXDruhy || zacatekYPrvni < zacatekYDruhy) {
            drawRoundRect(
                color = fill,
                topLeft = Offset(zacatekXPrvni, zacatekYPrvni),
                bottomRight = Offset(konecXDruhy, konecYDruhy),
                cornerRadius = CornerRadius(sirkaUlice.toPx() / 3)
            )
        } else {
            drawRoundRect(
                color = fill,
                topLeft = Offset(zacatekXDruhy, zacatekYDruhy),
                bottomRight = Offset(konecXPrvni, konecYPrvni),
                cornerRadius = CornerRadius(sirkaUlice.toPx() / 3)
            )
        }
    }
}
