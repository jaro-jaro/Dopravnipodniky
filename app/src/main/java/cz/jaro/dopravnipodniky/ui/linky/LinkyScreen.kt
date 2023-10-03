package cz.jaro.dopravnipodniky.ui.linky

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditRoad
import androidx.compose.material.icons.filled.FormatLineSpacing
import androidx.compose.material.icons.filled.GridGoldenratio
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.Vse
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.busy
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ulice
import cz.jaro.dopravnipodniky.dialogState
import cz.jaro.dopravnipodniky.shared.Menic
import cz.jaro.dopravnipodniky.shared.SharedViewModel
import cz.jaro.dopravnipodniky.shared.Smer
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.cenaTroleje
import cz.jaro.dopravnipodniky.shared.delkaUlice
import cz.jaro.dopravnipodniky.shared.je
import cz.jaro.dopravnipodniky.shared.jednotky.asString
import cz.jaro.dopravnipodniky.shared.sirkaUlice
import cz.jaro.dopravnipodniky.snackbarHostState
import cz.jaro.dopravnipodniky.ui.destinations.VytvareniLinkyScreenDestination
import cz.jaro.dopravnipodniky.ui.theme.Barvicka
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt


@Composable
@Destination
fun LinkyScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = koinViewModel<SharedViewModel>()

    val dp by viewModel.dp.collectAsStateWithLifecycle()
    val vse by viewModel.vse.collectAsStateWithLifecycle()

    if (
        dp != null &&
        vse != null
    ) LinkyScreen(
        dp = dp!!,
        vse = vse!!,
        menic = viewModel.menic,
        navigate = navigator::navigate,
        navigateBack = navigator::navigateUp
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LinkyScreen(
    dp: DopravniPodnik,
    vse: Vse,
    menic: Menic,
    navigate: (Direction) -> Unit,
    navigateBack: () -> Unit,
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
                            navigateBack()
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
                    navigate(VytvareniLinkyScreenDestination())
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
            if (dp.linky.isEmpty()) item {
                Text(stringResource(R.string.zadna_linka), Modifier.padding(8.dp))
            }
            items(dp.linky, key = { it.cislo }) { linka ->
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
                            val ctx = LocalContext.current
                            val scope = rememberCoroutineScope()
                            if (
                                !(vse.tutorial je StavTutorialu.Tutorialujeme.Uvod) &&
                                !(vse.tutorial je StavTutorialu.Tutorialujeme.Linky) &&
                                !(vse.tutorial je StavTutorialu.Tutorialujeme.Zastavky) &&
                                !(vse.tutorial je StavTutorialu.Tutorialujeme.Garaz) &&
                                !(vse.tutorial je StavTutorialu.Tutorialujeme.Obchod)
                            ) Row {
                                IconButton(
                                    onClick = {
                                        val ulicNaLince = linka.ulice.size
                                        val delkaLinky = ulicNaLince * (delkaUlice + sirkaUlice)
                                        val pocetBusu = linka.busy(dp).size

                                        if (pocetBusu == 0) {
                                            scope.launch {
                                                snackbarHostState.showSnackbar(
                                                    ctx.getString(R.string.pridejte_vozidla_na_linku),
                                                    duration = SnackbarDuration.Short,
                                                    withDismissAction = true,
                                                )
                                            }
                                            return@IconButton
                                        }

                                        val odstupy = (2 * delkaLinky) / pocetBusu.toFloat()

                                        menic.zmenitBusy {
                                            linka.busy(dp).forEachIndexed { i, bus ->
                                                val poziceOdZacatkuLinky = odstupy * i

                                                val jeDruhySmer = poziceOdZacatkuLinky / delkaLinky >= 1
                                                val indexUlice = (poziceOdZacatkuLinky % delkaLinky) / (delkaUlice + sirkaUlice)
                                                val poziceVUlici = poziceOdZacatkuLinky % (delkaUlice + sirkaUlice)

                                                val index = indexOfFirst { it.id == bus.id }

                                                this[index] = this[index].copy(
                                                    smerNaLince = if (!jeDruhySmer) Smer.Pozitivni else Smer.Negativni,
                                                    poziceNaLince = indexUlice.roundToInt() % linka.ulice.size,
                                                    poziceVUlici = poziceVUlici,
                                                )
                                            }
                                        }
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                ctx.getString(R.string.uspesne_rozmisteno),
                                                duration = SnackbarDuration.Short,
                                                withDismissAction = true,
                                            )
                                        }
                                        dialogState.hideTopMost()
                                    }
                                ) {
                                    Icon(Icons.Default.FormatLineSpacing, null)
                                }

                                var show by remember { mutableStateOf(false) }
                                IconButton(
                                    onClick = {
                                        show = !show
                                    }
                                ) {
                                    Icon(Icons.Default.MoreVert, null)
                                }
                                DropdownMenu(
                                    expanded = show,
                                    onDismissRequest = {
                                        show = false
                                    }
                                ) {
                                    var expanded by remember { mutableStateOf(false) }
                                    var cisloLinky by remember { mutableStateOf(linka.cislo) }
                                    var barva by remember { mutableStateOf(linka.barvicka) }
                                    DropdownMenuItem(
                                        text = {
                                            Text(stringResource(R.string.upravit_nazev))
                                        },
                                        onClick = {
                                            dialogState.show(
                                                confirmButton = {
                                                    TextButton(
                                                        onClick = {
                                                            if (cisloLinky.isEmpty()) {
                                                                Toast.makeText(ctx, R.string.spatne_cislo_linky, Toast.LENGTH_LONG).show()
                                                                return@TextButton
                                                            }
                                                            if (dp.linky.any { it.cislo == cisloLinky && it.cislo != linka.cislo }) {
                                                                Toast.makeText(ctx, R.string.linka_existuje, Toast.LENGTH_LONG).show()
                                                                return@TextButton
                                                            }

                                                            menic.zmenitLinky {
                                                                val i = indexOfFirst { it.id == linka.id }
                                                                this[i] = this[i].copy(
                                                                    cislo = cisloLinky,
                                                                    barvicka = barva,
                                                                )
                                                            }

                                                            dialogState.hideTopMost()
                                                        }
                                                    ) {
                                                        Text(stringResource(android.R.string.ok))
                                                    }
                                                },
                                                title = {
                                                    Text(stringResource(id = R.string.upravit_nazev2))
                                                },
                                                content = {
                                                    OutlinedTextField(
                                                        modifier = Modifier
                                                            .fillMaxWidth(),
                                                        value = cisloLinky,
                                                        onValueChange = {
                                                            cisloLinky = it
                                                        },
                                                        label = { Text(stringResource(R.string.zadejte_cislo_linky)) },
                                                    )
                                                    ExposedDropdownMenuBox(
                                                        expanded = expanded,
                                                        onExpandedChange = { expanded = !expanded },
                                                    ) {
                                                        OutlinedTextField(
                                                            modifier = Modifier
                                                                .menuAnchor()
                                                                .padding(top = 8.dp)
                                                                .fillMaxWidth(),
                                                            readOnly = true,
                                                            value = stringResource(barva.jmeno),
                                                            leadingIcon = {
                                                                Icon(Icons.Default.Circle, null, tint = barva.barva)
                                                            },
                                                            onValueChange = {},
                                                            label = { Text(stringResource(R.string.barva_linky)) },
                                                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                                            keyboardOptions = KeyboardOptions(
                                                                keyboardType = KeyboardType.Number,
                                                            )
                                                        )
                                                        ExposedDropdownMenu(
                                                            expanded = expanded,
                                                            onDismissRequest = { expanded = false },
                                                        ) {
                                                            Barvicka.entries.forEach { tahleBarva ->
                                                                DropdownMenuItem(
                                                                    text = { Text(stringResource(tahleBarva.jmeno)) },
                                                                    onClick = {
                                                                        barva = tahleBarva
                                                                        expanded = false
                                                                    },
                                                                    leadingIcon = {
                                                                        Icon(Icons.Default.Circle, null, tint = tahleBarva.barva)
                                                                    },
                                                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                                                )
                                                            }
                                                        }
                                                    }
                                                },
                                            )
                                            show = false
                                        },
                                        leadingIcon = {
                                            Icon(Icons.Default.Edit, null)
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = {
                                            Text(stringResource(R.string.upravit_vedeni))
                                        },
                                        onClick = {
                                            navigate(VytvareniLinkyScreenDestination(upravovani = linka.id))
                                            show = false
                                        },
                                        leadingIcon = {
                                            Icon(Icons.Default.EditRoad, null)
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = {
                                            Text(stringResource(R.string.pridat_troleje_linka))
                                        },
                                        onClick = {
                                            dialogState.show(
                                                confirmButton = {
                                                    TextButton(
                                                        onClick = {
                                                            if (vse.prachy < cenaTroleje * linka.ulice(dp).count { !it.maTrolej }) {
                                                                scope.launch {
                                                                    snackbarHostState.showSnackbar(
                                                                        ctx.getString(R.string.malo_penez),
                                                                        duration = SnackbarDuration.Short,
                                                                        withDismissAction = true,
                                                                    )
                                                                }
                                                                dialogState.hideTopMost()
                                                                return@TextButton
                                                            }

                                                            menic.zmenitPrachy {
                                                                it - cenaTroleje * linka.ulice(dp).count { !it.maTrolej }
                                                            }

                                                            menic.zmenitUlice {
                                                                linka.ulice(dp).forEach { ulice ->
                                                                    if (!ulice.maTrolej) {
                                                                        val i = this.indexOfFirst { it.id == ulice.id }
                                                                        this[i] = this[i].copy(
                                                                            maTrolej = true
                                                                        )
                                                                    }
                                                                }
                                                            }
                                                            scope.launch {
                                                                snackbarHostState.showSnackbar(
                                                                    ctx.getString(R.string.uspesne_pridany_troleje),
                                                                    duration = SnackbarDuration.Short,
                                                                    withDismissAction = true,
                                                                )
                                                            }
                                                            dialogState.hideTopMost()
                                                        }
                                                    ) {
                                                        Text(stringResource(android.R.string.ok))
                                                    }
                                                },
                                                content = {
                                                    Text(
                                                        stringResource(
                                                            R.string.pridat_troleje_nebo_zastavky_linka_dialog,
                                                            (cenaTroleje * linka.ulice(dp).count { !it.maTrolej }).asString(),
                                                            stringResource(R.string.troleje)
                                                        )
                                                    )
                                                },
                                                title = {
                                                    Text(stringResource(R.string.pridat_troleje_linka))
                                                },
                                            )
                                            show = false
                                        },
                                        leadingIcon = {
                                            Icon(Icons.Default.GridGoldenratio, null)
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = {
                                            Text(stringResource(R.string.odstranit_linku))
                                        },
                                        onClick = {
                                            dialogState.show(
                                                confirmButton = {
                                                    TextButton(
                                                        onClick = {
                                                            menic.zmenitBusy {
                                                                forEachIndexed { i, it ->
                                                                    if (it.linka == linka.id) this[i] = it.copy(
                                                                        linka = null
                                                                    )
                                                                }
                                                            }
                                                            menic.zmenitLinky {
                                                                remove(linka)
                                                            }
                                                            dialogState.hideTopMost()
                                                        }
                                                    ) {
                                                        Text(stringResource(android.R.string.ok))
                                                    }
                                                },
                                                icon = {
                                                    Icon(Icons.Default.Delete, null)
                                                },
                                                title = {
                                                    Text(stringResource(R.string.odstranit_linku))
                                                },
                                                content = {
                                                    Text(stringResource(R.string.jste_si_vedomi_odstraneni_linky))
                                                },
                                                dismissButton = {
                                                    TextButton(
                                                        onClick = {
                                                            dialogState.hideTopMost()
                                                        }
                                                    ) {
                                                        Text(stringResource(R.string.zrusit))
                                                    }
                                                }
                                            )
                                            show = false
                                        },
                                        leadingIcon = {
                                            Icon(Icons.Default.Delete, null)
                                        }
                                    )
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

private operator fun Dp.rem(other: Dp) = Dp(value % other.value)
