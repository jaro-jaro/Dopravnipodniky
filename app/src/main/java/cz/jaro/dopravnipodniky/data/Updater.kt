package cz.jaro.dopravnipodniky.data

import android.util.Log
import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Bus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DPInfo
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.StavZastavky
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Trakce
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Zastavka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.busy
import cz.jaro.dopravnipodniky.data.dopravnipodnik.delkaLinky
import cz.jaro.dopravnipodniky.data.dopravnipodnik.jsouVsechnyZatrolejovane
import cz.jaro.dopravnipodniky.data.dopravnipodnik.kapacitaZastavky
import cz.jaro.dopravnipodniky.data.dopravnipodnik.linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.maZastavku
import cz.jaro.dopravnipodniky.data.dopravnipodnik.pocetLinek
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ulice
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlovac
import cz.jaro.dopravnipodniky.shared.Smer
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.Text
import cz.jaro.dopravnipodniky.shared.delkaUlice
import cz.jaro.dopravnipodniky.shared.delkaZastavky
import cz.jaro.dopravnipodniky.shared.dobaPobytuNaZastavce
import cz.jaro.dopravnipodniky.shared.formatovat
import cz.jaro.dopravnipodniky.shared.hezkaCisla
import cz.jaro.dopravnipodniky.shared.idealniInterval
import cz.jaro.dopravnipodniky.shared.je
import cz.jaro.dopravnipodniky.shared.jednotky.PenizZaMinutu
import cz.jaro.dopravnipodniky.shared.jednotky.Tik
import cz.jaro.dopravnipodniky.shared.jednotky.div
import cz.jaro.dopravnipodniky.shared.jednotky.dp
import cz.jaro.dopravnipodniky.shared.jednotky.penez
import cz.jaro.dopravnipodniky.shared.jednotky.penezZaMin
import cz.jaro.dopravnipodniky.shared.jednotky.tiku
import cz.jaro.dopravnipodniky.shared.jednotky.times
import cz.jaro.dopravnipodniky.shared.jednotky.toDp
import cz.jaro.dopravnipodniky.shared.jednotky.toDuration
import cz.jaro.dopravnipodniky.shared.millisPerTik
import cz.jaro.dopravnipodniky.shared.nahodnostProjetiZastavky
import cz.jaro.dopravnipodniky.shared.nasobitelZisku
import cz.jaro.dopravnipodniky.shared.odsazeniBaraku
import cz.jaro.dopravnipodniky.shared.times
import cz.jaro.dopravnipodniky.shared.udrzbaTroleje
import cz.jaro.dopravnipodniky.shared.udrzbaZastavky
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

@Single(createdAtStart = true)
class Updater(
    hodiny: Hodiny,
    dataSource: PreferencesDataSource,
    dosahlovac: Dosahlovac,
) {
    init {
        update(
            hodiny,
            dataSource,
            dosahlovac
        )
    }
}

