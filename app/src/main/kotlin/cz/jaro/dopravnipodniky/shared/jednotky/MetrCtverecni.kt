package cz.jaro.dopravnipodniky.shared.jednotky

import androidx.compose.ui.unit.Dp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
@SerialName("Metr")
value class MetrCtverecni(val value: Double) : Comparable<MetrCtverecni> {
    override fun compareTo(other: MetrCtverecni) = value.compareTo(other.value)
}

val Int.m2 get() = MetrCtverecni(this.toDouble())
val Double.m2 get() = MetrCtverecni(this)
val Float.m2 get() = MetrCtverecni(this.toDouble())
val Long.m2 get() = MetrCtverecni(this.toDouble())

operator fun Metr.times(other: Metr) = MetrCtverecni(value * other.value)
operator fun Dp.times(other: Dp) = toMetr() * other.toMetr()