package cz.jaro.dopravnipodniky.ui.malovani

import android.graphics.Paint
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Bus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ulice
import cz.jaro.dopravnipodniky.shared.Orientace
import cz.jaro.dopravnipodniky.shared.Smer
import cz.jaro.dopravnipodniky.shared.delkaUlice
import cz.jaro.dopravnipodniky.shared.jednotky.toDp
import cz.jaro.dopravnipodniky.shared.odsazeniBusu
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.ui.main.DEBUG_TEXT

context (DrawScope)
fun Bus.draw(linky: List<Linka>, ulicove: List<Ulice>,) {
    if (linka == null) return
    val linka = linky.linka(linka)

    val seznamUlic = if (smerNaLince == Smer.Pozitivni) {
        linka.ulice.toList()
    } else {
        linka.ulice.reversed()
    }

    val ulice = ulicove.ulice(seznamUlic[poziceNaLince])

    val zacatekX = ulice.zacatekX.toPx()
    val zacatekY = ulice.zacatekY.toPx()

    val posunKonceBusuVUlici = poziceVUlici.toPx()

    val smerBusuNaUlici = when {
        poziceNaLince != 0 -> { // existuje ulice pred
            val ulicePred = ulicove.ulice(seznamUlic[poziceNaLince - 1])
            if (ulice.zacatek == ulicePred.zacatek || ulice.zacatek == ulicePred.konec) Smer.Pozitivni else Smer.Negativni
        }

        seznamUlic.size != 1 -> { // existuje ulice po
            val ulicePo = ulicove.ulice(seznamUlic[1])
            if (ulice.konec == ulicePo.zacatek || ulice.konec == ulicePo.konec) Smer.Pozitivni else Smer.Negativni
        }

        else -> smerNaLince
    }

    val delkaBusu = typBusu.delka.toDp().toPx()
    val sirkaBusu = typBusu.sirka.toDp().toPx()
    val odsazeni = odsazeniBusu.toPx()
    val odsazeni2 = sirkaUlice.toPx() - odsazeni - sirkaBusu
    val delkaUlice = delkaUlice.toPx()

    translate(
        left = zacatekX,
        top = zacatekY,
    ) {

        if (DEBUG_TEXT) drawIntoCanvas {
            it.nativeCanvas.drawText(
                cloveci.toString(),
                15.dp.toPx(),
                5.dp.toPx(),
                Paint()
            )
        }
        when {
            smerBusuNaUlici == Smer.Pozitivni && ulice.orientace == Orientace.Vodorovne -> {
                // bus jede doprava
                drawRoundRect(
                    color = linka.barvicka.barva,
                    topLeft = Offset(
                        x = posunKonceBusuVUlici,
                        y = odsazeni2,
                    ),
                    size = Size(
                        width = delkaBusu,
                        height = sirkaBusu,
                    ),
                    cornerRadius = CornerRadius(3F.dp.toPx()),
                )
            }

            smerBusuNaUlici == Smer.Pozitivni && ulice.orientace == Orientace.Svisle -> {
                // bus jede dolu
                drawRoundRect(
                    color = linka.barvicka.barva,
                    topLeft = Offset(
                        x = odsazeni,
                        y = posunKonceBusuVUlici,
                    ),
                    size = Size(
                        width = sirkaBusu,
                        height = delkaBusu,
                    ),
                    cornerRadius = CornerRadius(3F.dp.toPx()),
                )
//                pozice = zacatekX + odsazeni to zacatekY + posunKonceBusuVUlici
            }

            smerBusuNaUlici == Smer.Negativni && ulice.orientace == Orientace.Vodorovne -> {
                // bus jede doleva
                drawRoundRect(
                    color = linka.barvicka.barva,
                    topLeft = Offset(
                        x = delkaUlice - posunKonceBusuVUlici - delkaBusu,
                        y = odsazeni,
                    ),
                    size = Size(
                        width = delkaBusu,
                        height = sirkaBusu,
                    ),
                    cornerRadius = CornerRadius(3F.dp.toPx()),
                )
//                pozice = zacatekX + b - posunKonceBusuVUlici to zacatekY + odsazeni
            }

            smerBusuNaUlici == Smer.Negativni && ulice.orientace == Orientace.Svisle -> {
                // bus jede nahoru
                drawRoundRect(
                    color = linka.barvicka.barva,
                    topLeft = Offset(
                        x = odsazeni2,
                        y = delkaUlice - posunKonceBusuVUlici - delkaBusu,
                    ),
                    size = Size(
                        width = sirkaBusu,
                        height = delkaBusu,
                    ),
                    cornerRadius = CornerRadius(3F.dp.toPx()),
                )
//                pozice = zacatekX + odsazeni + 16F.toPx() to zacatekY + b - posunKonceBusuVUlici
            }
        }
    }

//        if (dp.sledovanejBus == this@draw) sleduj()
}

/*
context (DrawScope)
fun Bus.sleduj() {
    tx = -pozice.first + pocatecniPosunutiX
    ty = -pozice.second + pocatecniPosunutiY
    if (velikostBloku <= 1.9) {
        velikostBloku *= (TPS + .1F) / TPS.toFloat()
    }
}
*/
