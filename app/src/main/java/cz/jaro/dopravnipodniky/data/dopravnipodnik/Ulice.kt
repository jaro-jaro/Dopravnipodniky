package cz.jaro.dopravnipodniky.data.dopravnipodnik

import cz.jaro.dopravnipodniky.shared.Orientace
import cz.jaro.dopravnipodniky.shared.Orientace.Svisle
import cz.jaro.dopravnipodniky.shared.Orientace.Vodorovne
import cz.jaro.dopravnipodniky.shared.UliceID
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.shared.jednotky.dpSUlicema
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.ui.main.SerializableDp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
    val id: UliceID = UliceID.randomUUID(),
    val cloveci: Int = 0,
) {

    override fun toString() = "Ulice(zacatek=$zacatek,konec=$konec,baraky=List(${baraky.size}),zastavka=$zastavka,maTrolej=$maTrolej)"

    val kapacita get() = baraky.sumOf { it.kapacita }

    val orientace: Orientace = when {
        zacatek.x == konec.x -> Svisle
        zacatek.y == konec.y -> Vodorovne
        else -> Svisle
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

        when(orientace) {
            Svisle -> {
                zacatekX = zacatek.x.dpSUlicema
                zacatekY = zacatek.y.dpSUlicema + sirkaUlice
                konecX = konec.x.dpSUlicema + sirkaUlice
                konecY = konec.y.dpSUlicema

                sirka = konecX - zacatekX
                delka = konecY - zacatekY
            }
            Vodorovne -> {
                zacatekX = zacatek.x.dpSUlicema + sirkaUlice
                zacatekY = zacatek.y.dpSUlicema
                konecX = konec.x.dpSUlicema
                konecY = konec.y.dpSUlicema + sirkaUlice

                sirka = konecY - zacatekY
                delka = konecX - zacatekX
            }
        }
    }
}

fun Ulice.zasebevrazdujZastavku() = copy(
    cloveci = cloveci + (zastavka?.cloveci ?: 0),
    zastavka = null,
)

operator fun Ulice.contains(other: Pozice<UlicovyBlok>) = other == zacatek || other == konec

fun Ulice.pocetLinek(dp: DopravniPodnik) = dp.linky.count { id in it.ulice }

val Ulice.maZastavku get() = zastavka != null
