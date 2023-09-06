package cz.jaro.dopravnipodniky.data.dopravnipodnik

import cz.jaro.dopravnipodniky.shared.LinkaID
import cz.jaro.dopravnipodniky.shared.UliceID
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.shared.jednotky.ulicovychBloku
import cz.jaro.dopravnipodniky.ui.theme.Barvicka
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
@SerialName("Linka")
data class Linka(
    val cislo: String,
    val ulice: List<UliceID> = listOf(),
    val barvicka: Barvicka,
    val id: LinkaID = UUID.randomUUID(),
) {
    override fun toString() = "Linka(cislo=$cislo,ulice=List(${ulice.size}))"
}

fun Linka.busy(busy: List<Bus>) = busy.filter { it.linka == id }

fun Linka.ulice(ulicove: List<Ulice>) = ulicove.filter { it.id in ulice }.sortedBy { ulice.indexOf(it.id) }

val Linka.delkaLinky: UlicovyBlok get() = ulice.size.ulicovychBloku

fun List<Ulice>.jsouVsechnyZatrolejovane() = all { it.maTrolej }
