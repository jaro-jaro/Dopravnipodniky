package cz.jaro.dopravnipodniky.ui.malovani

import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.contains
import cz.jaro.dopravnipodniky.data.dopravnipodnik.maZastavku
import cz.jaro.dopravnipodniky.shared.Orientace
import cz.jaro.dopravnipodniky.shared.delkaUlice
import cz.jaro.dopravnipodniky.shared.delkaZastavky
import cz.jaro.dopravnipodniky.shared.odsazeniBarakuAZastavky
import cz.jaro.dopravnipodniky.shared.sirkaChodniku
import cz.jaro.dopravnipodniky.shared.sirkaUlice

fun getNamalovatLinky(
    linky: List<Linka>,
    ulicove: List<Ulice>,
): List<DrawScope.() -> Unit> {
    val pocetLinek = linky.size
    val sirka = (sirkaUlice - sirkaChodniku * 2) / pocetLinek

    val uliceSLinkama = ulicove.map { ulice ->
        ulice to linky.sortedBy { it.cislo }.mapIndexed { i, it -> it to i }.filter { ulice.id in it.first.ulice }
    }
    return uliceSLinkama.flatMap { (ulice, linky) ->
        val zacatekX = ulice.zacatekX
        val zacatekY = ulice.zacatekY

        linky.map { (linka, index) ->

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

            val odsazeniOdBoku = sirka * index + sirka / 2 + sirkaChodniku

            val odsazeniVMensiUlici =
                if (mensiUlice?.orientace == ulice.orientace) sirkaUlice
                else sirkaUlice - odsazeniOdBoku

            val odsazeniVeVetsiUlici =
                if (vetsiUlice?.orientace == ulice.orientace) sirkaUlice
                else odsazeniOdBoku

            val maPodSebouZastavku = ulice.maZastavku

            val rohovaRotace = when (ulice.orientace) {
                Orientace.Vodorovne -> 0F
                Orientace.Svisle -> 90F
            }

            val maluj: DrawScope.() -> Unit = {
                translate(
                    left = zacatekX.toPx(),
                    top = zacatekY.toPx(),
                ) {
                    rotate(
                        degrees = rohovaRotace,
                        pivot = androidx.compose.ui.geometry.Offset(x = sirkaUlice.toPx() / 2, y = sirkaUlice.toPx() / 2),
                    ) {
                        drawLine(
                            color = linka.barvicka.barva,
                            start = Offset(
                                x = -odsazeniVMensiUlici.toPx(),
                                y = odsazeniOdBoku.toPx(),
                            ),
                            end = Offset(
                                x = delkaUlice.toPx() / 2 - delkaZastavky.toPx() / 2 - (odsazeniBarakuAZastavky.toPx() + sirka.toPx() / 2),
                                y = odsazeniOdBoku.toPx(),
                            ),
                            strokeWidth = sirka.toPx(),
                            cap = StrokeCap.Round,
                        )
                        drawLine(
                            color = linka.barvicka.barva.copy(alpha = if (maPodSebouZastavku) 1 / 3F else 1F),
                            start = Offset(
                                x = delkaUlice.toPx() / 2 - delkaZastavky.toPx() / 2 - (odsazeniBarakuAZastavky.toPx() + sirka.toPx() / 2),
                                y = odsazeniOdBoku.toPx(),
                            ),
                            end = Offset(
                                x = delkaUlice.toPx() / 2 + delkaZastavky.toPx() / 2 + (odsazeniBarakuAZastavky.toPx() + sirka.toPx() / 2),
                                y = odsazeniOdBoku.toPx(),
                            ),
                            strokeWidth = sirka.toPx(),
                            cap = StrokeCap.Round,
                        )
                        drawLine(
                            color = linka.barvicka.barva,
                            start = Offset(
                                x = delkaUlice.toPx() / 2 + delkaZastavky.toPx() / 2 + (odsazeniBarakuAZastavky.toPx() + sirka.toPx() / 2),
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
