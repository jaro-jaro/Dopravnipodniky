package cz.jaro.dopravnipodniky.classes

import cz.jaro.dopravnipodniky.other.UliceId
import kotlin.random.Random.Default.nextInt

class Barak (
    val ulice: @UliceId Long,
    val panelak: Boolean,
    val stredovy: Boolean,
    val cisloPopisne: Int,
) {
    val barvicka: Int = nextInt(-2, 2)

    val kapacita = when {
        stredovy -> nextInt(200, 500)
        panelak -> nextInt(50, 200)
        else -> nextInt(5, 15)
    }
    var cloveci: Int = nextInt(kapacita / 2, kapacita)

}