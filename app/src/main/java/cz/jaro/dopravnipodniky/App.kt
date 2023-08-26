package cz.jaro.dopravnipodniky

import android.app.Application
import android.content.Context
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import cz.jaro.dopravnipodniky.data.Hodiny
import cz.jaro.dopravnipodniky.data.PreferencesDataSource
import cz.jaro.dopravnipodniky.data.dopravnipodnik.StavZastavky
import cz.jaro.dopravnipodniky.shared.Smer
import cz.jaro.dopravnipodniky.shared.delkaUlice
import cz.jaro.dopravnipodniky.shared.jednotky.penezZaMin
import cz.jaro.dopravnipodniky.shared.jednotky.tiku
import cz.jaro.dopravnipodniky.shared.jednotky.times
import cz.jaro.dopravnipodniky.shared.jednotky.toDuration
import cz.jaro.dopravnipodniky.shared.mutate
import cz.jaro.dopravnipodniky.shared.times
import kotlinx.coroutines.flow.first
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.ksp.generated.defaultModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(module {
                single {
                    PreferenceDataStoreFactory.create(
                        migrations = listOf()
                    ) {
                        get<Context>().preferencesDataStoreFile("DopravniPodniky_DataStore")
                    }
                }
            })
            defaultModule()
        }

        val hodiny = get<Hodiny>()
        val dataSource = get<PreferencesDataSource>()

        hodiny.registerListener(1.tiku) {
            var dp = dataSource.dp.first()

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

            dp.busy.forEach { staryBus ->

                var novyBus = staryBus

                // pocitani zisku
                zisk -= novyBus.naklady
                zisk += novyBus.vydelkuj(dp)

                // odebrani penez za naklady + starnuti busuu
                dataSource.zmenitVse {
                    it.copy(
                        prachy = it.prachy - novyBus.naklady * 1.tiku
                    )
                }

                novyBus = novyBus.copy(
                    najeto = novyBus.najeto + 1.tiku.toDuration()
                )

                if (novyBus.linka != null) {

                    val linka = dp.linka(novyBus.linka!!)
//                val ulicove = linka.ulice(dp)
//                val ulice = ulicove[
//                    when (bus.smerNaLince) {
//                        Smer.POZITIVNE -> bus.poziceNaLince
//                        Smer.NEGATIVNE -> linka.ulice.lastIndex - bus.poziceNaLince
//                    }
//                ]
//
//                if (bus.typBusu.trakce !is Trakce.Trolejbus || ulicove.jsouVsechnyZatrolejovane()) {
//                    if (bus.jeNaZastavce) {
//
//                        bus.cekaUzTakhleDlouhoT++
//
//                        if (bus.cekaUzTakhleDlouhoT >= dobaPobytuNaZastavceT) {
//
//                            // zapocitavani penez za vyjeti ze zastavky
//                            bus.jeNaZastavce = false
//
//                            bus.odjelZeZastavky(this)
//                        }
//                    }

                    if (novyBus.stavZastavky !is StavZastavky.Na) {

                        // posouvani busu po mape
                        novyBus = novyBus.copy(
                            poziceVUlici = novyBus.poziceVUlici + novyBus.typBusu.rychlost * 1.tiku.toDuration()
                        )

                        if (novyBus.poziceVUlici >= delkaUlice) {  // odjel mimo ulici
                            novyBus = novyBus.copy(
                                poziceVUlici = 0.dp,
                                stavZastavky = StavZastavky.Pred,
                                poziceNaLince = novyBus.poziceNaLince + 1
                            )

                            if (novyBus.poziceNaLince >= linka.ulice.size) { // dojel na konec linky
                                novyBus = novyBus.copy(
                                    poziceNaLince = 0,
                                    smerNaLince = novyBus.smerNaLince * Smer.NEGATIVNE
                                )
                            }
                        }
                    }
//                }
//
//                // zapocitani projeti zastavky
//
//                if (
//                    bus.poziceVUlici.roundToInt() > (velikostUlicovyhoBloku + velikostZastavky) / 2 - bus.typBusu.delka * nasobitelDelkyBusu - odsazeniBaraku &&
//                    !bus.neniVZastavce &&
//                    ulice.zastavka != null
//                ) {
//                    // je na zastavce a jeste na ni nebyl a zastavka existuje
//
//                    bus.neniVZastavce = true // uz tam je
//                    bus.jeNaZastavce = true
//
//                    dosahni("busNaZastavce", binding.fl)
//                    bus.cekaUzTakhleDlouhoT = if (nextInt(0, nahodnostProjetiZastavky) == 0) {
//                        dosahni("projetZastavku", binding.fl)
//                        dobaPobytuNaZastavceT.toInt()
//                    } else 0
//
//                    // cloveci musi jit domu a na zastavku a taky na záchod
//
//                    var celkPocet = 0
//
//                    ulice.baraky.forEach { barak ->
//                        val uliceOdBaraku = dp.ulice(barak.ulice)
//
//                        if (uliceOdBaraku.zastavka != null) {
//                            val pocet = nextInt(-uliceOdBaraku.zastavka!!.cloveci / 4, barak.cloveci / 4 + if (-uliceOdBaraku.zastavka!!.cloveci / 4 == barak.cloveci / 4) 1 else 0)
//                                .coerceAtLeast(barak.cloveci - barak.kapacita)
//                                .coerceAtMost(uliceOdBaraku.zastavka!!.kapacita - uliceOdBaraku.zastavka!!.cloveci)
//
//                            barak.cloveci -= pocet/home/rblaha15/Documents/AndroidStudioProjects/Dopravnpodniky/app/src/main/java/cz/jaro/dopravnipodniky
//                            uliceOdBaraku.zastavka!!.cloveci += pocet
//                            celkPocet += pocet
//                        }
//                    }
//                    Log.i("Přesunuti lidé", (if (celkPocet >= 0) "Na zastávku na ulici přišlo" else "Ze zastávky odešlo") + " ${celkPocet.absoluteValue} lidí.")
//                }
                    println(novyBus.poziceNaLince to linka.ulice.size)
                }
                dp = dp.copy(
                    busy = dp.busy.mutate {
                        this[indexOfFirst { it.id == novyBus.id }] = novyBus
                    }
                )
            }
            /*
                        // infrastruktura

                        zisk -= dp.zastavky.size * udrzbaZastavky
                        zisk -= dp.ulicove.filter { it.maTrolej }.size * udrzbaTroleje

                        // zobrazovani

                        binding.tvZisk.text = getString(R.string.zisk_kc, zisk.roundToLong().formatovat())
                        binding.tvPenize.text = getString(R.string.kc, vse.prachy.roundToLong().formatovat())

                        // ukladani casu a zisku

                        dp.cas = Calendar.getInstance().toInstant().toEpochMilli()
                        dp.zisk = zisk

                        // dosahlosti

                        vse.pocetniDosahlosti["penizeX"] = vse.prachy.roundToInt()

                        if (vse.prachy >= 10_000_000) dosahni("penize10000000", binding.toolbarLayout)
                        if (vse.prachy >= 2_000_000) dosahni("penize2000000", binding.toolbarLayout)
                        if (vse.prachy >= 1_000_000) dosahni("penize1000000", binding.toolbarLayout)
                        if (vse.prachy >= 500_000) dosahni("penize500000", binding.toolbarLayout)
                        if (vse.prachy >= 200_000) dosahni("penize200000", binding.toolbarLayout)

                        // tutorial

                        if (lifecycle.currentState.isAtLeast(STARTED)) {
                            zobrazitTutorial(1)
                            zobrazitTutorial(3)
                            if (vse.prachy >= 1_000_000) zobrazitTutorial(7)
                        }

                        if (vse.tutorial == 2) {
                            binding.btnLinky.visibility = View.VISIBLE
                            binding.btnGaraz.visibility = View.GONE
                            binding.tvBusy.visibility = View.GONE
                            binding.tvZisk.visibility = View.GONE
                            binding.ivI.visibility = View.GONE
                            binding.tvPenize.visibility = View.GONE
                            binding.vs.visibility = View.GONE
                        }
                        if (vse.tutorial == 4) {
                            binding.btnLinky.visibility = View.GONE
                            binding.btnGaraz.visibility = View.GONE
                            binding.tvBusy.visibility = View.GONE
                            binding.tvZisk.visibility = View.GONE
                            binding.ivI.visibility = View.GONE
                            binding.tvPenize.visibility = View.VISIBLE
                            binding.vs.visibility = View.VISIBLE
                        }
                        if (vse.tutorial > 6) {
                            binding.btnLinky.visibility = View.VISIBLE
                            binding.btnGaraz.visibility = View.VISIBLE
                            binding.tvBusy.visibility = View.VISIBLE
                            binding.tvZisk.visibility = View.VISIBLE
                            binding.ivI.visibility = View.VISIBLE
                            binding.tvPenize.visibility = View.VISIBLE
                            binding.vs.visibility = View.VISIBLE
                        }

                        // mimoradnosti

                        if (nextInt(0, nahodnostKamionu) == 1 && dp.ulicove.any { it.maTrolej }) {
                            MaterialAlertDialogBuilder(this).apply {
                                setTitle(R.string.kamion)
                                setCancelable(false)
                                setMessage(getString(R.string.kamion_prijel))
                                setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.cancel() }
                                show()
                            }

                            dp.ulicove.forEach { it.maTrolej = false }
                        }

                        if (dp.busy.size != 0) {

                            val bus = dp.busy.maxByOrNull { it.ponicenost }!!

                            if (bus.ponicenost > .0) {

                                if (nextInt(0, (10 * TPM / bus.ponicenost).roundToInt()) == 0) {

                                    vse.prachy -= bus.typBusu.cena - bus.prodejniCena

                                    MaterialAlertDialogBuilder(this).apply {
                                        setTitle(getString(R.string.porouchany, bus.typBusu.trakce))
                                        setCancelable(false)
                                        setMessage(getString(R.string.vas_bus_se_porouchal, bus.typBusu.trakce, bus.evCislo))
                                        setPositiveButton(getString(R.string.zaplatit, (bus.typBusu.cena - bus.prodejniCena).formatovat())) { dialog, _ -> dialog.cancel() }
                                        show()
                                    }
                                }
                            }
                        }

                        // umírání

                        if (dp.cloveci != 0) if (nextInt(0, nahodnostSebevrazdy) == 0) {
                            val clovek = Clovek()
                            clovek.sebevrazda(this)
                            Log.i("Sebevražda", "${clovek.jmeno} ve ${clovek.vek.roundToInt()} letech")
                        }
                        if (dp.cloveci != 0) if (nextInt(0, dobaVymreni / dp.cloveci + 1) == 0) {
                            val clovek = Clovek()
                            clovek.smrt(this)
                            Log.i("Zemřel člověk", "${clovek.jmeno} zemřel ve ${clovek.vek.roundToInt()} letech")
                        }

                        // rození

                        if (nextInt(0, dobaZnovuobnoveniPopulace / dp.cloveci + 1) == 0 && dp.baraky.any { it.kapacita != it.cloveci }) {
                            val barak = dp.baraky.filter { it.kapacita != it.cloveci }.random()
                            barak.cloveci ++
                            val clovek = Clovek()
                            clovek.pojmenuj(this)
                            Log.i("Narodil se člověk", clovek.jmeno)
                        }

                        // ukladani

                        val prefs = App.prefs
                        val gson = GsonBuilder().serializeSpecialFloatingPointValues().create()

                        prefs.edit {
                            putString("vse", gson.toJson(vse))
                        }*/
            dataSource.zmenitDp {
                dp
            }
        }
    }
}
