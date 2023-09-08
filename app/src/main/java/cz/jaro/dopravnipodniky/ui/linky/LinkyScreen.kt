package cz.jaro.dopravnipodniky.ui.linky

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FormatLineSpacing
import androidx.compose.material.icons.filled.GridGoldenratio
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.shared.SharedViewModel
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.je
import cz.jaro.dopravnipodniky.ui.destinations.VytvareniLinkyScreenDestination
import org.koin.androidx.compose.koinViewModel
import kotlin.reflect.KClass


@Composable
@Destination
fun LinkyScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = koinViewModel<SharedViewModel>()

    val linky by viewModel.linky.collectAsStateWithLifecycle()
    val tutorial by viewModel.tutorial.collectAsStateWithLifecycle()

    if (
        linky != null &&
        tutorial != null
    ) LinkyScreen(
        linky = linky!!,
        tutorial = tutorial!!,
        zmenitLinky = viewModel::zmenitLinky,
        navigatate = navigator::navigate,
        navigatateBack = navigator::navigateUp,
        dosahni = viewModel.dosahni
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LinkyScreen(
    linky: List<Linka>,
    tutorial: StavTutorialu,
    zmenitLinky: (MutableList<Linka>.() -> Unit) -> Unit,
    navigatate: (Direction) -> Unit,
    navigatateBack: () -> Unit,
    dosahni: (KClass<out Dosahlost>) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.linky))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigatateBack()
                        }
                    ) {
                        Icon(Icons.Default.ArrowBack, stringResource(R.string.zpet))
                    }
                },
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(stringResource(R.string.nova_linka))
                },
                icon = {
                    Icon(Icons.Default.Add, null)
                },
                onClick = {
                    navigatate(VytvareniLinkyScreenDestination)
                },
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { paddingValues ->
        LazyColumn(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (linky.isEmpty()) item {
                Text(stringResource(R.string.zadna_linka), Modifier.padding(8.dp))
            }
            items(linky, key = { it.cislo }) { linka ->
                Column(
                    Modifier
                        .animateItemPlacement()
                        .animateContentSize(),
                ) {
                    ListItem(
                        headlineContent = {
                            Text(linka.cislo)
                        },
                        trailingContent = {
                            if (
                                !(tutorial je StavTutorialu.Tutorialujeme.Uvod) &&
                                !(tutorial je StavTutorialu.Tutorialujeme.Linky) &&
                                !(tutorial je StavTutorialu.Tutorialujeme.Zastavky) &&
                                !(tutorial je StavTutorialu.Tutorialujeme.Garaz) &&
                                !(tutorial je StavTutorialu.Tutorialujeme.Obchod)
                            ) Row {
                                Icon(Icons.Default.FormatLineSpacing, null)
                                Icon(Icons.Default.GridGoldenratio, null)
                                IconButton(
                                    onClick = {
                                        zmenitLinky {
                                            remove(linka)
                                        }
                                    }
                                ) {
                                    Icon(Icons.Default.Delete, null)
                                }
                            }
                        },
                        leadingContent = {
                            Icon(
                                Icons.Default.Timeline,
                                stringResource(R.string.ikonka_busiku),
                                Modifier
                                    .width(40.dp)
                                    .height(40.dp),
                                tint = linka.barvicka.barva,
                            )
                        },
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}