package cz.jaro.dopravnipodniky.dopravnipodnik

import kotlinx.serialization.Serializable
import kotlin.random.Random.Default.nextInt

@Serializable
data class Barak(
    val typ: TypBaraku,
    val cisloPopisne: Int,
    val barvicka: Int,
    val kapacita: Int,
    val cloveci: Int,
) {
    companion object {
        operator fun invoke(
            typ: TypBaraku,
            cisloPopisne: Int,
        ): Barak {
            val kapacita = typ.getKapacita()
            return Barak(
                typ = typ,
                cisloPopisne = cisloPopisne,
                barvicka = barva(),
                kapacita = kapacita,
                cloveci = nextInt(kapacita / 2, kapacita)
            )
        }
    }
}

private fun barva() = nextInt(-2, 2)