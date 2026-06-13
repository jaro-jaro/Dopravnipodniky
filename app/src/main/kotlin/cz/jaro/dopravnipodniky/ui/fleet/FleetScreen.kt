package cz.jaro.dopravnipodniky.ui.fleet

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import cz.jaro.dopravnipodniky.data.dopravnipodnik.ikonka
import cz.jaro.dopravnipodniky.shared.barvaNepouzivanehoBusu
import cz.jaro.dopravnipodniky.shared.composeString
import cz.jaro.dopravnipodniky.shared.formatovat
import cz.jaro.dopravnipodniky.shared.getSharedViewModel
import cz.jaro.dopravnipodniky.shared.helpers.IconWithTooltip
import cz.jaro.dopravnipodniky.shared.helpers.unaryPlus
import cz.jaro.dopravnipodniky.shared.jednotky.asString
import cz.jaro.dopravnipodniky.shared.validateRegistrationNumber
import cz.jaro.dopravnipodniky.snackbarHostState
import cz.jaro.dopravnipodniky.ui.nav.Navigator
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.util.UUID

@Composable
fun FleetScreen(
    navigator: Navigator,
) {
    val sharedViewModel = getSharedViewModel()
    val viewModel = koinViewModel<FleetViewModel> {
        parametersOf(sharedViewModel, navigator)
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state != null) FleetScreen(
        state = state!!,
        onEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FleetScreen(
    state: FleetState,
    onEvent: (FleetEvent) -> Unit,
) {
    BackHandler(state.massActions != null) {
        onEvent(FleetEvent.UnselectAllBuses)
    }

    Scaffold(
        topBar = {
            if (state.massActions != null) TopAppBar(
                title = { Text(stringResource(R.string.vybrano, state.massActions.selectedCount)) },
                navigationIcon = {
                    IconButton(
                        onClick = { onEvent(FleetEvent.UnselectAllBuses) },
                    ) { IconWithTooltip(Icons.Default.Check, stringResource(R.string.zpet)) }
                },
                actions = {
                    IconButton(
                        onClick = { onEvent(FleetEvent.SelectAllBuses) },
                    ) { IconWithTooltip(Icons.Default.SelectAll, stringResource(R.string.vybrat_vse)) }

                    FleetMassActions(
                        massActions = state.massActions,
                        onEvent = onEvent,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
            ) else TopAppBar(
                title = { Text(stringResource(R.string.garaz)) },
                navigationIcon = {
                    IconButton(
                        onClick = { onEvent(FleetEvent.GoBack) },
                    ) { IconWithTooltip(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.zpet)) }
                },
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(stringResource(R.string.garaz_koupit_vozidlo)) },
                icon = { Icon(Icons.Default.AddShoppingCart, null) },
                onClick = { onEvent(FleetEvent.GoToShop) },
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { paddingValues ->
        var openedBusID by rememberSaveable { mutableStateOf(null as UUID?) }

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
                if (state.show.money) Text(state.money.asString())
                if (state.show.dispatchedBuses) Text(stringResource(R.string.vypraveno, state.dispatchedBusCount, state.busCount))
            }
            LazyColumn(Modifier.weight(1F)) {
                item {
                    if (state.buses.isEmpty()) Text(
                        text = stringResource(R.string.zadny_bus),
                        Modifier.padding(horizontal = 16.dp),
                    ) else HorizontalDivider()
                }
                items(state.buses, key = { it.id }) { bus ->
                    val busExpanded = openedBusID == bus.id
                    BusDetail(
                        onClick = {
                            if (state.massActions != null) onEvent(FleetEvent.ToggleBus(bus.id))
                            else openedBusID = if (busExpanded) null else bus.id
                        },
                        onHold = {
                            openedBusID = null
                            onEvent(FleetEvent.ToggleBus(bus.id))
                        },
                        onEvent = onEvent,
                        bus = bus,
                        state = state,
                        busExpanded = busExpanded
                    )
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun LazyItemScope.BusDetail(
    onClick: () -> Unit,
    onHold: () -> Unit,
    onEvent: (FleetEvent) -> Unit,
    bus: BusInFleet,
    state: FleetState,
    busExpanded: Boolean
) = Column(
    Modifier
        .animateItem()
        .animateContentSize()
        .combinedClickable(
            onLongClick = onHold,
            onClick = onClick,
        ),
) {
    val scope = rememberCoroutineScope()
    val res = LocalResources.current

    ListItem(
        headlineContent = {
            Text(buildString {
                +stringResource(R.string.ev_c, bus.registrationNumber)
                if (bus.line != null) {
                    +" "
                    +stringResource(R.string.linka_tohle, bus.line.number)
                }
            })
        },
        trailingContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                var registrationNumberText by rememberSaveable { mutableStateOf(bus.registrationNumber.toString()) }
                val scope = rememberCoroutineScope()
                val res = LocalResources.current
                if (busExpanded) IconButton(
                    onClick = {
                        AlertDialogManager.Global.showMaterial(
                            confirmButton = {
                                val validated by remember { derivedStateOf { validateRegistrationNumber(registrationNumberText) } }
                                TextButton(
                                    enabled = validated != null,
                                    onClick = {
                                        onEvent(
                                            FleetEvent.EditRegistrationNumber(
                                                busID = bus.id,
                                                number = validated!!,
                                            ) {
                                                hide()
                                                if (it == FleetViewModel.EditNumberResult.AlreadyExists) scope.launch {
                                                    snackbarHostState.showSnackbar(res.getString(R.string.ev_c_existuje))
                                                }
                                            }
                                        )
                                    },
                                ) { Text(stringResource(android.R.string.ok)) }
                            },
                            title = {
                                Text(
                                    stringResource(
                                        R.string.upravte_ev_c,
                                        stringResource(bus.traction.jmeno)
                                    ),
                                )
                            },
                            content = {
                                OutlinedTextField(
                                    value = registrationNumberText,
                                    onValueChange = { registrationNumberText = it },
                                    Modifier.fillMaxWidth(),
                                    label = {
                                        Text(
                                            stringResource(
                                                id = R.string.ev_c_busu,
                                                stringResource(bus.traction.jmeno)
                                            )
                                        )
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Done,
                                    ),
                                )
                            },
                        )
                    }
                ) { IconWithTooltip(Icons.Default.Edit, stringResource(R.string.upravit_evc)) }
//                                    Icon(Icons.Default.LocationSearching, null)
            }
        },
        leadingContent = {
            if (state.massActions != null) Box(
                Modifier.size(48.dp),
                contentAlignment = Alignment.Center,
            ) {
                IconWithTooltip(
                    imageVector = if (bus.id in state.massActions.selectedBuses) Icons.Outlined.CheckCircle else Icons.Outlined.Circle,
                    contentDescription = if (bus.id in state.massActions.selectedBuses) "Vybráno" else "Nevybráno",
                )
            } else Image(
                painter = painterResource(id = bus.traction.ikonka),
                contentDescription = stringResource(R.string.ikonka_busiku),
                Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onHold),
                colorFilter = ColorFilter.tint(color = bus.line?.color?.color ?: barvaNepouzivanehoBusu),
            )
        },
    )
    AnimatedVisibility(busExpanded) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(stringResource(bus.traction.jmeno))
            Text(bus.model)
            Text(stringResource(R.string.ponicenost, bus.damage.times(100).formatovat().composeString()))
            Text(stringResource(R.string.naklady, bus.runningCosts.asString()))

            Row(Modifier.fillMaxWidth()) {
                if (bus.availableLines.isNotEmpty()) Button(
                    onClick = {
                        AlertDialogManager.Global.showMaterial(
                            confirmButton = { },
                            dismissButton = {
                                TextButton(onClick = {
                                    onEvent(FleetEvent.RemoveFromLine(bus.id))
                                    hide()
                                }) { Text(stringResource(R.string.odebrat_bus_z_linek)) }
                            },
                            title = { Text(stringResource(R.string.vyberte_linku)) },
                            content = {
                                Column {
                                    bus.availableLines.forEach { line ->
                                        ListItem(
                                            headlineContent = { Text(line.cislo) },
                                            Modifier.clickable {
                                                onEvent(FleetEvent.PutOnLine(bus.id, line.id))
                                                hide()
                                            },
                                            leadingContent = { Icon(Icons.Default.Timeline, null, tint = line.color.color) },
                                        )
                                    }
                                }
                            },
                        )
                    }
                ) { Text(stringResource(if (bus.line == null) R.string.vypravit else R.string.zmenit_linku)) }

                Spacer(modifier = Modifier
                    .weight(1F)
                    .widthIn(min = 8.dp))

                OutlinedButton(
                    onClick = {
                        AlertDialogManager.Global.showMaterial(
                            confirmButton = {
                                TextButton(onClick = {
                                    onEvent(FleetEvent.Sell(bus.id) {
                                        hide()
                                        scope.launch {
                                            val price = bus.sellPrice.asString(res)
                                            val vehicle = res.getString(bus.traction.jmeno)
                                            snackbarHostState.showSnackbar(
                                                message = res.getString(R.string.prodali_jste_jeden, vehicle, price),
                                                withDismissAction = true,
                                            )
                                        }
                                    })
                                }) { Text(stringResource(android.R.string.ok)) }
                            },
                            dismissButton = { TextButton(::hide) { Text(stringResource(R.string.zrusit)) } },
                            icon = { Icon(Icons.Default.Euro, null) },
                            title = { Text(stringResource(R.string.prodat)) },
                            content = { Text(stringResource(R.string.fakt_chcete_prodat_bus)) },
                        )
                    },
                ) { Text(stringResource(R.string.prodat_za, bus.sellPrice.asString())) }
            }

            Spacer(Modifier.height(8.dp))
        }
    }
    HorizontalDivider()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FleetMassActions(
    massActions: FleetMassActionsState,
    onEvent: (FleetEvent) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val res = LocalResources.current

    IconButton(
        onClick = {
            AlertDialogManager.Global.showMaterial(
                confirmButton = {
                    TextButton(onClick = {
                        onEvent(FleetEvent.SellSelected {
                            scope.launch {
                                val count = massActions.selectedCount.toString()
                                val price = massActions.sellPrice.asString(res)
                                val vehicles = res.getQuantityString(R.plurals.vozidlo2p, massActions.selectedCount)
                                snackbarHostState.showSnackbar(
                                    message = res.getString(R.string.prodali_jste, count, vehicles, price),
                                    withDismissAction = true,
                                )
                            }
                        })
                    }) { Text(stringResource(android.R.string.ok)) }
                },
                dismissButton = { TextButton(::hide) { Text(stringResource(R.string.zrusit)) } },
                icon = { Icon(Icons.Default.Euro, null) },
                title = { Text(stringResource(R.string.prodat)) },
                content = { Text(stringResource(R.string.fakt_chcete_prodat_bus)) },
            )
        }
    ) { IconWithTooltip(Icons.Default.Euro, stringResource(R.string.prodat)) }

    if (massActions.availableLines.isNotEmpty()) IconButton(
        onClick = {
            AlertDialogManager.Global.showMaterial(
                confirmButton = { },
                dismissButton = {
                    TextButton(onClick = {
                        onEvent(FleetEvent.RemoveSelectedFromLines)
                        hide()
                    }) { Text(stringResource(R.string.odebrat_bus_z_linek)) }
                },
                title = { Text(stringResource(R.string.vyberte_linku)) },
                content = {
                    Column {
                        massActions.availableLines.forEach { line ->
                            ListItem(
                                headlineContent = { Text(line.cislo) },
                                Modifier.clickable {
                                    onEvent(FleetEvent.PutSelectedOnLine(line.id))
                                    hide()
                                },
                                leadingContent = { Icon(Icons.Default.Timeline, null, tint = line.color.color) },
                            )
                        }
                    }
                },
            )
        }
    ) { IconWithTooltip(Icons.Default.Timeline, stringResource(R.string.vypravit)) }
}