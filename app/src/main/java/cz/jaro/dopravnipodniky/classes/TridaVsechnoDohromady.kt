package cz.jaro.dopravnipodniky.classes

import cz.jaro.dopravnipodniky.BARVICKYTEMAT
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.pocatecniObnosPenez

class TridaVsechnoDohromady(dp: DopravniPodnik) {
    val podniky = mutableListOf(dp)
    var aktualniDp = 0 // index
    var prachy = pocatecniObnosPenez
    var automatickyEvC = false
    var vicenasobnyKupovani = false
    var zobrazitLinky = false
    var tutorial = 1 // kladné -> číslo tutoriálu, 0 -> bez
    var tema = R.style.Theme_DopravníPodniky_Cervene
    var barva = BARVICKYTEMAT[1]
    val dosahlosti = mutableListOf<Dosahlost>()
    val pocetniDosahlosti = mutableMapOf<String, Int>()

}