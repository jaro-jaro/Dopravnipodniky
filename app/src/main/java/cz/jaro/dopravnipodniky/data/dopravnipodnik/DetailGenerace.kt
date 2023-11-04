package cz.jaro.dopravnipodniky.data.dopravnipodnik

import cz.jaro.dopravnipodniky.shared.jednotky.Peniz
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
@SerialName("DetailGenerace")
sealed interface DetailGenerace {
    val investice: Peniz
    val nazevMestaSeed: Int

    fun ostatniSeedy(): List<Int>
}

/**
 * Použije nejnovější verzi generátoru
 * Nyní: V1
 */
fun DetailGenerace(
    investice: Peniz
): DetailGenerace = DetailGeneraceV1(
    investice = investice
)

@Serializable
@SerialName("DetailGeneraceV1")
data class DetailGeneraceV1(
    override val investice: Peniz,
    override val nazevMestaSeed: Int,
    val michaniSeed: Int,
    val sanceSeed: Int,
    val barakySeed: Int,
    val panelakySeed: Int,
    val stredovySeed: Int,
    val kapacitaSeed: Int,
) : DetailGenerace {
    override fun ostatniSeedy() = listOf(
        michaniSeed,
        sanceSeed,
        barakySeed,
        panelakySeed,
        stredovySeed,
        kapacitaSeed,
    )

    constructor(
        investice: Peniz
    ) : this(
        investice = investice,
        nazevMestaSeed = Random.nextInt(),
        michaniSeed = Random.nextInt(),
        sanceSeed = Random.nextInt(),
        barakySeed = Random.nextInt(),
        panelakySeed = Random.nextInt(),
        stredovySeed = Random.nextInt(),
        kapacitaSeed = Random.nextInt(),
    )
}