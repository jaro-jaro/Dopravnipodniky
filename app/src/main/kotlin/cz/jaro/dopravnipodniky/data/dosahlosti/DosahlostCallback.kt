package cz.jaro.dopravnipodniky.data.dosahlosti

fun interface DosahlostCallback {
    fun zobrazitSnackbar(dosahlost: Dosahlost.NormalniDosahlost, vsechnyDosahlosti: List<Dosahlost.NormalniDosahlost>)
}