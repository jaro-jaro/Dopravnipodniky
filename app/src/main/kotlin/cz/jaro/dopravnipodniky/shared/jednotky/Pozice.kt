package cz.jaro.dopravnipodniky.shared.jednotky

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Pozice")
data class Pozice<T : Comparable<T>>(
    val x: T,
    val y: T,
) : Comparable<Pozice<T>> {
    constructor(
        xy: T,
    ) : this(xy, xy)

    override fun toString() = "($x, $y)"

    override fun compareTo(other: Pozice<T>): Int {
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

operator fun Pozice<Dp>.minus(other: Dp) = Pozice(x - other, y - other)
operator fun Pozice<Dp>.plus(other: Dp) = Pozice(x + other, y + other)
operator fun Pozice<Dp>.plus(other: Pozice<Dp>) = Pozice(x + other.x, y + other.y)

fun Pozice<UlicovyBlok>.sousedi() = listOf(
    x - 1.ulicovychBloku to y,
    x + 1.ulicovychBloku to y,
    x to y - 1.ulicovychBloku,
    x to y + 1.ulicovychBloku,
)

fun Pozice<UlicovyBlok>.toDp() = Pozice(x.toDp(), y.toDp())
fun Pozice<UlicovyBlok>.toDpSKrizovatkama() = Pozice(x.toDpSKrizovatkama(), y.toDpSKrizovatkama())

fun Pozice<Dp>.toDpOffset() = DpOffset(x, y)

context(Density)
fun Pozice<Dp>.toPx() = Offset(x.toPx(), y.toPx())

context(Density)
fun Offset.toDp() = Pozice(x.toDp(), y.toDp())

infix fun UlicovyBlok.to(other: UlicovyBlok) = Pozice(this, other)
infix fun Dp.to(other: Dp) = Pozice(this, other)