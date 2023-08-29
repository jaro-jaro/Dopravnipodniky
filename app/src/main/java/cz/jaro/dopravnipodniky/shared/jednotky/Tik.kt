package cz.jaro.dopravnipodniky.shared.jednotky

import cz.jaro.dopravnipodniky.shared.TPS
import cz.jaro.dopravnipodniky.shared.millisPerTik
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@Serializable
@SerialName("Tik")
@JvmInline
value class Tik(val value: Long) : Comparable<Tik> {
    operator fun rem(other: Tik) = Tik(value % other.value)
    operator fun plus(other: Tik) = Tik(value + other.value)

    override fun compareTo(other: Tik) = value.compareTo(other.value)
}

val Int.tiku get() = Tik(this.toLong())
val Long.tiku get() = Tik(this)

fun Duration.toTiky() = Tik(inWholeSeconds * TPS)
fun Tik.toDuration() = (value * millisPerTik).milliseconds