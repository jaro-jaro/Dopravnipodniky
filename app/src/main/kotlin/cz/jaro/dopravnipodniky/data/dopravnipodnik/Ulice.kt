package cz.jaro.dopravnipodniky.data.dopravnipodnik

import cz.jaro.dopravnipodniky.shared.Orientace
import cz.jaro.dopravnipodniky.shared.Orientace.Svisle
import cz.jaro.dopravnipodniky.shared.Orientace.Vodorovne
import cz.jaro.dopravnipodniky.shared.Smer
import cz.jaro.dopravnipodniky.shared.UliceID
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.shared.jednotky.Vector
import cz.jaro.dopravnipodniky.shared.jednotky.toDpSKrizovatkama
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.ui.malovani.SerializableDp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!//
///        ULICE VŽDY POZITIVNĚ        //
///    (ZLEVA DOPRAVA / ZHORA DOLŮ)    //
///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

@Serializable
@SerialName("Ulice")
data class Ulice(
    val zacatek: Vector<UlicovyBlok>,
    val konec: Vector<UlicovyBlok>,
    val baraky: List<Barak> = listOf(),
    val potencial: Int = 1,
    val zastavka: Zastavka? = null,
    val maTrolej: Boolean = false,
    val id: UliceID = UliceID.randomUUID(),
    val cloveci: Int = 0,
) {

    override fun toString() =
        "Ulice(zacatek=$zacatek,konec=$konec,baraky=List(${baraky.size}),zastavka=$zastavka,maTrolej=$maTrolej)"

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

        when (orientace) {
            Svisle -> {
                zacatekX = zacatek.x.toDpSKrizovatkama()
                zacatekY = zacatek.y.toDpSKrizovatkama() + sirkaUlice
                konecX = konec.x.toDpSKrizovatkama() + sirkaUlice
                konecY = konec.y.toDpSKrizovatkama()

                sirka = konecX - zacatekX
                delka = konecY - zacatekY
            }

            Vodorovne -> {
                zacatekX = zacatek.x.toDpSKrizovatkama() + sirkaUlice
                zacatekY = zacatek.y.toDpSKrizovatkama()
                konecX = konec.x.toDpSKrizovatkama()
                konecY = konec.y.toDpSKrizovatkama() + sirkaUlice

                sirka = konecY - zacatekY
                delka = konecX - zacatekX
            }
        }
    }
}

fun Ulice.orientedInLine(line: List<Ulice>) =
    orientedInLine(directionInLine(line))

fun Ulice.orientedInLine(streetDirectionInLine: Smer) =
    when (streetDirectionInLine) {
        Smer.Pozitivni -> zacatek to konec
        Smer.Negativni -> konec to zacatek
    }

fun Ulice.directionInLine(line: List<Ulice>): Smer {
    val i = line.indexOfFirst { it.id == id }
    val nextStreet = line.getOrNull(i + 1)
    val previousStreet = line.getOrNull(i - 1)
    return when {
        nextStreet != null && zacatek in nextStreet -> Smer.Negativni
        nextStreet != null && konec in nextStreet -> Smer.Pozitivni
        previousStreet != null && konec in previousStreet -> Smer.Negativni
        previousStreet != null && zacatek in previousStreet -> Smer.Pozitivni
        else -> Smer.Pozitivni // Linka má 1 ulici => směr linky v ulici pozitivní
    }
}

fun List<Ulice>.krizovatkyNaLince() =
    flatMap { it.orientedInLine(this).toList() }.distinct()

fun Ulice.zasebevrazdujZastavku() = copy(
    cloveci = cloveci + (zastavka?.cloveci ?: 0),
    zastavka = null,
)

val Ulice.krizovatky get() = listOf(zacatek, konec)

operator fun Ulice.contains(other: Vector<UlicovyBlok>) = other == zacatek || other == konec

infix fun Ulice.x(other: Ulice) = krizovatky.find { it in other.krizovatky }

val Ulice.maZastavku get() = zastavka != null
