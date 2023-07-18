package cz.jaro.dopravnipodniky.activities

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.adapters.LinkyAdapter
import cz.jaro.dopravnipodniky.databinding.ActivityLinkyBinding
import cz.jaro.dopravnipodniky.other.PrefsHelper.dp
import cz.jaro.dopravnipodniky.other.PrefsHelper.vse

class LinkyActivity : AppCompatActivity() {

    private var a: LinkyAdapter? = null

    private lateinit var binding: ActivityLinkyBinding

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            finish()

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(vse.tema)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityLinkyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(findViewById(R.id.toolbar3))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = getString(R.string.linky)

        binding.tvZadnaLinka.visibility =  if (dp.linky.isEmpty()) VISIBLE else GONE

        a = LinkyAdapter(this)

        binding.rvLinky.layoutManager = LinearLayoutManager(this)
        binding.rvLinky.adapter = a

        binding.fabVybratLinku.setOnClickListener {

            val i = Intent(this, VybiraniLinkyActivity::class.java).apply {
                putExtra("vybirat_linku", true)
                putExtra("zobrazit_linky", true)
                putExtra("zobrazit_busy", false)
            }

            startActivity(i)
        }
    }


    override fun onResume() {
        super.onResume()

        binding.tvZadnaLinka.visibility = if (dp.linky.isEmpty()) VISIBLE else GONE

        a = LinkyAdapter(this)

        binding.rvLinky.adapter = a
    }
}
