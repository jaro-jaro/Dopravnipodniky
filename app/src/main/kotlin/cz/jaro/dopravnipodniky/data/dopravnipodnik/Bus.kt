package cz.jaro.dopravnipodniky.data.dopravnipodnik

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.shared.BusID
import cz.jaro.dopravnipodniky.shared.LinkaID
import cz.jaro.dopravnipodniky.shared.Smer
import cz.jaro.dopravnipodniky.shared.busRearAxlePosition
import cz.jaro.dopravnipodniky.shared.jednotky.Angle
import cz.jaro.dopravnipodniky.shared.jednotky.PenizZaMinutu
import cz.jaro.dopravnipodniky.shared.jednotky.Vector
import cz.jaro.dopravnipodniky.shared.jednotky.deg
import cz.jaro.dopravnipodniky.shared.odsazeniBusu
import cz.jaro.dopravnipodniky.shared.intersectionOffset
import cz.jaro.dopravnipodniky.shared.streetWidth
import cz.jaro.dopravnipodniky.shared.times
import cz.jaro.dopravnipodniky.shared.intersectionReach
import cz.jaro.dopravnipodniky.ui.malovani.SerializableDp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.pow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours

@Serializable
@SerialName("Bus")
data class Bus(
    // Properties
    val typBusu: TypBusu,
    val id: BusID = BusID.randomUUID(),
    // Info state
    val evCislo: Int,
    val najeto: Duration = 0.hours,
    val cloveci: Int = 0,
    val linka: LinkaID? = null,
    // Driving state
    val smerNaLince: Smer = Smer.Pozitivni,
    /** Index ulice na lince v daném směru */
    val poziceNaLince: Int = 0,
    val poziceVUlici: SerializableDp = 0.dp,
    val stavZastavky: StavZastavky = StavZastavky.Pred,
    /** Pozice zadní nápravy busu od počátku ulice ve směru doprava */
    val position: Vector<SerializableDp> = Vector(0.dp),
    /** Pozice a natočení středů dalších článků od počátku ulice ve směru doprava */
    val segmentEndsPosition: List<Pair<Vector<SerializableDp>, Angle>> = emptyList(),
    val rotation: Angle = 0.deg,
) {
//    override fun toString() = "Bus(evCislo=$evCislo,typBusu=$typBusu)"

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

val Bus.firstSegmentLength get() = typBusu.clanky.first()

fun Bus.placeOnStreetBeginning(
    line: LinkaID? = linka,
    directionOnLine: Smer = smerNaLince,
    positionOnLine: Int = poziceNaLince,
) = placeOnStreet(
    line = line,
    directionOnLine = directionOnLine,
    positionOnLine = positionOnLine,
    positionInStreet = intersectionOffset + intersectionReach,
)

fun Bus.placeOnStreet(
    line: LinkaID? = linka,
    directionOnLine: Smer = smerNaLince,
    positionOnLine: Int = poziceNaLince,
    positionInStreet: Dp = poziceVUlici,
) = copy(
    linka = line,
    smerNaLince = directionOnLine,
    poziceNaLince = positionOnLine,
    stavZastavky = StavZastavky.Pred,
    poziceVUlici = positionInStreet,
    position = Vector(positionInStreet, streetWidth - odsazeniBusu - typBusu.sirka / 2),
    segmentEndsPosition = withSegmentOffsets { offsetFromFirstSegmentEnd, segmentLength ->
        val distance = firstSegmentLength * busRearAxlePosition + offsetFromFirstSegmentEnd - segmentLength * .5
        Vector(positionInStreet - distance, streetWidth - odsazeniBusu - typBusu.sirka / 2) to 0.deg
    },
    rotation = 0.deg,
)

fun <R> Bus.withSegmentOffsets(transform: (offsetFromFirstSegmentEnd: Dp, segmentLength: Dp) -> R) =
    typBusu.clanky.drop(1).runningReduce { acc, length -> acc + length }.zip(typBusu.clanky.drop(1), transform)

val Bus.segmentOffsets
    get() = typBusu.clanky.drop(1).runningReduce { acc, length -> acc + length }.zip(typBusu.clanky.drop(1))