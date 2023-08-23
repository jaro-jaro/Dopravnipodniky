package cz.jaro.dopravnipodniky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.DestinationsNavHost
import cz.jaro.dopravnipodniky.data.PreferencesDataSource
import cz.jaro.dopravnipodniky.data.aktualniDp
import cz.jaro.dopravnipodniky.theme.DpTheme
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vse = get<PreferencesDataSource>().vse

        setContent {
            val faktVse by vse.collectAsStateWithLifecycle(null)

            if (faktVse != null) DpTheme(
                useDynamicColor = false,
                theme = faktVse!!.aktualniDp.tema
            ) {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}
