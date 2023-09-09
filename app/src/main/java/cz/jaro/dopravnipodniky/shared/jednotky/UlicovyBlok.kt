package cz.jaro.dopravnipodniky.shared.jednotky

import androidx.compose.ui.unit.times
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.shared.ulicovyBlok
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
@SerialName("UlicovyBlok")
value class UlicovyBlok(
    val value: Int
) : Comparable<UlicovyBlok> {
    override fun compareTo(other: UlicovyBlok) = value.compareTo(other.value)
    operator fun rangeTo(other: UlicovyBlok) = UlicovyBlokRange(other, this)

    operator fun times(other: Int): UlicovyBlok = UlicovyBlok(value * other)
    operator fun minus(other: UlicovyBlok) = UlicovyBlok(value - other.value)
    operator fun plus(other: UlicovyBlok) = UlicovyBlok(value + other.value)
    operator fun div(i: Int) = UlicovyBlok(value / i)
}

class UlicovyBlokRange(
    override val endInclusive: UlicovyBlok,
    override val start: UlicovyBlok
) : ClosedRange<UlicovyBlok>, Iterable<UlicovyBlok> {
    override fun iterator(): Iterator<UlicovyBlok> = object : Iterator<UlicovyBlok> {
        private var last = start - 1.ulicovychBloku

        override fun hasNext(): Boolean = last < endInclusive

        override fun next(): UlicovyBlok {
            if (last >= endInclusive) {
                throw kotlin.NoSuchElementException()
            }
            last += 1.ulicovychBloku
            return last
        }
    }
}

fun UlicovyBlok.toDp() = value * ulicovyBlok
fun UlicovyBlok.toDpSUlicema() = value * (ulicovyBlok + sirkaUlice)

val Int.ulicovychBloku get() = UlicovyBlok(this)
val Long.ulicovychBloku get() = UlicovyBlok(this.toInt())