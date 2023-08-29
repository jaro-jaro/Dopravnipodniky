package cz.jaro.dopravnipodniky.shared.jednotky

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Pozice")
data class Pozice<T : Comparable<T>>(
    val x: T,
    val y: T,
) : Comparable<Pozice<T>> {
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

fun Pozice<UlicovyBlok>.sousedi() = listOf(
    x - 1.ulicovychBloku to y,
    x + 1.ulicovychBloku to y,
    x to y - 1.ulicovychBloku,
    x to y + 1.ulicovychBloku,
)

val Pozice<UlicovyBlok>.dp get() = Pozice(x.dp, y.dp)
val Pozice<UlicovyBlok>.dpSUlicema get() = Pozice(x.dpSUlicema, y.dpSUlicema)
context(Density)
fun Pozice<Dp>.toPx() = Offset(x.toPx(), y.toPx())

infix fun UlicovyBlok.to(other: UlicovyBlok) = Pozice(this, other)
infix fun Dp.to(other: Dp) = Pozice(this, other)