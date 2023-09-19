package cz.jaro.dopravnipodniky.data.dopravnipodnik

import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.serializers.TypBusuSerializer
import cz.jaro.dopravnipodniky.shared.jednotky.DpZaHodinu
import cz.jaro.dopravnipodniky.shared.jednotky.Metr
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz
import cz.jaro.dopravnipodniky.shared.jednotky.PenizZaMinutu
import cz.jaro.dopravnipodniky.shared.jednotky.metru
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable(with = TypBusuSerializer::class)
@SerialName("TypBusu")
data class TypBusu(
    val model: String,
    val trakce: Trakce,
    val vyrobce: Vyrobce,
    val kapacita: Int,
    val maxRychlost: DpZaHodinu,
    val maxNaklady: PenizZaMinutu,
    val cena: Peniz,
    val delka: Metr,
    val clanky: List<Metr> = listOf(delka),
    val sirka: Metr,
    val vydrz: Duration,
    val popis: String,
) {
    override fun toString() = model

    init {
        require(clanky.isNotEmpty())
        require(clanky.sumOf { it.value }.metru == delka)
    }
}

val TypBusu.zrychleniOdebiraniPenez get() = maxNaklady.value / vydrz.inWholeHours

val TypBusu.nakladyTextem: Int get() = getNakladyTextem(zrychleniOdebiraniPenez)

fun getNakladyTextem(zrychleniOdebiraniPenez: Double) = when {
    zrychleniOdebiraniPenez < .16 -> R.string.velmi_nizke
    zrychleniOdebiraniPenez < .22 -> R.string.hodne_nizke
    zrychleniOdebiraniPenez < .25 -> R.string.nizke
    zrychleniOdebiraniPenez < .29 -> R.string.pomerne_nizke
    zrychleniOdebiraniPenez < .35 -> R.string.snizene
    zrychleniOdebiraniPenez < .45 -> R.string.normalni
    zrychleniOdebiraniPenez < .55 -> R.string.pomerne_vysoke
    zrychleniOdebiraniPenez < .66 -> R.string.vysoke
    zrychleniOdebiraniPenez < 1.4 -> R.string.hodne_vysoke
    zrychleniOdebiraniPenez < 3 -> R.string.velmi_vysoke
    zrychleniOdebiraniPenez < 10 -> R.string.muzejni_bus
    else -> R.string.JOSTOVSKE
}