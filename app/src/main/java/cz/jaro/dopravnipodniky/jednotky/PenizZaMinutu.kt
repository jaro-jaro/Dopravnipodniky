package cz.jaro.dopravnipodniky.jednotky

import cz.jaro.dopravnipodniky.minutes
import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
@JvmInline
value class PenizZaMinutu(val value: Double) {
    operator fun plus(other: PenizZaMinutu) = PenizZaMinutu(value + other.value)
    operator fun times(other: Double) = PenizZaMinutu(value * other)
}

operator fun Peniz.div(other: Duration) = PenizZaMinutu(value / other.minutes)
operator fun PenizZaMinutu.times(other: Duration) = Peniz(value * other.minutes)

val Int.penezZaMin get() = PenizZaMinutu(this.toDouble())
val Double.penezZaMin get() = PenizZaMinutu(this)
val Float.penezZaMin get() = PenizZaMinutu(this.toDouble())
val Long.penezZaMin get() = PenizZaMinutu(this.toDouble())