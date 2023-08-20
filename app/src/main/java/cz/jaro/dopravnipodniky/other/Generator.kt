package cz.jaro.dopravnipodniky.other

import android.util.Log
import cz.jaro.dopravnipodniky.MESTA
import cz.jaro.dopravnipodniky.TypBaraku
import cz.jaro.dopravnipodniky.classes.Barak
import cz.jaro.dopravnipodniky.classes.DopravniPodnik
import cz.jaro.dopravnipodniky.classes.Ulice
import cz.jaro.dopravnipodniky.jednotky.Peniz
import cz.jaro.dopravnipodniky.jednotky.Pozice
import cz.jaro.dopravnipodniky.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.jednotky.to
import cz.jaro.dopravnipodniky.jednotky.ulicovychBloku
import cz.jaro.dopravnipodniky.pocatecniCenaMesta
import kotlin.math.roundToInt
import kotlin.random.Random

class Generator(
    private val investice: Peniz,
) {
    companion object {
        fun vygenerujMiPrvniMesto(): DopravniPodnik = Generator(
            investice = pocatecniCenaMesta,
        ).vygenerujMiMestoAToHnedVykricnik()

        private const val nasobitelInvestice = 1 / 65536.0
        private const val nahodnostPoObnoveni = .35F
        private const val nahodnostNaZacatku = .5F
        private const val rozdilNahodnosti = .05F
        private const val nasobitelRedukce = .75F
    }

    private val velikost: Int = (investice * nasobitelInvestice).value.roundToInt()

    private val ulicove = mutableListOf<Ulice>()

    private val jmenoMesta = MESTA.trim().lines().random()

    fun vygenerujMiMestoAToHnedVykricnik(): DopravniPodnik {
        // Okej hned to bude, nez bys rekl pi
        ulicove.clear()

        opakovac(1, listOf(0.ulicovychBloku to 0.ulicovychBloku), nahodnostNaZacatku)

        zbarakuj()

        return DopravniPodnik(jmenoMesta = jmenoMesta, ulicove = ulicove)
    }

    private fun opakovac(hloubka: Int, posledniKrizovatky: List<Pozice<UlicovyBlok>>, sance: Float) {

        if (hloubka > velikost) return

        val noveKrizovatky = posledniKrizovatky.flatMap { krivovatka ->

            val sousedi = mutableListOf(
                krivovatka.x - 1.ulicovychBloku to krivovatka.y,
                krivovatka.x to krivovatka.y - 1.ulicovychBloku,
                krivovatka.x + 1.ulicovychBloku to krivovatka.y,
                krivovatka.x to krivovatka.y + 1.ulicovychBloku,
            )

            sousedi
                .filter { // todo snizit sanci pokud sousedi uz jsou
                    Random.nextFloat() < sance
                }
                .map { soused ->
                    val (zacatek, konec) =
                        if (krivovatka.x < soused.x || krivovatka.y < soused.y) krivovatka to soused else soused to krivovatka

                    if (ulicove.none { it.zacatek == zacatek && it.konec == konec }) {
                        ulicove += Ulice(zacatek, konec)
                    } else {
                        val i = ulicove.indexOfFirst { it.zacatek == zacatek && it.konec == konec }
                        ulicove[i] = ulicove[i].copy(
                            potencial = ulicove[i].potencial + 1
                        )
                    }

                    soused
                }
        }

        val procentaz = Regex("1*..[.]..").find("0${(hloubka / velikost.toDouble() * 100)}0")?.groupValues?.first()
        Log.i("generace", "$hloubka z $velikost -> $procentaz %")

        if (noveKrizovatky.isEmpty()) {
            val zredukovanyKrizovatky = posledniKrizovatky.shuffled().take((posledniKrizovatky.size * nasobitelRedukce).roundToInt())
            opakovac(hloubka + 1, zredukovanyKrizovatky, nahodnostPoObnoveni)
        } else {
            opakovac(hloubka + 1, noveKrizovatky, sance - rozdilNahodnosti)
        }
    }

    private fun zbarakuj() {

        ulicove.forEachIndexed { i, ulice ->

            if ("3141592" in investice.toString()) {
                ulicove[i] = ulicove[i].copy(
                    potencial = 100
                )
            }
            repeat((ulice.potencial * Random.nextFloat() * 4).roundToInt().coerceAtMost(8)) {
                val typ = if (ulice.potencial >= 6 && Random.nextBoolean()) TypBaraku.Panelak else TypBaraku.Normalni
                ulicove[i] = ulicove[i].copy(
                    baraky = ulicove[i].baraky + Barak(typ, i)
                )
            }
            if (Random.nextFloat() >= .25F && ulice.potencial >= 10)
                ulicove[i] = ulicove[i].copy(
                    baraky = ulicove[i].baraky + Barak(TypBaraku.Stredovy, i)
                )
        }

    }
}
