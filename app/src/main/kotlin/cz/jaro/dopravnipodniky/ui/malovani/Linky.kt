package cz.jaro.dopravnipodniky.ui.malovani

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.contains
import cz.jaro.dopravnipodniky.data.dopravnipodnik.maZastavku
import cz.jaro.dopravnipodniky.shared.Orientace
import cz.jaro.dopravnipodniky.shared.streetLength
import cz.jaro.dopravnipodniky.shared.stopLength
import cz.jaro.dopravnipodniky.shared.stopOffset
import cz.jaro.dopravnipodniky.shared.sidewalkWidth
import cz.jaro.dopravnipodniky.shared.streetWidth

fun getNamalovatLinky(
    linky: List<Linka>,
    ulicove: List<Ulice>,
): List<DrawScope.(Boolean) -> Unit> {
    val pocetLinek = linky.size
    val sirka = (streetWidth - sidewalkWidth * 2) / pocetLinek

    val uliceSLinkama = ulicove.map { ulice ->
        ulice to linky.sortedBy { it.cislo }.mapIndexed { i, it -> it to i }.filter { ulice.id in it.first.ulice }
    }
    return uliceSLinkama.flatMap { [ulice, linky] ->
        val zacatekX = ulice.zacatekX
        val zacatekY = ulice.zacatekY

        linky.map { [linka, index] ->

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

            val odsazeniOdBoku = sidewalkWidth + sirka * index + sirka / 2

            val odsazeniVMensiUlici =
                if (mensiUlice?.orientace == ulice.orientace) streetWidth
                else streetWidth - odsazeniOdBoku

            val odsazeniVeVetsiUlici =
                if (vetsiUlice?.orientace == ulice.orientace) streetWidth
                else odsazeniOdBoku

            val maPodSebouZastavku = ulice.maZastavku

            val rohovaRotace = when (ulice.orientace) {
                Orientace.Vodorovne -> 0F
                Orientace.Svisle -> 90F
            }

            val maluj: DrawScope.(Boolean) -> Unit = { jeVybiraniLinky ->
                translate(
                    left = zacatekX.toPx(),
                    top = zacatekY.toPx(),
                ) {
                    rotate(
                        degrees = rohovaRotace,
                        pivot = Offset(x = streetWidth.toPx() / 2, y = streetWidth.toPx() / 2),
                    ) {
                        scale(
                            scaleX = 1F,
                            scaleY = if (ulice.orientace == Orientace.Svisle) -1F else 1F,
                            pivot = Offset(x = streetLength.toPx() / 2, y = streetWidth.toPx() / 2)
                        ) {
                            drawLine(
                                color = linka.color.color.copy(alpha = if (jeVybiraniLinky) 2 / 3F else 1F),
                                start = Offset(
                                    x = -odsazeniVMensiUlici.toPx(),
                                    y = odsazeniOdBoku.toPx(),
                                ),
                                end = Offset(
                                    x = stopOffset.toPx() - sirka.toPx() / 2,
                                    y = odsazeniOdBoku.toPx(),
                                ),
                                strokeWidth = sirka.toPx(),
                                cap = StrokeCap.Round,
                            )
                            drawLine(
                                color = linka.color.color.copy(alpha = if (maPodSebouZastavku) 1 / 3F else if (jeVybiraniLinky) 2 / 3F else 1F),
                                start = Offset(
                                    x = stopOffset.toPx() - sirka.toPx() / 2,
                                    y = odsazeniOdBoku.toPx(),
                                ),
                                end = Offset(
                                    x = stopOffset.toPx() + stopLength.toPx() + sirka.toPx() / 2,
                                    y = odsazeniOdBoku.toPx(),
                                ),
                                strokeWidth = sirka.toPx(),
                                cap = StrokeCap.Round,
                            )
                            drawLine(
                                color = linka.color.color.copy(alpha = if (jeVybiraniLinky) 2 / 3F else 1F),
                                start = Offset(
                                    x = stopOffset.toPx() + stopLength.toPx() + sirka.toPx() / 2,
                                    y = odsazeniOdBoku.toPx(),
                                ),
                                end = Offset(
                                    x = streetLength.toPx() + odsazeniVeVetsiUlici.toPx(),
                                    y = odsazeniOdBoku.toPx(),
                                ),
                                strokeWidth = sirka.toPx(),
                                cap = StrokeCap.Round,
                            )
                        }
                    }
                }
            }
            maluj
        }
    }
}
