package cz.jaro.dopravnipodniky.ui.malovani

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Bus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.shared.Orientace
import cz.jaro.dopravnipodniky.shared.Smer
import cz.jaro.dopravnipodniky.shared.delkaUlice
import cz.jaro.dopravnipodniky.shared.jednotky.toDp
import cz.jaro.dopravnipodniky.shared.odsazeniBusu
import cz.jaro.dopravnipodniky.shared.sirkaUlice

context (DrawScope)
fun Bus.draw(dp: DopravniPodnik) {
    if (linka == null) return
    val linka = dp.linka(linka)

    val seznamUlic = if (smerNaLince == Smer.POZITIVNE) {
        linka.ulice.toList()
    } else {
        linka.ulice.reversed()
    }

    val ulice = dp.ulice(seznamUlic[poziceNaLince])

    val zacatekX = ulice.zacatekX.toPx()
    val zacatekY = ulice.zacatekY.toPx()

    val posunKonceBusuVUlici = poziceVUlici.toPx()

    val smerBusuNaUlici = when {
        poziceNaLince != 0 -> { // existuje ulice pred
            val ulicePred = dp.ulice(seznamUlic[poziceNaLince - 1])
            if (ulice.zacatek == ulicePred.zacatek || ulice.zacatek == ulicePred.konec) Smer.POZITIVNE else Smer.NEGATIVNE
        }

        seznamUlic.size != 1 -> { // existuje ulice po
            val ulicePo = dp.ulice(seznamUlic[1])
            if (ulice.konec == ulicePo.zacatek || ulice.konec == ulicePo.konec) Smer.POZITIVNE else Smer.NEGATIVNE
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
        when {
            smerBusuNaUlici == Smer.POZITIVNE && ulice.orientace == Orientace.VODOROVNE -> {
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

            smerBusuNaUlici == Smer.POZITIVNE && ulice.orientace == Orientace.SVISLE -> {
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

            smerBusuNaUlici == Smer.NEGATIVNE && ulice.orientace == Orientace.VODOROVNE -> {
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

            smerBusuNaUlici == Smer.NEGATIVNE && ulice.orientace == Orientace.SVISLE -> {
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
