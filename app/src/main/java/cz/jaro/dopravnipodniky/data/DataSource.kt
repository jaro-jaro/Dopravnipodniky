package cz.jaro.dopravnipodniky.data

import cz.jaro.dopravnipodniky.data.dopravnipodnik.Bus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DPInfo
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.shared.DPID
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz
import kotlinx.coroutines.flow.Flow

interface DataSource {
    val busy: Flow<List<Bus>>
    val linky: Flow<List<Linka>>
    val ulice: Flow<List<Ulice>>
    val dpInfo: Flow<DPInfo>
    val prachy: Flow<Peniz>
    val dosahlosti: Flow<List<Dosahlost.NormalniDosahlost>>
    val tutorial: Flow<StavTutorialu>
    val nastaveni: Flow<Nastaveni>
    val dp: Flow<DopravniPodnik>
    val podniky: Flow<List<DopravniPodnik>>
    val vse: Flow<Vse>

    suspend fun upravitBusy(update: suspend MutableList<Bus>.() -> Unit)

    suspend fun upravitLinky(update: suspend MutableList<Linka>.() -> Unit)

    suspend fun upravitUlice(update: suspend MutableList<Ulice>.() -> Unit)

    suspend fun upravitDPInfo(update: suspend (DPInfo) -> DPInfo)

    suspend fun upravitPrachy(update: suspend (Peniz) -> Peniz)

    suspend fun upravitDosahlosti(update: suspend MutableList<Dosahlost.NormalniDosahlost>.() -> Unit)

    suspend fun upravitTutorial(update: suspend (StavTutorialu) -> StavTutorialu)

    suspend fun upravitNastaveni(update: suspend (Nastaveni) -> Nastaveni)

    suspend fun upravitOstatniDopravniPodniky(update: suspend MutableList<DopravniPodnik>.() -> Unit)

    suspend fun zmenitDopravniPodnik(dpID: DPID)
}