package cz.jaro.dopravnipodniky.ui.malovani

import android.graphics.Paint
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Bus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.zacatekKonecNaLince
import cz.jaro.dopravnipodniky.shared.Orientace
import cz.jaro.dopravnipodniky.shared.Smer
import cz.jaro.dopravnipodniky.shared.TypZatoceni
import cz.jaro.dopravnipodniky.shared.TypZatoceni.Rovne
import cz.jaro.dopravnipodniky.shared.TypZatoceni.Vlevo
import cz.jaro.dopravnipodniky.shared.TypZatoceni.Vpravo
import cz.jaro.dopravnipodniky.shared.delkaUlice
import cz.jaro.dopravnipodniky.shared.delkyCastiZatoceni
import cz.jaro.dopravnipodniky.shared.jednotky.metru
import cz.jaro.dopravnipodniky.shared.jednotky.toDp
import cz.jaro.dopravnipodniky.shared.odsazeniBusu
import cz.jaro.dopravnipodniky.shared.predsazeniKrizovatky
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.shared.sumOfDp
import cz.jaro.dopravnipodniky.shared.translate
import cz.jaro.dopravnipodniky.shared.typZatoceni
import cz.jaro.dopravnipodniky.shared.zip
import cz.jaro.dopravnipodniky.ui.main.DEBUG_MODE
import kotlin.math.sqrt

