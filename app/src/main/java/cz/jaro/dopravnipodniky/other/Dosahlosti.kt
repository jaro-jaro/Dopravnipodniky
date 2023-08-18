package cz.jaro.dopravnipodniky.other

import cz.jaro.dopravnipodniky.Dosahlost
import cz.jaro.dopravnipodniky.Vse
import java.time.LocalDate
import kotlin.reflect.KClass

object Dosahlosti {

    fun dosahni(vse: Vse, novyVse: ((Vse) -> Vse) -> Unit, dosahlostKlass: KClass<Dosahlost>) {

        fun ulozit(novaDosahlost: Dosahlost) {
            novyVse { vse ->
                vse.copy(
                    dosahlosti = (listOf(novaDosahlost) + vse.dosahlosti).distinctBy { it::class }
                )
            }
        }

        val dosahlost = vse.dosahlosti.first { it::class == dosahlostKlass }

        if (dosahlost.stav is Dosahlost.Stav.Splneno) return

        if (dosahlost is Dosahlost.Pocetni) {
            val stav = dosahlost.stav as Dosahlost.Stav.Pocetni
            if (stav.pocet + 1 != dosahlost.cil) {
                ulozit(dosahlost.stav(stav.copy(pocet = stav.pocet + 1)))
                return
            }
        }

        ulozit(dosahlost.stav(Dosahlost.Stav.Splneno(LocalDate.now())))
        novyVse {
            it.copy(
                prachy = it.prachy + dosahlost.odmena
            )
        }
    }
}
