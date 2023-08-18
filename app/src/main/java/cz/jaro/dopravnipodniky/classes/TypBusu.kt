package cz.jaro.dopravnipodniky.classes

import cz.jaro.dopravnipodniky.jednotky.BlokZaHodinu
import cz.jaro.dopravnipodniky.jednotky.Metr
import cz.jaro.dopravnipodniky.jednotky.Peniz
import cz.jaro.dopravnipodniky.jednotky.PenizZaMinutu
import cz.jaro.dopravnipodniky.other.Trakce
import cz.jaro.dopravnipodniky.other.Vyrobce
import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
data class TypBusu(
    val model: String,
    val trakce: Trakce,
    val vyrobce: Vyrobce,
    val kapacita: Int,
    val rychlost: BlokZaHodinu,
    val maxNaklady: PenizZaMinutu,
    val cena: Peniz,
    val delka: Metr,
    val vydrz: Duration,
    val popis: String,
)