package cz.jaro.dopravnipodniky.ui.malovani

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Barak
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.TypBaraku
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.shared.Orientace.Svisle
import cz.jaro.dopravnipodniky.shared.Orientace.Vodorovne
import cz.jaro.dopravnipodniky.shared.delkaUlice
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.shared.jednotky.dpSUlicema
import cz.jaro.dopravnipodniky.shared.odsazeniBaraku
import cz.jaro.dopravnipodniky.shared.sedChodniku
import cz.jaro.dopravnipodniky.shared.sedUlice
import cz.jaro.dopravnipodniky.shared.sirkaChodniku
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.shared.translate
import cz.jaro.dopravnipodniky.shared.ulicovyBlok
import cz.jaro.dopravnipodniky.shared.velikostBaraku
import cz.jaro.dopravnipodniky.ui.main.Offset
import cz.jaro.dopravnipodniky.ui.theme.Theme

context(DrawScope)
fun namalovatKrizovatku(
    dp: DopravniPodnik,
    krizovatka: Pozice<UlicovyBlok>,
) {
    val (x, y) = krizovatka

    val sousedi = dp.ulicove.filter {
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

        if (sousedi.none { it.konec == krizovatka && it.orientace == Svisle }) drawRect(
            color = sedChodniku,
            topLeft = Offset(),
            size = Size(sirkaUlice, chodnik)
        ) // nahore
        if (sousedi.none { it.konec == krizovatka && it.orientace == Vodorovne }) drawRect(
            color = sedChodniku,
            topLeft = Offset(),
            size = Size(chodnik, sirkaUlice)
        ) // vlevo
        if (sousedi.none { it.zacatek == krizovatka && it.orientace == Svisle }) drawRect(
            color = sedChodniku,
            topLeft = Offset(y = sirkaUlice - chodnik),
            size = Size(sirkaUlice, chodnik)
        ) // dole
        if (sousedi.none { it.zacatek == krizovatka && it.orientace == Vodorovne }) drawRect(
            color = sedChodniku,
            topLeft = Offset(x = sirkaUlice - chodnik),
            size = Size(chodnik, sirkaUlice)
        ) // vpravo
    }
}

