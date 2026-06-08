package cz.jaro.dopravnipodniky.shared.jednotky

import cz.jaro.dopravnipodniky.shared.seconds
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@JvmInline
@Serializable
@SerialName("AngularVelocity")
value class AngularVelocity(val radiansPerSecond: Double) {
    operator fun plus(other: AngularVelocity) = AngularVelocity(radiansPerSecond + other.radiansPerSecond)
    operator fun times(other: Duration) = (radiansPerSecond * other.seconds).rad
    operator fun times(other: Number) = AngularVelocity(radiansPerSecond * other.toDouble())
    operator fun div(other: Number) = AngularVelocity(radiansPerSecond / other.toDouble())

}

operator fun Angle.div(other: Duration) = AngularVelocity(radians / other.seconds)
operator fun Angle.div(other: AngularVelocity) = (radians / other.radiansPerSecond).seconds

val Number.radiansPerSecond get() = AngularVelocity(toDouble())