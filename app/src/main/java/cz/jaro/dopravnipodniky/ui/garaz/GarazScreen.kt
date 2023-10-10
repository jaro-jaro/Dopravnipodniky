package cz.jaro.dopravnipodniky.ui.garaz

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Euro
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.Vse
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.StavZastavky
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Trakce
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ikonka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.jsouVsechnyZatrolejovane
import cz.jaro.dopravnipodniky.data.dopravnipodnik.linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ulice
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.dialogState
import cz.jaro.dopravnipodniky.shared.Menic
import cz.jaro.dopravnipodniky.shared.SharedViewModel
import cz.jaro.dopravnipodniky.shared.Smer
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.barvaNepouzivanehoBusu
import cz.jaro.dopravnipodniky.shared.composeString
import cz.jaro.dopravnipodniky.shared.formatovat
import cz.jaro.dopravnipodniky.shared.je
import cz.jaro.dopravnipodniky.shared.jednotky.asString
import cz.jaro.dopravnipodniky.shared.replaceBy
import cz.jaro.dopravnipodniky.snackbarHostState
import cz.jaro.dopravnipodniky.ui.destinations.ObchodScreenDestination
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.reflect.KClass

@Composable
@Destination
fun GarazScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = koinViewModel<SharedViewModel>()

    LaunchedEffect(Unit) {
        viewModel.menic.zmenitTutorial {
            if (it je StavTutorialu.Tutorialujeme.Obchod)
                StavTutorialu.Tutorialujeme.Vypraveni
            else it
        }
    }

    val dp by viewModel.dp.collectAsStateWithLifecycle()
    val vse by viewModel.vse.collectAsStateWithLifecycle()

    if (
        dp != null &&
        vse != null
    ) GarazScreen(
        dp = dp!!,
        vse = vse!!,
        menic = viewModel.menic,
        navigate = navigator::navigate,
        navigateBack = navigator::navigateUp,
        dosahni = viewModel.dosahni
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GarazScreen(
    dp: DopravniPodnik,
    vse: Vse,
    menic: Menic,
    navigate: (Direction) -> Unit,
    navigateBack: () -> Unit,
    dosahni: (KClass<out Dosahlost>) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.garaz))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigateBack()
                        }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.zpet))
                    }
                },
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(stringResource(R.string.garaz_koupit_vozidlo))
                },
                icon = {
                    Icon(Icons.Default.AddShoppingCart, null)
                },
                onClick = {
                    navigate(ObchodScreenDestination)
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { paddingValues ->
        var otevreno by rememberSaveable { mutableStateOf(null as Int?) }
        Column(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Row(
                Modifier.fillMaxWidth(),
            ) {
                if (
                    !(vse.tutorial je StavTutorialu.Tutorialujeme.Uvod)
                ) Text(
                    text = vse.prachy.asString(),
                    Modifier
                        .weight(1F)
                        .padding(all = 16.dp),
                    textAlign = TextAlign.Start,
                )

                if (
                    !(vse.tutorial je StavTutorialu.Tutorialujeme.Uvod) &&
                    !(vse.tutorial je StavTutorialu.Tutorialujeme.Linky) &&
                    !(vse.tutorial je StavTutorialu.Tutorialujeme.Zastavky) &&
                    !(vse.tutorial je StavTutorialu.Tutorialujeme.Garaz) &&
                    !(vse.tutorial je StavTutorialu.Tutorialujeme.Obchod)
                ) Text(
                    text = stringResource(R.string.pocet_busu, dp.busy.count { it.linka != null }, dp.busy.size),
                    Modifier
                        .weight(1F)
                        .padding(all = 16.dp),
                    textAlign = TextAlign.End,
                )
            }
            LazyColumn(
                Modifier.weight(1F)
            ) {
                if (dp.busy.isEmpty()) item {
                    Text(stringResource(R.string.zadny_bus), Modifier.padding(8.dp))
                }
                items(dp.busy.sortedBy { it.evCislo }, key = { it.evCislo }) { bus ->
                    val expanded = otevreno == bus.evCislo
                    Column(
                        Modifier
                            .animateItemPlacement()
                            .animateContentSize()
                            .clickable {
                                otevreno = if (expanded) null else bus.evCislo
                            },
                    ) {
                        val linka = bus.linka?.let { dp.linka(it) }
                        ListItem(
                            headlineContent = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(stringResource(R.string.ev_c, bus.evCislo))
                                    if (linka != null) Text(" " + stringResource(R.string.linka_tohle, linka.cislo))
                                }
                            },
                            trailingContent = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    var evc by rememberSaveable { mutableStateOf(bus.evCislo.toString()) }
                                    val ctx = LocalContext.current
                                    if (expanded) IconButton(
                                        onClick = {
                                            dialogState.show(
                                                confirmButton = {
                                                    TextButton(
                                                        enabled = evc.isNotEmpty() && evc.toIntOrNull() != null && evc.toInt() >= 1,
                                                        onClick = {
                                                            if (evc.toInt() == bus.evCislo) {
                                                                dialogState.hideTopMost()
                                                                return@TextButton
                                                            }

                                                            if (dp.busy.any { it.evCislo == evc.toInt() }) {
                                                                Toast.makeText(
                                                                    ctx,
                                                                    ctx.getString(R.string.ev_c_existuje),
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                                return@TextButton
                                                            }

                                                            dialogState.hideTopMost()

                                                            menic.zmenitBusy {
                                                                val i = indexOfFirst { it.id == bus.id }
                                                                this[i] = this[i].copy(
                                                                    evCislo = evc.toInt()
                                                                )
                                                            }
                                                        }
                                                    ) {
                                                        Text(stringResource(android.R.string.ok))
                                                    }
                                                },
                                                title = {
                                                    Text(stringResource(R.string.upravte_ev_c, stringResource(bus.typBusu.trakce.jmeno)))
                                                },
                                                content = {
                                                    OutlinedTextField(
                                                        value = evc,
                                                        onValueChange = {
                                                            evc = it
                                                        },
                                                        Modifier.fillMaxWidth(),
                                                        label = {
                                                            Text(
                                                                stringResource(
                                                                    id = R.string.ev_c_busu,
                                                                    stringResource(bus.typBusu.trakce.jmeno)
                                                                )
                                                            )
                                                        },
                                                        keyboardOptions = KeyboardOptions(
                                                            keyboardType = KeyboardType.Number,
                                                            imeAction = ImeAction.Done,
                                                        )
                                                    )
                                                },
                                            )
                                        }
                                    ) {
                                        Icon(Icons.Default.Edit, null)
                                    }
                                    Icon(Icons.Default.LocationSearching, null)
                                }
                            },
                            leadingContent = {
                                Image(
                                    painterResource(bus.typBusu.trakce.ikonka),
                                    stringResource(R.string.ikonka_busiku),
                                    Modifier
                                        .width(40.dp)
                                        .height(40.dp),
                                    colorFilter = ColorFilter.tint(color = linka?.barvicka?.barva ?: barvaNepouzivanehoBusu),
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
                                    text = stringResource(bus.typBusu.trakce.jmeno),
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(all = 8.dp),
                                )
                                Text(
                                    text = bus.typBusu.model,
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(all = 8.dp),
                                )
                                Text(
                                    text = stringResource(R.string.ponicenost, bus.ponicenost.times(100).formatovat().composeString()),
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(all = 8.dp),
                                )
                                Text(
                                    text = stringResource(R.string.naklady, bus.naklady.asString()),
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(all = 8.dp),
                                )
                                val ctx = LocalContext.current

                                Row(
                                    Modifier.fillMaxWidth(),
                                ) {
                                    Button(
                                        onClick = {

                                            val pouzitelneLinky = when (bus.typBusu.trakce) {
                                                is Trakce.Trolejbus -> dp.linky.filter { it.ulice(dp).jsouVsechnyZatrolejovane() }
                                                else -> dp.linky
                                            }

                                            if (pouzitelneLinky.isEmpty()) {
                                                Toast.makeText(ctx, R.string.nejprve_si_vytvorte_linku, Toast.LENGTH_SHORT).show()
                                            } else dialogState.show(
                                                confirmButton = { },
                                                dismissButton = {
                                                    TextButton(
                                                        onClick = {
                                                            menic.zmenitBusy {
                                                                replaceBy(bus.copy(linka = null)) { it.id }
                                                            }
                                                            dialogState.hideTopMost()
                                                        }
                                                    ) {
                                                        Text(stringResource(R.string.odebrat_bus_z_linek))
                                                    }
                                                },
                                                title = {
                                                    Text(stringResource(R.string.vyberte_linku))
                                                },
                                                content = {
                                                    pouzitelneLinky.forEach { linka ->
                                                        ListItem(
                                                            headlineContent = {
                                                                Text(linka.cislo)
                                                            },
                                                            Modifier.clickable {
                                                                menic.zmenitBusy {
                                                                    replaceBy(
                                                                        bus.copy(
                                                                            linka = linka.id,
                                                                            poziceNaLince = 0,
                                                                            poziceVUlici = 0.dp,
                                                                            smerNaLince = Smer.Pozitivni,
                                                                            stavZastavky = StavZastavky.Pred
                                                                        )
                                                                    ) { it.id }
                                                                }
                                                                dosahni(Dosahlost.BusNaLince::class)
                                                                dialogState.hideTopMost()
                                                            },
                                                            leadingContent = {
                                                                Icon(Icons.Default.Timeline, null, tint = linka.barvicka.barva)
                                                            },
                                                        )
                                                    }
                                                },
                                            )
                                        },
                                        Modifier
                                            .padding(all = 8.dp),
                                    ) {
                                        Text(
                                            text = stringResource(if (bus.linka == null) R.string.vypravit else R.string.zmenit_linku)
                                        )
                                    }
                                    Spacer(modifier = Modifier.weight(1F))
                                    OutlinedButton(
                                        onClick = {
                                            dialogState.show(
                                                confirmButton = {
                                                    val prodaciZprava = stringResource(
                                                        R.string.prodali_jste,
                                                        stringResource(bus.typBusu.trakce.jmeno),
                                                        bus.prodejniCena.asString()
                                                    )
                                                    TextButton(
                                                        onClick = {
                                                            MainScope().launch {

                                                                menic.zmenitUlice {
                                                                    var cloveci = bus.cloveci
                                                                    val noveUlice = shuffled().map { ulice ->
                                                                        var cloveciVUlici = ulice.cloveci
                                                                        while (cloveciVUlici < ulice.kapacita && cloveci > 0) {

                                                                            cloveci--
                                                                            cloveciVUlici++
                                                                        }

                                                                        ulice.copy(
                                                                            cloveci = cloveciVUlici
                                                                        )
                                                                    }

                                                                    clear()
                                                                    addAll(noveUlice)
                                                                }
                                                                menic.zmenitBusy {
                                                                    val i = indexOfFirst { it.id == bus.id }
                                                                    removeAt(i)
                                                                }
                                                                menic.zmenitPrachy {
                                                                    it - bus.prodejniCena
                                                                }

                                                                dialogState.hideTopMost()

                                                                snackbarHostState.showSnackbar(
                                                                    message = prodaciZprava,
                                                                    withDismissAction = true,
                                                                )
                                                            }
                                                        }
                                                    ) {
                                                        Text(stringResource(android.R.string.ok))
                                                    }
                                                },
                                                dismissButton = {
                                                    TextButton(
                                                        onClick = {
                                                            dialogState.hideTopMost()
                                                        }
                                                    ) {
                                                        Text(stringResource(R.string.zrusit))
                                                    }
                                                },
                                                icon = {
                                                    Icon(Icons.Default.Euro, null)
                                                },
                                                title = {
                                                    Text(stringResource(R.string.prodat))
                                                },
                                                content = {
                                                    Text(stringResource(R.string.fakt_chcete_prodat_bus))
                                                },
                                            )
                                        },
                                        Modifier
                                            .padding(all = 8.dp),
                                    ) {
                                        Text(
                                            text = stringResource(R.string.prodat_za, bus.prodejniCena.asString())
                                        )
                                    }
                                }
                            }
                        }
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}