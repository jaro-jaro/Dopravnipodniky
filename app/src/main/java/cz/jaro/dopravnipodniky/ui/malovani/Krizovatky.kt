package cz.jaro.dopravnipodniky.ui.malovani

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.shared.Orientace
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.shared.jednotky.toDpSUlicema
import cz.jaro.dopravnipodniky.shared.sedChodniku
import cz.jaro.dopravnipodniky.shared.sedUlice
import cz.jaro.dopravnipodniky.shared.sirkaChodniku
import cz.jaro.dopravnipodniky.shared.sirkaUlice

context(DrawScope)
fun namalovatKrizovatku(
    ulice: List<Ulice>,
    krizovatka: Pozice<UlicovyBlok>,
) {
    val (x, y) = krizovatka

    val sousedi = ulice.filter {
        it.konec == krizovatka || it.zacatek == krizovatka
    }

    val zacatekX = x.toDpSUlicema()
    val zacatekY = y.toDpSUlicema()

    val sirkaUlice = sirkaUlice.toPx()

    val chodnik = sirkaChodniku.toPx()

    translate(
        left = zacatekX.toPx(),
        top = zacatekY.toPx(),
    ) {
        drawRoundRect(
            color = sedUlice,
            size = Size(sirkaUlice, sirkaUlice),
            cornerRadius = CornerRadius(chodnik),
        )

        if (sousedi.none { it.konec == krizovatka && it.orientace == Orientace.Svisle }) drawRoundRect(
            color = sedChodniku,
            topLeft = Offset(),
            size = Size(sirkaUlice, chodnik),
            cornerRadius = CornerRadius(chodnik),
        ) // nahore
        if (sousedi.none { it.konec == krizovatka && it.orientace == Orientace.Vodorovne }) drawRoundRect(
            color = sedChodniku,
            topLeft = Offset(),
            size = Size(chodnik, sirkaUlice),
            cornerRadius = CornerRadius(chodnik),
        ) // vlevo
        if (sousedi.none { it.zacatek == krizovatka && it.orientace == Orientace.Svisle }) drawRoundRect(
            color = sedChodniku,
            topLeft = Offset(y = sirkaUlice - chodnik),
            size = Size(sirkaUlice, chodnik),
            cornerRadius = CornerRadius(chodnik),
        ) // dole
        if (sousedi.none { it.zacatek == krizovatka && it.orientace == Orientace.Vodorovne }) drawRoundRect(
            color = sedChodniku,
            topLeft = Offset(x = sirkaUlice - chodnik),
            size = Size(chodnik, sirkaUlice),
            cornerRadius = CornerRadius(chodnik),
        ) // vpravo
    }
}