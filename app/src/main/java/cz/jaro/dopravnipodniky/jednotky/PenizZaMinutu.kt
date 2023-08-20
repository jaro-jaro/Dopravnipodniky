package cz.jaro.dopravnipodniky.jednotky

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.composeString
import cz.jaro.dopravnipodniky.formatovat
import cz.jaro.dopravnipodniky.minutes
import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
@JvmInline
value class PenizZaMinutu(val value: Double) : Comparable<PenizZaMinutu> {
    operator fun plus(other: PenizZaMinutu) = PenizZaMinutu(value + other.value)
    operator fun times(other: Double) = PenizZaMinutu(value * other)
    operator fun times(other: Int) = PenizZaMinutu(value * other)

    override fun compareTo(other: PenizZaMinutu) = value.compareTo(other.value)
}

operator fun Peniz.div(other: Duration) = PenizZaMinutu(value / other.minutes)
operator fun PenizZaMinutu.times(other: Duration) = Peniz(value * other.minutes)

val Int.penezZaMin get() = PenizZaMinutu(this.toDouble())
val Double.penezZaMin get() = PenizZaMinutu(this)
val Float.penezZaMin get() = PenizZaMinutu(this.toDouble())
val Long.penezZaMin get() = PenizZaMinutu(this.toDouble())

@Composable
fun PenizZaMinutu.asString() = stringResource(R.string.zisk_kc, value.formatovat().composeString())

fun PenizZaMinutu.getNakladyTextem(trolejbus: Boolean = false): Int {
    val nakladove = if (trolejbus) this * 2 else this

    return when {
        nakladove < 50.penezZaMin -> R.string.velmi_nizke
        nakladove < 60.penezZaMin -> R.string.hodne_nizke
        nakladove < 65.penezZaMin -> R.string.nizke
        nakladove < 70.penezZaMin -> R.string.pomerne_nizke
        nakladove < 75.penezZaMin -> R.string.snizene
        nakladove < 80.penezZaMin -> R.string.normalni
        nakladove < 85.penezZaMin -> R.string.pomerne_vysoke
        nakladove < 90.penezZaMin -> R.string.vysoke
        nakladove < 95.penezZaMin -> R.string.hodne_vysoke
        nakladove < 100.penezZaMin -> R.string.velmi_vysoke
        nakladove < 500.penezZaMin -> R.string.muzejni_bus
        else -> R.string.JOSTOVSKE
    }
}