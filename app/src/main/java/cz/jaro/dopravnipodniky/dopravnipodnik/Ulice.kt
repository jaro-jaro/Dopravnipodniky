package cz.jaro.dopravnipodniky.dopravnipodnik

import cz.jaro.dopravnipodniky.main.SerializableDp
import cz.jaro.dopravnipodniky.shared.Orientace
import cz.jaro.dopravnipodniky.shared.Orientace.SVISLE
import cz.jaro.dopravnipodniky.shared.Orientace.VODOROVNE
import cz.jaro.dopravnipodniky.shared.UliceID
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.shared.jednotky.dpSUlicema
import cz.jaro.dopravnipodniky.shared.nasobitelMaxCloveku
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.roundToInt

/**!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!//
///        ULICE VŽDY POZITIVNĚ        //
///    (ZLEVA DOPRAVA / ZHORA DOLŮ)    //
///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

@Serializable
@SerialName("Ulice")
data class Ulice(
    val zacatek: Pozice<UlicovyBlok>,
    val konec: Pozice<UlicovyBlok>,
    val baraky: List<Barak> = listOf(),
    val potencial: Int = 1,
    val zastavka: Zastavka? = null,
    val maTrolej: Boolean = false,
    val id: UliceID = UliceID.randomUUID()
) {

    val cloveci get() = baraky.sumOf { it.cloveci }
    val kapacita get() = baraky.sumOf { it.kapacita }

    val orietace: Orientace = when {
        zacatek.x == konec.x -> SVISLE
        zacatek.y == konec.y -> VODOROVNE
        else -> SVISLE
    }

    val zacatekX: SerializableDp
    val zacatekY: SerializableDp
    val konecX: SerializableDp
    val konecY: SerializableDp

    val sirka: SerializableDp
    val delka: SerializableDp

    init {
        if (zacatek.x != konec.x && zacatek.y != konec.y) { // diagonala
            throw IllegalArgumentException("Vadná ulice")
        }

        when(orietace) {
            SVISLE -> {
                zacatekX = zacatek.x.dpSUlicema
                zacatekY = zacatek.y.dpSUlicema + sirkaUlice
                konecX = konec.x.dpSUlicema + sirkaUlice
                konecY = konec.y.dpSUlicema

                sirka = konecX - zacatekX
                delka = konecY - zacatekY
            }
            VODOROVNE -> {
                zacatekX = zacatek.x.dpSUlicema + sirkaUlice
                zacatekY = zacatek.y.dpSUlicema
                konecX = konec.x.dpSUlicema
                konecY = konec.y.dpSUlicema + sirkaUlice

                sirka = konecY - zacatekY
                delka = konecX - zacatekX
            }
        }
    }

    val kapacitaZastavky = (kapacita * nasobitelMaxCloveku).roundToInt()
}

operator fun Ulice.contains(other: Pozice<UlicovyBlok>) = other == zacatek || other == konec

fun Ulice.pocetLinek(dp: DopravniPodnik) = dp.linky.count { id in it.ulice }

val Ulice.maZastavku get() = zastavka != null
