package cz.jaro.dopravnipodniky.jednotky

import androidx.compose.ui.unit.Dp
import kotlinx.serialization.Serializable

@Serializable
data class Pozice<T>(
    val x: T,
    val y: T,
)

fun Pozice<Dp>.plus(other: Dp) = Pozice(x + other, y + other)

val Pozice<UlicovyBlok>.dp get() = Pozice(x.dp, y.dp)

infix fun UlicovyBlok.to(other: UlicovyBlok) = Pozice(this, other)
infix fun Dp.to(other: Dp) = Pozice(this, other)