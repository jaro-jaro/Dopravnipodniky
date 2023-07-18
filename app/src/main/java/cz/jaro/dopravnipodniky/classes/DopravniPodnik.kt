package cz.jaro.dopravnipodniky.classes

import cz.jaro.dopravnipodniky.*
import cz.jaro.dopravnipodniky.other.*
import cz.jaro.dopravnipodniky.other.Razeni.NIJAK
import java.util.*

class DopravniPodnik {

    var jizdne = 10

    private val tentoBusNeexistuje  = App.res.getString(R.string.tento_bus_neexistuje)
    private val tatoLinkaNeexistuje = App.res.getString(R.string.tato_linka_neexistuje)
    private val tatoUliceNeexistuje = App.res.getString(R.string.tato_ulice_neexistuje)

    var filtrovatTrakce = listOf<Trakce>()
    var filtrovatPodtyp = listOf<Podtyp>()
    var filtrovatDelka = listOf<IntRange>()
    var filtrovatTyp = listOf<Vyrobce>()
    var filtrovatNaklady = listOf<String>()
    var filtrovatPrachy = listOf<IntRange>()
    var filtrovatJestliNaToMam = false

    var raditCenou: Razeni = NIJAK
    var raditNakladama: Razeni = NIJAK
    var raditKapacitou: Razeni = NIJAK

    val cloveci
        get() = baraky.sumOf { it.cloveci }

    val kapacita
        get() = baraky.sumOf { it.kapacita }

    val linky = mutableListOf<Linka>()
    val busy = mutableListOf<Bus>()
    val ulicove = mutableListOf<Ulice>()
    val zastavky = mutableListOf<Zastavka>()
    var cas: Long = Calendar.getInstance().toInstant().toEpochMilli() // ms
    var zisk = 0.0 // Kč/min
    var jmenoMesta = "NeVěčné"
    var kalibrovat = 0 // jak dlouho se má kalibrovat tiku
    var sledovanejBus: Bus? = null

    val baraky: List<Barak>
        get() = ulicove.flatMap { it.baraky }

    fun bus(id: @BusId Long): Bus =
        busy.firstOrNull { bus -> id == bus.id } ?: throw IndexOutOfBoundsException(tentoBusNeexistuje)

    fun linka(id: @LinkaId Long): Linka =
        linky.firstOrNull { linka -> id == linka.id } ?: throw IndexOutOfBoundsException(tatoLinkaNeexistuje)

    fun ulice(id: @UliceId Long): Ulice =
        ulicove.firstOrNull { ulice -> id == ulice.id } ?: throw IndexOutOfBoundsException(tatoUliceNeexistuje)

    val velikostMesta: Pair<Pair<Int, Int>, Pair<Int, Int>> // UlicovyBloky
    get() {
        val maxX = ulicove.maxOf { it.konecX }
        val maxY = ulicove.maxOf { it.konecY }
        val minX = ulicove.minOf { it.zacatekX }
        val minY = ulicove.minOf { it.zacatekY }

        return (minX to minY) to (maxX to maxY)
    }

    val pocetSekundOdPoslednihoHrani: Long
        get() = (Calendar.getInstance().toInstant().toEpochMilli() - cas) / 1000 // s

    val nevyzvednuto: Double
        get() {
            val puvodniTiky = pocetSekundOdPoslednihoHrani * TPS
            val pocetTiku = if (puvodniTiky > TPH * 8L) TPH * 8L else puvodniTiky
            return zisk * nasobitelZiskuPoOffline * pocetTiku / TPM
        }

    fun zesebevrazdujSe () {
        linky.clear()
        busy.clear()
        ulicove.clear()
        zastavky.clear()
        cas = 0
        zisk = 0.0
        if (jmenoMesta == "Věčné") jmenoMesta = "Věčné není věčné"
    }

}
