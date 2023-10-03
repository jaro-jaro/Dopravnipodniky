package cz.jaro.dopravnipodniky.shared

import cz.jaro.dopravnipodniky.data.Nastaveni
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Bus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DPInfo
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz

interface Menic {
    fun zmenitBusy(update: MutableList<Bus>.() -> Unit)
    fun zmenitLinky(update: MutableList<Linka>.() -> Unit)
    fun zmenitUlice(update: MutableList<Ulice>.() -> Unit)
    fun zmenitDPInfo(update: (DPInfo) -> DPInfo)
    fun zmenitPrachy(update: (Peniz) -> Peniz)
    fun zmenitDosahlosti(update: MutableList<Dosahlost.NormalniDosahlost>.() -> Unit)
    fun zmenitTutorial(update: (StavTutorialu) -> StavTutorialu)
    fun zmenitNastaveni(update: (Nastaveni) -> Nastaveni)
    suspend fun zmenitOstatniDopravniPodniky(update: suspend MutableList<DopravniPodnik>.() -> Unit)
    suspend fun zmenitDopravniPodnik(dpID: DPID)
}