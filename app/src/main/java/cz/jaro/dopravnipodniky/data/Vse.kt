package cz.jaro.dopravnipodniky.data

import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz
import cz.jaro.dopravnipodniky.shared.pocatecniObnosPenez
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Vse")
data class Vse(
    val podniky: List<DopravniPodnik>,
    val indexAktualnihoDp: Int = 0,
    val prachy: Peniz = pocatecniObnosPenez,
    val tutorial: StavTutorialu = StavTutorialu.Tutorialujeme.Uvod,
    val dosahlosti: List<Dosahlost> = listOf(),
    val nastaveni: Nastaveni = Nastaveni(),
) {

    constructor(
        prvniDp: DopravniPodnik
    ) : this(
        podniky = listOf(prvniDp)
    )
}

val Vse.aktualniDp get() = podniky[indexAktualnihoDp]