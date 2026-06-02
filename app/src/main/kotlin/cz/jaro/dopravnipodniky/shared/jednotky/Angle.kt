package cz.jaro.dopravnipodniky.shared.jednotky

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
@SerialName("Radian")
value class Angle(val radians: Double)

val Int.rad get() = Angle(this.toDouble())
val Double.rad get() = Angle(this)
val Float.rad get() = Angle(this.toDouble())
val Long.rad get() = Angle(this.toDouble())

val Int.deg get() = (this * Math.PI / 180.0).rad
val Double.deg get() = (this * Math.PI / 180.0).rad
val Float.deg get() = (this * Math.PI / 180.0).rad
val Long.deg get() = (this * Math.PI / 180.0).rad