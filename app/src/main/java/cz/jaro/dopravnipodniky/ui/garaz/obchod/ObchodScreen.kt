package cz.jaro.dopravnipodniky.ui.garaz.obchod

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Help
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.Vse
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Bus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Trakce
import cz.jaro.dopravnipodniky.data.dopravnipodnik.TypBusu
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ikonka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.jsouVsechnyZatrolejovane
import cz.jaro.dopravnipodniky.data.dopravnipodnik.nakladyTextem
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ulice
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.dialogState
import cz.jaro.dopravnipodniky.shared.LinkaID
import cz.jaro.dopravnipodniky.shared.Menic
import cz.jaro.dopravnipodniky.shared.SharedViewModel
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.composeString
import cz.jaro.dopravnipodniky.shared.formatovat
import cz.jaro.dopravnipodniky.shared.je
import cz.jaro.dopravnipodniky.shared.jednotky.asString
import cz.jaro.dopravnipodniky.shared.toText
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.reflect.KClass

@Composable
@Destination
fun ObchodScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = koinViewModel<SharedViewModel>()

    val filtrovaneBusy by viewModel.filtrovaneBusy.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.menic.zmenitTutorial {
            if (it je StavTutorialu.Tutorialujeme.Garaz)
                StavTutorialu.Tutorialujeme.Obchod
            else it
        }
    }

    val dp by viewModel.dp.collectAsStateWithLifecycle()
    val vse by viewModel.vse.collectAsStateWithLifecycle()

    if (
        dp != null &&
        vse != null
    ) ObchodScreen(
        filtrovaneBusy = filtrovaneBusy,
        dp = dp!!,
        vse = vse!!,
        menic = viewModel.menic,
        navigateBack = navigator::navigateUp,
        dosahni = viewModel.dosahni,
    )
}

