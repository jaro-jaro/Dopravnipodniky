package cz.jaro.dopravnipodniky.ui.malovani

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.shared.Orientace
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.shared.jednotky.dpSUlicema
import cz.jaro.dopravnipodniky.shared.sedChodniku
import cz.jaro.dopravnipodniky.shared.sedUlice
import cz.jaro.dopravnipodniky.shared.sirkaChodniku
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.ui.main.Offset

context(DrawScope)
fun namalovatKrizovatku(
    dp: DopravniPodnik,
    krizovatka: Pozice<UlicovyBlok>,
) {
    val (x, y) = krizovatka

    val sousedi = dp.ulice.filter {
        it.konec == krizovatka || it.zacatek == krizovatka
    }

    val zacatekX = x.dpSUlicema
    val zacatekY = y.dpSUlicema

    val sirkaUlice = sirkaUlice.toPx()

    val chodnik = sirkaChodniku.toPx()

    translate(
        left = zacatekX.toPx(),
        top = zacatekY.toPx(),
    ) {
        drawRect(
            color = sedUlice,
            size = Size(sirkaUlice, sirkaUlice),
        )

        if (sousedi.none { it.konec == krizovatka && it.orientace == Orientace.Svisle }) drawRect(
            color = sedChodniku,
            topLeft = Offset(),
            size = Size(sirkaUlice, chodnik)
        ) // nahore
        if (sousedi.none { it.konec == krizovatka && it.orientace == Orientace.Vodorovne }) drawRect(
            color = sedChodniku,
            topLeft = Offset(),
            size = Size(chodnik, sirkaUlice)
        ) // vlevo
        if (sousedi.none { it.zacatek == krizovatka && it.orientace == Orientace.Svisle }) drawRect(
            color = sedChodniku,
            topLeft = Offset(y = sirkaUlice - chodnik),
            size = Size(sirkaUlice, chodnik)
        ) // dole
        if (sousedi.none { it.zacatek == krizovatka && it.orientace == Orientace.Vodorovne }) drawRect(
            color = sedChodniku,
            topLeft = Offset(x = sirkaUlice - chodnik),
            size = Size(chodnik, sirkaUlice)
        ) // vpravo
    }
}