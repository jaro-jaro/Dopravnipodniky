package cz.jaro.dopravnipodniky.data.dopravnipodnik

import cz.jaro.dopravnipodniky.shared.DPID
import cz.jaro.dopravnipodniky.shared.Text
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz
import cz.jaro.dopravnipodniky.shared.jednotky.PenizZaMinutu
import cz.jaro.dopravnipodniky.ui.theme.Theme
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Calendar

@Serializable
@SerialName("DPInfo")
data class DPInfo(
    val jizdne: Peniz,
    val jmenoMesta: String,
    val tema: Theme,
    val casPosledniNavstevy: Long = Calendar.getInstance().toInstant().toEpochMilli(),
    val zisk: PenizZaMinutu,
    val detailZisku: Text,
    val id: DPID,
    val detailGenerace: DetailGenerace,
)
