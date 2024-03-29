package cz.jaro.dopravnipodniky.data.generace

import cz.jaro.dopravnipodniky.data.dopravnipodnik.Krizovatka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice

data class VysledekGenerace(
    val ulicove: List<Ulice>,
    val krizovatky: List<Krizovatka>,
)