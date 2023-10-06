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

class DatabaseDataSource : DataSource {
    override val busy: Flow<List<Bus>>
        get() = TODO("Not yet implemented")
    override val linky: Flow<List<Linka>>
        get() = TODO("Not yet implemented")
    override val ulice: Flow<List<Ulice>>
        get() = TODO("Not yet implemented")
    override val dpInfo: Flow<DPInfo>
        get() = TODO("Not yet implemented")
    override val prachy: Flow<Peniz>
        get() = TODO("Not yet implemented")
    override val dosahlosti: Flow<List<Dosahlost.NormalniDosahlost>>
        get() = TODO("Not yet implemented")
    override val tutorial: Flow<StavTutorialu>
        get() = TODO("Not yet implemented")
    override val nastaveni: Flow<Nastaveni>
        get() = TODO("Not yet implemented")
    override val dp: Flow<DopravniPodnik>
        get() = TODO("Not yet implemented")
    override val podniky: Flow<List<DopravniPodnik>>
        get() = TODO("Not yet implemented")
    override val vse: Flow<Vse>
        get() = TODO("Not yet implemented")

    override suspend fun upravitBusy(update: suspend MutableList<Bus>.() -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun upravitLinky(update: suspend MutableList<Linka>.() -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun upravitUlice(update: suspend MutableList<Ulice>.() -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun upravitDPInfo(update: suspend (DPInfo) -> DPInfo) {
        TODO("Not yet implemented")
    }

    override suspend fun upravitPrachy(update: suspend (Peniz) -> Peniz) {
        TODO("Not yet implemented")
    }

    override suspend fun upravitDosahlosti(update: suspend MutableList<Dosahlost.NormalniDosahlost>.() -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun upravitTutorial(update: suspend (StavTutorialu) -> StavTutorialu) {
        TODO("Not yet implemented")
    }

    override suspend fun upravitNastaveni(update: suspend (Nastaveni) -> Nastaveni) {
        TODO("Not yet implemented")
    }

    override suspend fun upravitOstatniDopravniPodniky(update: suspend MutableList<DopravniPodnik>.() -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun zmenitDopravniPodnik(dpID: DPID) {
        TODO("Not yet implemented")
    }
}