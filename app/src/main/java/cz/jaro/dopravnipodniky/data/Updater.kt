package cz.jaro.dopravnipodniky.data

import android.util.Log
import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.data.dopravnipodnik.StavZastavky
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Trakce
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Zastavka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.jsouVsechnyZatrolejovane
import cz.jaro.dopravnipodniky.data.dopravnipodnik.kapacita
import cz.jaro.dopravnipodniky.data.dopravnipodnik.linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.maZastavku
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ulice
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlovac
import cz.jaro.dopravnipodniky.shared.Smer
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.delkaUlice
import cz.jaro.dopravnipodniky.shared.delkaZastavky
import cz.jaro.dopravnipodniky.shared.dobaPobytuNaZastavce
import cz.jaro.dopravnipodniky.shared.je
import cz.jaro.dopravnipodniky.shared.jednotky.penez
import cz.jaro.dopravnipodniky.shared.jednotky.penezZaMin
import cz.jaro.dopravnipodniky.shared.jednotky.tiku
import cz.jaro.dopravnipodniky.shared.jednotky.times
import cz.jaro.dopravnipodniky.shared.jednotky.toDp
import cz.jaro.dopravnipodniky.shared.jednotky.toDuration
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
    init {
        hodiny.registerListener(1.tiku) { tik ->

            val puvodniDp = dataSource.dp.first()
            val puvodniVse = dataSource.vse.first()

//            println("START $tik")
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
            var deltaPrachy = 0.0.penez

            puvodniDp.busy.forEach { bus ->

                // pocitani zisku
                zisk -= bus.naklady
                zisk += bus.vydelkuj(puvodniDp)

                // odebrani penez za naklady + starnuti busuu
                deltaPrachy -= bus.naklady * 1.tiku

            }
            dataSource.upravitBusy {
                forEachIndexed { i, bus ->
                    this[i] = bus.copy(
                        najeto = bus.najeto + 1.tiku.toDuration()
                    )
                }
            }
            dataSource.upravitBusy {
                forEachIndexed { i, bus ->
                    if (bus.linka == null) return@upravitBusy

                    var cloveci = bus.cloveci
                    var smerNaLince = bus.smerNaLince
                    var poziceNaLince = bus.poziceNaLince
                    var poziceVUlici = bus.poziceVUlici
                    var stavZastavky = bus.stavZastavky

                    val linka = puvodniDp.linky.linka(bus.linka)
                    val ulicove = linka.ulice(puvodniDp.ulice)
                    val ulice = ulicove[
                        when (smerNaLince) {
                            Smer.Pozitivni -> poziceNaLince
                            Smer.Negativni -> linka.ulice.lastIndex - poziceNaLince
                        }
                    ]

                    if (bus.typBusu.trakce !is Trakce.Trolejbus || ulicove.jsouVsechnyZatrolejovane()) {
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

                            var cloveciVUlici = ulice.cloveci
                            var cloveciNaZastavce = ulice.zastavka.cloveci

                            if (stavZastavky is StavZastavky.Na) {
                                cloveciNaZastavce = ulice.zastavka.cloveci

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

                                    deltaPrachy += puvodniDp.info.jizdne * vystupujici * nasobitelZisku

                                    val nastupujici =
                                        if (bus.poziceNaLince == linka.ulice.lastIndex) 0
                                        else if (ulice.zastavka.kapacita(ulice) == 0) 0
                                        else nextInt(
                                            from = (ulice.zastavka.cloveci - ulice.zastavka.kapacita(ulice)).coerceAtLeast(0),
                                            until = ulice.zastavka.cloveci + 1
                                        )/*.also(::println)*/
                                            .times(
                                                nasobitelPoctuLidiKteryTiNastoupiDoBusuNaZastavceKdyzZastaviANakyLidiTamJsouAMaVSobeJesteVolneMisto(
                                                    puvodniDp
                                                )
                                            )/*.also(::println)*/
                                            .roundToInt()
                                            .coerceIn(0, bus.typBusu.kapacita - cloveci)
                                            .coerceAtMost(ulice.zastavka.cloveci)

                                    cloveci += nastupujici
                                    cloveciNaZastavce -= nastupujici

                                    Log.i(
                                        "Přesunuti lidé",
                                        "Nastoupilo $nastupujici lidí."
                                    )
                                }
                            }

                            // zapocitani projeti zastavky

                            if (
                                stavZastavky == StavZastavky.Pred &&
                                poziceVUlici >= (delkaUlice + delkaZastavky) / 2 - bus.typBusu.delka.toDp() - odsazeniBaraku
                            ) {
                                cloveciNaZastavce = ulice.zastavka.cloveci

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
                                    .coerceAtMost(ulice.zastavka.kapacita(ulice) - cloveciNaZastavce)

                                cloveciVUlici -= lidiCoJdouZDomu
                                cloveciNaZastavce += lidiCoJdouZDomu

                                Log.i(
                                    "Přesunuti lidé",
                                    (if (lidiCoJdouZDomu >= 0) "Na zastávku na ulici přišlo" else "Ze zastávky odešlo") + " ${lidiCoJdouZDomu.absoluteValue} lidí."
                                )
                            }

                            dataSource.upravitUlice {
                                val indexUlice = indexOfFirst { it.id == ulice.id }
                                this[indexUlice] = ulice.copy(
                                    cloveci = cloveciVUlici,
                                    zastavka = Zastavka(cloveciNaZastavce)
                                )
                            }
                        }
                    }

                    this[i] = bus.copy(
                        poziceVUlici = poziceVUlici,
                        poziceNaLince = poziceNaLince,
                        smerNaLince = smerNaLince,
                        stavZastavky = stavZastavky,
                        cloveci = cloveci,
                    )
                }
            }

            // infrastruktura

            val zaZastavky = udrzbaZastavky * puvodniDp.ulice.count { it.maZastavku }
            val zaTroleje = udrzbaTroleje * puvodniDp.ulice.count { it.maTrolej }

//                println(zaZastavky)
//                println(zaTroleje)

            deltaPrachy -= zaZastavky * 1.tiku - zaTroleje * 1.tiku
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
                it + deltaPrachy
            }
            dataSource.upravitDPInfo {
                it.copy(
                    casPosledniNavstevy = System.currentTimeMillis(),
                    zisk = (it.zisk + zisk) / 2
                )
            }
        }
    }
}