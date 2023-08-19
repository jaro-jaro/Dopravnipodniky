package cz.jaro.dopravnipodniky

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import cz.jaro.dopravnipodniky.classes.DopravniPodnik
import cz.jaro.dopravnipodniky.destinations.GarazScreenDestination
import cz.jaro.dopravnipodniky.sketches.CeleMesto
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination(start = true)
fun MainScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = koinViewModel<MainViewModel>()

    val dp by viewModel.dp.collectAsStateWithLifecycle()
    val vse by viewModel.vse.collectAsStateWithLifecycle()

    if (dp != null && vse != null) MainScreen(
        dp = dp!!,
        vse = vse!!,
        upravitDp = viewModel::zmenitDp,
        upravitVse = viewModel::zmenitVse,
        navigatate = navigator::navigate,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    dp: DopravniPodnik,
    vse: Vse,
    upravitDp: ((DopravniPodnik) -> DopravniPodnik) -> Unit,
    upravitVse: ((Vse) -> Vse) -> Unit,
    navigatate: (Direction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(dp.jmenoMesta)
                }
            )
        }
    ) { paddingValues ->
        Surface(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            CeleMesto(
                dp = dp,
                vse = vse,
                modifier = Modifier.fillMaxSize(),
            )
            Column(
                Modifier.fillMaxSize(),
            ) {
                Text(
                    text = stringResource(R.string.kc, 3_141),
                    Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp),
                    textAlign = TextAlign.Center,
                )
                Row(
                    Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = stringResource(R.string.zisk_kc, 314),
                        Modifier
                            .weight(1F)
                            .padding(all = 8.dp),
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = stringResource(R.string.pocet_busu, 0, dp.busy.size),
                        Modifier
                            .weight(1F)
                            .padding(all = 8.dp),
                        textAlign = TextAlign.End,
                    )
                }
                Spacer(modifier = Modifier.weight(1F))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Icon(Icons.Default.Edit, null)
                }
                Row(
                    Modifier.fillMaxWidth(),
                ) {
                    TextButton(
                        onClick = {
                            navigatate(GarazScreenDestination)
                        },
                        Modifier
                            .weight(1F)
                            .padding(all = 8.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.garaz)
                        )
                    }
                    TextButton(
                        onClick = {

                        },
                        Modifier
                            .weight(1F)
                            .padding(all = 8.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.linky)
                        )
                    }
                }
            }
        }
    }
}