private fun update(
    hodiny: Hodiny,
    dataSource: PreferencesDataSource,
    dosahlovac: Dosahlovac,
) {
    hodiny.registerListener(10.seconds) { tik ->
//            Log.i("sekání", "tik: ${tik.hezky()}, čas: ${System.currentTimeMillis().hezky()}; Začátek")

        /*
        // zistovani jestli nejses moc dlouho pryc

        val pocetSekundOdPoslednihoHrani = (Calendar.getInstance().toInstant().toEpochMilli() - dp.cas) / 1000 // s

        if (pocetSekundOdPoslednihoHrani < 0) {.size}
            dosahni("citer", binding.toolbar)
        }

        if (pocetSekundOdPoslednihoHrani > 10) {

            val puvodniTiky = pocetSekundOdPoslednihoHrani * TPS
            val pocetTiku = if (puvodniTiky > TPH * 8L) TPH * 8L else puvodniTiky

            for (bus in dp.busy) {
                bus.najeto += puvodniTiky * (1.0 / TPH)
            }

            vse.prachy += dp.zisk * nasobitelZiskuPoOffline * pocetTiku / TPM

            MaterialAlertDialogBuilder(this).apply {
                setIcon(R.drawable.ic_baseline_attach_money_24)
//            setTheme(R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered)
                setTitle(R.string.slovo_vyuctovani)
                setMessage(
                    getString(
                        R.string.vyuctovani,
                        if (puvodniTiky / TPH < 2)
                            resources.getQuantityString(
                                R.plurals.min,
                                (pocetTiku / TPM).toInt(),
                                pocetTiku / TPM
                            )
                        else
                            resources.getQuantityString(
                                R.plurals.hod,
                                (puvodniTiky / TPH).toInt(),
                                puvodniTiky / TPH
                            ),
                        (dp.zisk * nasobitelZiskuPoOffline * pocetTiku / TPM).roundToLong()
                            .formatovat(),
                        (dp.zisk).roundToLong().formatovat(),
                        (pocetTiku / TPM).toString(),
                        nasobitelZiskuPoOffline.toString(),
                        (dp.zisk * nasobitelZiskuPoOffline * pocetTiku / TPM).roundToLong()
                            .formatovat(),
                        if (puvodniTiky != pocetTiku)
                            getString(R.string.bohuzel_dlouho_neaktivni)
                        else ""
                    )
                )
                show()
            }
        }*/

        dataSource.upravitBusy {
            forEachIndexed { i, bus ->
                this[i] = bus.copy(
                    najeto = bus.najeto + 10.seconds
                )
            }
        }
    }


    hodiny.registerListener(1.tiku) { tik ->
//            Log.i("sekání", "tik: ${tik.hezky()}, čas: ${System.currentTimeMillis().hezky()}; Začátek")

        val puvodniDp = dataSource.dp.first()

        dataSource.upravitBusy {
//                    Log.i("sekání", "tik: ${tik.hezky()}, čas: ${System.currentTimeMillis().hezky()}; Začátek posouvání busů")
            forEachIndexed { i, bus ->
                if (bus.linka == null) return@upravitBusy
//                        Log.i("sekání", "tik: ${tik.hezky()}, čas: ${System.currentTimeMillis().hezky()}; Začátek posouvání busu ${bus.evCislo}")

                val linka = puvodniDp.linky.linka(bus.linka)
                val ulicove = linka.ulice(puvodniDp.ulice)

                if (bus.typBusu.trakce is Trakce.Trolejbus && !ulicove.jsouVsechnyZatrolejovane()) return@upravitBusy

                var smerNaLince = bus.smerNaLince
                var poziceNaLince = bus.poziceNaLince
                var poziceVUlici = bus.poziceVUlici
                var stavZastavky = bus.stavZastavky

                val indexUliceNaLince = when (smerNaLince) {
                    Smer.Pozitivni -> poziceNaLince
                    Smer.Negativni -> linka.ulice.lastIndex - poziceNaLince
                }
                val ulice = ulicove[indexUliceNaLince]

                if (bus.stavZastavky !is StavZastavky.Na) {
                    // posouvani busu po mape

                    poziceVUlici += bus.typBusu.rychlost * 1.tiku.toDuration()

                    if (poziceVUlici >= delkaUlice) {  // odjel mimo ulici
                        poziceVUlici = 0.dp
                        stavZastavky = StavZastavky.Pred
                        poziceNaLince += 1

                        if (poziceNaLince >= linka.ulice.size) { // dojel na konec linky
                            poziceNaLince = 0
                            smerNaLince *= Smer.Negativni
                        }
                    }
                }

                if (ulice.zastavka != null) {

                    if (stavZastavky is StavZastavky.Na) {

                        stavZastavky = stavZastavky.copy(doba = stavZastavky.doba + 1.tiku)

                        if (stavZastavky.doba >= dobaPobytuNaZastavce) {

                            stavZastavky = StavZastavky.Po

                            launch(Dispatchers.IO) {
                                var cloveciVUlici = ulice.cloveci
                                var cloveciNaZastavce = ulice.zastavka.cloveci
                                var cloveci = bus.cloveci

                                val vystupujici = ziskatPocetVystupujicich(
                                    ulicove = ulicove,
                                    ulice = ulice,
                                    poziceNaLince = poziceNaLince,
                                    indexUliceNaLince = indexUliceNaLince,
                                    linky = puvodniDp.linky,
                                    cloveciNaZastavce = cloveciNaZastavce,
                                    cloveci = cloveci,
                                )

                                cloveci -= vystupujici
                                cloveciNaZastavce += vystupujici

                                val nastupujici = bus.ziskatPocetNastupujicich(
                                    ulicove = ulicove,
                                    ulice = ulice,
                                    indexUliceNaLince = indexUliceNaLince,
                                    linky = puvodniDp.linky,
                                    linka = linka,
                                    busy = this@upravitBusy,
                                    cloveciNaZastavce = cloveciNaZastavce,
                                    dpInfo = puvodniDp.info,
                                    cloveci = cloveci,
                                    poziceNaLince = poziceNaLince,
                                )

                                cloveci += nastupujici
                                cloveciNaZastavce -= nastupujici

                                if (cloveciNaZastavce > ulice.kapacitaZastavky())
                                    cloveciVUlici +=
                                        (cloveciNaZastavce - ulice.kapacitaZastavky()).coerceAtMost(ulice.kapacita - ulice.cloveci)

                                Log.i(
                                    "Přesunuti lidé",
                                    "Nastoupilo $nastupujici lidí a vystoupilo $vystupujici lidí."
                                )

                                dataSource.upravitPrachy {
                                    it + puvodniDp.info.jizdne * nastupujici * nasobitelZisku
                                }
                                dataSource.upravitBusy {
                                    this[i] = this[i].copy(
                                        cloveci = cloveci,
                                    )
                                }
                                dataSource.upravitUlice {
                                    val indexUlice = indexOfFirst { it.id == ulice.id }
                                    this[indexUlice] = ulice.copy(
                                        zastavka = Zastavka(cloveciNaZastavce),
                                        cloveci = cloveciVUlici
                                    )
                                }
                            }
                        }
                    }

                    // zapocitani projeti zastavky

                    if (
                        stavZastavky == StavZastavky.Pred &&
                        poziceVUlici >= (delkaUlice + delkaZastavky) / 2 - bus.typBusu.delka.toDp() - odsazeniBaraku
                    ) {
                        stavZastavky = StavZastavky.Na(
                            if (Random.nextInt(0, nahodnostProjetiZastavky) == 0) {
                                dosahlovac.dosahni(Dosahlost.ProjetZastavku::class)
                                dobaPobytuNaZastavce
                            } else 0.tiku
                        )

                        launch(Dispatchers.IO) {
                            var cloveciVUlici = ulice.cloveci
                            var cloveciNaZastavce = ulice.zastavka.cloveci

                            dosahlovac.dosahni(Dosahlost.BusNaZastavce::class)

                            // cloveci musi jit domu a na zastavku a taky na záchod

                            //        println(-cloveciNaZastavce / 4)
                            //        println(cloveciVUlici / 4 + 1)
                            //        println(cloveciVUlici - ulice.kapacita)
                            //        println(ulice.zastavka.kapacita(ulice) - cloveciNaZastavce)

                            val prebytekNaZastavce = cloveciNaZastavce - ulice.kapacitaZastavky()
                            val prebytekDoma = cloveciVUlici - ulice.kapacita

                            val volnoNaZastavce = ulice.kapacitaZastavky() - cloveciNaZastavce
                            val volnoDoma = ulice.kapacita - cloveciVUlici

                            val lidiCoJdouNaZastavku = Random.nextInt(
                                from = -cloveciNaZastavce / 4,
                                until = cloveciVUlici / 4 + 1,
                            )
                                .coerceAtMost(-prebytekNaZastavce)
                                .coerceAtLeast(prebytekDoma)
                                .coerceAtMost(volnoNaZastavce)
                                .coerceAtLeast(-volnoDoma)

                            cloveciVUlici -= lidiCoJdouNaZastavku
                            cloveciNaZastavce += lidiCoJdouNaZastavku

                            //                                        Log.i(
                            //                                            "Přesunuti lidé",
                            //                                            (if (lidiCoJdouZDomu >= 0) "Na zastávku na ulici přišlo" else "Ze zastávky odešlo") + " ${lidiCoJdouZDomu.absoluteValue} lidí."
                            //                                        )

                            dataSource.upravitUlice {
                                val indexUlice = indexOfFirst { it.id == ulice.id }
                                this[indexUlice] = ulice.copy(
                                    cloveci = cloveciVUlici,
                                    zastavka = Zastavka(cloveciNaZastavce)
                                )
                            }
                        }
                    }
                }

                this[i] = bus.copy(
                    poziceVUlici = poziceVUlici,
                    poziceNaLince = poziceNaLince,
                    smerNaLince = smerNaLince,
                    stavZastavky = stavZastavky,
                )
//                    Log.i("sekání", "tik: ${tik.hezky()}, čas: ${System.currentTimeMillis().hezky()}; Konec posouvání busů")
            }
        }
    }


    hodiny.registerListener(10.seconds) { tik ->
//            Log.i("sekání", "tik: ${tik.hezky()}, čas: ${System.currentTimeMillis().hezky()}; Začátek")

        val puvodniDp = dataSource.dp.first()
        val puvodniVse = dataSource.vse.first()

        var zisk = 0.0.penezZaMin
        var deltaPrachy = 0.0.penez

        puvodniDp.busy.forEach { bus ->

            // pocitani zisku
            zisk -= bus.naklady
            zisk += bus.vydelkuj(puvodniDp)

            // odebrani penez za naklady + starnuti busuu
            deltaPrachy -= bus.naklady * 10.seconds

        }

        // infrastruktura

        val zaZastavky = udrzbaZastavky * puvodniDp.ulice.count { it.maZastavku }
        val zaTroleje = udrzbaTroleje * puvodniDp.ulice.count { it.maTrolej }

//                println(zaZastavky)
//                println(zaTroleje)

        deltaPrachy -= zaZastavky * 10.seconds - zaTroleje * 10.seconds
        zisk -= (zaZastavky + zaTroleje)

        // dosahlosti

        dosahlovac.dosahniPocetniDosahlost(
            Dosahlost.SkupinovaDosahlost.Penize::class,
            dataSource.vse.first().prachy.value.roundToInt(),
        )

        // tutorial

        dataSource.upravitTutorial {
            if (it je StavTutorialu.Tutorialujeme.Vypraveni && puvodniVse.prachy + deltaPrachy >= 1_500_000.penez)
                StavTutorialu.Tutorialujeme.NovejDp
            else it
        }

//                 mimoradnosti
//
//                if (nextInt(0, nahodnostKamionu) == 1 && dp.ulicove.any { it.maTrolej }) {
//                    MaterialAlertDialogBuilder(this).apply {
//                        setTitle(R.string.kamion)
//                        setCancelable(false)
//                        setMessage(getString(R.string.kamion_prijel))
//                        setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.cancel() }
//                        show()
//                    }
//
//                    dp.ulicove.forEach { it.maTrolej = false }
//                }
//
//                if (dp.busy.size != 0) {
//
//                    val bus = dp.busy.maxByOrNull { it.ponicenost }!!
//
//                    if (bus.ponicenost > .0) {
//
//                        if (nextInt(0, (10 * TPM / bus.ponicenost).roundToInt()) == 0) {
//
//                            vse.prachy -= bus.typBusu.cena - bus.prodejniCena
//
//                            MaterialAlertDialogBuilder(this).apply {
//                                setTitle(getString(R.string.porouchany, bus.typBusu.trakce))
//                                setCancelable(false)
//                                setMessage(getString(R.string.vas_bus_se_porouchal, bus.typBusu.trakce, bus.evCislo))
//                                setPositiveButton(
//                                    getString(
//                                        R.string.zaplatit,
//                                        (bus.typBusu.cena - bus.prodejniCena).formatovat()
//                                    )
//                                ) { dialog, _ -> dialog.cancel() }
//                                show()
//                            }
//                        }
//                    }
//                }
//
//                 umírání
//
//                if (dp.cloveci != 0) if (nextInt(0, nahodnostSebevrazdy) == 0) {
//                    val clovek = Clovek()
//                    clovek.sebevrazda(this)
//                    Log.i("Sebevražda", "${clovek.jmeno} ve ${clovek.vek.roundToInt()} letech")
//                }
//                if (dp.cloveci != 0) if (nextInt(0, dobaVymreni / dp.cloveci + 1) == 0) {
//                    val clovek = Clovek()
//                    clovek.smrt(this)
//                    Log.i("Zemřel člověk", "${clovek.jmeno} zemřel ve ${clovek.vek.roundToInt()} letech")
//                }
//
        // rození

//                if (nextInt(0, dobaZnovuobnoveniPopulace / dp.cloveci + 1) == 0 && dp.baraky.any { it.kapacita != it.cloveci }) {
//                    val barak = dp.baraky.filter { it.kapacita != it.cloveci }.random()
//                    barak.cloveci++
//                    val clovek = Clovek()
//                    clovek.pojmenuj(this)
//                    Log.i("Narodil se člověk", clovek.jmeno)
//                }
//
        dataSource.upravitPrachy {
            println(it + deltaPrachy)
            it + deltaPrachy
        }
        dataSource.upravitDPInfo { dpInfo ->
            dpInfo.copy(
                casPosledniNavstevy = System.currentTimeMillis(),
                zisk = zisk.also {
                    zisky += zisk
//                    println(zisk)
                    println(zisky)
                    println(zisky.map { it.value }.average().penezZaMin)
                }
            )
        }
    }
}

