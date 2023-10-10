package cz.jaro.dopravnipodniky.shared.jednotky

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.shared.composeString
import cz.jaro.dopravnipodniky.shared.formatovat
import cz.jaro.dopravnipodniky.shared.minutes
import cz.jaro.dopravnipodniky.shared.toText
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Duration

@JvmInline
@Serializable
@SerialName("PenizZaMinutu")
value class PenizZaMinutu(val value: Double) : Comparable<PenizZaMinutu> {
    operator fun plus(other: PenizZaMinutu) = PenizZaMinutu(value + other.value)
    operator fun minus(other: PenizZaMinutu) = PenizZaMinutu(value - other.value)
    operator fun times(other: Double) = PenizZaMinutu(value * other)
    operator fun times(other: Int) = PenizZaMinutu(value * other)

    override fun compareTo(other: PenizZaMinutu) = value.compareTo(other.value)
    operator fun div(other: Int) = PenizZaMinutu(value / other)
}

operator fun Int.times(other: PenizZaMinutu) = PenizZaMinutu(this * other.value)

operator fun Peniz.div(other: Duration) = PenizZaMinutu(value / other.minutes)
operator fun PenizZaMinutu.times(other: Duration) = Peniz(value * other.minutes)
operator fun PenizZaMinutu.times(other: Tik) = times(other.toDuration())

val Int.penezZaMin get() = PenizZaMinutu(this.toDouble())
val Double.penezZaMin get() = PenizZaMinutu(this)
val Float.penezZaMin get() = PenizZaMinutu(this.toDouble())
val Long.penezZaMin get() = PenizZaMinutu(this.toDouble())

@Composable
fun PenizZaMinutu.asString() = stringResource(R.string.zisk_penez, value.formatovat(if (value >= 10_000) 0 else 2).composeString())

fun PenizZaMinutu.formatovat() = R.string.zisk_penez.toText(value.formatovat(if (value >= 10_000) 0 else 2))
fun PenizZaMinutu.formatovatBezEura() = value.formatovat(if (value >= 10_000) 0 else 2)

inline fun <T> Iterable<T>.sumOfPenizZaminutu(selector: (T) -> PenizZaMinutu): PenizZaMinutu {
    var sum = 0.penezZaMin
    for (element in this) {
        sum += selector(element)
    }
    return sum
}