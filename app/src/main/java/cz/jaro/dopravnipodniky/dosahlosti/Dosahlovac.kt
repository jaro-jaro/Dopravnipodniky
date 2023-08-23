package cz.jaro.dopravnipodniky.dosahlosti

import cz.jaro.dopravnipodniky.data.PreferencesDataSource
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Single
import java.time.LocalDate
import kotlin.reflect.KClass

@Single
class Dosahlovac(
    private val dataSource: PreferencesDataSource,
) {
    suspend fun dosahni(dosahlostKlass: KClass<out Dosahlost>) {

        suspend fun ulozit(novaDosahlost: Dosahlost) {
            dataSource.zmenitVse { vse ->
                vse.copy(
                    dosahlosti = (listOf(novaDosahlost) + vse.dosahlosti).distinctBy { it::class }
                )
            }
        }

        val dosahlost = dataSource.vse.first().dosahlosti.find { it::class == dosahlostKlass }
            ?: dosahlostKlass.objectInstance ?: dosahlostKlass.constructors.minBy { it.parameters.size }.call(Dosahlost.Stav.Nesplneno)

        if (dosahlost is Dosahlost.SkupinovaDosahlost) {
            dosahlost.dosahlosti.forEach {
                dosahni(it)
            }
            return
        }
        dosahlost as Dosahlost.NormalniDosahlost

        if (dosahlost.stav is Dosahlost.Stav.Splneno) return

        if (dosahlost is Dosahlost.Pocetni) {
            val stav =
                if (dosahlost.stav is Dosahlost.Stav.Pocetni) dosahlost.stav as Dosahlost.Stav.Pocetni else Dosahlost.Stav.Pocetni(0)
            if (stav.pocet + 1 != dosahlost.cil) {
                ulozit(dosahlost.kopirovat(stav.copy(pocet = stav.pocet + 1)))
                return
            }
        }

        ulozit(dosahlost.kopirovat(Dosahlost.Stav.Splneno(LocalDate.now())))
        dataSource.zmenitVse {
            it.copy(
                prachy = it.prachy + dosahlost.odmena
            )
        }
    }
}