var zisky = listOf<PenizZaMinutu>()

private fun ziskatPocetVystupujicich(
    ulicove: List<Ulice>,
    ulice: Ulice,
    poziceNaLince: Int,
    indexUliceNaLince: Int,
    linky: List<Linka>,
    cloveciNaZastavce: Int,
    cloveci: Int,
) = if (poziceNaLince == ulicove.indexOfLast { it.maZastavku }) cloveci
else {
    val zbyvajiciKapacitaZastavky = ulice.kapacitaZastavky() - cloveciNaZastavce
    val minuleUlice = ulicove.subList(0, indexUliceNaLince)
    val pocetLinekVUlici = ulice.pocetLinek(linky)

    val maximumLidiCoMuzeVystoupit = min(cloveci, zbyvajiciKapacitaZastavky)

    /**
     * @see <a href="https://www.desmos.com/calculator/7n8wgwxdle">Desmos</a>
     */
    val nasobitelKapacity = 1.25 + (ulice.kapacita - 470.0).pow(3) / 50000000
    val nasobitelPristichZastavek = .05 * minuleUlice.count { it.maZastavku }

    /**
     * @see <a href="https://www.desmos.com/calculator/ld49gzvioo">Desmos</a>
     */
    val nasobitelPoctuLinek = 2.0.pow((pocetLinekVUlici - 1) / 2.0) - 1

    val nasobitel = 1 + nasobitelKapacity + nasobitelPristichZastavek + nasobitelPoctuLinek

    Random.nextInt(0, cloveci + 1)
        .times(nasobitel)
        .roundToInt()
        .coerceAtLeast(0)
        .coerceAtMost(maximumLidiCoMuzeVystoupit)
}

