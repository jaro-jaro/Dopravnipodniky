package cz.jaro.dopravnipodniky.data.dopravnipodnik

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.jaro.dopravnipodniky.shared.DPID
import cz.jaro.dopravnipodniky.shared.LinkaID
import cz.jaro.dopravnipodniky.ui.theme.Barvicka
import kotlinx.serialization.SerialName
import java.util.UUID

//@Serializable
@Entity
@SerialName("Linka")
data class Linka(
    val dpID: DPID,
    val cislo: String,
    val barvicka: Barvicka,
    @PrimaryKey val id: LinkaID = UUID.randomUUID(),
) {
    override fun toString() = "Linka(cislo=$cislo)"
}

//fun Linka.busy(dp: DopravniPodnik) = dp.busy.filter { it.linka == id }

//fun Linka.ulice(dp: DopravniPodnik) = dp.ulice.filter { it.id in ulice }.sortedBy { ulice.indexOf(it.id) }

fun List<Ulice>.jsouVsechnyZatrolejovane() = all { it.maTrolej }
