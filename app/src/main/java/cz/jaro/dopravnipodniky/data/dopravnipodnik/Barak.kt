package cz.jaro.dopravnipodniky.data.dopravnipodnik

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.jaro.dopravnipodniky.shared.UliceID
import kotlinx.serialization.SerialName
import java.util.UUID

@Entity
//@Serializable
@SerialName("Barak")
data class Barak(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val uliceID: UliceID,
    val typ: TypBaraku,
    val cisloPopisne: Int,
    val cisloOrientacni: Int,
    val barvicka: Int,
    val kapacita: Int,
) {
    companion object {
        operator fun invoke(
            typ: TypBaraku,
            cisloPopisne: Int,
            cisloOrientacni: Int,
            uliceID: UliceID,
        ): Barak {
            val kapacita = typ.getKapacita()
            return Barak(
                typ = typ,
                cisloPopisne = cisloPopisne,
                cisloOrientacni = cisloOrientacni,
                barvicka = typ.getBarva(),
                kapacita = kapacita,
                uliceID = uliceID,
            )
        }
    }
}