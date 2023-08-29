package cz.jaro.dopravnipodniky.data.dopravnipodnik

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.random.Random.Default.nextInt

@Serializable
@SerialName("Barak")
data class Barak(
    val typ: TypBaraku,
    val cisloPopisne: Int,
    val barvicka: Int,
    val kapacita: Int,
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
            )
        }
    }
}

private fun barva() = nextInt(-2, 2)