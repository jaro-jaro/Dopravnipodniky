package cz.jaro.dopravnipodniky.classes

import cz.jaro.dopravnipodniky.TypBaraku
import cz.jaro.dopravnipodniky.kapacita
import kotlinx.serialization.Serializable
import kotlin.random.Random.Default.nextInt

@Serializable
data class Barak (
    val typ: TypBaraku,
    val cisloPopisne: Int,
    val barvicka: Int = barva(),
    val kapacita: Int = typ.kapacita,
    val cloveci: Int = nextInt(kapacita / 2, kapacita),
)

private fun barva() = nextInt(-2, 2)