package cz.jaro.dopravnipodniky.shared.jednotky

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.shared.hours
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.roundToLong
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours

@JvmInline
@Serializable
@SerialName("DpZaHodinu")
value class Velocity(val dpPerHour: Long) {
    operator fun plus(other: Velocity) = Velocity(dpPerHour + other.dpPerHour)
    operator fun times(other: Duration) = (dpPerHour * other.hours).dp

}

operator fun Dp.div(other: Duration) = Velocity((value / other.hours).roundToLong())
operator fun Dp.div(other: Velocity) = (value / other.dpPerHour).toDouble().hours

val Int.kilometersPerHour get() = (this * 1_000).dp / 1.hours

fun Velocity.coerceAtMost(maximumValue: Velocity) = Velocity(dpPerHour.coerceAtMost(maximumValue.dpPerHour))