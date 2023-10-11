package cz.jaro.dopravnipodniky.data

import android.util.Log
import androidx.compose.ui.unit.times
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Bus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DPInfo
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.StavZastavky
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Trakce
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Zastavka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.bonusoveVydajeZaNeekologicnost
import cz.jaro.dopravnipodniky.data.dopravnipodnik.busy
import cz.jaro.dopravnipodniky.data.dopravnipodnik.jsouVsechnyZatrolejovane
import cz.jaro.dopravnipodniky.data.dopravnipodnik.kapacitaZastavky
import cz.jaro.dopravnipodniky.data.dopravnipodnik.linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.maZastavku
import cz.jaro.dopravnipodniky.data.dopravnipodnik.pocetLinek
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ulice
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlovac
import cz.jaro.dopravnipodniky.shared.BusID
import cz.jaro.dopravnipodniky.shared.Orientace
import cz.jaro.dopravnipodniky.shared.Smer
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.Text
import cz.jaro.dopravnipodniky.shared.TypKrizovatky
import cz.jaro.dopravnipodniky.shared.delkaUlice
import cz.jaro.dopravnipodniky.shared.delkaZastavky
import cz.jaro.dopravnipodniky.shared.dobaPobytuNaZastavce
import cz.jaro.dopravnipodniky.shared.hezkaCisla
import cz.jaro.dopravnipodniky.shared.idealniInterval
import cz.jaro.dopravnipodniky.shared.je
import cz.jaro.dopravnipodniky.shared.jednotky.PenizZaMinutu
import cz.jaro.dopravnipodniky.shared.jednotky.coerceAtMost
import cz.jaro.dopravnipodniky.shared.jednotky.div
import cz.jaro.dopravnipodniky.shared.jednotky.formatovat
import cz.jaro.dopravnipodniky.shared.jednotky.formatovatBezEura
import cz.jaro.dopravnipodniky.shared.jednotky.kilometruZaHodinu
import cz.jaro.dopravnipodniky.shared.jednotky.penez
import cz.jaro.dopravnipodniky.shared.jednotky.penezZaMin
import cz.jaro.dopravnipodniky.shared.jednotky.sumOfPenizZaminutu
import cz.jaro.dopravnipodniky.shared.jednotky.tiku
import cz.jaro.dopravnipodniky.shared.jednotky.times
import cz.jaro.dopravnipodniky.shared.jednotky.toDp
import cz.jaro.dopravnipodniky.shared.jednotky.toDuration
import cz.jaro.dopravnipodniky.shared.nahodnostProjetiZastavky
import cz.jaro.dopravnipodniky.shared.nasobitelZisku
import cz.jaro.dopravnipodniky.shared.odsazeniBusu
import cz.jaro.dopravnipodniky.shared.odsazeniZastavky
import cz.jaro.dopravnipodniky.shared.predsazeniKrizovatky
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.shared.sumOfDp
import cz.jaro.dopravnipodniky.shared.times
import cz.jaro.dopravnipodniky.shared.toText
import cz.jaro.dopravnipodniky.shared.udrzbaTroleje
import cz.jaro.dopravnipodniky.shared.udrzbaZastavky
import cz.jaro.dopravnipodniky.shared.vecne
import cz.jaro.dopravnipodniky.shared.vecneLinky
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
    hodiny.registerListener(1.seconds) { ubehlo ->

        dataSource.upravitBusy {
            forEachIndexed { i, bus ->
                this[i] = bus.copy(
                    najeto = bus.najeto + ubehlo
                )
            }
        }
    }


    hodiny.registerListener(1.tiku) { ubehlo ->

        val puvodniDp = dataSource.dp.first()

        dataSource.upravitBusy {
            forEachIndexed { i, bus ->
                if (bus.linka == null) return@upravitBusy

                val linka = puvodniDp.linka(bus.linka)
                val ulicove = linka.ulice(puvodniDp)

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

                    poziceVUlici += bus.typBusu.maxRychlost.coerceAtMost(50.kilometruZaHodinu) * ubehlo

                    val pristiUlice = when (smerNaLince) {
                        Smer.Pozitivni -> ulicove.getOrNull(indexUliceNaLince + 1)
                        Smer.Negativni -> ulicove.getOrNull(indexUliceNaLince - 1)
                    }
                    val krizovatka = when {
                        pristiUlice == null -> TypKrizovatky.Otocka
                        pristiUlice.orientace == ulice.orientace -> TypKrizovatky.Rovne
                        else -> {
                            val vpravoSvisle = when {
                                ulice.zacatek == pristiUlice.konec -> false
                                ulice.zacatek == pristiUlice.zacatek -> true
                                ulice.konec == pristiUlice.konec -> true
                                ulice.konec == pristiUlice.zacatek -> false
                                else -> throw IllegalStateException("WTF")
                            }

                            val vpravo = if (ulice.orientace == Orientace.Svisle) vpravoSvisle else !vpravoSvisle
                            if (vpravo) TypKrizovatky.Vpravo else TypKrizovatky.Vlevo
                        }
                    }
                    val delkaKrizovatky = when (krizovatka) {
                        TypKrizovatky.Otocka -> {
                            val r = sirkaUlice / 2 - odsazeniBusu - bus.typBusu.sirka.toDp() / 2
                            val theta = Math.PI
                            predsazeniKrizovatky + theta * r + predsazeniKrizovatky
                        }

                        TypKrizovatky.Rovne -> predsazeniKrizovatky + sirkaUlice + predsazeniKrizovatky
                        TypKrizovatky.Vpravo -> {
                            val r = predsazeniKrizovatky + odsazeniBusu + bus.typBusu.sirka.toDp() / 2
                            val theta = Math.PI / 2
                            theta * r
                        }

                        TypKrizovatky.Vlevo -> {
                            val r = predsazeniKrizovatky + sirkaUlice - odsazeniBusu - bus.typBusu.sirka.toDp() / 2
                            val theta = Math.PI / 2
                            theta * r
                        }
                    }

                    if (poziceVUlici >= (delkaUlice - predsazeniKrizovatky) + delkaKrizovatky) {  // odjel mimo ulici
                        poziceVUlici = predsazeniKrizovatky
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

                        stavZastavky = stavZastavky.copy(doba = stavZastavky.doba + ubehlo)

                        if (stavZastavky.doba >= dobaPobytuNaZastavce.toDuration()) {

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
                        poziceVUlici >= (delkaUlice + delkaZastavky) / 2 - bus.typBusu.delka.toDp() - odsazeniZastavky
                    ) {
                        stavZastavky = StavZastavky.Na(
                            if (Random.nextInt(0, nahodnostProjetiZastavky) == 0) {
                                dosahlovac.dosahni(Dosahlost.ProjetZastavku::class)
                                dobaPobytuNaZastavce.toDuration()
                            } else 0.seconds
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


    hodiny.registerListener(1.seconds) { ubehlo ->

        val puvodniDp = dataSource.dp.first()
        val puvodniVse = dataSource.vse.first()

        var zisk = 0.0.penezZaMin
        var deltaPrachy = 0.0.penez

        val vydelky = puvodniDp.busy.associate { it.id to it.vydelkuj(puvodniDp) }

        val casti = casti(puvodniDp, vydelky)

        puvodniDp.busy.forEach { bus ->

            // pocitani zisku
            zisk -= bus.naklady
            zisk += vydelky[bus.id]!!

            // odebrani penez za naklady + starnuti busuu
            deltaPrachy -= bus.naklady * ubehlo
        }

        // infrastruktura

        val zaZastavky = udrzbaZastavky * puvodniDp.ulice.count { it.maZastavku }
        val zaTroleje = udrzbaTroleje * puvodniDp.ulice.count { it.maTrolej }

//                println(zaZastavky)
//                println(zaTroleje)

        deltaPrachy -= (zaZastavky * ubehlo + zaTroleje * ubehlo)
        zisk -= (zaZastavky + zaTroleje)

        // dosahlosti

        dosahlovac.dosahniPocetniDosahlost(
            Dosahlost.SkupinovaDosahlost.Penize::class,
            dataSource.vse.first().prachy.plus(deltaPrachy).value.roundToInt(),
        )

        dosahlovac.dosahniPocetniDosahlost(
            Dosahlost.SkupinovaDosahlost.Bus::class,
            dataSource.dp.first().busy.size,
        )

        if (dataSource.dp.first().busy.any { it.linka != null }) dosahlovac.dosahni(Dosahlost.BusNaLince::class)

        if (puvodniDp.info.jmenoMesta == vecne) {
            dosahlovac.dosahni(Dosahlost.Vecne1::class)
            if (puvodniDp.linky.map { it.cislo.toIntOrNull() }.toSet() == vecneLinky)
                dosahlovac.dosahni(Dosahlost.Vecne2::class)
        }

        // tutorial

        dataSource.upravitTutorial {
            if (it je StavTutorialu.Tutorialujeme.Vypraveni && puvodniVse.prachy + deltaPrachy >= 1_500_000.penez)
                StavTutorialu.Tutorialujeme.NovejDp
            else it
        }

        val detail = Text.Mix(*casti.toTypedArray())
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
//            println(it + deltaPrachy)
            it + deltaPrachy
        }
        dataSource.upravitDPInfo { dpInfo ->
            dpInfo.copy(
//                casPosledniNavstevy = System.currentTimeMillis(),
                zisk = zisk.also {
                    zisky += zisk
//                    println("UPDATE")
//                    println(zisk)
//                    println(zisky)
//                    println(zisky.map { it.value }.average().penezZaMin)
                },
                detailZisku = detail
            )
        }
    }
}

private fun casti(
    puvodniDp: DopravniPodnik,
    vydelky: Map<BusID, PenizZaMinutu>
): List<Text> {
    val vydajeZaZastavky = puvodniDp.ulice.count { it.maZastavku } * udrzbaZastavky
    val vydajeZaTroleje = puvodniDp.ulice.count { it.maTrolej } * udrzbaTroleje
    val vydajeZaInfrastrukturu = vydajeZaZastavky + vydajeZaTroleje

    return buildList {
        this += listOf(
            R.string.celkove_prijmy.toText(puvodniDp.busy.sumOfPenizZaminutu { vydelky[it.id]!! }.formatovat()),
            "\n".toText(),
            R.string.celkove_vydaje.toText((puvodniDp.busy.sumOfPenizZaminutu { it.naklady } + vydajeZaInfrastrukturu).formatovat()),
            "\n".toText(),
            R.string.celkovy_zisk.toText(puvodniDp.info.zisk.formatovat()),
            "\n".toText(),
            "\n".toText(),
            R.string.vydaje_za_infrastrukturu.toText(vydajeZaInfrastrukturu.formatovat()),
            "\n".toText(),
            R.string.vydaje_za_zastavky.toText(vydajeZaZastavky.formatovat()),
            "\n".toText(),
            R.string.vydaje_za_troleje.toText(vydajeZaTroleje.formatovat()),
            "\n".toText(),
            "\n".toText(),
            R.string.vydaje_za_ekologii.toText(),
            "\n".toText(),
            R.string.vydaje_za_neekologicke_vozy.toText(puvodniDp.busy.sumOfPenizZaminutu { it.typBusu.trakce.bonusoveVydajeZaNeekologicnost() }
                .formatovat()),
            "\n".toText(),
            "\n".toText(),
            R.string.zisk_linek.toText(),
            "\n".toText(),
        )

        this += puvodniDp.linky.flatMap { linka ->
            listOf(
                R.string.linka_vydelava_tohle.toText(
                    linka.cislo.toText(),
                    linka.busy(puvodniDp).sumOfPenizZaminutu { vydelky[it.id]!! - it.naklady }.formatovat()
                ),
                "\n".toText(),
            )
        }
        if (puvodniDp.linky.isEmpty()) this += listOf(
            R.string.nemate_zadnou_linku.toText(),
            "\n".toText(),
        )

        this += listOf(
            "\n".toText(),
            R.string.zisk_vozidel.toText(),
            "\n".toText(),
        )

        this += puvodniDp.busy.flatMap { bus ->
            listOf(
                if (bus.linka == null) {
                    R.string.bus_prodelava_tolik.toText(
                        bus.evCislo.toString().toText(),
                        bus.naklady.formatovat()
                    )
                } else {
                    R.string.bus_vydelava_tolik.toText(
                        bus.evCislo.toString().toText(),
                        vydelky[bus.id]!!.formatovatBezEura(),
                        bus.naklady.formatovatBezEura(),
                        (vydelky[bus.id]!! - bus.naklady).formatovat()
                    )
                },
                "\n".toText(),
            )
        }
        if (puvodniDp.busy.isEmpty()) this += listOf(
            R.string.nemate_zadny_bus.toText(),
            "\n".toText(),
        )
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
    nextInt: (Int, Int) -> Int = Random.Default::nextInt,
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

    nextInt(0, cloveci + 1)
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
    nextInt: (Int, Int) -> Int = Random.Default::nextInt,
) = if (poziceNaLince == ulicove.indexOfLast { it.maZastavku }) 0
else if (ulice.kapacitaZastavky() == 0) 0
else {
    val zbyvajiciKapacitaBusu = typBusu.kapacita - cloveci
    val zbyvajiciUlice = ulicove.subList(indexUliceNaLince + 1, ulicove.size)
    val pocetLinekVUlici = ulice.pocetLinek(linky)
    val interval = linka.ulice.size * 2 / busy.count { it.linka == linka.id }

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

    nextInt(0, maximumLidiCoChtejiNastoupit.coerceAtLeast(1))
        .times(nasobitel)
        .roundToInt()
        .coerceAtLeast(minimumLidiCoMusiNastoupit)
        .coerceAtMost(maximumLidiCoMuzeNastoupit)
}

fun Bus.vydelkuj(
    puvodniDp: DopravniPodnik
): PenizZaMinutu {
    if (linka == null) return 0.penezZaMin

    val linka = puvodniDp.linka(linka)
    val ulicove = linka.ulice(puvodniDp)

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
                cloveciNaZastavce = (ulice.kapacitaZastavky() * .6).roundToInt(),
                cloveci = cloveci,
                nextInt = { a, b ->
                    (a + b) / 2
                },
            )
//            println((ulice.zacatek to ulice.konec) to vystupujici)

            cloveci -= vystupujici

            val nastupujici = ziskatPocetNastupujicich(
                ulicove = ulicove,
                ulice = ulice,
                indexUliceNaLince = indexUliceNaLince,
                linky = puvodniDp.linky,
                linka = linka,
                busy = puvodniDp.busy,
                cloveciNaZastavce = (ulice.kapacitaZastavky() * .6).roundToInt(),
                dpInfo = puvodniDp.info,
                cloveci = cloveci,
                poziceNaLince = indexUliceNaLince,
                nextInt = { a, b ->
                    (a + b) / 2
                },
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
                nextInt = { a, b ->
                    (a + b) / 2
                },
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
                poziceNaLince = ulicove.lastIndex - indexUliceNaLince,
                nextInt = { a, b ->
                    (a + b) / 2
                },
            )

            cloveci += nastupujici

            nastupujici
        }

    val nastupujicichZaKolo = nastupujicichZaJizdu + nastupujicichZaJizduZpet

    val ziskZaKolo = puvodniDp.info.jizdne * nastupujicichZaKolo * nasobitelZisku

    val dobaUlic = (delkaUlice - predsazeniKrizovatky * 2) * linka.ulice.size * 2 / typBusu.maxRychlost.coerceAtMost(50.kilometruZaHodinu)

    val dobaKrizovatek = ulicove.windowed(
        2, partialWindows = true
    ).sumOfDp { dvojice ->
        val ulice = dvojice.first()
        val pristiUlice = dvojice.getOrNull(1)

        val krizovatka = when {
            pristiUlice == null -> TypKrizovatky.Otocka
            pristiUlice.orientace == ulice.orientace -> TypKrizovatky.Rovne
            else -> {
                val vpravoSvisle = when {
                    ulice.zacatek == pristiUlice.konec -> false
                    ulice.zacatek == pristiUlice.zacatek -> true
                    ulice.konec == pristiUlice.konec -> true
                    ulice.konec == pristiUlice.zacatek -> false
                    else -> throw IllegalStateException("WTF")
                }

                val vpravo = if (ulice.orientace == Orientace.Svisle) vpravoSvisle else !vpravoSvisle
                if (vpravo) TypKrizovatky.Vpravo else TypKrizovatky.Vlevo
            }
        }
        val delkaKrizovatky = when (krizovatka) {
            TypKrizovatky.Otocka -> {
                val r = sirkaUlice / 2 - odsazeniBusu - typBusu.sirka.toDp() / 2
                val theta = Math.PI
                predsazeniKrizovatky + theta * r + predsazeniKrizovatky
            }

            TypKrizovatky.Rovne -> predsazeniKrizovatky + sirkaUlice + predsazeniKrizovatky
            TypKrizovatky.Vpravo -> {
                val r = predsazeniKrizovatky + odsazeniBusu + typBusu.sirka.toDp() / 2
                val theta = Math.PI / 2
                theta * r
            }

            TypKrizovatky.Vlevo -> {
                val r = predsazeniKrizovatky + sirkaUlice - (odsazeniBusu + typBusu.sirka.toDp() / 2)
                val theta = Math.PI / 2
                theta * r
            }
        }

        delkaKrizovatky
    } * 2 / typBusu.maxRychlost.coerceAtMost(50.kilometruZaHodinu)

    val dobaKola = dobaUlic + dobaKrizovatek

    return ziskZaKolo / dobaKola
}
//
//private fun Long.hezky() = (div(1_000.0).minus(div(1_000_000).times(1_000)).formatovat() as Text.Plain).value
//
//private fun Tik.hezky() = ((System.currentTimeMillis() / millisPerTik - value).formatovat() as Text.Plain).value

inline fun <T> Iterable<T>.sumOfIndexed(selector: (Int, T) -> Int): Int {
    var index = 0
    var sum = 0
    for (element in this) {
        sum += selector(index++, element)
    }
    return sum
}