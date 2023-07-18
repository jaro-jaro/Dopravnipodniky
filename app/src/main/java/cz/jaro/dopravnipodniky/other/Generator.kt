package cz.jaro.dopravnipodniky.other

import android.util.Log
import cz.jaro.dopravnipodniky.*
import cz.jaro.dopravnipodniky.classes.Barak
import cz.jaro.dopravnipodniky.classes.DopravniPodnik
import cz.jaro.dopravnipodniky.classes.Ulice
import kotlin.math.roundToInt
import kotlin.random.Random

class Generator(private val investice: Long) {

    private val velikost: Int = (investice * nasobitelInvestice).roundToInt()
    private val dp = DopravniPodnik()

    fun vygenerujMiMestoAToHnedVykricnik(): DopravniPodnik {
        // Okej hned to bude, nez bys rekl pi

        dp.ulicove.clear()

        opakovac(1, listOf(0 to 0), nahodnostNaZacatku)

        zbarakuj()

        val inputStream = App.res.openRawResource(R.raw.mesta)

        val bufferedReader = inputStream.bufferedReader()

        val mesta = bufferedReader.use {
            it.readText().split("\n")
        }

        dp.jmenoMesta = mesta.random()

        return dp

    }

    private fun opakovac (hloubka: Int, krizovatky: List<Pair<Int, Int>>, sance: Float) {

        if (hloubka > velikost) return

        val noveKrizovatky = mutableListOf<Pair<Int, Int>>()

        for (zacatek in krizovatky) {

            val sousedi = mutableListOf(
                zacatek.first - 1 to zacatek.second,
                zacatek.first to zacatek.second - 1,
                zacatek.first + 1 to zacatek.second,
                zacatek.first to zacatek.second + 1,
            )

            for (konec in sousedi) {

                if (Random.nextFloat() < sance) {
                    noveKrizovatky += konec

                    if (zacatek.first  < konec.first ||
                        zacatek.second < konec.second
                    ) {

                        if (dp.ulicove.any { it.zacatek == zacatek && it.konec == konec }) {

                            dp.ulicove.first { it.zacatek == zacatek && it.konec == konec }.potencial++

                        } else {
                            dp.ulicove += Ulice(zacatek, konec)
                        }

                    } else {

                        if (dp.ulicove.any { it.zacatek == konec && it.konec == zacatek }) {

                            dp.ulicove.first { it.zacatek == konec && it.konec == zacatek }.potencial++

                        } else {
                            dp.ulicove += Ulice(konec, zacatek)
                        }
                    }


                }
            }
        }

        Log.i("generace", "$hloubka z $velikost -> ${Regex("[1]*..[.]..").find("0${(hloubka / velikost.toDouble() * 100)}0")?.groupValues?.first()} %")

        if (noveKrizovatky.isEmpty()) {
            val zredukovanyKrizovatky = krizovatky.shuffled().subList(0, (krizovatky.size * nasobitelRedukce).roundToInt())
            opakovac(hloubka + 1, zredukovanyKrizovatky, nahodnostPoObnoveni)
        } else {
            opakovac(hloubka + 1, noveKrizovatky, sance - rozdilNahodnosti)
        }
    }

    private fun zbarakuj() {

        dp.ulicove.forEachIndexed { i, ulice ->

            if ("3141592" in investice.toString()) {
                ulice.potencial = 100
            }
            repeat((ulice.potencial * Random.nextFloat() * 4).roundToInt().coerceAtMost(8)) {
                ulice.baraky += Barak(ulice.id, ulice.potencial >= 6 && Random.nextBoolean(), false, i)
            }
            if (Random.nextFloat() >= .25F && ulice.potencial >= 10)
                ulice.baraky += Barak(ulice.id, panelak = true, stredovy = true, i)
        }

    }
}
