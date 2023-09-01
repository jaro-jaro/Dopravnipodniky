package cz.jaro.dopravnipodniky.data.dopravnipodnik

import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.shared.BusID
import cz.jaro.dopravnipodniky.shared.LinkaID
import cz.jaro.dopravnipodniky.shared.Smer
import cz.jaro.dopravnipodniky.shared.Smer.Pozitivni
import cz.jaro.dopravnipodniky.shared.jednotky.PenizZaMinutu
import cz.jaro.dopravnipodniky.shared.jednotky.dp
import cz.jaro.dopravnipodniky.shared.jednotky.penezZaMin
import cz.jaro.dopravnipodniky.shared.nasobitelPoctuLidiKteryTiNastoupiDoBusuNaZastavceKdyzZastaviANakyLidiTamJsouAMaVSobeJesteVolneMisto
import cz.jaro.dopravnipodniky.ui.malovani.SerializableDp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.pow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@Serializable
@SerialName("Bus")
data class Bus(
    val evCislo: Int,
    val typBusu: TypBusu,
    val najeto: Duration = 0.hours,
    val cloveci: Int = 0,
    val stavZastavky: StavZastavky = StavZastavky.Pred,
    /** Index ulice na lince */
    val poziceNaLince: Int = 0,
    val poziceVUlici: SerializableDp = 0.dp,
    val smerNaLince: Smer = Pozitivni,
    val linka: LinkaID? = null,
    val id: BusID = BusID.randomUUID(),
) {
    override fun toString() = "Bus(evCislo=$evCislo,typBusu=$typBusu)"

    /**
     * @see <a href="https://www.desmos.com/calculator/0ezs54c4kg">Desmos</a>
     */
    val ponicenost: Double get() = 100.0.pow(x = najeto / typBusu.vydrz) / 100.0

    /**
     * @see <a href="https://www.desmos.com/calculator/ho8unbvqtr">Desmos</a>
     */
    val naklady: PenizZaMinutu
        get() = typBusu.maxNaklady * (ponicenost).pow(.5) + typBusu.trakce.bonusoveVydajeZaNeekologicnost()

    /**
     * Vypočítá průměrný výdělek busu za minutu
     */

    fun vydelkuj(dp: DopravniPodnik): PenizZaMinutu {
        if (linka == null) return 0.penezZaMin

        val linka = dp.linky.linka(linka)
        val ulice = linka.ulice.map { dp.ulice.ulice(it) }

        if (typBusu.trakce is Trakce.Trolejbus && !ulice.jsouVsechnyZatrolejovane()) return 0.penezZaMin

        val zastavekNaLince = ulice.count { it.maZastavku }

        //val prumernyVydelekZaZastavku = ctx.dp.kapacita.coerceAtMost(ctx.dp.busy.sumOf { it.typBusu.kapacita })
        // * ctx.dp.jizdne / uliceSeZastavkama.size
        val nasobitelCloveku =
            nasobitelPoctuLidiKteryTiNastoupiDoBusuNaZastavceKdyzZastaviANakyLidiTamJsouAMaVSobeJesteVolneMisto(dp).coerceIn(.0, 1.0).pow(2)
        val prumernyVydelekZaZastavku = (dp.info.jizdne * cloveci * nasobitelCloveku).value.penezZaMin

        val busUjede = typBusu.rychlost * 1.minutes // za minutu
        val ujetyZlomekLinky = busUjede / linka.delkaLinky.dp // za minutu
        val pocetZastavek = ujetyZlomekLinky * zastavekNaLince // za minutu

        return prumernyVydelekZaZastavku * pocetZastavek.toDouble() // * nasobitelZisku
    }

    val prodejniCena = typBusu.cena * (1 - ponicenost)
}