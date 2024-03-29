@file:Suppress("unused")

package cz.jaro.dopravnipodniky.data.generace

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
 * Nyní: V2
 */
fun DetailGenerace(
    investice: Peniz
): DetailGenerace = DetailGeneraceV2(
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

@Serializable
@SerialName("DetailGeneraceV2")
data class DetailGeneraceV2(
    override val investice: Peniz,
    override val nazevMestaSeed: Int,
    val michaniSeed: Int,
    val sanceSeed: Int,
    val barakySeed: Int,
    val panelakySeed: Int,
    val stredovySeed: Int,
    val kapacitaSeed: Int,
    val kruhaceSeed: Int,
) : DetailGenerace {
    override fun ostatniSeedy() = listOf(
        michaniSeed,
        sanceSeed,
        barakySeed,
        panelakySeed,
        stredovySeed,
        kapacitaSeed,
        kruhaceSeed,
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
        kruhaceSeed = Random.nextInt(),
    )
}