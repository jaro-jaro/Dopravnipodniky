package cz.jaro.dopravnipodniky.shared.jednotky

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.shared.hours
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours

@JvmInline
@Serializable
@SerialName("DpZaHodinu")
value class Velocity(val dpPerHour: Double) {
    operator fun plus(other: Velocity) = Velocity(dpPerHour + other.dpPerHour)
    operator fun times(other: Duration) = (dpPerHour * other.hours).dp
    operator fun times(other: Number) = Velocity(dpPerHour * other.toDouble())

}

operator fun Dp.div(other: Duration) = Velocity(value / other.hours)
operator fun Dp.div(other: Velocity) = (value / other.dpPerHour).hours

val Int.kilometersPerHour get() = (this * 1_000).dp / 1.hours
val Velocity.dpPerSecond get() = dpPerHour / 60

fun Velocity.coerceAtMost(maximumValue: Velocity) = Velocity(dpPerHour.coerceAtMost(maximumValue.dpPerHour))