package cz.jaro.dopravnipodniky.data.dosahlosti

import cz.jaro.dopravnipodniky.data.PreferencesDataSource
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.LocalDateTime
import kotlin.reflect.KClass

@Single
class Dosahlovac(
    private val dataSource: PreferencesDataSource,
) : KoinComponent {

    private val dosahlostCallback by inject<DosahlostCallback>()

    suspend fun dosahniPocetniDosahlost(
        dosahlostKlass: KClass<out Dosahlost>,
        hodnota: Int,
    ) {
        val dosahlost = dataSource.dosahlosti.first().find { it::class == dosahlostKlass }
            ?: dosahlostKlass.objectInstance ?: dosahlostKlass.constructors.minBy { it.parameters.size }.call(Dosahlost.Stav.Nesplneno)

        if (dosahlost is Dosahlost.SkupinovaDosahlost) {
            dosahlost.dosahlosti.forEach {
                dosahniPocetniDosahlost(it, hodnota)
            }
            return
        }
        dosahlost as Dosahlost.Pocetni

        if (dosahlost.stav is Dosahlost.Stav.Splneno) return

        val stav =
            if (dosahlost.stav is Dosahlost.Stav.Pocetni) dosahlost.stav as Dosahlost.Stav.Pocetni else Dosahlost.Stav.Pocetni(0)

        if (hodnota < dosahlost.cil) {
            ulozit(dosahlost.kopirovat(stav.copy(pocet = hodnota)))
            return
        }

        val novaDosahlost = dosahlost.kopirovat(Dosahlost.Stav.Splneno(LocalDateTime.now()))
        val vse = ulozit(novaDosahlost)
        dataSource.upravitPrachy {
            it + dosahlost.odmena
        }

        dosahlostCallback.zobrazitSnackbar(novaDosahlost, vse)
    }

    private suspend fun ulozit(novaDosahlost: Dosahlost.NormalniDosahlost): List<Dosahlost.NormalniDosahlost> {
        var vysledek: List<Dosahlost.NormalniDosahlost>? = null
        dataSource.upravitDosahlosti {
            add(0, novaDosahlost)
            val ruzne = distinctBy { it::class }
            clear()
            addAll(ruzne)
            vysledek = this
        }
        while (vysledek == null) Unit
        return vysledek!!
    }

    suspend fun dosahni(dosahlostKlass: KClass<out Dosahlost>) {

        val dosahlost = dataSource.dosahlosti.first().find { it::class == dosahlostKlass }
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

        val novaDosahlost = dosahlost.kopirovat(Dosahlost.Stav.Splneno(LocalDateTime.now()))
        val vse = ulozit(novaDosahlost)
        dataSource.upravitPrachy {
            it + dosahlost.odmena
        }

        dosahlostCallback.zobrazitSnackbar(novaDosahlost, vse)
    }
}
