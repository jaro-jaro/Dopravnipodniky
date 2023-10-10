package cz.jaro.dopravnipodniky.ui.malovani

import android.graphics.Paint
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.coerceIn
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Bus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
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
import cz.jaro.dopravnipodniky.shared.zip
import cz.jaro.dopravnipodniky.ui.main.DEBUG_TEXT
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

fun getNamalovatBus(bus: Bus, dp: DopravniPodnik): DrawScope.() -> Unit {
    if (bus.linka == null) return {}
    val linka = dp.linka(bus.linka)

    val seznamUlic = if (bus.smerNaLince == Smer.Pozitivni) {
        linka.ulice.toList()
    } else {
        linka.ulice.reversed()
    }

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
        Otocka -> -Math.PI // -180F
        Rovne -> 0.0 // 0F
        Vpravo -> Math.PI / 2 // 90F
        Vlevo -> -Math.PI / 2 // -90F
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

    // neplatí pro: Rovne vvv

    val pozicePoslednihoKolaPrvnihoClankuVKrizovatce = bus.poziceVUlici - (delkaUlice - predsazeniKrizovatky)

    val vzdalenostMistaDotykuOdKonceClanku = bus.typBusu.clanky.scan(bus.typBusu.posledniKoloVPrvnimClanku.toDp()) { minuly, clanek ->
        clanek.toDp() - minuly
    }.drop(1)

    val uhelNatoceniPrvnihoClanku =
        (pozicePoslednihoKolaPrvnihoClankuVKrizovatce.coerceIn(0.dp, delkaKrizovatky) / delkaKrizovatky) * uhelZatoceni

    val polomerKrizovatky = when (krizovatka) {
        Rovne -> Double.POSITIVE_INFINITY.dp
        Vpravo -> predsazeniKrizovatky + odsazeniBusu + bus.typBusu.sirka.toDp() / 2
        Vlevo -> predsazeniKrizovatky + sirkaUlice - odsazeniBusu - bus.typBusu.sirka.toDp() / 2
        Otocka -> sirkaUlice / 2 - odsazeniBusu - bus.typBusu.sirka.toDp() / 2
    }

    val uhelNatoceniClanku = vzdalenostMistaDotykuOdKonceClanku.scan(uhelNatoceniPrvnihoClanku) { minuly, vzdalenost ->
        minuly - 2 * tan(vzdalenost / polomerKrizovatky)
    }

    // neplatí pro: Rovne ^^^

    val poziceClankuVKrizovatce = clanekClanky.zip(vzdalenostMistaDotykuOdKonceClanku) { (konecMinulyho, clanek), vzdalenost ->
        pozicePoslednihoKolaPrvnihoClankuVKrizovatce + bus.typBusu.posledniKoloVPrvnimClanku.toDp() - konecMinulyho.toDp() - clanek.toDp() + vzdalenost
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
                    fun DrawScope.malovat(clanek: Float, vzdalenostMistaDotykuOdKonceClanku: Float, natoceni: Float, i: Int) {
                        rotate(
                            degrees = natoceni,
                            pivot = Offset()
                        ) {
                            val jePrvni = i == 0
//                            val jePosledni = i == clanekClanky.lastIndex
                            val zaobleni = sirkaBusu / 4
                            drawRoundRect(
                                color = linka.barvicka.barva,
                                topLeft = Offset(
                                    x = when {
//                                        !jePosledni -> -clanek / 2 - zaobleni
                                        else -> -vzdalenostMistaDotykuOdKonceClanku
                                    },
                                    y = -sirkaBusu / 2,
                                ),
                                size = Size(
                                    width = when {
//                                        jePrvni && jePosledni -> clanek
//                                        jePrvni || jePosledni -> clanek + zaobleni
//                                        else /*!jePrvni && !jePosledni*/ -> clanek + zaobleni * 2
                                        else -> clanek
                                    },
                                    height = sirkaBusu,
                                ),
//                                cornerRadius = CornerRadius(zaobleni * 2),
                            )
                            drawRoundRect(
                                color = Color.Green,
                                topLeft = Offset(
                                    x = when {
//                                        !jePosledni -> -clanek / 2 - zaobleni
                                        else -> -2.dp.toPx() / 2
                                    },
                                    y = -2.dp.toPx() / 2,
                                ),
                                size = Size(
                                    width = when {
//                                        jePrvni && jePosledni -> clanek
//                                        jePrvni || jePosledni -> clanek + zaobleni
//                                        else /*!jePrvni && !jePosledni*/ -> clanek + zaobleni * 2
                                        else -> 2.dp.toPx()
                                    },
                                    height = 2.dp.toPx(),
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
                            if (jePrvni && DEBUG_TEXT) drawIntoCanvas {
                                it.nativeCanvas.drawText(
                                    bus.cloveci.toString(),
                                    0F,
                                    5.dp.toPx(),
                                    Paint().apply {
                                        color = android.graphics.Color.WHITE
                                    }
                                )
                            }
                        }
                    }
                    zip(
                        poziceClankuVKrizovatce,
                        vzdalenostMistaDotykuOdKonceClanku,
                        uhelNatoceniClanku,
                        clanky
                    ).forEachIndexed { i, (pozice, vzdalenost, natoceni, clanek) ->
                        when {
                            pozice < 0.dp -> translate(
                                left = delkaUlice - predsazeniKrizovatky.toPx() + pozice.toPx(),
                                top = sirkaUlice - odsazeni - sirkaBusu / 2,
                            ) {
                                malovat(clanek, vzdalenost.toPx(), 0F, i)
                            }

                            krizovatka == Rovne -> translate(
                                left = delkaUlice - predsazeniKrizovatky.toPx() + pozice.toPx(),
                                top = sirkaUlice - odsazeni - sirkaBusu / 2,
                            ) {
                                malovat(clanek, vzdalenost.toPx(), 0F, i)
                            }

                            krizovatka == Vpravo -> translate(
                                left = delkaUlice - predsazeniKrizovatky.toPx(),
                                top = sirkaUlice + predsazeniKrizovatky.toPx(),
                            ) {
                                val x = polomerKrizovatky.toPx() * sin(natoceni).toFloat()
                                val y = polomerKrizovatky.toPx() * cos(natoceni).toFloat()
                                translate(
                                    left = x,
                                    top = -y,
                                ) {
                                    malovat(clanek, vzdalenost.toPx(), Math.toDegrees(natoceni).toFloat(), i)
                                }
                            }

                            krizovatka == Vlevo -> translate(
                                left = delkaUlice - predsazeniKrizovatky.toPx(),
                                top = -predsazeniKrizovatky.toPx(),
                            ) {
                                val x = polomerKrizovatky.toPx() * sin(natoceni).toFloat()
                                val y = polomerKrizovatky.toPx() * cos(natoceni).toFloat()
                                translate(
                                    left = -x,
                                    top = y,
                                ) {
                                    malovat(clanek, vzdalenost.toPx(), Math.toDegrees(natoceni).toFloat(), i)
                                }
                            }

                            else /*krizovatka == Otocka*/ -> translate(
                                left = delkaUlice - predsazeniKrizovatky.toPx(),
                                top = sirkaUlice / 2,
                            ) {
                                val x = polomerKrizovatky.toPx() * sin(natoceni).toFloat()
                                val y = polomerKrizovatky.toPx() * cos(natoceni).toFloat()
                                translate(
                                    left = -x,
                                    top = y,
                                ) {
                                    malovat(clanek, vzdalenost.toPx(), Math.toDegrees(natoceni).toFloat(), i)
                                }
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
