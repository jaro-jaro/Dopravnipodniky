package cz.jaro.dopravnipodniky.ui.fleet.shop

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Timeline
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.jaro.better_dialog.AlertDialogManager
import cz.jaro.better_dialog.showMaterial
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.TypBusu
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ikonka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.runningCosts
import cz.jaro.dopravnipodniky.data.shop_settings.FilterDisplayOptionGroup
import cz.jaro.dopravnipodniky.data.shop_settings.ShopSortOption
import cz.jaro.dopravnipodniky.data.shop_settings.ShopSortSetting
import cz.jaro.dopravnipodniky.data.shop_settings.SortDirection
import cz.jaro.dopravnipodniky.shared.LinkaID
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.composeString
import cz.jaro.dopravnipodniky.shared.formatovat
import cz.jaro.dopravnipodniky.shared.getSharedViewModel
import cz.jaro.dopravnipodniky.shared.helpers.IconWithTooltip
import cz.jaro.dopravnipodniky.shared.je
import cz.jaro.dopravnipodniky.shared.jednotky.asString
import cz.jaro.dopravnipodniky.shared.validateRegistrationNumber
import cz.jaro.dopravnipodniky.snackbarHostState
import cz.jaro.dopravnipodniky.ui.nav.Navigator
import cz.jaro.dopravnipodniky.ui.theme.themeColor
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ShopScreen(
    navigator: Navigator,
) {
    val sharedViewModel = getSharedViewModel()
    val viewModel = koinViewModel<ShopViewModel> {
        parametersOf(sharedViewModel, navigator)
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state != null) ShopScreen(
        state = state!!,
        onEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(
    state: ShopState,
    onEvent: (ShopEvent) -> Unit,
) {
    BackHandler { onEvent(ShopEvent.BackPressed) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.obchod)) },
                actions = {
                    if (state.tutorialState je StavTutorialu.Tutorialujeme.Obchod) IconButton(
                        onClick = { onEvent(ShopEvent.ShowTutorial) }
                    ) { IconWithTooltip(Icons.AutoMirrored.Filled.Help, stringResource(R.string.tutorial)) }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onEvent(ShopEvent.BackPressed) }
                    ) { IconWithTooltip(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.zpet)) }
                },
            )
        },
    ) { paddingValues ->
        var openedBusModel by rememberSaveable { mutableStateOf(null as String?) }

        Column(
            Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(state.money.asString())
                Text(stringResource(R.string.zobrazeno, state.buses.count()))
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                TextButton(
                    onClick = { onEvent(ShopEvent.FilterButtonClicked) }
                ) { Text(stringResource(if (state.showState == ShopViewModel.Show.Filters) R.string.schovat_viltry else R.string.filtrovat)) }
                TextButton(
                    onClick = { onEvent(ShopEvent.SortButtonClicked) }
                ) { Text(stringResource(if (state.showState == ShopViewModel.Show.Sort) R.string.schovat_razeni else R.string.radit)) }
            }
            LazyColumn(Modifier.weight(1F)) {
                if (state.showState == ShopViewModel.Show.Filters) items(FilterDisplayOptionGroup.groups) { group ->
                    Text(
                        text = group.name.composeString(),
                        Modifier.padding(horizontal = 8.dp),
                    )
                    FlowRow(
                        Modifier.padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        group.filters.forEach { filter ->
                            FilterChip(
                                selected = filter in state.shopSettings.filters,
                                onClick = { onEvent(ShopEvent.ToggleFilter(filter)) },
                                label = { Text(filter.name.composeString()) },
                            )
                        }
                    }
                }
                if (state.showState == ShopViewModel.Show.Sort) items(ShopSortOption.shopSortOptions) { option ->
                    Text(
                        option.name.composeString(),
                        Modifier.padding(horizontal = 8.dp),
                    )
                    FlowRow(
                        Modifier.padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        SortDirection.entries.forEach { direction ->
                            val setting = remember {
                                ShopSortSetting.Sort(
                                    sortBy = option,
                                    sortDirection = direction,
                                )
                            }
                            FilterChip(
                                selected = state.shopSettings.sort == setting,
                                onClick = { onEvent(ShopEvent.SetSort(setting)) },
                                label = { Text(direction.label.composeString()) },
                            )
                        }
                    }
                }
                if (state.showState == ShopViewModel.Show.Buses) {
                    item {
                        if (state.buses.isEmpty()) Text(
                            text = stringResource(R.string.moc_filtru),
                            Modifier.padding(horizontal = 16.dp),
                        ) else HorizontalDivider()
                    }
                    items(state.buses, key = { it.model }) { busType ->
                        val busExpanded by remember { derivedStateOf { openedBusModel == busType.model } }
                        BusTypeItem(
                            busType = busType,
                            expanded = busExpanded,
                            onEvent = onEvent,
                            onClick = { openedBusModel = if (busExpanded) null else busType.model },
                        )
                    }
                }
            }
        }
    }
}

