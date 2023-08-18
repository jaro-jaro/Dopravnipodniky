package cz.jaro.dopravnipodniky.jednotky

import cz.jaro.dopravnipodniky.hours
import kotlinx.serialization.Serializable
import kotlin.math.roundToInt
import kotlin.math.roundToLong
import kotlin.time.Duration

@Serializable
@JvmInline
value class BlokZaHodinu(val value: Long) {
    operator fun plus(other: BlokZaHodinu) = BlokZaHodinu(value + other.value)
    operator fun times(other: Duration) = Blok((value * other.hours).roundToInt())

}

operator fun Blok.div(other: Duration) = BlokZaHodinu((value / other.hours).roundToLong())

val Int.blokuZaHodinu get() = BlokZaHodinu(toLong())
val Int.kilometruZaHodinu get() = BlokZaHodinu(toLong() * 90)