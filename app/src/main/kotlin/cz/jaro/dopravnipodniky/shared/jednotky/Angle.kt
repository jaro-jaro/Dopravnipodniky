package cz.jaro.dopravnipodniky.shared.jednotky

import androidx.compose.ui.unit.Dp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

@JvmInline
@Serializable
@SerialName("Angle")
value class Angle(val straightAngles: Double) : Comparable<Angle> {
    operator fun plus(other: Angle) = Angle(straightAngles + other.straightAngles)
    operator fun minus(other: Angle) = Angle(straightAngles - other.straightAngles)
    operator fun unaryMinus() = Angle(-straightAngles)
    operator fun times(other: Number) = Angle(straightAngles * other.toDouble())
    operator fun div(other: Number) = Angle(straightAngles / other.toDouble())
    val absoluteValue get() = if (straightAngles > 0) this else -this
    override fun compareTo(other: Angle) = straightAngles.compareTo(other.straightAngles)
}

val Angle.radians get() = straightAngles * Math.PI
val Angle.degrees get() = straightAngles * 180

val Int.rad get() = Angle(this.toDouble() / Math.PI)
val Double.rad get() = Angle(this)
val Float.rad get() = Angle(this.toDouble() / Math.PI)
val Long.rad get() = Angle(this.toDouble() / Math.PI)

val Int.deg get() = Angle(this / 180.0)
val Double.deg get() = Angle(this / 180.0)
val Float.deg get() = Angle(this / 180.0)
val Long.deg get() = Angle(this / 180.0)

fun sin(angle: Angle) = sin(angle.radians)
fun cos(angle: Angle) = cos(angle.radians)
fun tan(angle: Angle) = tan(angle.radians)
fun asin(number: Double) = kotlin.math.asin(number).rad
fun acos(number: Double) = kotlin.math.acos(number).rad
fun atan(number: Double) = kotlin.math.atan(number).rad
fun asin(number: Float) = kotlin.math.asin(number).rad
fun acos(number: Float) = kotlin.math.acos(number).rad
fun atan(number: Float) = kotlin.math.atan(number).rad
fun atan2(y: Dp, x: Dp) = kotlin.math.atan2(y.value, x.value).rad

operator fun Number.times(other: Angle) = Angle(toDouble() * other.straightAngles)