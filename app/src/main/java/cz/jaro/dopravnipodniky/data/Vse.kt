package cz.jaro.dopravnipodniky.data

import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.shared.DPID
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz
import cz.jaro.dopravnipodniky.shared.pocatecniObnosPenez
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Vse")
data class Vse(
    val podniky: List<DopravniPodnik>,
    val aktualniDPID: DPID = podniky.first().info.id,
    val prachy: Peniz = pocatecniObnosPenez,
    val tutorial: StavTutorialu = StavTutorialu.Tutorialujeme.Uvod,
    val dosahlosti: List<Dosahlost.NormalniDosahlost> = listOf(),
    val nastaveni: Nastaveni = Nastaveni(),
) {

    constructor(
        prvniDp: DopravniPodnik
    ) : this(
        podniky = listOf(prvniDp)
    )
}

val Vse.aktualniDp get() = podniky.first { it.info.id == aktualniDPID }