package cz.jaro.dopravnipodniky.activities

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.adapters.BusyAdapter
import cz.jaro.dopravnipodniky.classes.Bus
import cz.jaro.dopravnipodniky.databinding.ActivityGarazBinding
import cz.jaro.dopravnipodniky.formatovat
import cz.jaro.dopravnipodniky.other.Dosahlosti.dosahni
import cz.jaro.dopravnipodniky.other.PrefsHelper.dp
import cz.jaro.dopravnipodniky.other.PrefsHelper.vse
import cz.jaro.dopravnipodniky.other.Smer
import cz.jaro.dopravnipodniky.other.Trakce
import cz.jaro.dopravnipodniky.other.Tutorial.zobrazitTutorial


class GarazActivity : AppCompatActivity() {

    private var a: BusyAdapter? = null

    private lateinit var binding: ActivityGarazBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(vse.tema)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityGarazBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)

        title = getString(R.string.garaz)

        binding.tvZadnyBus.visibility =  if (dp.busy.isEmpty()) VISIBLE else GONE

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        a = BusyAdapter(this)

        binding.rvBusy.layoutManager = LinearLayoutManager(this)
        binding.rvBusy.adapter = a


        binding.fabObchod.setOnClickListener {

            val i = Intent(this, ObchodActivity::class.java)

            startActivity(i)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()

                true
            }
            else -> false
        }
    }

    override fun onResume() {
        super.onResume()

        binding.tvZadnyBus.visibility =  if (dp.busy.isEmpty()) VISIBLE else GONE

        zobrazitTutorial(6)

        a = BusyAdapter(this)

        binding.rvBusy.adapter = a
    }

    private var actionMode: ActionMode? = null

    private val callback = object : ActionMode.Callback {

        val vybrane = mutableListOf<Bus>()

        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            mode.menuInflater.inflate(R.menu.garaz_menu, menu)
            return true
        }

        override fun onPrepareActionMode(p0: ActionMode, p1: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.actionProdat -> {

                    val prodejniCena = vybrane.sumOf { it.prodejniCena }.toLong()
                    MaterialAlertDialogBuilder(this@GarazActivity).apply {
                        setIcon(R.drawable.ic_baseline_delete_24_white)
                        setTheme(R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered)
                        setTitle(R.string.prodat_vsechna_vozidla)
                        setMessage(getString(R.string.prodat_vsechna_vozidla_za, prodejniCena.formatovat()))

                        setPositiveButton(getString(R.string.prodat_za, prodejniCena.formatovat())) { dialog, _ ->
                            dialog.cancel()

                            vse.prachy += prodejniCena

                            dp.busy.removeAll(vybrane)

                            vybrane.filter { it.linka != -1L }.forEach { dp.linka(it.linka).busy.remove(it.id) }

                            Toast.makeText(context, R.string.uspesne_prodano, Toast.LENGTH_SHORT).show()

                            (binding.rvBusy.adapter as BusyAdapter).ulozit()

                            mode.finish()
                        }
                        setNegativeButton(R.string.zrusit) { dialog, _ -> dialog.cancel() }
                        show()
                    }
                    true
                }
                R.id.actionVybratVse -> {

                    (binding.rvBusy.adapter as BusyAdapter).vybrane.clear()
                    (binding.rvBusy.adapter as BusyAdapter).vybrane += dp.busy

                    (binding.rvBusy.adapter as BusyAdapter).ulozit()

                    true
                }
                R.id.actionVypravit -> {

                    MaterialAlertDialogBuilder(this@GarazActivity).apply {
                        setTitle(R.string.vyberte_linku)

                        val seznamLinek = when {
                            vybrane.any { it.typBusu.trakce == Trakce.TROLEJBUS } -> dp.linky.filter { it.trolej(context) }
                            else -> dp.linky
                        }

                        if (seznamLinek.isEmpty()) {
                            Toast.makeText(context, R.string.nejprve_si_vytvorte_linku, Toast.LENGTH_LONG).show()
                            return false
                        }

                        val arr = seznamLinek.map { it.cislo.toString() }.toTypedArray()

                        setItems(arr) { dialog, i ->
                            vybrane.forEach {
                                it.linka = seznamLinek[i].id

                                it.poziceNaLince = 0
                                it.poziceVUlici = 0F

                                dp.linka(it.linka).busy.add(it.id)
                            }

                            dialog.cancel()
                            mode.finish()

                            (binding.rvBusy.adapter as BusyAdapter).ulozit()

                            context.dosahni("busNaLince", binding.toolbarLayout)
                        }

                        setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.cancel() }

                        show()
                    }
                    true
                }
                R.id.actionDomu -> {

                    MaterialAlertDialogBuilder(this@GarazActivity).apply {
                        setTitle(R.string.odebrat_z_linek)
                        setMessage(R.string.opravdu_chcete_odebrat_vozidla_z_linek)

                        setPositiveButton(R.string.ano) {dialog, _ ->

                            vybrane.forEach {
                                if (it.linka != -1L) {
                                    dp.linka(it.linka).busy.remove(it.id)
                                }

                                it.linka = -1L

                                it.poziceNaLince = 0
                                it.poziceVUlici = 0F
                                it.smerNaLince = Smer.POZITIVNE
                            }

                            dialog.cancel()
                            mode.finish()
                        }

                        setNegativeButton(android.R.string.cancel) { dialog, _ ->
                            dialog.cancel()
                        }

                        (binding.rvBusy.adapter as BusyAdapter).ulozit()

                        show()
                    }
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            actionMode = null
            vybrane.clear()
            (binding.rvBusy.adapter as BusyAdapter).vybrane.clear()
            (binding.rvBusy.adapter as BusyAdapter).vybirani = false
            (binding.rvBusy.adapter as BusyAdapter).ulozit()
        }
    }

    fun update(vybirani: Boolean, vybrane: MutableList<Bus>) {

        if (vybirani) {
            callback.vybrane.clear()
            callback.vybrane += vybrane
            if (actionMode == null) {
                actionMode = startSupportActionMode(callback)
            }
            actionMode!!.title = getString(R.string.vybrano, vybrane.size)
        } else {
            actionMode?.finish()
        }
    }

    override fun onBackPressed() {
        if (actionMode != null) {
            actionMode!!.finish()
            return
        }
        super.onBackPressed()
    }
}
