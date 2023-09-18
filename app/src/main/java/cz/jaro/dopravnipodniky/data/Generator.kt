package cz.jaro.dopravnipodniky.data

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import cz.jaro.dopravnipodniky.shared.jednotky.sousedi
import cz.jaro.dopravnipodniky.shared.jednotky.to
import cz.jaro.dopravnipodniky.shared.jednotky.ulicovychBloku
import cz.jaro.dopravnipodniky.shared.nahodnostNaZacatku
import cz.jaro.dopravnipodniky.shared.nahodnostPoObnoveni
import cz.jaro.dopravnipodniky.shared.nahodnostStaveniKNeokupantum
import cz.jaro.dopravnipodniky.shared.nahodnostStaveniKOkupantum
import cz.jaro.dopravnipodniky.shared.nasobitelInvestice
import cz.jaro.dopravnipodniky.shared.nasobitelRedukce
import cz.jaro.dopravnipodniky.shared.pocatecniCenaMesta
import cz.jaro.dopravnipodniky.shared.rozdilNahodnosti
import cz.jaro.dopravnipodniky.shared.seznamy.MESTA
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt
import kotlin.random.Random

var seed: Int by mutableIntStateOf(Random.nextInt())

class Generator(
    private val investice: Peniz,
    jmenoMestaRandom: Random = Random,
) {
    companion object {
        suspend fun vygenerujMiPrvniMesto(): DopravniPodnik = withContext(Dispatchers.IO) {

            Generator(
                investice = pocatecniCenaMesta,
                jmenoMestaRandom = Random(18),
            ).vygenerujMiMestoAToHnedVykricnik(
                Random(seed),
            ) {}
        }
    }

    private val velikost: Int = (investice * nasobitelInvestice).value.roundToInt()

    private val ulicove = mutableListOf<Ulice>()

    private val jmenoMesta = MESTA.trim().lines().random(jmenoMestaRandom)

    private lateinit var michaniRandom: Random
    private lateinit var sanceRandom: Random
    private lateinit var barakyRandom: Random
    private lateinit var panelakyRandom: Random
    private lateinit var stredovyRandom: Random
    private lateinit var kapacitaRandom: Random

    suspend fun vygenerujMiMestoAToHnedVykricnik(
        michaniRandom: Random = Random,
        sanceRandom: Random = Random,
        barakyRandom: Random = Random,
        panelakyRandom: Random = Random,
        stredovyRandom: Random = Random,
        kapacitaRandom: Random = Random,
        step: (Float) -> Unit,
    ) = withContext(Dispatchers.IO) {
        // Okej hned to bude, nez bys rekl pi
        this@Generator.michaniRandom = michaniRandom
        this@Generator.sanceRandom = sanceRandom
        this@Generator.barakyRandom = barakyRandom
        this@Generator.panelakyRandom = panelakyRandom
        this@Generator.stredovyRandom = stredovyRandom
        this@Generator.kapacitaRandom = kapacitaRandom

        opakovac(1, listOf(0.ulicovychBloku to 0.ulicovychBloku), nahodnostNaZacatku)

        zbarakuj()

        DopravniPodnik(jmenoMesta = jmenoMesta, ulicove = ulicove)
    }

    private tailrec fun opakovac(hloubka: Int, posledniKrizovatky: List<Pozice<UlicovyBlok>>, sance: Float) {

        if (hloubka > velikost) return

        val noveKrizovatky = posledniKrizovatky.flatMap { krizovatka ->

            val sousedi = krizovatka.sousedi()

            sousedi
                .filter { soused ->
                    val uzNekdoOkupujeSouseda = ulicove.any { it.zacatek == soused || it.konec == soused }
                    val novaSance = if (uzNekdoOkupujeSouseda) sance * nahodnostStaveniKOkupantum else sance * nahodnostStaveniKNeokupantum
                    sanceRandom.nextFloat() < novaSance
                }
                .map { soused ->
                    val (zacatek, konec) =
                        if (krizovatka.x < soused.x || krizovatka.y < soused.y) krizovatka to soused else soused to krizovatka

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
            val zredukovanyKrizovatky = posledniKrizovatky.shuffled(michaniRandom).take((posledniKrizovatky.size * nasobitelRedukce).roundToInt())
            opakovac(hloubka + 1, zredukovanyKrizovatky, nahodnostPoObnoveni)
        } else {
            opakovac(hloubka + 1, noveKrizovatky, sance - rozdilNahodnosti)
        }
    }

    private fun zbarakuj() {

        var baraku = 0

        ulicove.forEachIndexed { i, ulice ->

            if ("3141592" in investice.toString()) {
                ulicove[i] = ulicove[i].copy(
                    potencial = 100
                )
            }
            repeat(
                (ulice.potencial * barakyRandom.nextFloat() * 6)
                    .roundToInt()
                    .coerceAtLeast(1)
                    .coerceAtMost((barakuVUlici - 1) * 2)
            ) {
                val typ = if (ulice.potencial >= 3 && panelakyRandom.nextBoolean()) TypBaraku.Panelak else TypBaraku.Normalni
                ulicove[i] = ulicove[i].copy(
                    baraky = ulicove[i].baraky + Barak(typ, ++baraku, it),
                )
            }
            if (stredovyRandom.nextFloat() >= .25F && ulice.potencial >= 5)
                ulicove[i] = ulicove[i].copy(
                    baraky = ulicove[i].baraky + Barak(TypBaraku.Stredovy, ++baraku, (barakuVUlici - 1) * 2 + 1)
                )

//            println(ulicove[i].baraky)
            ulicove[i] = ulicove[i].copy(
                baraky = ulicove[i].baraky.take((barakuVUlici - 1) * 2)
            )

            ulicove[i] = ulicove[i].copy(
                cloveci = kapacitaRandom.nextInt(ulicove[i].kapacita / 2, ulicove[i].kapacita)
            )

            println(ulice.baraky.size)
        }
    }
}
