package cz.jaro.dopravnipodniky.data.dopravnipodnik

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Barak")
data class Barak(
    val typ: TypBaraku,
    val cisloPopisne: Int,
    val cisloOrientacni: Int,
    val kapacita: Int,
) {
    companion object {
        operator fun invoke(
            typ: TypBaraku,
            cisloPopisne: Int,
            cisloOrientacni: Int,
        ): Barak {
            val kapacita = typ.kapacita.random()
            return Barak(
                typ = typ,
                cisloPopisne = cisloPopisne,
                cisloOrientacni = cisloOrientacni,
                kapacita = kapacita,
            )
        }
    }
}