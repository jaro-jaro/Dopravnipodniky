package cz.jaro.dopravnipodniky.classes

import android.content.Context
import androidx.core.content.edit
import cz.jaro.dopravnipodniky.other.App
import cz.jaro.dopravnipodniky.other.LinkaId
import cz.jaro.dopravnipodniky.other.PrefsHelper.dp
import cz.jaro.dopravnipodniky.other.UliceId

class Linka(
    var cislo: Int,
    seznamKrizovatek: List<Pair<Int, Int>>, // v ulicovych blokach,
    var barvicka: Int,
    ctx: Context,
) {

    var id: @LinkaId Long

    val busy = mutableListOf<Long>()

    val seznamUlic = mutableListOf<@UliceId Long>()

    init {
        val prefs = App.prefs

        val posledniId = prefs.getLong("bus_id", 0)

        id = posledniId + 1

        prefs.edit {
            putLong("bus_id", posledniId + 1)
        }

        seznamKrizovatek.forEachIndexed { i, krizovatka ->

            if (i == 0) return@forEachIndexed

            val minulaKrizovatka = seznamKrizovatek[i - 1]


            val ulice = dp.ulicove.first {
                it.zacatek == krizovatka && it.konec   == minulaKrizovatka ||
                it.konec   == krizovatka && it.zacatek == minulaKrizovatka
            }

            seznamUlic.add(ulice.id!!)
        }

    }

    fun trolej(ctx: Context): Boolean = seznamUlic.all { dp.ulice(it).trolej }
}
