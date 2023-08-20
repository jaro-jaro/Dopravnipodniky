package cz.jaro.dopravnipodniky

import cz.jaro.dopravnipodniky.SkupinaFiltru.Companion.pocatecniFiltry
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
    val tema: Theme = Theme.Zelene,
    val dosahlosti: List<Dosahlost> = listOf(),
    val filtry: List<SkupinaFiltru.Filtr> = pocatecniFiltry,
    val razeni: Razeni = Razeni.Zadne,
) {

    constructor(
        prvniDp: DopravniPodnik
    ) : this(
        podniky = listOf(prvniDp)
    )
}

val Vse.aktualniDp get() = podniky[indexAktualnihoDp]