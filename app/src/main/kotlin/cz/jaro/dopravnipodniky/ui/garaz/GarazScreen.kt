package cz.jaro.dopravnipodniky.ui.garaz

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Euro
import androidx.compose.material.icons.filled.SelectAll
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import cz.jaro.compose_dialog.show
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.Vse
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.StavZastavky
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Trakce
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ikonka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.jsouVsechnyZatrolejovane
import cz.jaro.dopravnipodniky.data.dopravnipodnik.linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.rozmistitBusy
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ulice
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.dialogState
import cz.jaro.dopravnipodniky.shared.BusID
import cz.jaro.dopravnipodniky.shared.Menic
import cz.jaro.dopravnipodniky.shared.SharedViewModel
import cz.jaro.dopravnipodniky.shared.Smer
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.barvaNepouzivanehoBusu
import cz.jaro.dopravnipodniky.shared.composeString
import cz.jaro.dopravnipodniky.shared.formatovat
import cz.jaro.dopravnipodniky.shared.je
import cz.jaro.dopravnipodniky.shared.jednotky.asString
import cz.jaro.dopravnipodniky.shared.jednotky.sumOfPeniz
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

    var vybraneBusy by rememberSaveable { mutableStateOf(null as Set<String>?) }

    if (
        dp != null &&
        vse != null
    ) GarazScreen(
        dp = dp!!,
        vse = vse!!,
        menic = viewModel.menic,
        navigate = navigator::navigate,
        navigateBack = navigator::navigateUp,
        dosahni = viewModel.dosahni,
        vybraneBusy = vybraneBusy,
        vybratBusy = scope@{ busy ->
            vybraneBusy = (vybraneBusy ?: return@scope) + busy.map { it.toString() }
        },
        prepnoutBus = scope@{
            vybraneBusy = vybraneBusy ?: setOf()

            vybraneBusy = if (it.toString() in vybraneBusy!!) {
                vybraneBusy!! - it.toString()
            } else {
                vybraneBusy!! + it.toString()
            }

            if (vybraneBusy!!.isEmpty()) vybraneBusy = null
        },
        zavritVsechny = {
            vybraneBusy = null
        },
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
    vybraneBusy: Set<String>?,
    prepnoutBus: (BusID) -> Unit,
    vybratBusy: (List<BusID>) -> Unit,
    zavritVsechny: () -> Unit,
) {

    val bg = MaterialTheme.colorScheme.background
    val sec = MaterialTheme.colorScheme.secondaryContainer

    val view = LocalView.current

    LaunchedEffect(vybraneBusy) {
        if (!view.isInEditMode) {
            val window = (view.context as? Activity)?.window ?: return@LaunchedEffect
            window.statusBarColor = if (vybraneBusy == null) bg.toArgb() else sec.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    BackHandler(vybraneBusy != null) {
        zavritVsechny()
    }

    Scaffold(
        topBar = {
            if (vybraneBusy != null) {
                TopAppBar(
                    title = {
                        Text(stringResource(R.string.vybrano, vybraneBusy.size))
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                zavritVsechny()
                            }
                        ) {
                            Icon(Icons.Default.Check, stringResource(R.string.zpet))
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                vybratBusy(dp.busy.map { it.id })
                            }
                        ) {
                            Icon(Icons.Default.SelectAll, stringResource(R.string.vybrat_vse))
                        }

                        CoMuzesDelatBusum(
                            dp = dp,
                            vybraneBusy = vybraneBusy,
                            menic = menic,
                            zavritVsechny = zavritVsechny,
                            dosahni = dosahni
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                )
            } else {
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
            }
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
                            .combinedClickable(
                                onLongClick = {
                                    otevreno = null
                                    prepnoutBus(bus.id)
                                }
                            ) {
                                if (vybraneBusy != null) prepnoutBus(bus.id)
                                else otevreno = if (expanded) null else bus.evCislo
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
                                                                hide()
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

                                                            hide()

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
//                                    Icon(Icons.Default.LocationSearching, null)
                                }
                            },
                            leadingContent = {
                                if (vybraneBusy != null) {
                                    Box(
                                        Modifier
                                            .width(48.dp)
                                            .height(48.dp),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Icon(
                                            if (bus.id.toString() in vybraneBusy) Icons.Outlined.CheckCircle else Icons.Outlined.Circle,
                                            ""
                                        )
                                    }
                                } else {
                                    Image(
                                        painterResource(bus.typBusu.trakce.ikonka),
                                        stringResource(R.string.ikonka_busiku),
                                        Modifier
                                            .width(48.dp)
                                            .height(48.dp)
                                            .clip(CircleShape)
                                            .clickable {
                                                otevreno = null
                                                prepnoutBus(bus.id)
                                            },
                                        colorFilter = ColorFilter.tint(color = linka?.barvicka?.barva ?: barvaNepouzivanehoBusu),
                                    )
                                }
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
                                                            hide()
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
                                                                hide()
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
                                                                    it + bus.prodejniCena
                                                                }

                                                                hide()

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
                                                            hide()
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

@Composable
private fun CoMuzesDelatBusum(
    dp: DopravniPodnik,
    vybraneBusy: Set<String>,
    menic: Menic,
    zavritVsechny: () -> Unit,
    dosahni: (KClass<out Dosahlost>) -> Unit
) {
    IconButton(
        onClick = {
            dialogState.show(
                confirmButton = {
                    val prodejniBusy = remember(dp.busy, vybraneBusy) {
                        dp.busy.filter { it.id.toString() in vybraneBusy }
                    }
                    val prodejniCena = remember(prodejniBusy) {
                        prodejniBusy.sumOfPeniz { it.prodejniCena }
                    }
                    val prodaciZprava = stringResource(
                        R.string.prodali_jste,
                        "${vybraneBusy.size} ${
                            pluralStringResource(R.plurals.vozidlo2p, vybraneBusy.size)
                        }",
                        prodejniCena.asString()
                    )
                    TextButton(
                        onClick = {
                            MainScope().launch {
                                menic.zmenitUlice {
                                    prodejniBusy.forEach { bus ->
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
                                }
                                menic.zmenitBusy {
                                    prodejniBusy.forEach { bus ->
                                        val i = indexOfFirst { it.id == bus.id }
                                        removeAt(i)
                                    }
                                }

                                menic.zmenitPrachy {
                                    it + prodejniCena
                                }

                                hide()

                                zavritVsechny()

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
                            hide()
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
        }
    ) {
        Icon(Icons.Default.Euro, stringResource(R.string.prodat))
    }

    val ctx = LocalContext.current

    IconButton(
        onClick = {
            val prodejniBusy = dp.busy.filter { it.id.toString() in vybraneBusy }

            val pouzitelneLinky = when {
                prodejniBusy.any { it.typBusu.trakce is Trakce.Trolejbus } -> dp.linky.filter {
                    it.ulice(dp).jsouVsechnyZatrolejovane()
                }

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
                                val prodejniLinky = prodejniBusy.mapNotNull { it.linka }.distinct()
                                prodejniBusy.forEach { bus ->
                                    replaceBy(bus.copy(linka = null)) { it.id }
                                }
                                prodejniLinky.forEach {
                                    apply(dp.linka(it).rozmistitBusy)
                                }
                            }
                            zavritVsechny()
                            hide()
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
                                    val prodejniLinky = prodejniBusy.mapNotNull { it.linka }.distinct()
                                    prodejniBusy.forEach { bus ->
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
                                    prodejniLinky.forEach {
                                        apply(dp.linka(it).rozmistitBusy)
                                    }
                                    apply(linka.rozmistitBusy)
                                }
                                dosahni(Dosahlost.BusNaLince::class)
                                zavritVsechny()
                                hide()
                            },
                            leadingContent = {
                                Icon(Icons.Default.Timeline, null, tint = linka.barvicka.barva)
                            },
                        )
                    }
                },
            )
        }
    ) {
        Icon(Icons.Default.Timeline, stringResource(R.string.vypravit))
    }
}