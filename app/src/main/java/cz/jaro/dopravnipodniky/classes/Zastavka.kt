package cz.jaro.dopravnipodniky.classes

import android.content.Context
import android.util.Log
import cz.jaro.dopravnipodniky.nasobitelMaxCloveku
import cz.jaro.dopravnipodniky.other.PrefsHelper.dp
import cz.jaro.dopravnipodniky.other.UliceId
import kotlin.math.roundToInt

class Zastavka(
    val ulice: @UliceId Long,
    ctx: Context,
) {
    val kapacita: Int

    var cloveci = 0

    init {
        val ulice = dp.ulice(ulice)

        kapacita = (ulice.kapacita * nasobitelMaxCloveku).roundToInt()

        ulice.baraky.forEach {
            while (it.cloveci > 0 && cloveci < ulice.cloveci * 3/4) {

                cloveci ++
                it.cloveci--
            }
        }

        Log.i("Vytvořena zastávka", "Byla vytvořena zastávka na ulici ${ulice.id}.")
    }

    fun zesebevrazdujSe(ctx: Context) {

        val ulice = dp.ulice(ulice)

        ulice.baraky.forEach {
            while (it.kapacita <= it.cloveci && cloveci != 0) {

                cloveci --
                it.cloveci++
            }
        }
    }
}
