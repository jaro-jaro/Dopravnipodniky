package cz.jaro.dopravnipodniky.activities

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.adapters.DopravniPodnikyAdapter
import cz.jaro.dopravnipodniky.databinding.ActivityDopravniPodnikyBinding
import cz.jaro.dopravnipodniky.formatovat
import cz.jaro.dopravnipodniky.minimumInvestice
import cz.jaro.dopravnipodniky.other.Dosahlosti.dosahni
import cz.jaro.dopravnipodniky.other.Generator
import cz.jaro.dopravnipodniky.other.PrefsHelper.vse
import kotlin.math.pow
import kotlin.math.roundToLong


class DopravniPodnikyActivity : AppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            finish()

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    lateinit var binding: ActivityDopravniPodnikyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(vse.tema)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityDopravniPodnikyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar5)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.tvPodnikyPrachy.text = this.getString(R.string.kc, vse.prachy.roundToLong().formatovat())

        title = getString(R.string.dopravni_podniky)

        binding.rvDp.layoutManager = LinearLayoutManager(this)
        binding.rvDp.adapter = DopravniPodnikyAdapter(this)

        binding.fabDp.setOnClickListener {
            vytvareniNovehoDp()
        }
        binding.fabDp.setOnLongClickListener {

            val clEditText = LayoutInflater.from(this).inflate(R.layout.edit_text, null)

            val et = clEditText.findViewById<TextInputLayout>(R.id.tilEditText)

            et.hint = getString(R.string.logaritmus_vyse_investice)

            MaterialAlertDialogBuilder(this).apply {
                setTitle(R.string.novy_dp)
                setView(clEditText)
                setPositiveButton(android.R.string.ok) { dialog, _ ->

                    dialog.cancel()

                    val investice = 10.0.pow(et.editText!!.text.toString().toInt()).toLong()

                    if (investice > vse.prachy) {

                        Toast.makeText(context, R.string.malo_penez, Toast.LENGTH_LONG).show()

                        return@setPositiveButton
                    }

                    if (investice < minimumInvestice) {

                        Toast.makeText(context, R.string.nigdo_nebyl_ochoten, Toast.LENGTH_LONG).show()

                        vse.prachy -= investice / 10.0

                        return@setPositiveButton
                    }

                    vse.prachy -= investice

                    vybiraniNovehoDp(investice)
                }
                show()
            }
            
            true
        }
    }

    private fun vytvareniNovehoDp() {

        val clEditText = LayoutInflater.from(this).inflate(R.layout.edit_text, null)

        val et = clEditText.findViewById<TextInputLayout>(R.id.tilEditText)

        et.hint = getString(R.string.vyse_investice)

        MaterialAlertDialogBuilder(this).apply {
            setTitle(R.string.novy_dp)
            setView(clEditText)
            setPositiveButton(android.R.string.ok) { dialog, _ ->

                dialog.cancel()

                val investice = et.editText!!.text.toString().toLong()

                if (investice > vse.prachy) {

                    Toast.makeText(context, R.string.malo_penez, Toast.LENGTH_LONG).show()

                    return@setPositiveButton
                }

                if (investice == 69_420L) {

                    vse.prachy -= investice


                    vybiraniNovehoDp(investice * 100)

                    dosahni("jostoMesto", et)

                    return@setPositiveButton

                } else if (investice < minimumInvestice) {

                    Toast.makeText(context, R.string.nigdo_nebyl_ochoten, Toast.LENGTH_LONG).show()

                    vse.prachy -= investice / 10.0

                    return@setPositiveButton

                }

                vse.prachy -= investice

                vybiraniNovehoDp(investice)
            }
            show()
        }
    }

    private fun vybiraniNovehoDp(investice: Long) {

        if (investice >= 500_000_000) {
            MaterialAlertDialogBuilder(this).apply {
                setTitle(R.string.velke_mesto)
                setMessage(R.string.velke_mnozstvi_penez_za_dp)
                setPositiveButton(R.string.potvrdit) { dialog, _ ->
                    vse.prachy -= investice
                    Toast.makeText(context, R.string.generuje_se_nove_mesto, Toast.LENGTH_SHORT).show()
                    vse.podniky += Generator(investice).vygenerujMiMestoAToHnedVykricnik()
                    dialog.cancel()

                    if (investice <= 4_000_000_000) vse.aktualniDp = vse.podniky.lastIndex

                    finish()
                }
                setNegativeButton(R.string.zrusit) { dialog, _ -> dialog.cancel() }
                show()
            }
            return
        }

        val i = Intent(this, NovejPodnikActivity::class.java)

        i.putExtra("investice", investice)

        startActivity(i)

    }


    override fun onResume() {
        super.onResume()

        binding.rvDp.adapter = DopravniPodnikyAdapter(this)
    }
}
