package cz.jaro.dopravnipodniky.data.dopravnipodnik

import androidx.room.Embedded
import androidx.room.Entity
import cz.jaro.dopravnipodniky.shared.jednotky.times
import cz.jaro.dopravnipodniky.shared.nasobitelZiskuPoOffline
import kotlinx.serialization.SerialName
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds

//@Serializable
@Entity
@SerialName("DopravniPodnik")
data class DopravniPodnik(
    @Embedded val info: DPInfo,
) {
//    constructor(
//        jmenoMesta: String,
//        ulicove: List<Ulice>,
//        tema: Theme,
//    ) : this(
//        info = DPInfo(
//            jmenoMesta = jmenoMesta,
//            jizdne = 10.penez,
//            zisk = 0.penezZaMin,
//            tema = tema,
//            id = DPID.randomUUID(),
//        ),
//    )

    override fun toString() =
        "DopravniPodnik(jmenoMesta=${info.jmenoMesta})"

//    private val baraky = ulice.flatMap { it.baraky }

//    val cloveci = ulice.sumOf { it.cloveci + (it.zastavka?.cloveci ?: 0) } + busy.sumOf { it.cloveci }

//    val kapacita = ulice.sumOf { it.kapacita }
}

//fun DopravniPodnik.ulice(id: UliceID): Ulice =
//    ulice.find { ulice -> id == ulice.id } ?: throw IndexOutOfBoundsException("tatoUliceNeexistuje")

//fun DopravniPodnik.linka(id: LinkaID): Linka =
//    linky.find { linka -> id == linka.id } ?: throw IndexOutOfBoundsException("tatoLinkaNeexistuje")

//fun DopravniPodnik.bus(id: BusID): Bus = busy.find { bus -> id == bus.id } ?: throw IndexOutOfBoundsException("tentoBusNeexistuje")

val DPInfo.dobaOdPoslednihoHrani get() = (System.currentTimeMillis() - casPosledniNavstevy).milliseconds

//val DopravniPodnik.velikostMesta
//    get() = Pair(
//        rohyMesta.second.x - rohyMesta.first.x,
//        rohyMesta.second.y - rohyMesta.first.y
//    )

//val DopravniPodnik.plocha get() = velikostMesta.first.toDpSKrizovatkama() * velikostMesta.second.toDpSKrizovatkama()

//val DopravniPodnik.hustotaZalidneni get() = cloveci / plocha.value

//val DopravniPodnik.urovenMesta get() = (hustotaZalidneni * ulice.sumOf { it.potencial } * ulice.size).roundToInt()

//val DopravniPodnik.typMesta
//    get() = when {
//        info.jmenoMesta == vecne -> vecne.toText()
//        plocha >= 500_000_000.m2 && cloveci >= 5_000_000 && urovenMesta >= 10_000_000 -> R.string.mesto_jostless.toText()
//        plocha >= 25_000_000.m2 && cloveci >= 500_000 && urovenMesta >= 500_000 -> R.string.super_mesto.toText()
//        plocha >= 15_000_000.m2 && cloveci >= 250_000 && urovenMesta >= 100_000 -> R.string.ultra_velkomesto.toText()
//        plocha >= 5_000_000.m2 && cloveci >= 150_000 && urovenMesta >= 25_000 -> R.string.velkomesto.toText()
//        plocha >= 2_500_000.m2 && cloveci >= 40_000 && urovenMesta >= 600 -> R.string.velke_mesto.toText()
//        plocha >= 1_500_000.m2 && cloveci >= 10_000 && urovenMesta >= 300 -> R.string.mesto.toText()
//        plocha >= 500_000.m2 && cloveci >= 4_000 && urovenMesta >= 40 -> R.string.mestecko.toText()
//        else -> R.string.vesnice.toText()
//    }

val DPInfo.nevyzvednuto get() = (zisk * dobaOdPoslednihoHrani.coerceAtMost(8.hours)) * nasobitelZiskuPoOffline

//val DopravniPodnik.stred
//    get() = rohyMesta.let { (min, max) ->
//        (min.x + max.x) / 2 to (min.y + max.y) / 2
//    }

//val DopravniPodnik.rohyMesta: Pair<Pozice<UlicovyBlok>, Pozice<UlicovyBlok>>
//    get() {
//        val maxX = ulice.maxOf { it.konec.x }
//        val maxY = ulice.maxOf { it.konec.y }
//        val minX = ulice.minOf { it.zacatek.x }
//        val minY = ulice.minOf { it.zacatek.y }
//
//        return (minX to minY) to (maxX to maxY)
//    }

//val DopravniPodnik.seznamKrizovatek
//    get() = (rohyMesta.first.x..rohyMesta.second.x).flatMap x@{ x ->
//        (rohyMesta.first.y..rohyMesta.second.y).map y@{ y ->
//            x to y
//        }
//    }.filter { (x, y) ->
//        val sousedi = ulice.filter {
//            it.konec == x to y || it.zacatek == x to y
//        }
//
//        sousedi.isNotEmpty()
//    }
