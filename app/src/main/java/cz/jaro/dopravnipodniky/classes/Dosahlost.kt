package cz.jaro.dopravnipodniky.classes

import java.util.*

class Dosahlost (
    val cislo: String,
    var splneno: Boolean = false,
    var datumSplneni: Calendar? = null
)