package cz.jaro.dopravnipodniky.data.dopravnipodnik

import androidx.compose.ui.unit.times
import cz.jaro.dopravnipodniky.shared.LinkaID
import cz.jaro.dopravnipodniky.shared.Smer
import cz.jaro.dopravnipodniky.shared.UliceID
import cz.jaro.dopravnipodniky.shared.streetLength
import cz.jaro.dopravnipodniky.shared.streetWidth
import cz.jaro.dopravnipodniky.ui.linky.rem
import cz.jaro.dopravnipodniky.ui.theme.LineColor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID
import kotlin.math.roundToInt

@Serializable
@SerialName("Linka")
data class Linka(
    val cislo: String,
    val ulice: List<UliceID> = listOf(),
    val color: LineColor = LineColor.Red,
    val id: LinkaID = UUID.randomUUID(),
) {
    override fun toString() = "Linka(cislo=$cislo,ulice=List(${ulice.size}))"
}

fun Linka.busy(dp: DopravniPodnik) = busy(dp.busy)
fun Linka.busy(busy: List<Bus>) = busy.filter { it.linka == id }

fun Linka.ulice(dp: DopravniPodnik) = dp.getStreets(ulice.asSequence())

fun Iterable<Ulice>.jsouVsechnyZatrolejovane() = all { it.maTrolej }
fun Sequence<Ulice>.jsouVsechnyZatrolejovane() = all { it.maTrolej }

val Linka.rozmistitBusy: MutableList<Bus>.() -> Unit
    get() = {
        val pocetBusu = busy(this).size
        val ulicNaLince = ulice.size
        val delkaLinky = ulicNaLince * (streetLength + streetWidth)
        val odstupy = (2 * delkaLinky) / pocetBusu.toFloat()

        busy(this).forEachIndexed { i, bus ->
            val poziceOdZacatkuLinky = odstupy * i

            val jeDruhySmer = poziceOdZacatkuLinky / delkaLinky >= 1
            val indexUlice = (poziceOdZacatkuLinky % delkaLinky) / (streetLength + streetWidth)
            val poziceVUlici = poziceOdZacatkuLinky % (streetLength + streetWidth)

            val index = indexOfFirst { it.id == bus.id }

            this[index] = this[index].placeOnStreet(
                directionOnLine = if (!jeDruhySmer) Smer.Pozitivni else Smer.Negativni,
                positionOnLine = indexUlice.roundToInt() % ulice.size,
                positionInStreet = poziceVUlici,
            )
        }
    }
