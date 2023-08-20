package cz.jaro.dopravnipodniky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ramcosta.composedestinations.DestinationsNavHost
import cz.jaro.dopravnipodniky.theme.DpTheme
import cz.jaro.dopravnipodniky.theme.Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DpTheme(
                useDynamicColor = false,
                theme = Theme.Zlute, // todo
            ) {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}
