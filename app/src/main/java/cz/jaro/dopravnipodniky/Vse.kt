package cz.jaro.dopravnipodniky

import cz.jaro.dopravnipodniky.classes.DopravniPodnik
import cz.jaro.dopravnipodniky.jednotky.Peniz
import cz.jaro.dopravnipodniky.theme.Theme
import kotlinx.serialization.Serializable

@Serializable
data class Vse(
    val podniky: List<DopravniPodnik>,
    val indexAktualnihoDp: Int = 0,
    val prachy: Peniz = pocatecniObnosPenez,
    val automatickyUdelovatEvC: Boolean = false,
    val vicenasobnyKupovani: Boolean = false,
    val zobrazitLinky: Boolean = false,
    val tutorial: StavTutorialu = StavTutorialu.Tutorialujeme.Uvod,
    val tema: Theme = Theme.Cervene,
    val dosahlosti: List<Dosahlost> = listOf(),
) {
    constructor(
        prvniDp: DopravniPodnik
    ) : this(
        podniky = listOf(prvniDp)
    )
}
