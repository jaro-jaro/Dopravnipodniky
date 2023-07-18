package cz.jaro.dopravnipodniky

import android.util.Log
import android.view.View
import androidx.core.content.edit
import androidx.lifecycle.Lifecycle.State.STARTED
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.GsonBuilder
import cz.jaro.dopravnipodniky.activities.MainActivity
import cz.jaro.dopravnipodniky.classes.Clovek
import cz.jaro.dopravnipodniky.other.App
import cz.jaro.dopravnipodniky.other.Dosahlosti.dosahni
import cz.jaro.dopravnipodniky.other.PrefsHelper.dp
import cz.jaro.dopravnipodniky.other.PrefsHelper.vse
import cz.jaro.dopravnipodniky.other.Smer.NEGATIVNE
import cz.jaro.dopravnipodniky.other.Smer.POZITIVNE
import cz.jaro.dopravnipodniky.other.Trakce.TROLEJBUS
import cz.jaro.dopravnipodniky.other.Tutorial.zobrazitTutorial
import cz.jaro.dopravnipodniky.other.times
import java.util.*
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.math.roundToLong
import kotlin.random.Random.Default.nextInt

var editor = false

fun MainActivity.update() {

    // zistovani jestli nejses moc dlouho pryc

    val pocetSekundOdPoslednihoHrani = (Calendar.getInstance().toInstant().toEpochMilli() - dp.cas) / 1000 // s

    if (pocetSekundOdPoslednihoHrani < 0) {
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
            setTheme(R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered)
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
    }

    // prizpusobeni temata a podobnych blbosti

    title = dp.jmenoMesta

    if (vse.tema == 0) vse.tema = R.style.Theme_DopravníPodniky_Cervene

    setTheme(vse.tema)

    // pocitani penez a vykreslovani

    var zisk = 0.0 // Kč/min

    binding.tvBusy.text = getString(R.string.pocet_busu, dp.busy.filter { it.linka != -1L }.size, dp.busy.size)

    dp.busy.forEach { bus ->

        // pocitani zisku
        zisk -= bus.naklady
        zisk += bus.vydelek(this)

        // odebrani penez za naklady + starnuti busuu
        vse.prachy -= bus.naklady / TPM
        bus.najeto += 1.0 / TPH

        if (bus.linka == -1L) return@forEach

        val linka = dp.linka(bus.linka)
        val ulice = dp.ulice(linka.seznamUlic[
                when (bus.smerNaLince) {
                    POZITIVNE -> bus.poziceNaLince
                    NEGATIVNE -> linka.seznamUlic.lastIndex - bus.poziceNaLince
                }
        ])

        if (bus.typBusu.trakce != TROLEJBUS || linka.trolej(this)) {
            if (bus.jeNaZastavce) {

                bus.cekaUzTakhleDlouhoT++

                if (bus.cekaUzTakhleDlouhoT >= dobaPobytuNaZastavceT) {

                    // zapocitavani penez za vyjeti ze zastavky
                    bus.jeNaZastavce = false

                    bus.odjelZeZastavky(this)
                }
            }
            if (!bus.jeNaZastavce) {

                // posouvani busu po mape
                bus.poziceVUlici += bus.typBusu.rychlost * nasobitelRychlosti

                if (bus.poziceVUlici >= velikostUlicovyhoBloku) {  // odjel mimo ulici
                    bus.poziceVUlici = 0F

                    bus.projeto = false // je zas pred zastavkou

                    bus.poziceNaLince++

                    if (bus.poziceNaLince >= linka.seznamUlic.size) { // dojel na konec linky
                        bus.poziceNaLince = 0

                        bus.smerNaLince *= NEGATIVNE
                    }
                }
            }
        }

        // zapocitani projeti zastavky

        if (
            bus.poziceVUlici.roundToInt() > (velikostUlicovyhoBloku + velikostZastavky) / 2 - bus.typBusu.delka * nasobitelDelkyBusu - odsazeniBaraku &&
            !bus.projeto &&
            ulice.zastavka != null
        ) {
            // je na zastavce a jeste na ni nebyl a zastavka existuje

            bus.projeto = true // uz tam je
            bus.jeNaZastavce = true

            dosahni("busNaZastavce", binding.fl)
            bus.cekaUzTakhleDlouhoT = if (nextInt(0, nahodnostProjetiZastavky) == 0) {
                dosahni("projetZastavku", binding.fl)
                dobaPobytuNaZastavceT.toInt()
            } else 0

            // cloveci musi jit domu a na zastavku a taky na záchod

            var celkPocet = 0

            ulice.baraky.forEach { barak ->
                val uliceOdBaraku = dp.ulice(barak.ulice)

                if (uliceOdBaraku.zastavka != null) {
                    val pocet = nextInt(-uliceOdBaraku.zastavka!!.cloveci / 4, barak.cloveci / 4 + if (-uliceOdBaraku.zastavka!!.cloveci / 4 == barak.cloveci / 4) 1 else 0)
                        .coerceAtLeast(barak.cloveci - barak.kapacita)
                        .coerceAtMost(uliceOdBaraku.zastavka!!.kapacita - uliceOdBaraku.zastavka!!.cloveci)

                    barak.cloveci -= pocet
                    uliceOdBaraku.zastavka!!.cloveci += pocet
                    celkPocet += pocet
                }
            }
            Log.i("Přesunuti lidé", (if (celkPocet >= 0) "Na zastávku na ulici přišlo" else "Ze zastávky odešlo") + " ${celkPocet.absoluteValue} lidí.")
        }
    }

    // infrastruktura

    zisk -= dp.zastavky.size * udrzbaZastavky
    zisk -= dp.ulicove.filter { it.trolej }.size * udrzbaTroleje

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

    if (nextInt(0, nahodnostKamionu) == 1 && dp.ulicove.any { it.trolej }) {
        MaterialAlertDialogBuilder(this).apply {
            setTitle(R.string.kamion)
            setCancelable(false)
            setMessage(getString(R.string.kamion_prijel))
            setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.cancel() }
            show()
        }

        dp.ulicove.forEach { it.trolej = false }
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
    }
}
