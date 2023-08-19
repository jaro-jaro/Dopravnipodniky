package cz.jaro.dopravnipodniky

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import cz.jaro.dopravnipodniky.classes.Bus
import cz.jaro.dopravnipodniky.classes.DopravniPodnik
import cz.jaro.dopravnipodniky.classes.nakladyTextem
import cz.jaro.dopravnipodniky.jednotky.asString
import cz.jaro.dopravnipodniky.other.ikonka
import cz.jaro.dopravnipodniky.other.typyBusu
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random

@Composable
@Destination
fun ObchodScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = koinViewModel<MainViewModel>()

    val dp by viewModel.dp.collectAsStateWithLifecycle()
    val vse by viewModel.vse.collectAsStateWithLifecycle()

    if (dp != null && vse != null) ObchodScreen(
        dp = dp!!,
        vse = vse!!,
        upravitDp = viewModel::zmenitDp,
        upravitVse = viewModel::zmenitVse,
        navigatate = navigator::navigate,
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ObchodScreen(
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
                    Text(stringResource(R.string.obchod))
                }
            )
        },
    ) { paddingValues ->
        LazyColumn(
            Modifier.padding(paddingValues)
        ) {
            items(typyBusu, key = { it.model }) { bus ->
                var expanded by remember { mutableStateOf(false) }
                Column(
                    Modifier
                        .animateItemPlacement()
                        .animateContentSize()
                        .clickable {
                            expanded = !expanded
                        },
                ) {
                    ListItem(
                        headlineContent = {
                            Text(bus.model)
                        },
                        trailingContent = {
                            if (!expanded) Text(bus.cena.asString(), style = MaterialTheme.typography.bodyLarge)
                        },
                        leadingContent = {
                            Image(
                                painterResource(bus.trakce.ikonka),
                                stringResource(R.string.ikonka_busiku),
                                Modifier
                                    .width(40.dp)
                                    .height(40.dp),
                                colorFilter = ColorFilter.tint(color = vse.tema.barva),
                            )
                        },
                    )
                    AnimatedVisibility(expanded) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = stringResource(bus.trakce.jmeno),
                                Modifier
                                    .fillMaxWidth()
                                    .padding(all = 8.dp),
                            )
                            Text(
                                text = stringResource(R.string.naklady, stringResource(bus.nakladyTextem)),
                                Modifier
                                    .fillMaxWidth()
                                    .padding(all = 8.dp),
                            )
                            Text(
                                text = bus.popis,
                                Modifier
                                    .fillMaxWidth()
                                    .padding(all = 8.dp),
                            )
                            Button(
                                onClick = {
                                    upravitDp {
                                        it.copy(
                                            busy = it.busy + Bus(Random.nextInt(), bus)
                                        )
                                    }
                                },
                                Modifier
                                    .fillMaxWidth()
                                    .padding(all = 8.dp),
                            ) {
                                Text(stringResource(id = R.string.koupit, bus.cena.value.formatovat().composeString()))
                            }
                        }
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}