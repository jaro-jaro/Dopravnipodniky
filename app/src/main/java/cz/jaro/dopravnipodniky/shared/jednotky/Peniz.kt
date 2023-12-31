package cz.jaro.dopravnipodniky.shared.jednotky

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.shared.composeString
import cz.jaro.dopravnipodniky.shared.formatovat
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
@SerialName("Peniz")
value class Peniz(val value: Double) : Comparable<Peniz> {
    operator fun times(other: Double) = Peniz(value * other)
    operator fun times(other: Int) = Peniz(value * other)
    operator fun div(other: Int) = Peniz(value / other)
    operator fun plus(other: Peniz) = Peniz(value + other.value)
    operator fun minus(other: Peniz) = Peniz(value - other.value)

    override fun compareTo(other: Peniz) = value.compareTo(other.value)
}

val Int.penez get() = Peniz(this.toDouble())
val Double.penez get() = Peniz(this)
val Float.penez get() = Peniz(this.toDouble())
val Long.penez get() = Peniz(this.toDouble())

@Composable
fun Peniz.asString() = stringResource(R.string.kc, value.formatovat(0).composeString())

inline fun <T> Iterable<T>.sumOfPeniz(selector: (T) -> Peniz): Peniz {
    var sum = 0.penez
    for (element in this) {
        sum += selector(element)
    }
    return sum
}