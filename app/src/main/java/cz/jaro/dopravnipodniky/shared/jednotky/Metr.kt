package cz.jaro.dopravnipodniky.shared.jednotky

import androidx.compose.ui.unit.Dp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
@SerialName("Metr")
value class Metr(val value: Double)

val Int.metru get() = Metr(this.toDouble())
val Double.metru get() = Metr(this)
val Float.metru get() = Metr(this.toDouble())
val Long.metru get() = Metr(this.toDouble())

fun Metr.toDp() = Dp((value * 4).toFloat())