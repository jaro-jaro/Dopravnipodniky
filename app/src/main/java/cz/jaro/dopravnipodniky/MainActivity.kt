package cz.jaro.dopravnipodniky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cz.jaro.dopravnipodniky.other.Generator
import cz.jaro.dopravnipodniky.sketches.Sketch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Sketch(
                dp = remember { Generator.vygenerujMiPrvniMesto() },
                Modifier.fillMaxSize(),
            )
        }
    }
}
