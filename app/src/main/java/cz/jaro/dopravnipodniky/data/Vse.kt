package cz.jaro.dopravnipodniky.data

import cz.jaro.dopravnipodniky.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.garaz.obchod.Razeni
import cz.jaro.dopravnipodniky.garaz.obchod.SkupinaFiltru
import cz.jaro.dopravnipodniky.garaz.obchod.SkupinaFiltru.Companion.pocatecniFiltry
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
    val automatickyUdelovatEvC: Boolean = false,
    val vicenasobnyKupovani: Boolean = false,
    val tutorial: StavTutorialu = StavTutorialu.Tutorialujeme.Uvod,
    val dosahlosti: List<Dosahlost> = listOf(),
    val filtry: List<SkupinaFiltru.Filtr> = pocatecniFiltry,
    val razeni: Razeni = Razeni.Naklady.Vzestupne,
) {

    constructor(
        prvniDp: DopravniPodnik
    ) : this(
        podniky = listOf(prvniDp)
    )
}

val Vse.aktualniDp get() = podniky[indexAktualnihoDp]