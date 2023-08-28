package cz.jaro.dopravnipodniky.data.dopravnipodnik

import cz.jaro.dopravnipodniky.shared.BusID
import cz.jaro.dopravnipodniky.shared.DPID
import cz.jaro.dopravnipodniky.shared.LinkaID
import cz.jaro.dopravnipodniky.shared.UliceID
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz
import cz.jaro.dopravnipodniky.shared.jednotky.PenizZaMinutu
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlokRange
import cz.jaro.dopravnipodniky.shared.jednotky.penez
import cz.jaro.dopravnipodniky.shared.jednotky.penezZaMin
import cz.jaro.dopravnipodniky.shared.jednotky.times
import cz.jaro.dopravnipodniky.shared.jednotky.to
import cz.jaro.dopravnipodniky.shared.nasobitelZiskuPoOffline
import cz.jaro.dopravnipodniky.ui.theme.Theme
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Calendar
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds

@Serializable
@SerialName("DopravniPodnik")
data class DopravniPodnik(
    val jizdne: Peniz,
    val cas: Long = Calendar.getInstance().toInstant().toEpochMilli(),
    val jmenoMesta: String,
    val linky: List<Linka>,
    val busy: List<Bus>,
    val ulicove: List<Ulice>,
    val zisk: PenizZaMinutu,
    val tema: Theme,
    val id: DPID,
) {
    constructor(
        jmenoMesta: String,
        ulicove: List<Ulice> = listOf(),
    ) : this(
        jizdne = 10.penez,
        jmenoMesta = jmenoMesta,
        linky = listOf(),
        busy = listOf(),
        ulicove = ulicove,
        zisk = 0.penezZaMin,
        tema = Theme.entries.random(),
        id = DPID.randomUUID(),
    )

    override fun toString() =
        "DopravniPodnik(jmenoMesta=$jmenoMesta,linky=List(${linky.size}),busy=List(${busy.size}),ulicove=List(${ulicove.size}))"

    private val baraky = ulicove.flatMap { it.baraky }

    val cloveci = baraky.sumOf { it.cloveci }

    val kapacita = baraky.sumOf { it.kapacita }

    fun bus(id: BusID): Bus = busy.find { bus -> id == bus.id } ?: throw IndexOutOfBoundsException("tentoBusNeexistuje")

    fun linka(id: LinkaID): Linka = linky.find { linka -> id == linka.id } ?: throw IndexOutOfBoundsException("tatoLinkaNeexistuje")

    fun ulice(id: UliceID): Ulice = ulicove.find { ulice -> id == ulice.id } ?: throw IndexOutOfBoundsException("tatoUliceNeexistuje")

    private val dobaOdPoslednihoHrani = (Calendar.getInstance().toInstant().toEpochMilli() - cas).milliseconds

    val nevyzvednuto = (zisk * dobaOdPoslednihoHrani.coerceAtMost(8.hours)) * nasobitelZiskuPoOffline
}

val DopravniPodnik.stred
    get() = velikostMesta.let { (min, max) ->
        (min.x + max.x) / 2 to (min.y + max.y) / 2
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