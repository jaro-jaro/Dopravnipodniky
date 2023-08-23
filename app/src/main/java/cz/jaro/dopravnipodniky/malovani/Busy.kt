package cz.jaro.dopravnipodniky.malovani

import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import cz.jaro.dopravnipodniky.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.dopravnipodnik.contains
import cz.jaro.dopravnipodniky.main.Offset
import cz.jaro.dopravnipodniky.shared.Orientace
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.shared.ulicovyBlok

fun getNamalovatLinky(
    linky: List<Linka>,
    ulicove: List<Ulice>,
): List<DrawScope.() -> Unit> {
    val sirkaUlice = sirkaUlice
    val delkaUlice = ulicovyBlok

    val pocetLinek = linky.size
    val sirka = sirkaUlice / pocetLinek

    val uliceSLinkama = ulicove.map { ulice ->
        ulice to linky.sortedBy { it.cislo }.mapIndexed { i, it -> it to i }.filter { ulice.id in it.first.ulice }
    }
    return uliceSLinkama.flatMap { (ulice, linky) ->
        val zacatekX = ulice.zacatekX
        val zacatekY = ulice.zacatekY

        linky.map { (linka, index) ->

            println("${linka.cislo} - $index")

            val indexUliceNaLince = linka.ulice.indexOf(ulice.id)

            val minulaUlice = linka.ulice.getOrNull(indexUliceNaLince - 1)?.let { ulicove.first { ul -> ul.id == it } }
            val dalsiUlice = linka.ulice.getOrNull(indexUliceNaLince + 1)?.let { ulicove.first { ul -> ul.id == it } }
            val mensiUlice = when {
                minulaUlice != null && ulice.zacatek in minulaUlice -> minulaUlice
                dalsiUlice != null && ulice.zacatek in dalsiUlice -> dalsiUlice
                else -> null
            }
            val vetsiUlice = when {
                minulaUlice != null && ulice.konec in minulaUlice -> minulaUlice
                dalsiUlice != null && ulice.konec in dalsiUlice -> dalsiUlice
                else -> null
            }

            val odsazeniOdBoku = sirka * index + sirka / 2

            val odsazeniVMensiUlici =
                if (mensiUlice?.orietace == ulice.orietace) sirkaUlice
                else sirkaUlice - odsazeniOdBoku

            val odsazeniVeVetsiUlici =
                if (vetsiUlice?.orietace == ulice.orietace) sirkaUlice
                else odsazeniOdBoku

            val maluj: DrawScope.() -> Unit = {
                translate(
                    left = zacatekX.toPx(),
                    top = zacatekY.toPx(),
                ) {
                    when (ulice.orietace) {
                        Orientace.SVISLE -> drawLine(
                            color = linka.barvicka.barva,
                            start = Offset(
                                x = odsazeniOdBoku.toPx(),
                                y = -odsazeniVMensiUlici.toPx(),
                            ),
                            end = Offset(
                                x = odsazeniOdBoku.toPx(),
                                y = delkaUlice.toPx() + odsazeniVeVetsiUlici.toPx(),
                            ),
                            strokeWidth = sirka.toPx(),
                            cap = StrokeCap.Round,
                        )

                        Orientace.VODOROVNE -> drawLine(
                            color = linka.barvicka.barva,
                            start = Offset(
                                x = -odsazeniVMensiUlici.toPx(),
                                y = odsazeniOdBoku.toPx(),
                            ),
                            end = Offset(
                                x = delkaUlice.toPx() + odsazeniVeVetsiUlici.toPx(),
                                y = odsazeniOdBoku.toPx(),
                            ),
                            strokeWidth = sirka.toPx(),
                            cap = StrokeCap.Round,
                        )
                    }
                }
            }
            maluj
        }
    }
}
