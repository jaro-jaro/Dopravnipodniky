package cz.jaro.dopravnipodniky.classes

import cz.jaro.dopravnipodniky.other.Podtyp
import cz.jaro.dopravnipodniky.other.Trakce
import cz.jaro.dopravnipodniky.other.Vyrobce
import kotlin.math.pow

class TypBusu(
    val model: String,
    val podtyp: Podtyp,
    val trakce: Trakce,
    val vyrobce: Vyrobce,
    val kapacita: Int,
    val rychlost: Int,
    val nasobitelNakladuu: Double,
    val cena: Int,
    val delka: Float,

    // Nezobrazovat:
    maxHodin: Int, // max hodin co to ujede, než se to ponici

    val popis: String,
) {
    val nasobitelPonicenosti = (100.0.pow(1.0 / maxHodin))
}