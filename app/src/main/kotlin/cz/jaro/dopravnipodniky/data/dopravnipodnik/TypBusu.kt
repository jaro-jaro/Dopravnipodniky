package cz.jaro.dopravnipodniky.data.dopravnipodnik

import androidx.compose.ui.unit.Dp
import cz.jaro.dopravnipodniky.data.serializers.TypBusuSerializer
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz
import cz.jaro.dopravnipodniky.shared.jednotky.PenizZaMinutu
import cz.jaro.dopravnipodniky.shared.jednotky.Velocity
import cz.jaro.dopravnipodniky.shared.sumOfDp
import cz.jaro.dopravnipodniky.shared.zaokrouhlit
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable(with = TypBusuSerializer::class)
@SerialName("TypBusu")
data class TypBusu(
    val model: String,
    val trakce: Trakce,
    val vyrobce: Vyrobce,
    val kapacita: Int,
    val maxRychlost: Velocity,
    val maxNaklady: PenizZaMinutu,
    val cena: Peniz,
    val delka: Dp,
    val clanky: List<Dp> = listOf(delka),
    val sirka: Dp,
    val vydrz: Duration,
    val popis: String,
) {
    override fun toString() = model

    init {
        require(clanky.isNotEmpty())
        require(clanky.sumOfDp { it }.value.toDouble().zaokrouhlit(3) == delka.value.toDouble().zaokrouhlit(3)) { "${clanky.sumOfDp { it }.value.toDouble().zaokrouhlit(3)} != ${delka.value.toDouble().zaokrouhlit(3)}" }
    }
}

val TypBusu.costChangeSpeed get() = maxNaklady.value / vydrz.inWholeHours

val TypBusu.runningCosts get() =
    BusRunningCosts.entries.first { costChangeSpeed in it.costChangeSpeedRange }