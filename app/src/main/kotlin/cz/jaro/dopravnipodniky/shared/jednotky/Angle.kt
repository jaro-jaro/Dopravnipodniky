package cz.jaro.dopravnipodniky.shared.jednotky

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

operator fun Number.times(other: Angle) = Angle(toDouble() * other.straightAngles)