package cz.jaro.dopravnipodniky.shared.jednotky

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.sqrt

@JvmInline
@Serializable
@SerialName("Area")
value class Area(val dpSquared: Float) : Comparable<Area> {
    override fun compareTo(other: Area) = dpSquared.compareTo(other.dpSquared)
}

val Int.dp2 get() = Area(this.toFloat())
val Double.dp2 get() = Area(this.toFloat())
val Float.dp2 get() = Area(this)
val Long.dp2 get() = Area(this.toFloat())

operator fun Dp.times(other: Dp) = Area(value * other.value)
operator fun Area.plus(other: Area) = Area(dpSquared + other.dpSquared)
fun sqrt(area: Area) = sqrt(area.dpSquared).dp