package cz.jaro.dopravnipodniky.dopravnipodnik

import cz.jaro.dopravnipodniky.shared.jednotky.DpZaHodinu
import cz.jaro.dopravnipodniky.shared.jednotky.Metr
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz
import cz.jaro.dopravnipodniky.shared.jednotky.PenizZaMinutu
import cz.jaro.dopravnipodniky.shared.jednotky.getNakladyTextem
import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
data class TypBusu(
    val model: String,
    val trakce: Trakce,
    val vyrobce: Vyrobce,
    val kapacita: Int,
    val rychlost: DpZaHodinu,
    val maxNaklady: PenizZaMinutu,
    val cena: Peniz,
    val delka: Metr,
    val vydrz: Duration,
    val popis: String,
)

val TypBusu.nakladyTextem: Int get() = maxNaklady.getNakladyTextem(trakce is Trakce.Trolejbus)