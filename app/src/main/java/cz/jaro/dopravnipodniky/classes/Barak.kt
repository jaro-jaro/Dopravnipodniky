package cz.jaro.dopravnipodniky.classes

import cz.jaro.dopravnipodniky.TypBaraku
import kotlinx.serialization.Serializable
import kotlin.random.Random.Default.nextInt

@Serializable
data class Barak (
    val typ: TypBaraku,
    val cisloPopisne: Int,
    val barvicka: Int = nextInt(-2, 2),
    val cloveci: Int = 5//nextInt(typ.kapacita / 2, typ.kapacita),
)