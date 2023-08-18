package cz.jaro.dopravnipodniky.jednotky

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class Peniz(val value: Double) {
    operator fun times(other: Double) = Peniz(value * other)
    operator fun times(other: Int) = Peniz(value * other)
    operator fun plus(other: Peniz) = Peniz(value + other.value)
}

val Int.penez get() = Peniz(this.toDouble())
val Double.penez get() = Peniz(this)
val Float.penez get() = Peniz(this.toDouble())
val Long.penez get() = Peniz(this.toDouble())