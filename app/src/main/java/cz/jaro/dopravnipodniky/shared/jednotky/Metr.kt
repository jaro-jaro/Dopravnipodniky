package cz.jaro.dopravnipodniky.shared.jednotky

import androidx.compose.ui.unit.Dp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
@SerialName("Metr")
value class Metr(val value: Double) {
    operator fun plus(other: Metr) = Metr(value + other.value)
    operator fun minus(other: Metr) = Metr(value - other.value)
}

val Int.metru get() = Metr(this.toDouble())
val Double.metru get() = Metr(this)
val Float.metru get() = Metr(this.toDouble())
val Long.metru get() = Metr(this.toDouble())

fun Metr.toDp() = Dp((value * 1).toFloat())
fun Dp.toMetr() = Metr(value / 1.0)