fun getNamalovatBus(bus: Bus, dp: DopravniPodnik): DrawScope.() -> Unit {
    if (bus.linka == null) return {}
    val linka = dp.linka(bus.linka)

    val seznamUlic = if (bus.smerNaLince == Smer.Pozitivni) {
        linka.ulice.toList()
    } else {
        linka.ulice.reversed()
    }

    val ulicove = dp.ulice.filter { it.id in seznamUlic }.sortedBy { seznamUlic.indexOf(it.id) }

    val ulice = dp.ulice(seznamUlic[bus.poziceNaLince])
    val pristiUlice = seznamUlic.getOrNull(bus.poziceNaLince + 1)?.let { dp.ulice(it) }

    val smerBusuNaUlici = when {
        bus.poziceNaLince != 0 -> { // existuje ulice pred
            val ulicePred = dp.ulice(seznamUlic[bus.poziceNaLince - 1])
            if (ulice.zacatek == ulicePred.zacatek || ulice.zacatek == ulicePred.konec) Smer.Pozitivni else Smer.Negativni
        }

        seznamUlic.size != 1 -> { // existuje ulice po
            val ulicePo = dp.ulice(seznamUlic[1])
            if (ulice.konec == ulicePo.zacatek || ulice.konec == ulicePo.konec) Smer.Pozitivni else Smer.Negativni
        }

        else -> bus.smerNaLince
    }

    val rohovaRotace = when (ulice.orientace) {
        Orientace.Vodorovne -> 0F
        Orientace.Svisle -> 90F
    }

    val stredovaRotace = when (smerBusuNaUlici) {
        Smer.Pozitivni -> 0F
        Smer.Negativni -> 180F
    }

    val orientovanaUlice = ulice.zacatekKonecNaLince(ulicove)

    val krizovatka = dp.krizovatky.find { it.pozice == orientovanaUlice.second }

    println(orientovanaUlice to krizovatka)

    val zatoceni = typZatoceni(krizovatka, ulice, pristiUlice)

    val castiZatoceni = when(zatoceni) {
        Rovne -> listOf(0F, 0F)
        Vlevo -> listOf(0F, -90F)
        Vpravo -> listOf(0F, 90F)
        TypZatoceni.Spatne -> listOf(0F, -180F)
        TypZatoceni.KruhacVpravo -> listOf(0F, 45F, 45F, 90F)
        TypZatoceni.KruhacRovne -> listOf(0F, 45F, -45F, 0F)
        TypZatoceni.KruhacVlevo -> listOf(0F, 45F, -135F, -90F)
        TypZatoceni.KruhacOtocka -> listOf(0F, 45F, -225F, -180F)
    }.windowed(2)

    val delkyCastiZatoceni = zatoceni.delkyCastiZatoceni(bus.typBusu.sirka.toDp())

    val delkaKrizovatky = delkyCastiZatoceni.sumOfDp { it }

    val clanekClanky = bus.typBusu.clanky.scan(0.metru to 0.metru) { (konecPredminulyho, minuly), clanek ->
        konecPredminulyho + minuly to clanek
    }.drop(1)

    val poziceKonceVKrizovatce = bus.poziceVUlici - (delkaUlice - predsazeniKrizovatky)

    val poziceClankuVKrizovatce = clanekClanky.map { (konecMinulyho, clanek) ->
        poziceKonceVKrizovatce + bus.typBusu.delka.toDp() - konecMinulyho.toDp() - clanek.toDp() / 2
    }

    val indexCasti = poziceClankuVKrizovatce.map { pozice ->
        delkyCastiZatoceni.runningReduce { acc, delka -> delka + acc }.let {
            it.indexOfFirst { pozice < it }.takeUnless { it == -1 } ?: it.lastIndex
        }
    }

    val uhelCastiZatoceni = indexCasti.map { i ->
        castiZatoceni[i]
    }

    val delkaCastiZatoceni = indexCasti.map { i ->
        delkyCastiZatoceni[i]
    }

    val posunutiCastiZatoceni = indexCasti.map { i ->
        (listOf(0.dp) + delkyCastiZatoceni.runningReduce { acc, delka -> delka + acc })[i]
    }

    val poziceVCasti = posunutiCastiZatoceni.zip(poziceClankuVKrizovatce) { posunuti, pozice ->
        pozice - posunuti
    }

    val natoceniClanku = zip(poziceVCasti, delkaCastiZatoceni, uhelCastiZatoceni) { pozice, delka, uhel ->
        (pozice.coerceIn(0.dp, delka) / delka) * (uhel.last() - uhel.first()) + uhel.first()
//        pozice.coerceIn(0.dp, delka).map(0.dp to delka, uhel.first()..uhel.last())
    }

//    println(poziceKonceVKrizovatce)
//    println(bus.typBusu.clanky)
//    println(clanekClanky)
//    println(poziceClankuVKrizovatce)
//    println(natoceniClanku)

    return {
        val zacatekX = ulice.zacatekX.toPx()
        val zacatekY = ulice.zacatekY.toPx()

        val clanky = bus.typBusu.clanky.map { it.toDp().toPx() }
        val sirkaBusu = bus.typBusu.sirka.toDp().toPx()
        val odsazeni = odsazeniBusu.toPx()
        val sirkaUlice = sirkaUlice.toPx()
        val delkaUlice = delkaUlice.toPx()

        translate(
            left = zacatekX,
            top = zacatekY,
        ) {
            rotate(
                degrees = rohovaRotace,
                pivot = Offset(x = sirkaUlice / 2, y = sirkaUlice / 2),
            ) {
                rotate(
                    degrees = stredovaRotace,
                    pivot = Offset(x = delkaUlice / 2, y = sirkaUlice / 2),
                ) {
                    val malovat: DrawScope.(clanek: Float, natoceni: Float, i: Int) -> Unit = { clanek, natoceni, i ->
                        rotate(
                            degrees = natoceni,
                            pivot = Offset()
                        ) {
                            val jePrvni = i == 0
                            val jePosledni = i == clanekClanky.lastIndex
                            val zaobleni = sirkaBusu / 4
                            drawRoundRect(
                                color = linka.barvicka.barva,
                                topLeft = Offset(
                                    x = when {
                                        !jePosledni -> -clanek / 2 - zaobleni
                                        else -> -clanek / 2
                                    },
                                    y = -sirkaBusu / 2,
                                ),
                                size = Size(
                                    width = when {
                                        jePrvni && jePosledni -> clanek
                                        jePrvni || jePosledni -> clanek + zaobleni
                                        else /*!jePrvni && !jePosledni*/ -> clanek + zaobleni * 2
                                    },
                                    height = sirkaBusu,
                                ),
                                cornerRadius = CornerRadius(zaobleni * 2),
                            )
//                            drawRoundRect(
//                                color = Color.Green,
//                                topLeft = Offset(
//                                    x = -1.dp.toPx(),
//                                    y = -1.dp.toPx(),
//                                ),
//                                size = Size(
//                                    width = 2.dp.toPx(),
//                                    height = 2.dp.toPx(),
//                                ),
//                                cornerRadius = CornerRadius(3F.dp.toPx()),
//                            )
                            /*if (jePrvni) drawIntoCanvas {
                                it.nativeCanvas.drawText(
//                                    uhelCastiZatoceni[0].joinToString { it.toDouble().zaokrouhlit(1).toString() },
//                                    castiZatoceni.joinToString { it.joinToString { it.toDouble().zaokrouhlit(1).toString() } },
//                                    poziceVCasti.joinToString { it.value.toDouble().zaokrouhlit(1).toString() },
//                                    posunutiCastiZatoceni.joinToString { it.value.toDouble().zaokrouhlit(1).toString() },
//                                    delkyCastiZatoceni.runningReduce { acc, delka -> delka + acc }.joinToString { it.value.toDouble().zaokrouhlit(1).toString() },
//                                    delkyCastiZatoceni.joinToString { it.value.toDouble().zaokrouhlit(1).toString() },
                                    "${poziceClankuVKrizovatce[0].value.toDouble().zaokrouhlit(1)} ${natoceniClanku[0].toDouble().zaokrouhlit(1)} $indexCasti",
//                                    delkyCastiZatoceni.runningReduce { acc, delka -> delka + acc }.joinToString { it.value.toDouble().zaokrouhlit(1).toString() },
                                    0F,
                                    5.dp.toPx(),
                                    Paint().apply {
                                        color = android.graphics.Color.WHITE
                                    }
                                )
                            }*/
                            if (jePrvni && DEBUG_MODE) drawIntoCanvas {
                                it.nativeCanvas.drawText(
                                    "${bus.cloveci}/${bus.typBusu.kapacita}",
                                    0F,
                                    7.dp.toPx(),
                                    Paint().apply {
                                        color = android.graphics.Color.WHITE
                                    }
                                )
                            }
                        }
                    }
                    // --->
                    zip(poziceClankuVKrizovatce, natoceniClanku, clanky, indexCasti, posunutiCastiZatoceni).forEachIndexed { i, (pozice, natoceni, clanek, index, delka) ->
                        when {
                            pozice < 0.dp -> translate(
                                left = delkaUlice - predsazeniKrizovatky.toPx() + pozice.toPx(),
                                top = sirkaUlice - odsazeni - sirkaBusu / 2,
                            ) {
                                malovat(clanek, natoceni, i)
                            }

                            zatoceni == Rovne -> translate(
                                left = delkaUlice - predsazeniKrizovatky.toPx() + pozice.toPx(),
                                top = sirkaUlice - odsazeni - sirkaBusu / 2,
                            ) {
                                malovat(clanek, natoceni, i)
                            }

                            pozice > delkaKrizovatky && zatoceni == TypZatoceni.KruhacRovne -> translate(
                                left = delkaUlice + sirkaUlice + predsazeniKrizovatky.toPx(),
                                top = sirkaUlice + predsazeniKrizovatky.toPx(),
                            ) {
                                val r = predsazeniKrizovatky.toPx() + odsazeni + sirkaBusu / 2
                                translate(
                                    left = pozice.toPx() - delkaKrizovatky.toPx(),
                                    top = -r,
                                ) {
                                    malovat(clanek, natoceni, i)
                                }
                            }

                            pozice > delkaKrizovatky && zatoceni in listOf(Vpravo, TypZatoceni.KruhacVpravo) -> translate(
                                left = delkaUlice - predsazeniKrizovatky.toPx(),
                                top = sirkaUlice + predsazeniKrizovatky.toPx(),
                            ) {
                                val r = predsazeniKrizovatky.toPx() + odsazeni + sirkaBusu / 2
                                translate(
                                    left = r,
                                    top = pozice.toPx() - delkaKrizovatky.toPx(),
                                ) {
                                    malovat(clanek, natoceni, i)
                                }
                            }

                            zatoceni == Vpravo -> translate(
                                pivot = Offset(
                                    x = delkaUlice - predsazeniKrizovatky.toPx(),
                                    y = sirkaUlice + predsazeniKrizovatky.toPx(),
                                ),
                                radius = predsazeniKrizovatky.toPx() + odsazeni + sirkaBusu / 2,
                                degrees = natoceni,
                                isTurnRight = true,
                            ) {
                                malovat(clanek, natoceni, i)
                            }

                            pozice > delkaKrizovatky && zatoceni in listOf(Vlevo, TypZatoceni.KruhacVlevo) -> translate(
                                left = delkaUlice - predsazeniKrizovatky.toPx(),
                                top = -predsazeniKrizovatky.toPx(),
                            ) {
                                val r = predsazeniKrizovatky.toPx() + sirkaUlice - odsazeni - sirkaBusu / 2
                                translate(
                                    left = r,
                                    top = -(pozice.toPx() - delkaKrizovatky.toPx()),
                                ) {
                                    malovat(clanek, natoceni, i)
                                }
                            }

                            zatoceni == Vlevo -> translate(
                                pivot = Offset(
                                    x = delkaUlice - predsazeniKrizovatky.toPx(),
                                    y = -predsazeniKrizovatky.toPx(),
                                ),
                                radius = predsazeniKrizovatky.toPx() + sirkaUlice - odsazeni - sirkaBusu / 2,
                                degrees = natoceni,
                                isTurnRight = false,
                            ) {
                                malovat(clanek, natoceni, i)
                            }

                            pozice > delkaKrizovatky && zatoceni == TypZatoceni.KruhacOtocka -> translate(
                                left = delkaUlice - predsazeniKrizovatky.toPx(),
                                top = sirkaUlice / 2,
                            ) {
                                val r = sirkaUlice / 2 - odsazeni - sirkaBusu / 2
                                translate(
                                    left = -(pozice.toPx() - delkaKrizovatky.toPx()),
                                    top = -r,
                                ) {
                                    malovat(clanek, natoceni, i)
                                }
                            }

                            zatoceni is TypZatoceni.Kruhac && index == 0 -> translate(
                                pivot = Offset(
                                    x = delkaUlice - predsazeniKrizovatky.toPx(),
                                    y = sirkaUlice + predsazeniKrizovatky.toPx(),
                                ),
                                radius = predsazeniKrizovatky.toPx() + odsazeni + sirkaBusu / 2,
                                degrees = natoceni,
                                isTurnRight = true,
                            ) {
                                malovat(clanek, natoceni, i)
                            }

                            zatoceni is TypZatoceni.Kruhac && index == 1 -> translate(
                                pivot = Offset(
                                    x = delkaUlice + sirkaUlice / 2,
                                    y = sirkaUlice / 2,
                                ),
                                radius = .5F * sqrt(2F) * sirkaUlice + predsazeniKrizovatky.toPx() * sqrt(2F) - predsazeniKrizovatky.toPx() - odsazeni - sirkaBusu / 2,
                                degrees = natoceni,
                                isTurnRight = false,
                            ) {
                                malovat(clanek, natoceni, i)
                            }

                            zatoceni == TypZatoceni.KruhacVpravo && index == 2 -> translate(
                                pivot = Offset(
                                    x = delkaUlice - predsazeniKrizovatky.toPx(),
                                    y = sirkaUlice + predsazeniKrizovatky.toPx(),
                                ),
                                radius = predsazeniKrizovatky.toPx() + odsazeni + sirkaBusu / 2,
                                degrees = natoceni,
                                isTurnRight = true,
                            ) {
                                malovat(clanek, natoceni, i)
                            }
                            zatoceni == TypZatoceni.KruhacRovne && index == 2 -> translate(
                                pivot = Offset(
                                    x = delkaUlice + sirkaUlice + predsazeniKrizovatky.toPx(),
                                    y = sirkaUlice + predsazeniKrizovatky.toPx(),
                                ),
                                radius = predsazeniKrizovatky.toPx() + odsazeni + sirkaBusu / 2,
                                degrees = natoceni,
                                isTurnRight = true,
                            ) {
                                malovat(clanek, natoceni, i)
                            }
                            zatoceni == TypZatoceni.KruhacVlevo && index == 2 -> translate(
                                pivot = Offset(
                                    x = delkaUlice + sirkaUlice + predsazeniKrizovatky.toPx(),
                                    y = -predsazeniKrizovatky.toPx(),
                                ),
                                radius = predsazeniKrizovatky.toPx() + odsazeni + sirkaBusu / 2,
                                degrees = natoceni,
                                isTurnRight = true,
                            ) {
                                malovat(clanek, natoceni, i)
                            }
                            zatoceni == TypZatoceni.KruhacOtocka && index == 2 -> translate(
                                pivot = Offset(
                                    x = delkaUlice - predsazeniKrizovatky.toPx(),
                                    y = -predsazeniKrizovatky.toPx(),
                                ),
                                radius = predsazeniKrizovatky.toPx() + odsazeni + sirkaBusu / 2,
                                degrees = natoceni,
                                isTurnRight = true,
                            ) {
                                malovat(clanek, natoceni, i)
                            }
                        }
                    }
                }
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
