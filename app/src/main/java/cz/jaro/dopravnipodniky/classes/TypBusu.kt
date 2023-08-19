package cz.jaro.dopravnipodniky.classes

import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.jednotky.DpZaHodinu
import cz.jaro.dopravnipodniky.jednotky.Metr
import cz.jaro.dopravnipodniky.jednotky.Peniz
import cz.jaro.dopravnipodniky.jednotky.PenizZaMinutu
import cz.jaro.dopravnipodniky.jednotky.penezZaMin
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
    val rychlost: DpZaHodinu,
    val maxNaklady: PenizZaMinutu,
    val cena: Peniz,
    val delka: Metr,
    val vydrz: Duration,
    val popis: String,
)

val TypBusu.nakladyTextem: Int
    get() {
        val nakladove = if (trakce is Trakce.Trolejbus) maxNaklady * 2 else maxNaklady

        return when {
            nakladove < 50.penezZaMin -> R.string.velmi_nizke
            nakladove < 60.penezZaMin -> R.string.hodne_nizke
            nakladove < 65.penezZaMin -> R.string.nizke
            nakladove < 70.penezZaMin -> R.string.pomerne_nizke
            nakladove < 75.penezZaMin -> R.string.snizene
            nakladove < 80.penezZaMin -> R.string.normalni
            nakladove < 85.penezZaMin -> R.string.pomerne_vysoke
            nakladove < 90.penezZaMin -> R.string.vysoke
            nakladove < 95.penezZaMin -> R.string.hodne_vysoke
            nakladove < 100.penezZaMin -> R.string.velmi_vysoke
            nakladove < 500.penezZaMin -> R.string.muzejni_bus
            else -> R.string.JOSTOVSKE
        }
    }