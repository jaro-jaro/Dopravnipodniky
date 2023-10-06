package cz.jaro.dopravnipodniky.data.dopravnipodnik

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.jaro.dopravnipodniky.shared.DPID
import cz.jaro.dopravnipodniky.shared.Orientace
import cz.jaro.dopravnipodniky.shared.Orientace.Svisle
import cz.jaro.dopravnipodniky.shared.Orientace.Vodorovne
import cz.jaro.dopravnipodniky.shared.UliceID
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.shared.jednotky.toDpSKrizovatkama
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.ui.malovani.SerializableDp
import kotlinx.serialization.SerialName

/**!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!//
///        ULICE VŽDY POZITIVNĚ        //
///    (ZLEVA DOPRAVA / ZHORA DOLŮ)    //
///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

//@Serializable
@Entity
@SerialName("Ulice")
data class Ulice(
    val dpID: DPID,
    val zacatek: Pozice<UlicovyBlok>,
    val konec: Pozice<UlicovyBlok>,
    val potencial: Int = 1,
    val cloveciNaZastavce: Int? = null,
    val maTrolej: Boolean = false,
    @PrimaryKey val id: UliceID = UliceID.randomUUID(),
    val cloveci: Int = 0,
) {

    override fun toString() = "Ulice(zacatek=$zacatek,konec=$konec,cloveciNaZastavce=$cloveciNaZastavce,maTrolej=$maTrolej)"

//    val kapacita get() = baraky.sumOf { it.kapacita }

    val orientace: Orientace
        get() = when {
        zacatek.x == konec.x -> Svisle
        zacatek.y == konec.y -> Vodorovne
        else -> Svisle
    }

    val zacatekX: SerializableDp get() = if (orientace == Svisle) zacatek.x.toDpSKrizovatkama() else zacatek.x.toDpSKrizovatkama() + sirkaUlice
    val zacatekY: SerializableDp get() = if (orientace == Svisle) zacatek.y.toDpSKrizovatkama() + sirkaUlice else zacatek.y.toDpSKrizovatkama()
    val konecX: SerializableDp get() = if (orientace == Svisle) konec.x.toDpSKrizovatkama() + sirkaUlice else konec.x.toDpSKrizovatkama()
    val konecY: SerializableDp get() = if (orientace == Svisle) konec.y.toDpSKrizovatkama() else konec.y.toDpSKrizovatkama() + sirkaUlice

    init {
        if (zacatek.x != konec.x && zacatek.y != konec.y) { // diagonala
            throw IllegalArgumentException("Vadná ulice")
        }
    }
}

fun Ulice.zasebevrazdujZastavku() = copy(
    cloveci = cloveci + (cloveciNaZastavce ?: 0),
    cloveciNaZastavce = null,
)

operator fun Ulice.contains(other: Pozice<UlicovyBlok>) = other == zacatek || other == konec

//fun Ulice.pocetLinek(dp: DopravniPodnik) = dp.linky.count { id in it.ulice }
//fun Ulice.pocetLinek(linky: List<Linka>) = linky.count { id in it.ulice }

val Ulice.maZastavku get() = cloveciNaZastavce != null
