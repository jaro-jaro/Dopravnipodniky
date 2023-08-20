package cz.jaro.dopravnipodniky.classes

import cz.jaro.dopravnipodniky.Barvicka
import cz.jaro.dopravnipodniky.LinkaID
import cz.jaro.dopravnipodniky.UliceID
import cz.jaro.dopravnipodniky.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.jednotky.ulicovychBloku
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
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
