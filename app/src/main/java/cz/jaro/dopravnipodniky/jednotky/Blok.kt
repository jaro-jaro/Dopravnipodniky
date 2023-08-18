package cz.jaro.dopravnipodniky.jednotky

import androidx.compose.ui.unit.Dp
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class Blok(val value: Int) {
    operator fun div(other: Blok) = value / other.value.toDouble()
    operator fun plus(other: Blok) = Blok(value + other.value)
    operator fun minus(other: Blok) = Blok(value - other.value)
    operator fun div(other: Int) = Blok(value / other)
    operator fun times(other: Int) = Blok(value * other)
    operator fun unaryMinus() = Blok(-value)
}

val Int.bloku get() = Blok(this)
val Long.bloku get() = Blok(this.toInt())

fun Blok.toDp(priblizeni: Float) = Dp(value * priblizeni)