package cz.jaro.dopravnipodniky.shared.jednotky

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import cz.jaro.dopravnipodniky.shared.helpers.toDp
import cz.jaro.dopravnipodniky.shared.helpers.toPx
import cz.jaro.dopravnipodniky.shared.times
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Pozice")
data class Vector<T : Comparable<T>>(
    val x: T,
    val y: T,
) : Comparable<Vector<T>> {
    constructor(
        xy: T,
    ) : this(xy, xy)

    override fun toString() = "($x, $y)"

    override fun compareTo(other: Vector<T>): Int {
        val cx = x.compareTo(other.x)
        val cy = y.compareTo(other.y)
        return when {
            cx == cy -> cx
            cx == 0 -> cy
            cy == 0 -> cx
            else -> 0
        }
    }
}

operator fun Vector<Dp>.plus(other: Dp) = Vector(x + other, y + other)
operator fun Vector<Dp>.minus(other: Dp) = Vector(x - other, y - other)
operator fun Vector<Dp>.plus(other: Vector<Dp>) = Vector(x + other.x, y + other.y)
operator fun Vector<Dp>.minus(other: Vector<Dp>) = Vector(x - other.x, y - other.y)
operator fun Vector<Dp>.times(other: Number) = Vector(x * other.toDouble(), y * other.toDouble())
operator fun Number.times(other: Vector<Dp>) = other * this
operator fun Vector<Double>.times(other: Dp) = Vector(x * other, y * other)
operator fun Dp.times(other: Vector<Double>) = other * this

fun Vector<UlicovyBlok>.sousedi() = listOf(
    x - 1.ulicovychBloku to y,
    x + 1.ulicovychBloku to y,
    x to y - 1.ulicovychBloku,
    x to y + 1.ulicovychBloku,
)

fun Vector<UlicovyBlok>.toDp() = Vector(x.toDp(), y.toDp())
fun Vector<UlicovyBlok>.toDpSKrizovatkama() = Vector(x.toDpSKrizovatkama(), y.toDpSKrizovatkama())

fun Vector<Dp>.toDpOffset() = DpOffset(x, y)

context(_: Density)
fun Vector<Dp>.toPx() = Offset(x.toPx(), y.toPx())

fun Vector(x: Dp = 0.dp, y: Dp = 0.dp) = Vector<Dp>(x = x, y = y)
fun Vector(r: Dp, theta: Angle) = r * Vector(cos(theta), sin(theta))
val Vector<Dp>.theta get() = atan2(y, x)
val Vector<Dp>.r get() = sqrt(x * x + y * y)

context(_: Density)
fun Offset.toDp() = Vector(x.toDp(), y.toDp())

infix fun UlicovyBlok.to(other: UlicovyBlok) = Vector(this, other)
infix fun Dp.to(other: Dp) = Vector(this, other)