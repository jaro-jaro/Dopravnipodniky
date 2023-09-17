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
import cz.jaro.dopravnipodniky.shared.jednotky.toDp
import cz.jaro.dopravnipodniky.shared.odsazeniBusu
import cz.jaro.dopravnipodniky.shared.predsazeniKrizovatky
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.ui.main.DEBUG_TEXT
import java.lang.Math.toRadians
import kotlin.math.abs
import kotlin.math.cos
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

    val poziceVKrizovatce = bus.poziceVUlici - (delkaUlice - predsazeniKrizovatky)

    val natoceniBusu = if (poziceVKrizovatce < 0.dp) 0F else (poziceVKrizovatce / delkaKrizovatky) * uhelZatoceni

    if (poziceVKrizovatce > 0.dp) println(listOf(poziceVKrizovatce, bus.poziceVUlici, natoceniBusu, delkaKrizovatky, uhelZatoceni))

    return {
        val zacatekX = ulice.zacatekX.toPx()
        val zacatekY = ulice.zacatekY.toPx()

        val posunPulkyBusuVUlici = bus.poziceVUlici.toPx()

        val delkaBusu = bus.typBusu.delka.toDp().toPx()
        val sirkaBusu = bus.typBusu.sirka.toDp().toPx()
        val odsazeni = odsazeniBusu.toPx()
        val odsazeni2 = sirkaUlice.toPx() - odsazeni - sirkaBusu
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
                    val malovat: DrawScope.() -> Unit = {
                        rotate(
                            degrees = natoceniBusu,
                            pivot = Offset()
                        ) {
                            drawRoundRect(
                                color = linka.barvicka.barva,
                                topLeft = Offset(
                                    x = -delkaBusu / 2,
                                    y = -sirkaBusu / 2,
                                ),
                                size = Size(
                                    width = delkaBusu,
                                    height = sirkaBusu,
                                ),
                                cornerRadius = CornerRadius(3F.dp.toPx()),
                            )
                            drawRoundRect(
                                color = Color.Green,
                                topLeft = Offset(
                                    x = -1.dp.toPx(),
                                    y = -1.dp.toPx(),
                                ),
                                size = Size(
                                    width = 2.dp.toPx(),
                                    height = 2.dp.toPx(),
                                ),
                                cornerRadius = CornerRadius(3F.dp.toPx()),
                            )
                            if (DEBUG_TEXT) drawIntoCanvas {
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
                    when {
                        poziceVKrizovatce < 0.dp -> translate(
                            left = posunPulkyBusuVUlici,
                            top = sirkaUlice - odsazeni - sirkaBusu / 2,
                        ) {
                            malovat()
                        }

                        krizovatka == Rovne -> translate(
                            left = posunPulkyBusuVUlici,
                            top = sirkaUlice - odsazeni - sirkaBusu / 2,
                        ) {
                            malovat()
                        }

                        krizovatka == Vpravo -> translate(
                            left = delkaUlice - predsazeniKrizovatky.toPx(),
                            top = sirkaUlice + predsazeniKrizovatky.toPx(),
                        ) {
                            val r = predsazeniKrizovatky.toPx() + odsazeni + sirkaBusu / 2
                            val x = r * sin(toRadians(natoceniBusu.toDouble())).toFloat()
                            val y = -r * cos(toRadians(natoceniBusu.toDouble())).toFloat()
                            translate(
                                left = x,
                                top = y,
                            ) {
                                malovat()
                            }
                        }

                        krizovatka == Vlevo -> translate(
                            left = delkaUlice - predsazeniKrizovatky.toPx(),
                            top = -predsazeniKrizovatky.toPx(),
                        ) {
                            val r = predsazeniKrizovatky.toPx() + sirkaUlice - odsazeni - sirkaBusu / 2
                            val x = r * sin(abs(toRadians(natoceniBusu.toDouble()))).toFloat()
                            val y = r * cos(toRadians(natoceniBusu.toDouble())).toFloat()
                            translate(
                                left = x,
                                top = y,
                            ) {
                                malovat()
                            }
                        }

                        else/*krizovatka == Otocka*/ -> translate(
                            left = delkaUlice - predsazeniKrizovatky.toPx(),
                            top = sirkaUlice / 2,
                        ) {
                            val r = sirkaUlice / 2 - odsazeni - sirkaBusu / 2
                            val x = -r * sin(toRadians(natoceniBusu.toDouble())).toFloat()
                            val y = r * cos(toRadians(natoceniBusu.toDouble())).toFloat()
                            translate(
                                left = x,
                                top = y,
                            ) {
                                malovat()
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
