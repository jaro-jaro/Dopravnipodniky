package cz.jaro.dopravnipodniky.data.dopravnipodnik

import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.shared.BusID
import cz.jaro.dopravnipodniky.shared.LinkaID
import cz.jaro.dopravnipodniky.shared.Smer
import cz.jaro.dopravnipodniky.shared.Smer.Pozitivni
import cz.jaro.dopravnipodniky.shared.jednotky.PenizZaMinutu
import cz.jaro.dopravnipodniky.ui.malovani.SerializableDp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.pow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours

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

    val prodejniCena = typBusu.cena * (1 - ponicenost)
}