package cz.jaro.dopravnipodniky.activities

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import cz.jaro.dopravnipodniky.*
import cz.jaro.dopravnipodniky.BuildConfig.DEBUG
import cz.jaro.dopravnipodniky.BuildConfig.VERSION_NAME
import cz.jaro.dopravnipodniky.adapters.DosahlostiAdapter
import cz.jaro.dopravnipodniky.classes.Dosahlost
import cz.jaro.dopravnipodniky.classes.OrigoDosahlost
import cz.jaro.dopravnipodniky.databinding.ActivityMainBinding
import cz.jaro.dopravnipodniky.databinding.NastaveniBinding
import cz.jaro.dopravnipodniky.other.Dosahlosti.dosahni
import cz.jaro.dopravnipodniky.other.Dosahlosti.seznamDosahlosti
import cz.jaro.dopravnipodniky.other.Podtyp
import cz.jaro.dopravnipodniky.other.PrefsHelper.dp
import cz.jaro.dopravnipodniky.other.PrefsHelper.vse
import cz.jaro.dopravnipodniky.other.Tutorial.zobrazitTutorial
import cz.jaro.dopravnipodniky.sketches.Sketch
import processing.android.PFragment
import java.util.*
import kotlin.math.roundToLong


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onResume() {
        super.onResume()

        if (dp.jmenoMesta.contains("Věčné")) {

            dosahni("vecne1", binding.toolbar)
            if (dp.linky.map { it.cislo }.toSet() == setOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 15, 16, 33, 34, 44, 74, 77)) {
                dosahni("vecne2", binding.toolbar)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(vse.tema)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(findViewById(R.id.toolbar))

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        seznamDosahlosti.forEach { origoDosahlost: OrigoDosahlost ->
            if (!vse.dosahlosti.any { it.cislo == origoDosahlost.cislo }) {
                vse.dosahlosti += Dosahlost(origoDosahlost.cislo, false, null)
            }
        }

        if (vse.aktualniDp == -1) {
            vse.aktualniDp = 0
        }

        if (vse.tema == 0) vse.tema = BARVICKYTEMAT[1]

        val sketch = Sketch()
        val fragment = PFragment(sketch)
        fragment.setView(binding.fl, this)

        binding.btnLinky.setOnClickListener {
            val i = Intent(this, LinkyActivity::class.java)

            startActivity(i)
        }

        binding.btnGaraz.setOnClickListener {
            val i = Intent(this, GarazActivity::class.java)

            startActivity(i)
        }

        binding.btnEditor1.setOnClickListener {
            editor = !editor

            binding.vs.shouldDelayChildPressedState()
            binding.vs.setOutAnimation(this, R.anim.slide_out_left)
            binding.vs.setInAnimation(this, R.anim.slide_in_right)
            binding.vs.displayedChild = (1)
        }
        binding.btnEditor2.setOnClickListener {
            editor = !editor

            binding.vs.shouldDelayChildPressedState()
            binding.vs.setOutAnimation(this, android.R.anim.slide_out_right)
            binding.vs.setInAnimation(this, android.R.anim.slide_in_left)
            binding.vs.displayedChild = (0)

            if (zobrazitTutorial(4)) {
                binding.btnLinky.visibility = GONE
                binding.btnGaraz.visibility = View.VISIBLE
                binding.tvBusy.visibility = GONE
                binding.tvZisk.visibility = GONE
                binding.ivI.visibility = GONE
                binding.tvPenize.visibility = View.VISIBLE
                binding.vs.visibility = View.VISIBLE
            }
        }

        binding.toolbar.setOnClickListener {
            val i = Intent(this, DopravniPodnikyActivity::class.java)

            startActivity(i)
        }


        binding.toolbar.setOnLongClickListener {

            val ivSuperTajnaVec = ImageView(this)
            ivSuperTajnaVec.setImageResource(R.drawable.super_tajna_vec_doopravdy_na_me_neklikej)

            MaterialAlertDialogBuilder(this).apply {
                setView(ivSuperTajnaVec)
                setPositiveButton(getString(R.string.kocka)) { dialog, _->

                    dialog.cancel()

                    if (DEBUG) vse.prachy = if (vse.prachy > Long.MAX_VALUE) 1_000_000.0 else Double.MAX_VALUE
                }
                show()
            }

            dosahni("kocka1", binding.toolbar)
            return@setOnLongClickListener true
        }

        binding.ivI.setOnClickListener {

            val prijmyZaVsechnyBusy = dp.busy.sumOf { it.vydelek(this) }

            val vydajeZaZastavky = dp.zastavky.size * udrzbaZastavky
            val vydajeZaTroleje = dp.ulicove.count { it.trolej } * udrzbaTroleje
            val vydajeZaInfrastrukturu = vydajeZaZastavky + vydajeZaTroleje

            MaterialAlertDialogBuilder(this).apply {
                setTitle(R.string.podrobnosti_o_zisku)
                setMessage("""
                    |${getString(R.string.celkove_prijmy, prijmyZaVsechnyBusy.roundToLong().formatovat())}
                    |${getString(R.string.celkove_vydaje, (dp.busy.sumOf { it.naklady } + vydajeZaInfrastrukturu).roundToLong().formatovat())}
                    |${getString(R.string.celkovy_zisk, dp.zisk.roundToLong().formatovat())}
                    |
                    |${getString(R.string.vydaje_za_infrastrukturu)}
                    |${getString(R.string.vydaje_za_zastavky, vydajeZaZastavky.formatovat())}
                    |${getString(R.string.vydaje_za_troleje, vydajeZaTroleje.formatovat())}
                    |
                    |${getString(R.string.vydaje_za_ekologii)}
                    |${getString(R.string.vydaje_za_neekologicke_vozy, (
                        dp.busy.count { it.typBusu.podtyp == Podtyp.DIESELOVY } * bonusoveVydajeZaNeekologickeBusy +
                        dp.busy.count { it.typBusu.podtyp in listOf(Podtyp.HYBRIDNI, Podtyp.ZEMEPLYNOVY) } * bonusoveVydajeZaPoloekologickeBusy
                    ).formatovat())}
                    |
                    |${getString(R.string.zisk_linek)} 
                    |${dp.linky.joinToString("\n|") { linka ->
                        getString(R.string.linka_vydelava_tohle, 
                            linka.cislo,
                            linka.busy.map { dp.bus(it) }.sumOf { it.vydelek(this@MainActivity) - it.naklady }.roundToLong().formatovat()
                        )
                    }}
                    |
                    |${getString(R.string.zisk_vozidel)}
                    |${dp.busy.joinToString("\n|") { bus ->
                         if (bus.linka == -1L) {
                             getString(R.string.bus_prodelava_tolik, 
                                 bus.evCislo, bus.naklady.roundToLong().formatovat()
                             )
                         } else {
                             getString(R.string.bus_vydelava_tolik, 
                                 bus.evCislo, 
                                 bus.vydelek(this@MainActivity).roundToLong().formatovat(), 
                                 bus.naklady.roundToLong().formatovat(), 
                                 (bus.vydelek(this@MainActivity) - bus.naklady).roundToLong().formatovat()
                             )
                         }
                     }}
                    |
                """.trimMargin())

                show()
            }
        }

        if (intent.getBooleanExtra("uspechy", false)) {

            MaterialAlertDialogBuilder(this).apply {
                setTitle(R.string.uspechy)

                val rv = RecyclerView(context)
                rv.layoutManager = LinearLayoutManager(context)
                rv.adapter = DosahlostiAdapter(this@MainActivity)

                setView(rv)


                show()
            }
        }
        handler = Handler(Looper.getMainLooper())
        startRepeatingTask()

    }

    private var handler: Handler? = null

    private fun startRepeatingTask() {
        statusChecker.run()
    }

    private val statusChecker = object : Runnable {
        override fun run() {
            try {
                update()
            } finally {
                handler!!.postDelayed(this, 1000L / TPS)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            stopRepeatingTask()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun stopRepeatingTask() {
        handler!!.removeCallbacks(statusChecker)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        menu!!.findItem(R.id.actionZobrazitBusyNeboLinky).setIcon(if (vse.zobrazitLinky) R.drawable.neznamy_bus else R.drawable.ic_baseline_timeline_24)

        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.actionZobrazitBusyNeboLinky -> {
                vse.zobrazitLinky = !vse.zobrazitLinky

                item.setIcon(if (vse.zobrazitLinky) R.drawable.neznamy_bus else R.drawable.ic_baseline_timeline_24)

                true
            }
            R.id.actionKalibrovat -> {
                dp.kalibrovat = -1

                true
            }
            R.id.actionUspechy -> {
                MaterialAlertDialogBuilder(this).apply {
                    setTitle(R.string.uspechy)

                    val rv = RecyclerView(context)
                    rv.layoutManager = LinearLayoutManager(context)
                    rv.adapter = DosahlostiAdapter(this@MainActivity)

                    setView(rv)

                    setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.cancel() }

                    show()
                }

                true
            }
            R.id.actionNastaveni -> {

                MaterialAlertDialogBuilder(this).apply {
                    setTitle(R.string.nastaveni)

                    val nastaveniBinding = NastaveniBinding.inflate(LayoutInflater.from(context))

                    nastaveniBinding.swAutomatickyEvc.isChecked = vse.automatickyEvC
                    nastaveniBinding.swVicenasobnyKupovani.isChecked = vse.vicenasobnyKupovani

                    setView(nastaveniBinding.root)

                    var pocetKliknuti = if (vse.barva in BARVICKYTEMAT) BARVICKYTEMAT.indexOf(vse.barva) else 1

                    nastaveniBinding.btnTema.setBackgroundColor(BARVICKYTEMAT[pocetKliknuti])
                    nastaveniBinding.tvTema.text = getString(R.string.barva_temata, NAZVYTEMAT[pocetKliknuti])

                    nastaveniBinding.btnDopravaNastaveni.setOnClickListener {
                        pocetKliknuti += 1

                        if (pocetKliknuti > BARVICKYTEMAT.lastIndex) pocetKliknuti = 1

                        nastaveniBinding.btnTema.setBackgroundColor(BARVICKYTEMAT[pocetKliknuti])
                        nastaveniBinding.tvTema.text = getString(R.string.barva_temata, NAZVYTEMAT[pocetKliknuti])
                    }
                    nastaveniBinding.btnDolevaNastaveni.setOnClickListener {
                        pocetKliknuti -= 1

                        if (pocetKliknuti < 1 ) pocetKliknuti = BARVICKYTEMAT.lastIndex

                        nastaveniBinding.btnTema.setBackgroundColor(BARVICKYTEMAT[pocetKliknuti])
                        nastaveniBinding.tvTema.text = getString(R.string.barva_temata, NAZVYTEMAT[pocetKliknuti])
                    }

                    setPositiveButton(R.string.potvrdit) { dialog, _ ->

                        vse.barva = BARVICKYTEMAT[pocetKliknuti]
                        vse.tema = TEMATA[pocetKliknuti]
                        vse.automatickyEvC = nastaveniBinding.swAutomatickyEvc.isChecked
                        vse.vicenasobnyKupovani = nastaveniBinding.swVicenasobnyKupovani.isChecked

                        dialog.cancel()


                        val i = Intent(this@MainActivity, MainActivity::class.java)

                        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                        startActivity(i)
                    }
                    show()
                }
                true
            }
            R.id.actionInfo -> {
                MaterialAlertDialogBuilder(this)
                    .setTitle(R.string.o_aplikaci)
                    .setMessage(
                        """
                            ${getString(R.string.vytvorilo_ro_studios)}

                            ${getString(R.string.verze, VERSION_NAME)}
                            
                            ${getString(R.string.jazyk, Locale.getDefault().displayLanguage)}
                        """.trimIndent()
                    )
                    .show()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
