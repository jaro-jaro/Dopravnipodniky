package cz.jaro.dopravnipodniky.ui.malovani

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Bus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ulice
import cz.jaro.dopravnipodniky.shared.Orientace
import cz.jaro.dopravnipodniky.shared.Smer
import cz.jaro.dopravnipodniky.shared.TypKrizovatky.Otocka
import cz.jaro.dopravnipodniky.shared.TypKrizovatky.Rovne
import cz.jaro.dopravnipodniky.shared.TypKrizovatky.Vlevo
import cz.jaro.dopravnipodniky.shared.TypKrizovatky.Vpravo
import cz.jaro.dopravnipodniky.shared.delkaUlice
import cz.jaro.dopravnipodniky.shared.jednotky.metru
import cz.jaro.dopravnipodniky.shared.jednotky.toDp
import cz.jaro.dopravnipodniky.shared.odsazeniBusu
import cz.jaro.dopravnipodniky.shared.predsazeniKrizovatky
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.ui.main.DEBUG_TEXT
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

fun getNamalovatBus(bus: Bus, linky: List<Linka>, ulicove: List<Ulice>): DrawScope.() -> Unit {
    if (bus.linka == null) return {}
    val linka = linky.linka(bus.linka)

    val seznamUlic = if (bus.smerNaLince == Smer.Pozitivni) {
        linka.ulice.toList()
    } else {
        linka.ulice.reversed()
    }

    val ulice = ulicove.ulice(seznamUlic[bus.poziceNaLince])
    val pristiUlice = seznamUlic.getOrNull(bus.poziceNaLince + 1)?.let { ulicove.ulice(it) }

    val smerBusuNaUlici = when {
        bus.poziceNaLince != 0 -> { // existuje ulice pred
            val ulicePred = ulicove.ulice(seznamUlic[bus.poziceNaLince - 1])
            if (ulice.zacatek == ulicePred.zacatek || ulice.zacatek == ulicePred.konec) Smer.Pozitivni else Smer.Negativni
        }

        seznamUlic.size != 1 -> { // existuje ulice po
            val ulicePo = ulicove.ulice(seznamUlic[1])
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

    val krizovatka = when {
        pristiUlice == null -> Otocka
        pristiUlice.orientace == ulice.orientace -> Rovne
        else -> {
            val vpravoSvisle = when {
                ulice.zacatek == pristiUlice.konec -> false
                ulice.zacatek == pristiUlice.zacatek -> true
                ulice.konec == pristiUlice.konec -> true
                ulice.konec == pristiUlice.zacatek -> false
                else -> throw IllegalStateException("WTF")
            }

            val vpravo = if (ulice.orientace == Orientace.Svisle) vpravoSvisle else !vpravoSvisle
            if (vpravo) Vpravo else Vlevo
        }
    }

    val uhelZatoceni = when (krizovatka) {
        Otocka -> -180F
        Rovne -> 0F
        Vpravo -> 90F
        Vlevo -> -90F
    }
    val delkaKrizovatky = when (krizovatka) {
        Otocka -> {
            val r = sirkaUlice / 2 - odsazeniBusu - bus.typBusu.sirka.toDp() / 2
            val theta = Math.PI
            predsazeniKrizovatky + theta * r + predsazeniKrizovatky
        }

        Rovne -> predsazeniKrizovatky + sirkaUlice + predsazeniKrizovatky
        Vpravo -> {
            val r = predsazeniKrizovatky + odsazeniBusu + bus.typBusu.sirka.toDp() / 2
            val theta = Math.PI / 2
            theta * r
        }

        Vlevo -> {
            val r = predsazeniKrizovatky + sirkaUlice - odsazeniBusu - bus.typBusu.sirka.toDp() / 2
            val theta = Math.PI / 2
            theta * r
        }
    }

    val clanekClanky = bus.typBusu.clanky.scan(0.metru to 0.metru) { (konecPredminulyho, minuly), clanek ->
        konecPredminulyho + minuly to clanek
    }.drop(1)

    val poziceZacatkuVKrizovatce = bus.poziceVUlici - (delkaUlice - predsazeniKrizovatky)

    val poziceZacatkuClankuVKrizovatce = clanekClanky.map { (konecMinulyho, _) ->
        poziceZacatkuVKrizovatce - konecMinulyho.toDp()
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
                    fun DrawScope.malovat(
                        start: Offset,
                        end: Offset,
                        i: Int,
                    ) {
                        drawLine(
                            color = linka.barvicka.barva,
                            start = start,
                            end = end,
                            strokeWidth = sirkaBusu,
                            cap = StrokeCap.Butt,
                        )

                        val jePrvni = i == 0
                        val jePosledni = i == clanekClanky.lastIndex

                        if (jePrvni && DEBUG_TEXT) drawIntoCanvas {
                            it.nativeCanvas.drawText(
                                bus.cloveci.toString(),
                                start.x,
                                start.y + 5.dp.toPx(),
                                Paint().apply {
                                    color = Color.WHITE
                                }
                            )
                        }
                    }
                    (poziceZacatkuClankuVKrizovatce zip clanky).forEachIndexed { i, (pozice, clanek) ->
                        when {
                            pozice < 0.dp -> translate(
                                left = delkaUlice - predsazeniKrizovatky.toPx() + pozice.toPx(),
                                top = sirkaUlice - odsazeni - sirkaBusu / 2,
                            ) {
                                malovat(
                                    start = Offset(0F, 0F),
                                    end = Offset(-clanek, 0F),
                                    i = i
                                )
                            }

                            krizovatka == Rovne -> translate(
                                left = delkaUlice - predsazeniKrizovatky.toPx() + pozice.toPx(),
                                top = sirkaUlice - odsazeni - sirkaBusu / 2,
                            ) {
                                malovat(
                                    start = Offset(0F, 0F),
                                    end = Offset(-clanek, 0F),
                                    i = i
                                )
                            }

                            pozice > delkaKrizovatky && krizovatka == Vpravo -> translate(
                                left = delkaUlice - predsazeniKrizovatky.toPx(),
                                top = sirkaUlice + predsazeniKrizovatky.toPx(),
                            ) {
                                val r = predsazeniKrizovatky.toPx() + odsazeni + sirkaBusu / 2
                                translate(
                                    left = r,
                                    top = pozice.toPx() - delkaKrizovatky.toPx(),
                                ) {
                                    malovat(
                                        start = Offset(0F, 0F),
                                        end = Offset(0F, -clanek),
                                        i = i
                                    )
                                }
                            }

                            krizovatka == Vpravo -> translate(
                                left = delkaUlice - predsazeniKrizovatky.toPx(),
                                top = sirkaUlice + predsazeniKrizovatky.toPx(),
                            ) {
                                val r = predsazeniKrizovatky.toPx() + odsazeni + sirkaBusu / 2
                                val d = clanek * 1
                                val s = pozice.toPx()
                                val x1 = r * sin(s / r)
                                val y1 = r * cos(s / r)
                                val x = r * sin(s / r) - d * sin(s / r + acos(d / (2 * r)))
                                val y = r * cos(s / r) - d * cos(s / r + acos(d / (2 * r)))

                                malovat(
                                    start = Offset(x, -y),
                                    end = Offset(x1, -y1),
                                    i = i
                                )
                            }

                            pozice > delkaKrizovatky && krizovatka == Vlevo -> translate(
                                left = delkaUlice - predsazeniKrizovatky.toPx(),
                                top = -predsazeniKrizovatky.toPx(),
                            ) {
                                val r = predsazeniKrizovatky.toPx() + sirkaUlice - odsazeni - sirkaBusu / 2
                                translate(
                                    left = r,
                                    top = -(pozice.toPx() - delkaKrizovatky.toPx()),
                                ) {
                                    malovat(
                                        start = Offset(0F, 0F),
                                        end = Offset(0F, clanek),
                                        i = i
                                    )
                                }
                            }

                            krizovatka == Vlevo -> translate(
                                left = delkaUlice - predsazeniKrizovatky.toPx(),
                                top = -predsazeniKrizovatky.toPx(),
                            ) {
                                val r = predsazeniKrizovatky.toPx() + sirkaUlice - odsazeni - sirkaBusu / 2
                                val d = clanek * 1
                                val s = pozice.toPx()
                                val x1 = r * sin(s / r)
                                val y1 = r * cos(s / r)
                                val x = x1 - d * sin(s / r + acos(d / (2 * r)))
                                val y = y1 - d * cos(s / r + acos(d / (2 * r)))

                                println("-------------------------------------------------------------------------------------")
                                println(r)
                                println(d)
                                println(s)
                                println(delkaKrizovatky.toPx())
                                println(r * cos(s / r) - d * cos(s / r + acos(d / (2 * r))))
                                println(r * cos(s / r))
                                println(-d * cos(s / r + acos(d / (2 * r))))
                                println(s / r + acos(d / (2 * r)))
                                println(s / r)
                                println(acos(d / (2 * r)))
                                println(d / (2 * r))

                                malovat(
                                    start = Offset(
                                        if (x < 0) d * (s / (r * acos(1 - (d.pow(2) / (2 * r.pow(2))))) - 1) else x,
                                        if (x < 0) r else y
                                    ),
                                    end = Offset(x1, y1),
                                    i = i
                                )
                            }

                            pozice > delkaKrizovatky/* && krizovatka == Otocka*/ -> translate(
                                left = delkaUlice - predsazeniKrizovatky.toPx(),
                                top = sirkaUlice / 2,
                            ) {
                                val r = sirkaUlice / 2 - odsazeni - sirkaBusu / 2
                                translate(
                                    left = -(pozice.toPx() - delkaKrizovatky.toPx()),
                                    top = -r,
                                ) {
                                    malovat(
                                        start = Offset(0F, 0F),
                                        end = Offset(0F, clanek),
                                        i = i
                                    )
                                }
                            }

                            else/*krizovatka == Otocka*/ -> translate(
                                left = delkaUlice - predsazeniKrizovatky.toPx(),
                                top = sirkaUlice / 2,
                            ) {
//                                val r = sirkaUlice / 2 - odsazeni - sirkaBusu / 2
//                                val d = clanek * 1
//                                val s pozice.toPx()
//                                val x1 = r * sin(s/r)
//                                val y1 = r * cos(s/r)
//                                val x = r * sin(s/r) - d * sin(s/r + acos(d/(2 * r)))
//                                val y = r * cos(s/r) - d * cos(s/r + acos(d/(2 * r)))
//
//                                println(r * cos(s/r) - d * cos(s/r + acos(d/(2 * r))))
//                                println(r * cos(s/r))
//                                println(d * cos(s/r + acos(d/(2 * r))))
//                                println(s/r + acos(d/(2 * r)))
//                                println(s/r)
//                                println(acos(d/(2 * r)))
//                                println(d/(2 * r))
//
//                                malovat(
//                                    start = Offset(x, y),
//                                    end = Offset(x1, y1),
//                                    i = i
//                                )
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
