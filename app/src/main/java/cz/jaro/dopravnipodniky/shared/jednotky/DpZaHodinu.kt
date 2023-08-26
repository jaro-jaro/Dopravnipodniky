package cz.jaro.dopravnipodniky.shared.jednotky

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.shared.hours
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.roundToLong
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours

@JvmInline
@Serializable
@SerialName("DpZaHodinu")
value class DpZaHodinu(val value: Long) {
    operator fun plus(other: DpZaHodinu) = DpZaHodinu(value + other.value)
    operator fun times(other: Duration) = (value * other.hours).dp

}

operator fun Dp.div(other: Duration) = DpZaHodinu((value / other.hours).roundToLong())

val Int.dpZaHodinu get() = DpZaHodinu(toLong())
val Int.kilometruZaHodinu get() = (this * 1_000L).metru.toDp() / 1.hours