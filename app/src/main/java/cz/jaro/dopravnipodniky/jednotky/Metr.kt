package cz.jaro.dopravnipodniky.jednotky

import androidx.compose.ui.unit.Dp
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class Metr(val value: Double)

val Int.metru get() = Metr(this.toDouble())
val Double.metru get() = Metr(this)
val Float.metru get() = Metr(this.toDouble())
val Long.metru get() = Metr(this.toDouble())

fun Metr.toDp() = Dp((value * 2).toFloat())