package cz.jaro.dopravnipodniky.jednotky

import cz.jaro.dopravnipodniky.sirkaUlice
import cz.jaro.dopravnipodniky.ulicovyBlok
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class UlicovyBlok(
    val value: Int
) : Comparable<UlicovyBlok> {
    override fun compareTo(other: UlicovyBlok) = value.compareTo(other.value)
    operator fun rangeTo(other: UlicovyBlok) = UlicovyBlokRange(other, this)

    operator fun times(other: Int): UlicovyBlok = UlicovyBlok(value * other)
    operator fun minus(other: UlicovyBlok) = UlicovyBlok(value - other.value)
    operator fun plus(other: UlicovyBlok) = UlicovyBlok(value + other.value)
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

val UlicovyBlok.bloku get() = ulicovyBlok * value
val UlicovyBlok.blokuSUlicema get() = ulicovyBlok * value + sirkaUlice * value

val Int.ulicovychBloku get() = UlicovyBlok(this)
val Long.ulicovychBloku get() = UlicovyBlok(this.toInt())