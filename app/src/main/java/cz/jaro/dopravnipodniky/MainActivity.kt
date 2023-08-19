package cz.jaro.dopravnipodniky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.jaro.dopravnipodniky.sketches.Sketch
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel = koinViewModel<MainViewModel>()

            val dp by viewModel.dp.collectAsStateWithLifecycle()
            val vse by viewModel.vse.collectAsStateWithLifecycle()

            if (dp != null && vse != null) Sketch(
                dp = dp!!,
                vse = vse!!,
                upravitDp = viewModel::zmenitDp,
                upravitVse = viewModel::zmenitVse,
                Modifier.fillMaxSize(),
            )
        }
    }
}