private fun Bus.ziskatPocetNastupujicich(
    ulicove: List<Ulice>,
    ulice: Ulice,
    indexUliceNaLince: Int,
    linky: List<Linka>,
    linka: Linka,
    busy: List<Bus>,
    cloveciNaZastavce: Int,
    cloveci: Int,
    dpInfo: DPInfo,
    poziceNaLince: Int,
) = if (poziceNaLince == ulicove.indexOfLast { it.maZastavku }) 0
else if (ulice.kapacitaZastavky() == 0) 0
else {
    val zbyvajiciKapacitaBusu = typBusu.kapacita - cloveci
    val zbyvajiciUlice = ulicove.subList(indexUliceNaLince + 1, ulicove.size)
    val pocetLinekVUlici = ulice.pocetLinek(linky)
    val interval = linka.ulice.size * 2 / linka.busy(busy).size

    val maximumLidiCoChtejiNastoupit =
        cloveciNaZastavce / pocetLinekVUlici + Random.nextInt(-5, 5)
    val minimumLidiCoMusiNastoupit =
        if (cloveciNaZastavce > ulice.kapacitaZastavky()) cloveciNaZastavce - ulice.kapacitaZastavky()
        else 0
    val maximumLidiCoMuzeNastoupit = min(zbyvajiciKapacitaBusu, cloveciNaZastavce)

    val nasobitelPristichZastavek = .1 * zbyvajiciUlice.count { it.maZastavku }
    val nasobitelHezkehoCisla = if (evCislo in hezkaCisla) 1 - Math.PI / Math.E else .0
    val nasobitelStari = .7 - ponicenost

    /**
     * @see <a href="https://www.desmos.com/calculator/6qyvoticme">Desmos</a>
     */
    val nasobitelJizdneho = 1 - dpInfo.jizdne.value / 20.0

    /**
     * @see <a href="https://www.desmos.com/calculator/v6hoawdstb">Desmos</a>
     */
    val nasobitelIntervalu =
        (0.5 - (interval - idealniInterval).pow(2) / idealniInterval.pow(2)).coerceAtLeast(-.25)

    val nasobitel =
        1 + nasobitelPristichZastavek + nasobitelHezkehoCisla + nasobitelStari + nasobitelJizdneho + nasobitelIntervalu

    Random.nextInt(0, maximumLidiCoChtejiNastoupit.coerceAtLeast(1))
        .times(nasobitel)
        .roundToInt()
        .coerceAtLeast(minimumLidiCoMusiNastoupit)
        .coerceAtMost(maximumLidiCoMuzeNastoupit)
}

