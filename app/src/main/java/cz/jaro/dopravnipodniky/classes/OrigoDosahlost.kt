package cz.jaro.dopravnipodniky.classes

import cz.jaro.dopravnipodniky.other.CisloDosahlosti

class OrigoDosahlost (
    val cislo: @CisloDosahlosti String,
    val jmeno: String,
    val popis: String,

    val odmena: Double = 0.0,

    val secret: Boolean = false,
    val napoveda: String? = null,
)