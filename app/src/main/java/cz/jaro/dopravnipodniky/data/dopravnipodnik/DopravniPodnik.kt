package cz.jaro.dopravnipodniky.data.dopravnipodnik

import cz.jaro.dopravnipodniky.shared.BusID
import cz.jaro.dopravnipodniky.shared.DPID
import cz.jaro.dopravnipodniky.shared.LinkaID
import cz.jaro.dopravnipodniky.shared.UliceID
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.shared.jednotky.penez
import cz.jaro.dopravnipodniky.shared.jednotky.penezZaMin
import cz.jaro.dopravnipodniky.shared.jednotky.times
import cz.jaro.dopravnipodniky.shared.jednotky.to
import cz.jaro.dopravnipodniky.shared.nasobitelZiskuPoOffline
import cz.jaro.dopravnipodniky.ui.theme.Theme
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds

@Serializable
@SerialName("DopravniPodnik")
data class DopravniPodnik(
    val linky: List<Linka>,
    val busy: List<Bus>,
    val ulice: List<Ulice>,
    val info: DPInfo,
) {
    constructor(
        jmenoMesta: String,
        ulicove: List<Ulice> = listOf(),
    ) : this(
        linky = listOf(),
        busy = listOf(),
        ulice = ulicove,
        info = DPInfo(
            jmenoMesta = jmenoMesta,
            jizdne = 10.penez,
            zisk = 0.penezZaMin,
            tema = Theme.entries.random(),
            id = DPID.randomUUID(),
        ),
    )

    override fun toString() =
        "DopravniPodnik(jmenoMesta=${info.jmenoMesta},linky=List(${linky.size}),busy=List(${busy.size}),ulicove=List(${ulice.size}))"

    private val baraky = ulice.flatMap { it.baraky }

    val cloveci = ulice.sumOf { it.cloveci } + busy.sumOf { it.cloveci }

    val kapacita = ulice.sumOf { it.kapacita }
}

fun List<Ulice>.ulice(id: UliceID): Ulice = find { ulice -> id == ulice.id } ?: throw IndexOutOfBoundsException("tatoUliceNeexistuje")
fun List<Linka>.linka(id: LinkaID): Linka = find { linka -> id == linka.id } ?: throw IndexOutOfBoundsException("tatoLinkaNeexistuje")
fun List<Bus>.bus(id: BusID): Bus = find { bus -> id == bus.id } ?: throw IndexOutOfBoundsException("tentoBusNeexistuje")

val DPInfo.dobaOdPoslednihoHrani get() = (System.currentTimeMillis() - casPosledniNavstevy).milliseconds

val DPInfo.nevyzvednuto get() = (zisk * dobaOdPoslednihoHrani.coerceAtMost(8.hours)) * nasobitelZiskuPoOffline

val List<Ulice>.stred
    get() = velikostMesta.let { (min, max) ->
        (min.x + max.x) / 2 to (min.y + max.y) / 2
    }

val List<Ulice>.velikostMesta: Pair<Pozice<UlicovyBlok>, Pozice<UlicovyBlok>>
    get() {
        val maxX = maxOf { it.konec.x }
        val maxY = maxOf { it.konec.y }
        val minX = minOf { it.zacatek.x }
        val minY = minOf { it.zacatek.y }

        return (minX to minY) to (maxX to maxY)
    }

val List<Ulice>.seznamKrizovatek
    get() = (velikostMesta.first.x..velikostMesta.second.x).flatMap x@{ x ->
        (velikostMesta.first.y..velikostMesta.second.y).map y@{ y ->
            x to y
        }
    }.filter { (x, y) ->
        val sousedi = filter {
            it.konec == x to y || it.zacatek == x to y
        }

        sousedi.isNotEmpty()
    }
