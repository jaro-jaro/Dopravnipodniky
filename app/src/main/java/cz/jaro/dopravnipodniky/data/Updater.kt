package cz.jaro.dopravnipodniky.data

import android.util.Log
import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.data.dopravnipodnik.StavZastavky
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Trakce
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Zastavka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.jsouVsechnyZatrolejovane
import cz.jaro.dopravnipodniky.data.dopravnipodnik.kapacita
import cz.jaro.dopravnipodniky.data.dopravnipodnik.maZastavku
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ulice
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlovac
import cz.jaro.dopravnipodniky.shared.BusID
import cz.jaro.dopravnipodniky.shared.Smer
import cz.jaro.dopravnipodniky.shared.delkaUlice
import cz.jaro.dopravnipodniky.shared.delkaZastavky
import cz.jaro.dopravnipodniky.shared.dobaPobytuNaZastavce
import cz.jaro.dopravnipodniky.shared.jednotky.penezZaMin
import cz.jaro.dopravnipodniky.shared.jednotky.tiku
import cz.jaro.dopravnipodniky.shared.jednotky.times
import cz.jaro.dopravnipodniky.shared.jednotky.toDp
import cz.jaro.dopravnipodniky.shared.jednotky.toDuration
import cz.jaro.dopravnipodniky.shared.mutate
import cz.jaro.dopravnipodniky.shared.nahodnostProjetiZastavky
import cz.jaro.dopravnipodniky.shared.nasobitelPoctuLidiKteryTiNastoupiDoBusuNaZastavceKdyzZastaviANakyLidiTamJsouAMaVSobeJesteVolneMisto
import cz.jaro.dopravnipodniky.shared.nasobitelZisku
import cz.jaro.dopravnipodniky.shared.odsazeniBaraku
import cz.jaro.dopravnipodniky.shared.times
import cz.jaro.dopravnipodniky.shared.udrzbaTroleje
import cz.jaro.dopravnipodniky.shared.udrzbaZastavky
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Single
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.random.Random.Default.nextInt

