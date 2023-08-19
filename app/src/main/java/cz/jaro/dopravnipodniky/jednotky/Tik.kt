package cz.jaro.dopravnipodniky.jednotky

import cz.jaro.dopravnipodniky.TPS
import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
@JvmInline
value class Tik(val value: Long) {
    operator fun rem(other: Tik) = Tik(value % other.value)
}

val Int.tiku get() = Tik(this.toLong())
val Long.tiku get() = Tik(this)

fun Duration.toTiky() = Tik(inWholeSeconds * TPS)