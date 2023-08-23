package cz.jaro.dopravnipodniky.shared

import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.dopravnipodnik.maZastavku
import cz.jaro.dopravnipodniky.dopravnipodnik.pocetLinek
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.roundToLong
import kotlin.math.sqrt
import kotlin.time.Duration


/**
 * Zformátuje číslo, aby vypadalo hezky
 */
fun Double.formatovat(decimalPlaces: Int = 2): Text {
    if (this == Double.POSITIVE_INFINITY) return R.string.nekonecne_mnoho.toText()
    if (this == Double.NEGATIVE_INFINITY) return R.string.nekonecne_malo.toText()

    return this
        .times(10F.pow(decimalPlaces))
        .roundToLong()
        .toDouble()
        .div(10F.pow(decimalPlaces))
        .toBigDecimal()
        .toPlainString()
        .split(".")
        .let {
            if (it.size == 1) it
            else if (it[1] == "0") it.dropLast(1)
            else if (it[1].length == 1) listOf(it[0], "${it[1]}0")
            else it
        }
        .mapIndexed { i, it ->
            if (i == 0) it.formatovatTrojice() else it
        }
        .joinToString(",")
        .toText()
}

private fun String.formatovatTrojice() = toList()
    .reversed()
    .flatMapIndexed { i, cislice ->
        val a = listOf(cislice)

        if ((i + 1) % 3 == 0) a + ' ' else a
    }
    .reversed()
    .joinToString("")
    .trim()

fun Float.formatovat(decimalPlaces: Int = 2) = toDouble().formatovat(decimalPlaces)
fun Long.formatovat(decimalPlaces: Int = 2) = toDouble().formatovat(decimalPlaces)
fun Int.formatovat(decimalPlaces: Int = 2) = toDouble().formatovat(decimalPlaces)

/**
 * Vrátí úroveň města na základě jeho počtu obyvatel, počtu potenciálů, počtu ulic a nepřímo i plochy
 */

fun uroven(plocha: Int, obyv: Int, ulice: Int, potencialy: Int): Int =
    (obyv / plocha.toDouble() * potencialy * ulice * nasobitelUrovne).roundToInt()

/**
 * Vrátí velikost města na základě jeho plochy, počtu obyvatel a úrovně
 */

fun velkomesto(plocha: Int, obyvatel: Int, uroven: Int): Text = when {
    plocha >= 500_000_000 && obyvatel >= 5_000_000 && uroven >= 10_000_000 -> R.string.mesto_jostless
    plocha >= 25_000_000 && obyvatel >= 500_000 && uroven >= 500_000 -> R.string.super_mesto
    plocha >= 15_000_000 && obyvatel >= 250_000 && uroven >= 100_000 -> R.string.ultra_velkomesto
    plocha >= 5_000_000 && obyvatel >= 150_000 && uroven >= 25_000 -> R.string.velkomesto
    plocha >= 2_500_000 && obyvatel >= 40_000 && uroven >= 600 -> R.string.velke_mesto
    plocha >= 1_500_000 && obyvatel >= 10_000 && uroven >= 300 -> R.string.mesto
    plocha >= 500_000 && obyvatel >= 4_000 && uroven >= 40 -> R.string.mestecko
    else -> R.string.vesnice
}.toText()


/**
 * Udělá ideální barvu pozadí cardviewu za daných kritérií
 *
 * 2 -> výrazně
 *
 * 4 -> normálně
 *
 * 6 -> tmavě
 */

//fun pozadi(delitel: Int): Int {
//    return Color.rgb(vse.barva.red / delitel, vse.barva.green / delitel, vse.barva.blue / delitel)
//}

/**
 * Vrátí násobitel náhodnosti, vytvořené z kapacity busu a kapacity zastávky, udávající, kolik lidí je ochotno nastoupit do busu závisle na ceně jízdenky a rozsáhlosti dopravního podniku
 */

fun nasobitelPoctuLidiKteryTiNastoupiDoBusuNaZastavceKdyzZastaviANakyLidiTamJsouAMaVSobeJesteVolneMisto(dp: DopravniPodnik): Double {

    val naRozsahlosti = sqrt(1.0 - (1.0 / (dp.busy.size.coerceAtLeast(1) + 1))) *
            (dp.ulicove.filter { it.maZastavku }.count { it.pocetLinek(dp) != 0 }.toDouble() /
                    dp.ulicove.size.toDouble())

    var naJizdnem = 1.0 -
            abs(
                .6 -
                        abs(
                            .5 -
                                    ((dp.jizdne.value - nasobitelRozsahlosti * naRozsahlosti) /
                                            (nasobitelRozsahlosti * naRozsahlosti / 2.0)
                                            ).pow(2)
                        )
            )

    if (dp.jizdne.value <= nasobitelRozsahlosti * naRozsahlosti * 2 / 5.0) {
        naJizdnem = 4 / 9.0
    }

    //Log.d("funguj", "---${dp.jizdne}/${(naRozsahlosti * nasobitelRozsahlosti).roundToInt()}---")
    //Log.d("funguj", naJizdnem.toString())
    //Log.d("funguj", naRozsahlosti.toString())
    //Log.d("funguj", sqrt(naJizdnem.coerceAtLeast(.0)).toString())

    return sqrt(naJizdnem.coerceAtLeast(.0))
}

fun soucinPromenneNaRozsahlostiVNasobiteliPoctuLidiKteryTiNastoupiDoBusuNaZastavceKdyzZastaviANakyLidiTamJsouAMaVSobeJesteVolneMistoANasobiteleRozsahlosti(
    dp: DopravniPodnik
): Double {


    return sqrt(1.0 - (1.0 / (dp.busy.size.coerceAtLeast(1) + 1))) *
            (dp.ulicove.filter { it.maZastavku }.count { it.pocetLinek(dp) != 0 }.toDouble() /
                    dp.ulicove.size.toDouble()) *
            nasobitelRozsahlosti
}


val Duration.minutes get() = inWholeSeconds / 60.0
val Duration.hours get() = minutes / 60.0

fun <E> List<E>.mutate(mutator: MutableList<E>.() -> Unit): List<E> = buildList {
    addAll(this@mutate)
    apply(mutator)
}