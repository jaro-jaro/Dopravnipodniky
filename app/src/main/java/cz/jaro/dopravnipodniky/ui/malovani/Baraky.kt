package cz.jaro.dopravnipodniky.ui.malovani

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Barak
import cz.jaro.dopravnipodniky.data.dopravnipodnik.TypBaraku
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.barva
import cz.jaro.dopravnipodniky.data.dopravnipodnik.kapacita
import cz.jaro.dopravnipodniky.shared.Orientace
import cz.jaro.dopravnipodniky.shared.barvaTematu
import cz.jaro.dopravnipodniky.shared.convert
import cz.jaro.dopravnipodniky.shared.odsazeniBaraku
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.shared.toArgb2
import cz.jaro.dopravnipodniky.shared.translate
import cz.jaro.dopravnipodniky.shared.ulicovyBlok
import cz.jaro.dopravnipodniky.shared.velikostBaraku
import cz.jaro.dopravnipodniky.shared.zaobleniBaraku
import cz.jaro.dopravnipodniky.ui.theme.Theme

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

    val barvicka = convert(kapacita, typ.kapacita, typ.barva.barvaTematu(tema))
//    val barvicka = Color(tema.barva.toArgb2())

    println(tema.barva.toArgb2())
    println(Color(tema.barva.toArgb2()).toArgb2())

    val velikost = velikostBaraku.toPx()

    if (typ == TypBaraku.Stredovy) {

        val pulUlicovyhoBloku = ulicovyBlok.toPx() / 2
        translate(
            left = zacatekUliceX,
            top = zacatekUliceY,
        ) {
            val rohBloku = when (ulice.orientace) {
                Orientace.Svisle -> Offset(x = sirkaUlice.toPx(), y = 0F)
                Orientace.Vodorovne -> Offset(x = 0F, y = sirkaUlice.toPx())
            }
            translate(
                offset = rohBloku
            ) {
                val stredBloku = Offset(
                    x = pulUlicovyhoBloku,
                    y = pulUlicovyhoBloku
                )
                translate(
                    offset = stredBloku - Offset(x = velikost, y = velikost)
                ) {
                    drawRoundRect(
                        color = barvicka,
                        size = Size(velikost, velikost) * 2F,
                        cornerRadius = CornerRadius(zaobleniBaraku.toPx() * 4)
                    )
                }
            }
        }
    } else {
        when (ulice.orientace to barakJeNaDruheStraneUlice) {
            Orientace.Vodorovne to true -> drawRoundRect( // dole
                color = barvicka,
                topLeft = Offset(
                    x = zacatekUliceX + odsazeni + (i + 1) * (velikost + odsazeni),
                    y = konecUliceY + odsazeni,
                ),
                size = Size(
                    width = velikost,
                    height = velikost,
                ),
                cornerRadius = CornerRadius(zaobleniBaraku.toPx())
            )

            Orientace.Vodorovne to false -> drawRoundRect( // nahore
                color = barvicka,
                topLeft = Offset(
                    x = zacatekUliceX + odsazeni + i * (velikost + odsazeni),
                    y = zacatekUliceY - odsazeni - velikost,
                ),
                size = Size(
                    width = velikost,
                    height = velikost,
                ),
                cornerRadius = CornerRadius(zaobleniBaraku.toPx())
            )

            Orientace.Svisle to true -> drawRoundRect( // vlevo
                color = barvicka,
                topLeft = Offset(
                    x = zacatekUliceX - odsazeni - velikost,
                    y = zacatekUliceY + odsazeni + (i + 1) * (velikost + odsazeni),
                ),
                size = Size(
                    width = velikost,
                    height = velikost,
                ),
                cornerRadius = CornerRadius(zaobleniBaraku.toPx())
            )

            Orientace.Svisle to false -> drawRoundRect( // vpravo
                color = barvicka,
                topLeft = Offset(
                    x = konecUliceX + odsazeni,
                    y = zacatekUliceY + odsazeni + i * (velikost + odsazeni),
                ),
                size = Size(
                    width = velikost,
                    height = velikost,
                ),
                cornerRadius = CornerRadius(zaobleniBaraku.toPx())
            )
        }
    }
}