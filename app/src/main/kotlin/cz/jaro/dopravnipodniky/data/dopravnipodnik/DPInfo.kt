package cz.jaro.dopravnipodniky.data.dopravnipodnik

import cz.jaro.dopravnipodniky.data.generace.DetailGenerace
import cz.jaro.dopravnipodniky.data.serializers.InstantMillisSerializer
import cz.jaro.dopravnipodniky.shared.DPID
import cz.jaro.dopravnipodniky.shared.Text
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz
import cz.jaro.dopravnipodniky.shared.jednotky.PenizZaMinutu
import cz.jaro.dopravnipodniky.ui.theme.Theme
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Serializable
@SerialName("DPInfo")
data class DPInfo(
    val jizdne: Peniz,
    val jmenoMesta: String,
    val theme: Theme = Theme.Default,
    @Serializable(with = InstantMillisSerializer::class)
    val casPosledniNavstevy: Instant = Clock.System.now(),
    val zisk: PenizZaMinutu,
    val detailZisku: Text,
    val id: DPID,
    val detailGenerace: DetailGenerace,
)
