package cz.jaro.dopravnipodniky.other

import cz.jaro.dopravnipodniky.Dosahlost
import cz.jaro.dopravnipodniky.PreferencesDataSource
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Single
import java.time.LocalDate
import kotlin.reflect.KClass

@Single
class Dosahlovac(
    private val dataSource: PreferencesDataSource,
) {
    suspend fun dosahni(dosahlostKlass: KClass<Dosahlost>) {

        suspend fun ulozit(novaDosahlost: Dosahlost) {
            dataSource.zmenitVse { vse ->
                vse.copy(
                    dosahlosti = (listOf(novaDosahlost) + vse.dosahlosti).distinctBy { it::class }
                )
            }
        }

        val dosahlost = dataSource.vse.first().dosahlosti.first { it::class == dosahlostKlass }

        if (dosahlost.stav is Dosahlost.Stav.Splneno) return

        if (dosahlost is Dosahlost.Pocetni) {
            val stav = dosahlost.stav as Dosahlost.Stav.Pocetni
            if (stav.pocet + 1 != dosahlost.cil) {
                ulozit(dosahlost.stav(stav.copy(pocet = stav.pocet + 1)))
                return
            }
        }

        ulozit(dosahlost.stav(Dosahlost.Stav.Splneno(LocalDate.now())))
        dataSource.zmenitVse {
            it.copy(
                prachy = it.prachy + dosahlost.odmena
            )
        }
    }
}
