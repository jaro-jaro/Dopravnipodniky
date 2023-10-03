package cz.jaro.dopravnipodniky.data.dopravnipodnik

import cz.jaro.dopravnipodniky.shared.LinkaID
import cz.jaro.dopravnipodniky.shared.UliceID
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

fun Linka.busy(dp: DopravniPodnik) = dp.busy.filter { it.linka == id }

fun Linka.ulice(dp: DopravniPodnik) = dp.ulice.filter { it.id in ulice }.sortedBy { ulice.indexOf(it.id) }

fun List<Ulice>.jsouVsechnyZatrolejovane() = all { it.maTrolej }