enum class Zobrait {
    Filtry,
    Razeni,
    Vysledky,
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ObchodScreen(
    filtrovaneBusy: Sequence<TypBusu>,
    dp: DopravniPodnik,
    vse: Vse,
    menic: Menic,
    navigateBack: () -> Unit,
    dosahni: (KClass<out Dosahlost>) -> Unit,
) {
    var stav by rememberSaveable { mutableStateOf(Zobrait.Vysledky) }
    BackHandler {
        when (stav) {
            Zobrait.Filtry -> stav = Zobrait.Vysledky
            Zobrait.Razeni -> stav = Zobrait.Vysledky
            Zobrait.Vysledky -> navigateBack()
        }
    }
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.obchod))
                },
                actions = {
                    if (vse.tutorial je StavTutorialu.Tutorialujeme.Obchod) IconButton(
                        onClick = {
                            menic.zmenitTutorial {
                                StavTutorialu.Tutorialujeme.Obchod
                            }
                        }
                    ) {
                        Icon(Icons.Default.Help, stringResource(R.string.tutorial))
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            when (stav) {
                                Zobrait.Filtry -> stav = Zobrait.Vysledky
                                Zobrait.Razeni -> stav = Zobrait.Vysledky
                                Zobrait.Vysledky -> navigateBack()
                            }
                        }
                    ) {
                        Icon(Icons.Default.ArrowBack, stringResource(R.string.zpet))
                    }
                },
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { paddingValues ->
        var otevreno by rememberSaveable { mutableStateOf(null as String?) }

        Column(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Row(
                Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = vse.prachy.asString(),
                    Modifier
                        .weight(1F)
                        .padding(all = 16.dp),
                    textAlign = TextAlign.Start,
                )

                Text(
                    text = stringResource(R.string.zobrazeno, filtrovaneBusy.count()),
                    Modifier
                        .weight(1F)
                        .padding(all = 16.dp),
                    textAlign = TextAlign.End,
                )
            }
            LazyColumn(
                Modifier
                    .weight(1F)
            ) {
                stickyHeader {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(8.dp),
                    ) {
                        TextButton(
                            onClick = {
                                stav = if (stav == Zobrait.Filtry) Zobrait.Vysledky else Zobrait.Filtry
                            }
                        ) {
                            Text(stringResource(if (stav == Zobrait.Filtry) R.string.schovat_viltry else R.string.filtrovat))
                        }
                        Spacer(modifier = Modifier.weight(1F))
                        TextButton(
                            onClick = {
                                stav = if (stav == Zobrait.Razeni) Zobrait.Vysledky else Zobrait.Razeni
                            }
                        ) {
                            Text(stringResource(if (stav == Zobrait.Razeni) R.string.schovat_razeni else R.string.radit))
                        }
                    }
                }
                if (stav == Zobrait.Filtry) items(SkupinaFiltru.skupinyFiltru) { skupina ->
                    Text(
                        text = skupina.nazev.composeString(),
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                    FlowRow(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        val filtry =
                            if (skupina is SkupinaFiltru.Cena) SkupinaFiltru.Cena.filtry + SkupinaFiltru.Cena.MamNaTo else skupina.filtry
                        filtry.forEach { filtr ->
                            FilterChip(
                                selected = filtr in vse.nastaveni.filtry,
                                onClick = {
                                    menic.zmenitNastaveni {
                                        it.copy(
                                            filtry = if (filtr in vse.nastaveni.filtry) vse.nastaveni.filtry - filtr else vse.nastaveni.filtry + filtr
                                        )
                                    }
                                },
                                label = {
                                    Text(filtr.nazevFiltru.composeString())
                                },
                                Modifier.padding(end = 8.dp, bottom = 8.dp),
                            )
                        }
                    }
                }
                if (stav == Zobrait.Razeni) items(Razeni.razeni) { skupina ->
                    Text(
                        skupina.first().nazev.composeString(),
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                    FlowRow(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        skupina.forEachIndexed { i, razeni ->
                            FilterChip(
                                selected = vse.nastaveni.razeni == razeni,
                                onClick = {
                                    menic.zmenitNastaveni {
                                        it.copy(
                                            razeni = if (vse.nastaveni.razeni == razeni) Razeni.Zadne else razeni
                                        )
                                    }
                                },
                                label = {
                                    Text(stringResource(if (i % 2 == 0) R.string.sestupne else R.string.vzestupne))
                                },
                                Modifier.padding(end = 8.dp, bottom = 8.dp),
                            )
                        }
                    }
                }
                if (stav == Zobrait.Vysledky) items(filtrovaneBusy.toList(), key = { it.model }) { typBusu ->
                    val expanded = otevreno == typBusu.model
                    Column(
                        Modifier
                            .animateItemPlacement()
                            .animateContentSize()
                            .clickable {
                                otevreno = if (expanded) null else typBusu.model
                            },
                    ) {
                        ListItem(
                            headlineContent = {
                                Text(typBusu.model)
                            },
                            trailingContent = {
                                if (!expanded) Text(typBusu.cena.asString(), style = MaterialTheme.typography.bodyLarge)
                            },
                            leadingContent = {
                                Image(
                                    painterResource(typBusu.trakce.ikonka),
                                    stringResource(R.string.ikonka_busiku),
                                    Modifier
                                        .width(40.dp)
                                        .height(40.dp),
                                    colorFilter = ColorFilter.tint(color = dp.info.tema.barva),
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
                                    text = stringResource(typBusu.trakce.jmeno),
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(all = 8.dp),
                                )
                                Text(
                                    text = stringResource(R.string.bus_ma_naklady, stringResource(typBusu.nakladyTextem)),
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(all = 8.dp),
                                )
                                Text(
                                    text = typBusu.popis,
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(all = 8.dp),
                                )

                                val scope = rememberCoroutineScope()
                                val ctx = LocalContext.current
                                val maloPenez = stringResource(R.string.malo_penez)
                                val zadejteValidniPocet = stringResource(R.string.zadejte_validni_pocet)
                                val bohuzelNeVicNez50 = stringResource(R.string.bohuzel_ne_vic_nez_50)
                                val evCExistuje = stringResource(R.string.ev_c_existuje)

                                suspend fun koupit(seznamEvC: List<Int>, naLinku: LinkaID?) {
                                    if (typBusu.cena * seznamEvC.size > vse.prachy) {

                                        snackbarHostState.showSnackbar(
                                            message = maloPenez,
                                        )
                                        return
                                    }

                                    val novyBusy = seznamEvC.map { evC ->
                                        if (naLinku != null) {
                                            dosahni(Dosahlost.BusNaLince::class)
                                        }
                                        dosahni(Dosahlost.SkupinovaDosahlost.Bus::class)

                                        Bus(
                                            evCislo = evC,
                                            typBusu = typBusu,
                                            linka = naLinku,
                                        )
                                    }
                                    menic.zmenitPrachy {
                                        it - typBusu.cena * seznamEvC.size
                                    }
                                    menic.zmenitBusy {
                                        addAll(novyBusy)
                                    }

//                                Toast.makeText(ctx, ctx.getString(R.string.uspesne_koupeno_tolik_busuu, seznamEvC.size, trakce), Toast.LENGTH_SHORT).show()
                                }

                                var pocet by rememberSaveable { mutableStateOf("") }
                                fun zeptatSeNaPocet(callback: (String) -> Unit) {
                                    dialogState.show(
                                        confirmButton = {
                                            TextButton(
                                                onClick = {
                                                    dialogState.hideTopMost()
                                                    callback(pocet)
                                                }
                                            ) {
                                                Text(stringResource(android.R.string.ok))
                                            }
                                        },
                                        title = {
                                            Text(stringResource(R.string.nadpis_vicenasobne_kupovani))
                                        },
                                        content = {
                                            OutlinedTextField(
                                                value = pocet,
                                                onValueChange = {
                                                    pocet = it
                                                },
                                                Modifier.fillMaxWidth(),
                                                label = {
                                                    Text(stringResource(id = R.string.pocet_busuu, stringResource(typBusu.trakce.jmeno)))
                                                },
                                                keyboardOptions = KeyboardOptions(
                                                    keyboardType = KeyboardType.Number,
                                                    imeAction = ImeAction.Done,
                                                )
                                            )
                                        },
                                    )
                                }
                                fun vybratLinku(callback: (LinkaID?) -> Unit) {
                                    val pouzitelneLinky = when (typBusu.trakce) {
                                        is Trakce.Trolejbus -> dp.linky.filter { it.ulice(dp).jsouVsechnyZatrolejovane() }
                                        else -> dp.linky
                                    }

                                    if (pouzitelneLinky.isEmpty()) {
                                        dialogState.hideTopMost()
                                        callback(null)
                                    }
                                    else dialogState.show(
                                        confirmButton = {},
                                        dismissButton = {
                                            TextButton(
                                                onClick = {
                                                    dialogState.hideTopMost()
                                                    callback(null)
                                                }
                                            ) {
                                                Text(stringResource(R.string.nepridavat))
                                            }
                                        },
                                        title = {
                                            Text(stringResource(R.string.vyberte_linku))
                                        },
                                        content = {
                                            pouzitelneLinky.forEach {
                                                ListItem(
                                                    headlineContent = {
                                                        Text(it.cislo)
                                                    },
                                                    Modifier.clickable {
                                                        dialogState.hideTopMost()
                                                        callback(it.id)
                                                    },
                                                )
                                            }
                                        },
                                    )
                                }
                                var evc by rememberSaveable { mutableStateOf("") }
                                fun zadejteEvC(callback: (String) -> Unit) {
                                    dialogState.show(
                                        confirmButton = {
                                            TextButton(
                                                enabled = evc.isNotEmpty() && evc.toIntOrNull() != null && evc.toInt() >= 1,
                                                onClick = {
                                                    dialogState.hideTopMost()
                                                    callback(evc)
                                                }
                                            ) {
                                                Text(stringResource(android.R.string.ok))
                                            }
                                        },
                                        title = {
                                            Text(stringResource(R.string.zadejte_ev_c, stringResource(typBusu.trakce.jmeno)))
                                        },
                                        content = {
                                            OutlinedTextField(
                                                value = evc,
                                                onValueChange = {
                                                    evc = it
                                                },
                                                Modifier.fillMaxWidth(),
                                                label = {
                                                    Text(stringResource(id = R.string.ev_c_busu, stringResource(typBusu.trakce.jmeno)))
                                                },
                                                keyboardOptions = KeyboardOptions(
                                                    keyboardType = KeyboardType.Number,
                                                    imeAction = ImeAction.Done,
                                                )
                                            )
                                        },
                                    )
                                }

                                Button(
                                    onClick = {

                                        when {
                                            vse.nastaveni.vicenasobnyKupovani && vse.nastaveni.automatickyUdelovatEvC -> {
                                                zeptatSeNaPocet { pocet ->
                                                    if (pocet.isEmpty() || pocet.toIntOrNull() == null || pocet.toInt() < 1) {
                                                        Toast.makeText(ctx, zadejteValidniPocet, Toast.LENGTH_SHORT).show()
                                                        return@zeptatSeNaPocet
                                                    }

                                                    if (pocet.toInt() > 50) {
                                                        Toast.makeText(ctx, bohuzelNeVicNez50, Toast.LENGTH_SHORT).show()
                                                        return@zeptatSeNaPocet
                                                    }

                                                    vybratLinku { linkaID ->
                                                        val aktualniEvCisla = dp.busy.map { it.evCislo }
                                                        val neobsazenaEvC = (1..1_000_000).asSequence().filter { it !in aktualniEvCisla }

                                                        val seznamEvC = neobsazenaEvC.take(pocet.toInt())

                                                        scope.launch {
                                                            koupit(seznamEvC.toList(), linkaID)
                                                        }
                                                    }
                                                }
                                            }

                                            vse.nastaveni.vicenasobnyKupovani/* && !nastaveni.automatickyUdelovatEvC*/ -> {
                                                zeptatSeNaPocet { pocet ->
                                                    if (pocet.isEmpty() || pocet.toIntOrNull() == null || pocet.toInt() < 1) {
                                                        Toast.makeText(ctx, zadejteValidniPocet, Toast.LENGTH_SHORT).show()
                                                        return@zeptatSeNaPocet
                                                    }

                                                    if (pocet.toInt() > 50) {
                                                        Toast.makeText(ctx, bohuzelNeVicNez50, Toast.LENGTH_SHORT).show()
                                                        return@zeptatSeNaPocet
                                                    }

                                                    vybratLinku { linkaID ->
                                                        zadejteEvC { evc ->
                                                            val cisla = evc.toInt()..<(evc.toInt() + pocet.toInt())

                                                            if (cisla.any { cislo -> dp.busy.any { it.evCislo == cislo } }) {
                                                                Toast.makeText(ctx, evCExistuje, Toast.LENGTH_SHORT).show()
                                                                return@zadejteEvC
                                                            }
                                                            scope.launch {
                                                                koupit(cisla.toList(), linkaID)
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            /*!nastaveni.vicenasobnyKupovani && */vse.nastaveni.automatickyUdelovatEvC -> {
                                            val aktualniEvCisla = dp.busy.map { it.evCislo }
                                                val nejnizsiNoveEvc = (1..10000).first { it !in aktualniEvCisla }

                                                scope.launch {
                                                    koupit(listOf(nejnizsiNoveEvc), null)
                                                }
                                            }

                                            /*!nastaveni.vicenasobnyKupovani && !nastaveni.automatickyUdelovatEvC*/else -> {
                                                zadejteEvC { evc ->
                                                    if (dp.busy.any { it.evCislo == evc.toInt() }) {
                                                        Toast.makeText(ctx, evCExistuje, Toast.LENGTH_SHORT).show()
                                                        return@zadejteEvC
                                                    }
                                                    scope.launch {
                                                        koupit(listOf(evc.toInt()), null)
                                                    }
                                                }
                                            }
                                        }
                                    },
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(all = 8.dp),
                                ) {
                                    Text(stringResource(id = R.string.koupit, typBusu.cena.value.formatovat().composeString()))
                                }
                            }
                        }
                        HorizontalDivider()
                    }
                }
                if (stav == Zobrait.Vysledky && filtrovaneBusy.toList().isEmpty()) item {
                    Text(text = R.string.moc_filtru.toText().composeString(), Modifier.padding(8.dp))
                }
            }
        }
    }
}
