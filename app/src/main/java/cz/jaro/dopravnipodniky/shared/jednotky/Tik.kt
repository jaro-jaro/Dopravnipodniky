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
value class Tik(val value: Long) {
    operator fun rem(other: Tik) = Tik(value % other.value)
}

val Int.tiku get() = Tik(this.toLong())
val Long.tiku get() = Tik(this)

fun Duration.toTiky() = Tik(inWholeSeconds * TPS)
fun Tik.toDuration() = (value * millisPerTik).milliseconds