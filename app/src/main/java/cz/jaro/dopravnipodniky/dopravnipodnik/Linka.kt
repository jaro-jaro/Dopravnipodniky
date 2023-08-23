package cz.jaro.dopravnipodniky.dopravnipodnik

import cz.jaro.dopravnipodniky.shared.LinkaID
import cz.jaro.dopravnipodniky.shared.UliceID
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.shared.jednotky.ulicovychBloku
import cz.jaro.dopravnipodniky.theme.Barvicka
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
@SerialName("Linka")
data class Linka(
    val cislo: Int,
    val ulice: List<UliceID> = listOf(),
    val barvicka: Barvicka,
    val id: LinkaID = UUID.randomUUID(),
)

fun Linka.busy(dp: DopravniPodnik) = dp.busy.filter { it.linka == id }

fun Linka.ulice(dp: DopravniPodnik) = dp.ulicove.filter { it.id in ulice }

val Linka.delkaLinky: UlicovyBlok get() = ulice.size.ulicovychBloku

fun List<Ulice>.jsouVsechnyZatrolejovane() = all { it.maTrolej }