fun Bus.vydelkuj(
    puvodniDp: DopravniPodnik
): PenizZaMinutu {
    if (linka == null) return 0.penezZaMin

    val linka = puvodniDp.linky.linka(linka)
    val ulicove = linka.ulice(puvodniDp.ulice)

    if (typBusu.trakce is Trakce.Trolejbus && !ulicove.jsouVsechnyZatrolejovane()) return 0.penezZaMin

    var cloveci = 0

    val nastupujicichZaJizdu = ulicove
        .filter {
            it.maZastavku
        }
        .sumOfIndexed { indexUliceNaLince, ulice ->

            val vystupujici = ziskatPocetVystupujicich(
                ulicove = ulicove,
                ulice = ulice,
                poziceNaLince = indexUliceNaLince,
                indexUliceNaLince = indexUliceNaLince,
                linky = puvodniDp.linky,
                cloveciNaZastavce = (ulice.kapacitaZastavky() * .8).roundToInt(),
                cloveci = cloveci,
            )

            cloveci -= vystupujici

            val nastupujici = ziskatPocetNastupujicich(
                ulicove = ulicove,
                ulice = ulice,
                indexUliceNaLince = indexUliceNaLince,
                linky = puvodniDp.linky,
                linka = linka,
                busy = puvodniDp.busy,
                cloveciNaZastavce = (ulice.kapacitaZastavky() * .8).roundToInt(),
                dpInfo = puvodniDp.info,
                cloveci = cloveci,
                poziceNaLince = indexUliceNaLince
            )

            cloveci += nastupujici

            nastupujici
        }

    val nastupujicichZaJizduZpet = ulicove
        .filter {
            it.maZastavku
        }
        .reversed()
        .sumOfIndexed { indexUliceNaLince, ulice ->

            val vystupujici = ziskatPocetVystupujicich(
                ulicove = ulicove,
                ulice = ulice,
                poziceNaLince = ulicove.lastIndex - indexUliceNaLince,
                indexUliceNaLince = indexUliceNaLince,
                linky = puvodniDp.linky,
                cloveciNaZastavce = (ulice.kapacitaZastavky() * .8).roundToInt(),
                cloveci = cloveci,
            )

            cloveci -= vystupujici

            val nastupujici = ziskatPocetNastupujicich(
                ulicove = ulicove,
                ulice = ulice,
                indexUliceNaLince = indexUliceNaLince,
                linky = puvodniDp.linky,
                linka = linka,
                busy = puvodniDp.busy,
                cloveciNaZastavce = (ulice.kapacitaZastavky() * .8).roundToInt(),
                dpInfo = puvodniDp.info,
                cloveci = cloveci,
                poziceNaLince = ulicove.lastIndex - indexUliceNaLince
            )

            cloveci += nastupujici

            nastupujici
        }

    val nastupujicichZaKolo = nastupujicichZaJizdu + nastupujicichZaJizduZpet

    val ziskZaKolo = puvodniDp.info.jizdne * nastupujicichZaKolo * nasobitelZisku

    val dobaKola = linka.delkaLinky.dp * 2 / typBusu.rychlost

    return ziskZaKolo / dobaKola
}

private fun Long.hezky() = (div(1_000.0).minus(div(1_000_000).times(1_000)).formatovat() as Text.Plain).value

private fun Tik.hezky() = ((System.currentTimeMillis() / millisPerTik - value).formatovat() as Text.Plain).value

inline fun <T> Iterable<T>.sumOfIndexed(selector: (Int, T) -> Int): Int {
    var index = 0
    var sum = 0
    for (element in this) {
        sum += selector(index++, element)
    }
    return sum
}