context(lazyItemScope: LazyItemScope) @Composable
private fun BusTypeItem(
    busType: TypBusu,
    expanded: Boolean,
    onEvent: (ShopEvent) -> Unit,
    onClick: () -> Unit,
) = Column(
    with(lazyItemScope) {
        Modifier
            .animateItem()
            .animateContentSize()
            .clickable(onClick = onClick)
    },
) {
    ListItem(
        headlineContent = { Text(busType.model) },
        trailingContent = {
            if (!expanded) Text(busType.cena.asString(), style = MaterialTheme.typography.bodyLarge)
        },
        leadingContent = {
            Image(
                painter = painterResource(busType.trakce.ikonka),
                contentDescription = stringResource(R.string.ikonka_busiku),
                Modifier.size(48.dp),
                colorFilter = ColorFilter.tint(themeColor),
            )
        },
    )
    AnimatedVisibility(expanded) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(stringResource(busType.trakce.jmeno))
            Text(stringResource(R.string.bus_ma_naklady, busType.runningCosts.label.composeString()))
            Text(busType.popis)

            val scope = rememberCoroutineScope()
            val res = LocalResources.current
            Button(
                onClick = {
                    onEvent(
                        ShopEvent.BuyBus(
                            busType = busType,
                            callbacks = AskMoreCallbacks,
                            onComplete = { result ->
                                scope.launch {
                                    when (result) {
                                        is ShopViewModel.BuyResult.Success -> snackbarHostState.showSnackbar(
                                            res.getString(R.string.uspesne_koupeno_tolik_busuu, result.count, busType.trakce)
                                        )

                                        ShopViewModel.BuyResult.NotEnoughMoney ->
                                            snackbarHostState.showSnackbar(res.getString(R.string.malo_penez))

                                        ShopViewModel.BuyResult.CountNotValid ->
                                            snackbarHostState.showSnackbar(res.getString(R.string.zadejte_validni_pocet))

                                        ShopViewModel.BuyResult.TooManyBuses ->
                                            snackbarHostState.showSnackbar(res.getString(R.string.bohuzel_ne_vic_nez_50))
                                    }
                                }
                            }
                        ))
                },
                Modifier.fillMaxWidth(),
            ) { Text(stringResource(id = R.string.koupit, busType.cena.value.formatovat().composeString())) }

            Spacer(Modifier.height(8.dp))
        }
    }
    HorizontalDivider()
}

private object AskMoreCallbacks : ShopViewModel.AskMoreCallbacks {
    override fun askForBusCount(
        busType: TypBusu,
        callback: (enteredText: String) -> Unit,
    ) {
        var busCountText by mutableStateOf("")
        AlertDialogManager.Global.showMaterial(
            confirmButton = {
                TextButton(onClick = { hide(); callback(busCountText) }) {
                    Text(stringResource(android.R.string.ok))
                }
            },
            title = { Text(stringResource(R.string.nadpis_vicenasobne_kupovani)) },
            content = {
                OutlinedTextField(
                    value = busCountText,
                    onValueChange = { busCountText = it },
                    Modifier.fillMaxWidth(),
                    label = { Text(stringResource(R.string.pocet_busuu, stringResource(busType.trakce.jmeno))) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
                )
            },
        )
    }

    override fun askForLine(
        busType: TypBusu,
        lines: List<Linka>,
        callback: (chosen: LinkaID?) -> Unit
    ) {
        AlertDialogManager.Global.showMaterial(
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { hide(); callback(null) }) {
                    Text(stringResource(R.string.nepridavat))
                }
            },
            title = { Text(stringResource(R.string.vyberte_linku)) },
            content = {
                lines.forEach {
                    ListItem(
                        headlineContent = { Text(it.cislo) },
                        Modifier.clickable { hide(); callback(it.id) },
                        leadingContent = { Icon(Icons.Default.Timeline, null, tint = it.color.color) },
                    )
                }
            },
        )
    }

    override fun askForRegistrationNumber(
        busType: TypBusu,
        callback: (number: Int) -> Unit
    ) {
        var registrationNumberText by mutableStateOf("")
        AlertDialogManager.Global.showMaterial(
            confirmButton = {
                val validated by remember { derivedStateOf { validateRegistrationNumber(registrationNumberText) } }
                TextButton(
                    enabled = validated != null,
                    onClick = { hide(); callback(validated!!) },
                ) { Text(stringResource(android.R.string.ok)) }
            },
            title = { Text(stringResource(R.string.zadejte_ev_c, stringResource(busType.trakce.jmeno))) },
            content = {
                OutlinedTextField(
                    value = registrationNumberText,
                    onValueChange = { registrationNumberText = it },
                    Modifier.fillMaxWidth(),
                    label = { Text(stringResource(R.string.ev_c_busu, stringResource(busType.trakce.jmeno))) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
                )
            },
        )
    }
}