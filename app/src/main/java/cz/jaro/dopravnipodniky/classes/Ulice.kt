package cz.jaro.dopravnipodniky.classes

import cz.jaro.dopravnipodniky.Orientace
import cz.jaro.dopravnipodniky.Orientace.SVISLE
import cz.jaro.dopravnipodniky.Orientace.VODOROVNE
import cz.jaro.dopravnipodniky.UliceID
import cz.jaro.dopravnipodniky.jednotky.Blok
import cz.jaro.dopravnipodniky.jednotky.Pozice
import cz.jaro.dopravnipodniky.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.jednotky.blokuSUlicema
import cz.jaro.dopravnipodniky.kapacita
import cz.jaro.dopravnipodniky.nasobitelMaxCloveku
import cz.jaro.dopravnipodniky.sirkaUlice
import kotlinx.serialization.Serializable
import kotlin.math.roundToInt

/**!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!//
///        ULICE VŽDY POZITIVNĚ        //
///    (ZLEVA DOPRAVA / ZHORA DOLŮ)    //
///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

@Serializable
data class Ulice(
    val zacatek: Pozice<UlicovyBlok>,
    val konec: Pozice<UlicovyBlok>,
    val baraky: List<Barak> = listOf(),
    val potencial: Int = 1,
    val zastavka: Zastavka? = null,
    val maTrolej: Boolean = true, //TODO false
    val id: UliceID = UliceID.randomUUID()
) {

    val cloveci get() = baraky.sumOf { it.cloveci }
    val kapacita get() = baraky.sumOf { it.typ.kapacita }

    val orietace: Orientace = when {
        zacatek.x == konec.x -> SVISLE
        zacatek.y == konec.y -> VODOROVNE
        else -> SVISLE
    }

    val zacatekX: Blok
    val zacatekY: Blok
    val konecX: Blok
    val konecY: Blok

    val sirka: Blok
    val delka: Blok

    init {
        if (zacatek.x != konec.x && zacatek.y != konec.y) { // diagonala
            throw IllegalArgumentException("Vadná ulice")
        }

        when(orietace) {
            SVISLE -> {
                zacatekX = zacatek.x.blokuSUlicema
                zacatekY = zacatek.y.blokuSUlicema + sirkaUlice
                konecX = konec.x.blokuSUlicema + sirkaUlice
                konecY = konec.y.blokuSUlicema

                sirka = konecX - zacatekX
                delka = konecY - zacatekY
            }
            VODOROVNE -> {
                zacatekX = zacatek.x.blokuSUlicema + sirkaUlice
                zacatekY = zacatek.y.blokuSUlicema
                konecX = konec.x.blokuSUlicema
                konecY = konec.y.blokuSUlicema + sirkaUlice

                sirka = konecY - zacatekY
                delka = konecX - zacatekX
            }
        }
    }

    val kapacitaZastavky = (kapacita * nasobitelMaxCloveku).roundToInt()
}

fun Ulice.pocetLinek(dp: DopravniPodnik) = dp.linky.count { id in it.ulice }

val Ulice.maZastavku get() = zastavka != null