@Single(createdAtStart = true)
class Updater(
    hodiny: Hodiny,
    private val dataSource: PreferencesDataSource,
    private val dosahlovac: Dosahlovac,
) {
    private suspend fun busPrijelNaZastavku(busID: BusID) = dataSource.zmenitDp { dp ->

        val bus = dp.bus(busID)
        val linka = dp.linka(bus.linka!!)
        val ulicove = linka.ulice(dp)
        val ulice = ulicove[
            when (bus.smerNaLince) {
                Smer.Pozitivni -> bus.poziceNaLince
                Smer.Negativni -> linka.ulice.lastIndex - bus.poziceNaLince
            }
        ]


        var cloveciVUlici = ulice.cloveci
        var cloveciNaZastavce: Int = ulice.zastavka!!.cloveci

        dosahlovac.dosahni(Dosahlost.BusNaZastavce::class)

        // cloveci musi jit domu a na zastavku a taky na záchod

//        println(-cloveciNaZastavce / 4)
//        println(cloveciVUlici / 4 + 1)
//        println(cloveciVUlici - ulice.kapacita)
//        println(ulice.zastavka.kapacita(ulice) - cloveciNaZastavce)

        val lidiCoJdouZDomu = nextInt(
            from = -cloveciNaZastavce / 4,
            until = cloveciVUlici / 4 + 1,
        )
            .coerceAtLeast(cloveciVUlici - ulice.kapacita)
            .coerceAtMost(ulice.zastavka.kapacita(ulice) - cloveciNaZastavce)

        cloveciVUlici -= lidiCoJdouZDomu
        cloveciNaZastavce += lidiCoJdouZDomu

        Log.i(
            "Přesunuti lidé",
            (if (lidiCoJdouZDomu >= 0) "Na zastávku na ulici přišlo" else "Ze zastávky odešlo") + " ${lidiCoJdouZDomu.absoluteValue} lidí."
        )

        dp.copy(
            ulice = dp.ulice.mutate {
                val i = this.indexOfFirst { it.id == ulice.id }
                this[i] = ulice.copy(
                    zastavka = Zastavka(cloveciNaZastavce),
                    cloveci = cloveciVUlici
                )
            },
        )
    }

    private suspend fun busOdjelZeZastavky(busID: BusID) = dataSource.zmenitDp { dp ->
        val bus = dp.bus(busID)
        val linka = dp.linka(bus.linka!!)
        val ulicove = linka.ulice(dp)
        val ulice = ulicove[
            when (bus.smerNaLince) {
                Smer.Pozitivni -> bus.poziceNaLince
                Smer.Negativni -> linka.ulice.lastIndex - bus.poziceNaLince
            }
        ]

        var cloveci = bus.cloveci
        var cloveciNaZastavce: Int = ulice.zastavka!!.cloveci


        Log.i(
            "Přesunuti lidé",
            "V busu je $cloveci lidí, na zastávce $cloveciNaZastavce lidí"
        )

        val vystupujici = if (bus.poziceNaLince == linka.ulice.lastIndex) cloveci
        else nextInt(0, cloveci + 1)

        cloveci -= vystupujici
        cloveciNaZastavce += vystupujici

        Log.i(
            "Přesunuti lidé",
            "Vystoupilo $vystupujici lidí."
        )

        dataSource.zmenitVse {
            it.copy(
                prachy = it.prachy + dp.jizdne * vystupujici * nasobitelZisku
            )
        }

        val nastupujici =
            if (bus.poziceNaLince == linka.ulice.lastIndex) 0
            else if (ulice.zastavka.kapacita(ulice) == 0) 0
            else nextInt(
                from = (ulice.zastavka.cloveci - ulice.zastavka.kapacita(ulice)).coerceAtLeast(0),
                until = ulice.zastavka.cloveci + 1
            ).also(::println)
                .times(
                    nasobitelPoctuLidiKteryTiNastoupiDoBusuNaZastavceKdyzZastaviANakyLidiTamJsouAMaVSobeJesteVolneMisto(
                        dp
                    )
                ).also(::println)
                .roundToInt()
                .coerceIn(0, bus.typBusu.kapacita - cloveci)
                .coerceAtMost(ulice.zastavka.cloveci)

        cloveci += nastupujici
        cloveciNaZastavce -= nastupujici

        Log.i(
            "Přesunuti lidé",
            "Nastoupilo $nastupujici lidí."
        )

        dp.copy(
            busy = dp.busy.mutate {
                val i = indexOfFirst { it.id == bus.id }
                this[i] = bus.copy(
                    cloveci = cloveci,
                )
            },
            ulice = dp.ulice.mutate {
                val i = this.indexOfFirst { it.id == ulice.id }
                this[i] = ulice.copy(
                    zastavka = Zastavka(cloveciNaZastavce)
                )
            },
        )

//                                    if (dp.sledovanejBus == this) {
//                                        Toast.makeText(
//                                            ctx,
//                                            ctx.getString(
//                                                R.string.bus_zastavil,
//                                                evCislo.formatovat(),
//                                                vystupujici.formatovat(),
//                                                nastupujici.formatovat(),
//                                                cloveci.formatovat(),
//                                                ulice.zastavka!!.cloveci.formatovat(),
//                                                (vystupujici * dp.jizdne * nasobitelZisku).roundToInt().formatovat()
//                                            ),
//                                            Toast.LENGTH_LONG
//                                        ).show()
//                                    }
    }

    init {
        hodiny.registerListener(1.tiku) { tik ->
//            println("START $tik")
            val dp = dataSource.dp.first()

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

            // pocitani penez a vykreslovani

            var zisk = 0.0.penezZaMin

            dp.busy.forEach { bus ->

                // pocitani zisku
                zisk -= bus.naklady
                zisk += bus.vydelkuj(dp)

                // odebrani penez za naklady + starnuti busuu
                dataSource.zmenitVse {
                    it.copy(
                        prachy = it.prachy - bus.naklady * 1.tiku
                    )
                }
            }
            dataSource.zmenitDp { puvodniDp ->
                puvodniDp.busy.fold(puvodniDp) { dp, bus ->

                    var cloveciNaZastavce: Int? = null
                    var cloveciVUlici: Int? = null
                    var cloveci = bus.cloveci
                    var ulice: Ulice? = null
                    val najeto = bus.najeto + 1.tiku.toDuration()
                    var poziceVUlici = bus.poziceVUlici
                    var poziceNaLince = bus.poziceNaLince
                    var smerNaLince = bus.smerNaLince
                    var stavZastavky = bus.stavZastavky

                    if (bus.linka != null) {

                        val linka = dp.linka(bus.linka)
                        val ulicove = linka.ulice(dp)
                        ulice = ulicove[
                            when (smerNaLince) {
                                Smer.Pozitivni -> poziceNaLince
                                Smer.Negativni -> linka.ulice.lastIndex - poziceNaLince
                            }
                        ]
                        cloveciNaZastavce = ulice.zastavka?.cloveci
                        cloveciVUlici = ulice.cloveci

                        if (bus.typBusu.trakce !is Trakce.Trolejbus || ulicove.jsouVsechnyZatrolejovane()) {
                            if (stavZastavky is StavZastavky.Na) {
                                cloveciNaZastavce = ulice.zastavka!!.cloveci

                                stavZastavky = stavZastavky.copy(doba = stavZastavky.doba + 1.tiku)

                                if (stavZastavky.doba >= dobaPobytuNaZastavce) {

                                    stavZastavky = StavZastavky.Po

                                    Log.i(
                                        "Přesunuti lidé",
                                        "V busu je $cloveci lidí, na zastávce $cloveciNaZastavce lidí"
                                    )

                                    val vystupujici = if (bus.poziceNaLince == linka.ulice.lastIndex) cloveci
                                    else nextInt(0, cloveci + 1)

                                    cloveci -= vystupujici
                                    cloveciNaZastavce += vystupujici

                                    Log.i(
                                        "Přesunuti lidé",
                                        "Vystoupilo $vystupujici lidí."
                                    )

                                    dataSource.zmenitVse {
                                        it.copy(
                                            prachy = it.prachy + dp.jizdne * vystupujici * nasobitelZisku
                                        )
                                    }

                                    val nastupujici =
                                        if (bus.poziceNaLince == linka.ulice.lastIndex) 0
                                        else if (ulice.zastavka!!.kapacita(ulice) == 0) 0
                                        else nextInt(
                                            from = (ulice.zastavka!!.cloveci - ulice.zastavka!!.kapacita(ulice)).coerceAtLeast(0),
                                            until = ulice.zastavka!!.cloveci + 1
                                        ).also(::println)
                                            .times(
                                                nasobitelPoctuLidiKteryTiNastoupiDoBusuNaZastavceKdyzZastaviANakyLidiTamJsouAMaVSobeJesteVolneMisto(
                                                    dp
                                                )
                                            ).also(::println)
                                            .roundToInt()
                                            .coerceIn(0, bus.typBusu.kapacita - cloveci)
                                            .coerceAtMost(ulice.zastavka!!.cloveci)

                                    cloveci += nastupujici
                                    cloveciNaZastavce -= nastupujici

                                    Log.i(
                                        "Přesunuti lidé",
                                        "Nastoupilo $nastupujici lidí."
                                    )
                                }
                            }

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

                            // zapocitani projeti zastavky

                            if (
                                poziceVUlici >= (delkaUlice + delkaZastavky) / 2 - bus.typBusu.delka.toDp() - odsazeniBaraku &&
                                stavZastavky == StavZastavky.Pred &&
                                ulice.zastavka != null
                            ) {
                                cloveciNaZastavce = ulice.zastavka!!.cloveci

                                stavZastavky = StavZastavky.Na(
                                    if (nextInt(0, nahodnostProjetiZastavky) == 0) {
                                        dosahlovac.dosahni(Dosahlost.ProjetZastavku::class)
                                        dobaPobytuNaZastavce
                                    } else 0.tiku
                                )

                                dosahlovac.dosahni(Dosahlost.BusNaZastavce::class)

                                // cloveci musi jit domu a na zastavku a taky na záchod

//        println(-cloveciNaZastavce / 4)
//        println(cloveciVUlici / 4 + 1)
//        println(cloveciVUlici - ulice.kapacita)
//        println(ulice.zastavka.kapacita(ulice) - cloveciNaZastavce)

                                val lidiCoJdouZDomu = nextInt(
                                    from = -cloveciNaZastavce / 4,
                                    until = cloveciVUlici / 4 + 1,
                                )
                                    .coerceAtLeast(cloveciVUlici - ulice.kapacita)
                                    .coerceAtMost(ulice.zastavka!!.kapacita(ulice) - cloveciNaZastavce)

                                cloveciVUlici -= lidiCoJdouZDomu
                                cloveciNaZastavce += lidiCoJdouZDomu

                                Log.i(
                                    "Přesunuti lidé",
                                    (if (lidiCoJdouZDomu >= 0) "Na zastávku na ulici přišlo" else "Ze zastávky odešlo") + " ${lidiCoJdouZDomu.absoluteValue} lidí."
                                )
                            }
                        }
                    }
                    val sBusem = dp.copy(
                        busy = dp.busy.mutate {
                            val i = indexOfFirst { it.id == bus.id }
                            if (i == -1) {
                                println("BUS NEBYL NALEZEN!!!!")
                                return@mutate
                            }
//                            println(smerNaLince)
//                            println(poziceNaLince)
//                            println(poziceVUlici)
                            this[i] = bus.copy(
                                najeto = najeto,
                                poziceVUlici = poziceVUlici,
                                poziceNaLince = poziceNaLince,
                                smerNaLince = smerNaLince,
                                stavZastavky = stavZastavky,
                                cloveci = cloveci,
                            )
                        }
                    )
                    if (cloveciNaZastavce == null || cloveciVUlici == null || ulice == null) sBusem
                    else {
                        sBusem.copy(
                            ulice = sBusem.ulice.mutate {
                                val i = this.indexOfFirst { it.id == ulice.id }
                                this[i] = ulice.copy(
                                    cloveci = cloveciVUlici,
                                    zastavka = Zastavka(cloveciNaZastavce)
                                )
                            }
                        )
                    }
                }
            }

            // infrastruktura

            val zaZastavky = udrzbaZastavky * dp.ulice.count { it.maZastavku }
            val zaTroleje = udrzbaTroleje * dp.ulice.count { it.maTrolej }

//                println(zaZastavky)
//                println(zaTroleje)

            dataSource.zmenitVse {
                it.copy(
                    prachy = it.prachy - zaZastavky * 1.tiku - zaTroleje * 1.tiku
                )
            }

            zisk -= (zaZastavky + zaTroleje)

            // dosahlosti

            dosahlovac.dosahniPocetniDosahlost(
                Dosahlost.SkupinovaDosahlost.Penize::class,
                dataSource.vse.first().prachy.value.roundToInt(),
            )

            // tutorial

//                if (lifecycle.currentState.isAtLeast(STARTED)) {
//                    zobrazitTutorial(1)
//                    zobrazitTutorial(3)
//                    if (vse.prachy >= 1_000_000) zobrazitTutorial(7)
//                }
//
//                if (vse.tutorial == 2) {
//                    binding.btnLinky.visibility = View.VISIBLE
//                    binding.btnGaraz.visibility = View.GONE
//                    binding.tvBusy.visibility = View.GONE
//                    binding.tvZisk.visibility = View.GONE
//                    binding.ivI.visibility = View.GONE
//                    binding.tvPenize.visibility = View.GONE
//                    binding.vs.visibility = View.GONE
//                }
//                if (vse.tutorial == 4) {
//                    binding.btnLinky.visibility = View.GONE
//                    binding.btnGaraz.visibility = View.GONE
//                    binding.tvBusy.visibility = View.GONE
//                    binding.tvZisk.visibility = View.GONE
//                    binding.ivI.visibility = View.GONE
//                    binding.tvPenize.visibility = View.VISIBLE
//                    binding.vs.visibility = View.VISIBLE
//                }
//                if (vse.tutorial > 6) {
//                    binding.btnLinky.visibility = View.VISIBLE
//                    binding.btnGaraz.visibility = View.VISIBLE
//                    binding.tvBusy.visibility = View.VISIBLE
//                    binding.tvZisk.visibility = View.VISIBLE
//                    binding.ivI.visibility = View.VISIBLE
//                    binding.tvPenize.visibility = View.VISIBLE
//                    binding.vs.visibility = View.VISIBLE
//                }
//
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
            dataSource.zmenitDp {
                it.copy(
                    zisk = (zisk + it.zisk) / 2,
                    cas = System.currentTimeMillis()
                )
            }/*.also {
                println("END $tik")
            }*/
        }
    }
}