package cz.jaro.dopravnipodniky.activities

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.databinding.VybiratorBinding
import cz.jaro.dopravnipodniky.other.Generator
import cz.jaro.dopravnipodniky.other.PrefsHelper.vse
import cz.jaro.dopravnipodniky.pocatecniCenaMesta
import cz.jaro.dopravnipodniky.sketches.MiniSketch
import processing.android.PFragment

class NovejPodnikActivity : AppCompatActivity() {

    private lateinit var binding: VybiratorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(vse.tema)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = VybiratorBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar6)

        title = getString(R.string.nove_mesto)

        val investice = intent.getLongExtra("investice", pocatecniCenaMesta)


        val podniky =
            if (investice >= 250_000_000) listOf(
                Generator(investice).vygenerujMiMestoAToHnedVykricnik()
                )
            else if (investice >= 100_000_000) listOf(
                Generator(investice).vygenerujMiMestoAToHnedVykricnik(),
                Generator(investice).vygenerujMiMestoAToHnedVykricnik()
            )
            else listOf(
                Generator(investice).vygenerujMiMestoAToHnedVykricnik(),
                Generator(investice).vygenerujMiMestoAToHnedVykricnik(),
                Generator(investice).vygenerujMiMestoAToHnedVykricnik()
            )

        var aktualneVybrano = 0

        fun aktualizovat() {
            val fragment = PFragment(MiniSketch(podniky[aktualneVybrano]))
            fragment.setView(binding.flVybirani, this)

            binding.fabVybratMesto.text = this.getString(R.string.vybrat_mesto, podniky[aktualneVybrano].jmenoMesta)
        }

        binding.fabDoleva.setOnClickListener {
            aktualneVybrano -= 1

            if (aktualneVybrano < 0) aktualneVybrano = podniky.lastIndex
            aktualizovat()
        }

        binding.fabDoprava.setOnClickListener {
            aktualneVybrano += 1

            if (aktualneVybrano > podniky.lastIndex) aktualneVybrano = 0
            aktualizovat()
        }

        aktualizovat()


        binding.fabVybratMesto.setOnClickListener {

            vse.podniky += podniky[aktualneVybrano]

            vse.aktualniDp = vse.podniky.lastIndex

            finish()

            val i = Intent(this, MainActivity::class.java)

            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            Toast.makeText(this, this.getString(R.string.uspesne_vytvoreno), Toast.LENGTH_LONG).show()

            startActivity(i)

        }


    }

}
