package cz.jaro.dopravnipodniky.classes

import androidx.core.content.edit
import cz.jaro.dopravnipodniky.other.App
import cz.jaro.dopravnipodniky.other.Orientace
import cz.jaro.dopravnipodniky.other.Orientace.SVISLE
import cz.jaro.dopravnipodniky.other.Orientace.VODOROVNE
import cz.jaro.dopravnipodniky.other.UliceId
import cz.jaro.dopravnipodniky.sirkaUlice
import cz.jaro.dopravnipodniky.velikostUlicovyhoBloku

/**!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!//
///        ULICE VŽDY POZITIVNĚ        //
///    (ZLEVA DOPRAVA / ZHORA DOLŮ)    //
///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

class Ulice(
    val zacatek: Pair<Int, Int>, // v ulicovych blokach
    val konec: Pair<Int, Int>, // v ulicovych blokach
) {

    val baraky = mutableListOf<Barak>()

    val cloveci
        get() = baraky.sumOf { it.cloveci }
    val kapacita
        get() = baraky.sumOf { it.kapacita }

    var zacatekX = zacatek.first // v ulicovych blokach
    var zacatekY = zacatek.second // v ulicovych blokach
    var konecX = konec.first // v ulicovych blokach
    var konecY = konec.second // v ulicovych blokach

    var potencial = 1

    var id: @UliceId Long

    var zacatekXBlokuu = zacatekX * (velikostUlicovyhoBloku + sirkaUlice) // v blocich
    var zacatekYBlokuu = zacatekY * (velikostUlicovyhoBloku + sirkaUlice) // v blocich
    var konecXBlokuu   = konecX   * (velikostUlicovyhoBloku + sirkaUlice) // v blocich
    var konecYBlokuu   = konecY   * (velikostUlicovyhoBloku + sirkaUlice) // v blocich

    var orietace: Orientace

    var zastavka: Zastavka? = null

    var sirkaBlokuu: Int?
    var delkaBlokuu: Int?

    var pocetLinek = 0

    var trolej = false

    init {
        val prefs = App.prefs

        val posledniId = prefs.getLong("ulice_id", 0)

        id = posledniId + 1

        prefs.edit {
            putLong("ulice_id", posledniId + 1)
        }

        if (zacatekX != konecX && zacatekY != konecY) { // diagonala
            throw IllegalArgumentException("Vadná ulice")
        }

        orietace = when {
            zacatekX == konecX -> SVISLE
            zacatekY == konecY -> VODOROVNE
            else -> SVISLE
        }

        when(orietace) {
            SVISLE -> {
                zacatekYBlokuu += sirkaUlice
                konecXBlokuu += sirkaUlice

                sirkaBlokuu = konecXBlokuu - zacatekXBlokuu
                delkaBlokuu = konecYBlokuu - zacatekYBlokuu
            }
            VODOROVNE -> {
                zacatekXBlokuu += sirkaUlice
                konecYBlokuu += sirkaUlice

                sirkaBlokuu = konecYBlokuu - zacatekYBlokuu
                delkaBlokuu = konecXBlokuu - zacatekXBlokuu
            }
        }
    }
}
