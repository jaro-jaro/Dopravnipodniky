package cz.jaro.dopravnipodniky.data

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Barak
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.TypBaraku
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.shared.barakuVUlici
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.shared.jednotky.penez
import cz.jaro.dopravnipodniky.shared.jednotky.to
import cz.jaro.dopravnipodniky.shared.jednotky.ulicovychBloku
import cz.jaro.dopravnipodniky.shared.seznamy.MESTA
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt
import kotlin.random.Random

var seed: Int? by mutableStateOf(null)

class Generator(
    private val investice: Peniz,
    val random: Random = Random,
    jmenoMestaRandom: Random = Random,
) {
    companion object {
        suspend fun vygenerujMiPrvniMesto(): DopravniPodnik = withContext(Dispatchers.IO) {

            while (seed == null) Unit

            Generator(
                investice = pocatecniCenaMesta,
                random = Random(seed!!),
                jmenoMestaRandom = Random(seed!!),
            ).vygenerujMiMestoAToHnedVykricnik()
        }

        private val pocatecniCenaMesta = 1_200_000L.penez/*3_141_592.penez*//*10_000_000.penez*/
        private const val nasobitelInvestice = 1 / 65536.0
        private const val nahodnostStaveniKOkupantum = .6F
        private const val nahodnostStaveniKNeokupantum = 1.1F
        private const val nahodnostPoObnoveni = .35F
        private const val nahodnostNaZacatku = .5F
        private const val rozdilNahodnosti = .05F
        private const val nasobitelRedukce = .75F
    }

    private val velikost: Int = (investice * nasobitelInvestice).value.roundToInt()

    private val ulicove = mutableListOf<Ulice>()

    private val jmenoMesta = MESTA.trim().lines().random(jmenoMestaRandom)

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
                .filter { pozice ->
                    val uzNekdoOkupujeSouseda = ulicove.any { it.zacatek == pozice || it.konec == pozice }
                    val novaSance = if (uzNekdoOkupujeSouseda) sance * nahodnostStaveniKOkupantum else sance * nahodnostStaveniKNeokupantum
                    random.nextFloat() < novaSance
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
            repeat(
                (ulice.potencial * random.nextFloat() * 6)
                    .roundToInt()
                    .coerceAtLeast(1)
                    .coerceAtMost((barakuVUlici - 1) * 2)
            ) {
                val typ = if (ulice.potencial >= 3 && random.nextBoolean()) TypBaraku.Panelak else TypBaraku.Normalni
                ulicove[i] = ulicove[i].copy(
                    baraky = ulicove[i].baraky + Barak(typ, i),
                )
            }
            if (random.nextFloat() >= .25F && ulice.potencial >= 5)
                ulicove[i] = ulicove[i].copy(
                    baraky = ulicove[i].baraky + Barak(TypBaraku.Stredovy, i)
                )

//            println(ulicove[i].baraky)
            ulicove[i] = ulicove[i].copy(
                cloveci = random.nextInt(ulicove[i].kapacita / 2, ulicove[i].kapacita)
            )
        }
    }
}