context (DrawScope)
fun Barak.draw(
    tema: Theme,
    ulice: Ulice,
) {
    val (i, barakJeNaDruheStraneUlice) = ulice.baraky.indexOf(this@draw).let { i ->
        val jeNaDruheStrane = i >= ulice.baraky.size / 2

        (if (jeNaDruheStrane) i - ulice.baraky.size / 2 else i) to jeNaDruheStrane
    }

    val zacatekUliceX = ulice.zacatekX.toPx()
    val zacatekUliceY = ulice.zacatekY.toPx()
    val konecUliceX = ulice.konecX.toPx()
    val konecUliceY = ulice.konecY.toPx()

    val odsazeni = odsazeniBaraku.toPx()

    val indexbarvy = Theme.entries.indexOf(tema)
    val indexSkoroNoveBarvy = indexbarvy + barvicka
    val indexNoveBarvy = indexSkoroNoveBarvy
        .minus(if (indexSkoroNoveBarvy > Theme.entries.lastIndex) Theme.entries.lastIndex else 0)
        .plus(if (indexSkoroNoveBarvy < 1) Theme.entries.lastIndex else 0)
    val barvicka = Theme.entries[indexNoveBarvy].barva // todo je to cerny

    val sirka = velikostBaraku.toPx()

    @Suppress("UnnecessaryVariable")
    val vyska = /*if (ulice.maZastavku && !rohovy) {
        scitanecVysky = .3F * sirka
        sirka * .65F
    } else*/ sirka

    if (typ == TypBaraku.Stredovy) {

        val pulUlicovyhoBloku = ulicovyBlok.toPx() / 2
        translate(
            left = zacatekUliceX,
            top = zacatekUliceY,
        ) {
            val rohBloku = when (ulice.orientace) {
                Svisle -> Offset(x = sirkaUlice.toPx(), y = 0F)
                Vodorovne -> Offset(x = 0F, y = sirkaUlice.toPx())
            }
            translate(
                offset = rohBloku
            ) {
                val stredBloku = Offset(
                    x = pulUlicovyhoBloku,
                    y = pulUlicovyhoBloku
                )
                translate(
                    offset = stredBloku - Offset(x = sirka, y = sirka)
                ) {
                    drawRoundRect(
                        color = barvicka,
                        size = Size(sirka, sirka) * 2F,
                        cornerRadius = CornerRadius(20.dp.toPx())
                    )
                }
            }
        }
    } else {
        when (ulice.orientace to barakJeNaDruheStraneUlice) {
            Vodorovne to true -> drawRoundRect( // dole
                color = barvicka,
                topLeft = Offset(
                    x = zacatekUliceX + odsazeni + sirka + odsazeni + i * (sirka + odsazeni),
                    y = konecUliceY + odsazeni + (sirka - vyska),
                ),
                size = Size(
                    width = sirka,
                    height = vyska,
                ),
                cornerRadius = CornerRadius(5.dp.toPx() * (sirka / vyska))
            )

            Vodorovne to false -> drawRoundRect( // nahore
                color = barvicka,
                topLeft = Offset(
                    x = zacatekUliceX + odsazeni + i * (sirka + odsazeni),
                    y = zacatekUliceY - odsazeni - sirka,
                ),
                size = Size(
                    width = sirka,
                    height = vyska,
                ),
                cornerRadius = CornerRadius(5.dp.toPx() * (sirka / vyska))
            )

            Svisle to true -> drawRoundRect( // vlevo
                color = barvicka,
                topLeft = Offset(
                    x = zacatekUliceX - odsazeni - sirka,
                    y = zacatekUliceY + sirka + odsazeni + odsazeni + i * (sirka + odsazeni),
                ),
                size = Size(
                    width = vyska,
                    height = sirka,
                ),
                cornerRadius = CornerRadius(5.dp.toPx() * (sirka / vyska))
            )

            Svisle to false -> drawRoundRect( // vpravo
                color = barvicka,
                topLeft = Offset(
                    x = konecUliceX + odsazeni + (sirka - vyska),
                    y = zacatekUliceY + odsazeni + i * (sirka + odsazeni),
                ),
                size = Size(
                    width = vyska,
                    height = sirka,
                ),
                cornerRadius = CornerRadius(5.dp.toPx() * (sirka / vyska))
            )
        }
    }
}

context(DrawScope)
fun Ulice.draw() {
//    zastavka?.draw()

    val zacatekX = zacatekX.toPx()
    val zacatekY = zacatekY.toPx()

    //fill(BARVICKY[ulice.potencial])
    //fill(ulice.potencial * 20)obrubnik

    translate(
        left = zacatekX,
        top = zacatekY,
    ) {
        val sirkaChodniku = sirkaChodniku.toPx()
        val sirkaUlice = sirkaUlice.toPx()
        val delkaUlice = delkaUlice.toPx()
        when (orientace) {
            Svisle -> {
                drawRect(
                    color = sedUlice,
                    size = Size(sirkaUlice, delkaUlice)
                )
                drawRect(
                    color = sedChodniku,
                    topLeft = Offset(),
                    size = Size(sirkaChodniku, delkaUlice),
                ) // vlevo
                drawRect(
                    color = sedChodniku,
                    topLeft = Offset(x = sirkaUlice - sirkaChodniku),
                    size = Size(sirkaChodniku, delkaUlice),
                ) // vpravo
            }

            Vodorovne -> {
                drawRect(
                    color = sedUlice,
                    size = Size(delkaUlice, sirkaUlice)
                )
                drawRect(
                    color = sedChodniku,
                    topLeft = Offset(),
                    size = Size(delkaUlice, sirkaChodniku),
                ) // nahore
                drawRect(
                    color = sedChodniku,
                    topLeft = Offset(y = sirkaUlice - sirkaChodniku),
                    size = Size(delkaUlice, sirkaChodniku),
                ) // dole
            }
        }
    }
}