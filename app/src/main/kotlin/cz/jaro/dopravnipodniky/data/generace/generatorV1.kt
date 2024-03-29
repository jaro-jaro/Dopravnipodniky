package cz.jaro.dopravnipodniky.data.generace

import android.util.Log
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Barak
import cz.jaro.dopravnipodniky.data.dopravnipodnik.TypBaraku
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.shared.barakuVUlici
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.shared.jednotky.sousedi
import cz.jaro.dopravnipodniky.shared.jednotky.to
import cz.jaro.dopravnipodniky.shared.jednotky.ulicovychBloku
import cz.jaro.dopravnipodniky.shared.nahodnostNaZacatku
import cz.jaro.dopravnipodniky.shared.nahodnostPoObnoveni
import cz.jaro.dopravnipodniky.shared.nahodnostStaveniKNeokupantum
import cz.jaro.dopravnipodniky.shared.nahodnostStaveniKOkupantum
import cz.jaro.dopravnipodniky.shared.nasobitelInvesticeProHloubkuRekurce
import cz.jaro.dopravnipodniky.shared.nasobitelRedukce
import cz.jaro.dopravnipodniky.shared.rozdilNahodnosti
import kotlin.math.roundToInt
import kotlin.random.Random

fun generatorV1(
    detailGenerace: DetailGeneraceV1,
    step: (Float) -> Unit,
): VysledekGenerace {

    val investice = detailGenerace.investice
    val ulicove = mutableListOf<Ulice>()

    val michaniRandom = Random(detailGenerace.michaniSeed)
    val sanceRandom = Random(detailGenerace.sanceSeed)
    val barakyRandom = Random(detailGenerace.barakySeed)
    val panelakyRandom = Random(detailGenerace.panelakySeed)
    val stredovyRandom = Random(detailGenerace.stredovySeed)
    val kapacitaRandom = Random(detailGenerace.kapacitaSeed)

    // Okej hned to bude, nez bys rekl pi

    tailrec fun opakovac(
        hloubka: Int,
        posledniKrizovatky: List<Pozice<UlicovyBlok>>,
        sance: Float,
        step: (Float) -> Unit,
    ) {
        val velikost: Int = (investice * nasobitelInvesticeProHloubkuRekurce).value.roundToInt()

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

        val uzHotovo = hloubka / velikost.toDouble()
        step(uzHotovo.toFloat())
        val uzHotovoTextem = Regex("1?..\\...").find("0${(uzHotovo * 100)}0")?.groupValues?.first()
        Log.i("generace", "$hloubka z $velikost -> $uzHotovoTextem %")

        if (noveKrizovatky.isEmpty()) {
            val zredukovanyKrizovatky =
                posledniKrizovatky.shuffled(michaniRandom).take((posledniKrizovatky.size * nasobitelRedukce).roundToInt())
            opakovac(
                hloubka = hloubka + 1,
                posledniKrizovatky = zredukovanyKrizovatky,
                sance = nahodnostPoObnoveni,
                step = step,
            )
        } else {
            opakovac(
                hloubka = hloubka + 1,
                posledniKrizovatky = noveKrizovatky,
                sance = sance - rozdilNahodnosti,
                step = step,
            )
        }
    }

    fun zbarakuj() {

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

//            println(ulice.baraky.size)
        }
    }

    opakovac(
        hloubka = 1,
        posledniKrizovatky = listOf(0.ulicovychBloku to 0.ulicovychBloku),
        sance = nahodnostNaZacatku,
        step = step,
    )

    zbarakuj()

    return VysledekGenerace(ulicove = ulicove, krizovatky = listOf())
}