package cz.jaro.dopravnipodniky.shared.jednotky

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.shared.hours
import kotlinx.serialization.Serializable
import kotlin.math.roundToLong
import kotlin.time.Duration

@Serializable
@JvmInline
value class DpZaHodinu(val value: Long) {
    operator fun plus(other: DpZaHodinu) = DpZaHodinu(value + other.value)
    operator fun times(other: Duration) = (value * other.hours).dp

}

operator fun Dp.div(other: Duration) = DpZaHodinu((value / other.hours).roundToLong())

val Int.dpZaHodinu get() = DpZaHodinu(toLong())
val Int.kilometruZaHodinu get() = DpZaHodinu(toLong() * 90)