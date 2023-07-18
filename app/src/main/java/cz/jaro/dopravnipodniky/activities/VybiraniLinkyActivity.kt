package cz.jaro.dopravnipodniky.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cz.jaro.dopravnipodniky.databinding.ActivityVybiraniLinkyBinding
import cz.jaro.dopravnipodniky.other.PrefsHelper.vse
import cz.jaro.dopravnipodniky.other.Tutorial.zobrazitTutorial
import cz.jaro.dopravnipodniky.sketches.Sketch
import processing.android.PFragment

class VybiraniLinkyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVybiraniLinkyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(vse.tema)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityVybiraniLinkyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val sketch = Sketch(
            intent.getBooleanExtra("zobrazit_busy", true),
            intent.getBooleanExtra("zobrazit_linky", false),
            intent.getBooleanExtra("vybirat_linku", false),
        )

        val fragment = PFragment(sketch)
        fragment.setView(binding.flMapa, this)

        zobrazitTutorial(2)

    }
}
