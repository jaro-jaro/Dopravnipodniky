package cz.jaro.dopravnipodniky.jednotky

import androidx.compose.ui.unit.Dp
import kotlinx.serialization.Serializable

@Serializable
data class Pozice<T>(
    val x: T,
    val y: T,
)

fun Pozice<Blok>.plus(other: Blok) = Pozice(x + other, y + other)

val Pozice<UlicovyBlok>.bloku get() = Pozice(x.bloku, y.bloku)

infix fun UlicovyBlok.to(other: UlicovyBlok) = Pozice(this, other)
infix fun Blok.to(other: Blok) = Pozice(this, other)
infix fun Dp.to(other: Dp) = Pozice(this, other)