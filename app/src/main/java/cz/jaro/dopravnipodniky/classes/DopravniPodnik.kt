package cz.jaro.dopravnipodniky.classes

import cz.jaro.dopravnipodniky.BusID
import cz.jaro.dopravnipodniky.LinkaID
import cz.jaro.dopravnipodniky.UliceID
import cz.jaro.dopravnipodniky.jednotky.Peniz
import cz.jaro.dopravnipodniky.jednotky.PenizZaMinutu
import cz.jaro.dopravnipodniky.jednotky.Pozice
import cz.jaro.dopravnipodniky.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.jednotky.UlicovyBlokRange
import cz.jaro.dopravnipodniky.jednotky.penez
import cz.jaro.dopravnipodniky.jednotky.penezZaMin
import cz.jaro.dopravnipodniky.jednotky.times
import cz.jaro.dopravnipodniky.jednotky.to
import cz.jaro.dopravnipodniky.nasobitelZiskuPoOffline
import kotlinx.serialization.Serializable
import java.util.Calendar
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds

@Serializable
data class DopravniPodnik(
    val jizdne: Peniz = 10.penez,
    val cas: Long = Calendar.getInstance().toInstant().toEpochMilli(),
    val jmenoMesta: String = "NeVěčné",
    val linky: List<Linka> = listOf(),
    val busy: List<Bus> = listOf(),
    val ulicove: List<Ulice> = listOf(),
    val zisk: PenizZaMinutu = 0.penezZaMin,
) {
    val baraky = ulicove.flatMap { it.baraky }

    val cloveci = baraky.sumOf { it.cloveci }

    val kapacita = baraky.sumOf { it.kapacita }

    fun bus(id: BusID): Bus = busy.find { bus -> id == bus.id } ?: throw IndexOutOfBoundsException("tentoBusNeexistuje")

    fun linka(id: LinkaID): Linka = linky.find { linka -> id == linka.id } ?: throw IndexOutOfBoundsException("tatoLinkaNeexistuje")

    fun ulice(id: UliceID): Ulice = ulicove.find { ulice -> id == ulice.id } ?: throw IndexOutOfBoundsException("tatoUliceNeexistuje")

    private val dobaOdPoslednihoHrani = (Calendar.getInstance().toInstant().toEpochMilli() - cas).milliseconds

    val nevyzvednuto = (zisk * dobaOdPoslednihoHrani.coerceAtMost(8.hours)) * nasobitelZiskuPoOffline
}

val DopravniPodnik.velikostMesta: Pair<Pozice<UlicovyBlok>, Pozice<UlicovyBlok>>
    get() {
        val maxX = ulicove.maxOf { it.konec.x }
        val maxY = ulicove.maxOf { it.konec.y }
        val minX = ulicove.minOf { it.zacatek.x }
        val minY = ulicove.minOf { it.zacatek.y }

        return (minX to minY) to (maxX to maxY)
    }

val DopravniPodnik.seznamKrizovatek
    get() = (velikostMesta.first.x..velikostMesta.second.x).flatMap x@{ x ->
        (velikostMesta.first.y..velikostMesta.second.y).map y@{ y ->
            x to y
        }
    }.filter { (x, y) ->
        val sousedi = ulicove.filter {
            it.konec == x to y || it.zacatek == x to y
        }

        sousedi.isNotEmpty()
    }

val DopravniPodnik.oblastiPodniku: Pair<UlicovyBlokRange, UlicovyBlokRange>
    get() {
        val (min, max) = velikostMesta

        val rangeX = (min.x/* - 2.ulicovychBloku*/)..(max.x/* + 2.ulicovychBloku*/)
        val rangeY = (min.y/* - 2.ulicovychBloku*/)..(max.y/* + 2.ulicovychBloku*/)

        return rangeX to rangeY
    }