package cz.jaro.dopravnipodniky.ui.fleet

import androidx.lifecycle.ViewModel
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Trakce
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.dopravnipodnik.getStreets
import cz.jaro.dopravnipodniky.data.dopravnipodnik.jsouVsechnyZatrolejovane
import cz.jaro.dopravnipodniky.data.dopravnipodnik.linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.placeOnStreetBeginning
import cz.jaro.dopravnipodniky.data.dopravnipodnik.rozmistitBusy
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.shared.BusID
import cz.jaro.dopravnipodniky.shared.SharedViewModel
import cz.jaro.dopravnipodniky.shared.Smer
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.helpers.combineStates
import cz.jaro.dopravnipodniky.shared.helpers.getAll
import cz.jaro.dopravnipodniky.shared.helpers.intersect
import cz.jaro.dopravnipodniky.shared.helpers.mapState
import cz.jaro.dopravnipodniky.shared.je
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz
import cz.jaro.dopravnipodniky.shared.jednotky.sumOfPeniz
import cz.jaro.dopravnipodniky.shared.removeFirst
import cz.jaro.dopravnipodniky.shared.toggle
import cz.jaro.dopravnipodniky.ui.nav.Navigator
import cz.jaro.dopravnipodniky.ui.nav.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FleetViewModel(
    sharedViewModel: SharedViewModel,
    private val navigator: Navigator,
) : ViewModel() {
    private val dp = sharedViewModel.dp
    private val vse = sharedViewModel.vse
    private val menic = sharedViewModel.menic
    private val dosahni = sharedViewModel.dosahni

    init {
        menic.zmenitTutorial {
            if (it je StavTutorialu.Tutorialujeme.Obchod)
                StavTutorialu.Tutorialujeme.Vypraveni
            else it
        }
    }

    private val selectedBusIDs = MutableStateFlow<Set<BusID>>(emptySet())
    private val allBuses = dp.mapState { dp ->
        dp?.busy
            ?.sortedBy { it.evCislo }
            ?.associateBy { it.id }
            ?.mapValues { [id, bus] ->
                BusInFleet(
                    id = id,
                    registrationNumber = bus.evCislo,
                    line = bus.linka?.let { dp.linka(bus.linka) }?.let { line ->
                        LineOfBusInFleet(
                            number = line.cislo,
                            color = line.color,
                            id = line.id,
                        )
                    },
                    traction = bus.typBusu.trakce,
                    model = bus.typBusu.model,
                    damage = bus.ponicenost,
                    runningCosts = bus.naklady,
                    availableLines = when (bus.typBusu.trakce) {
                        is Trakce.Trolejbus -> dp.linky.toSet()
                            .filterTo(mutableSetOf()) { dp.getStreets(it.ulice.toSet()).jsouVsechnyZatrolejovane() }

                        else -> dp.linky.toSet()
                    },
                    sellPrice = bus.prodejniCena,
                    people = bus.cloveci,
                )
            }
            ?: emptyMap()
    }

    private val showParts = vse.mapState { vse ->
        if (vse == null) ShowFleetParts(
            money = false,
            dispatchedBuses = false
        ) else ShowFleetParts(
            money = !(vse.tutorial je StavTutorialu.Tutorialujeme.Uvod),
            dispatchedBuses = !(vse.tutorial je StavTutorialu.Tutorialujeme.Uvod) &&
                    !(vse.tutorial je StavTutorialu.Tutorialujeme.Linky) &&
                    !(vse.tutorial je StavTutorialu.Tutorialujeme.Zastavky) &&
                    !(vse.tutorial je StavTutorialu.Tutorialujeme.Garaz) &&
                    !(vse.tutorial je StavTutorialu.Tutorialujeme.Obchod)
        )
    }

    val selectedBuses = combineStates(
        selectedBusIDs, allBuses,
    ) { selectedBusIDs, allBuses ->
        allBuses.getAll(selectedBusIDs)
    }

    val massActionsState = combineStates(
        selectedBusIDs, selectedBuses,
    ) { selectedBusIDs, selectedBuses ->
        if (selectedBusIDs.isEmpty()) null else FleetMassActionsState(
            selectedBuses = selectedBusIDs,
            selectedCount = selectedBusIDs.size,
            sellPrice = selectedBuses.sumOfPeniz { it.sellPrice },
            availableLines = selectedBuses.mapTo(mutableSetOf()) { it.availableLines }.intersect(),
        )
    }

    val state = combineStates(
        vse, massActionsState, allBuses, showParts,
    ) { vse, massActionsState, allBuses, showParts ->
        if (vse == null) null else FleetState(
            massActions = massActionsState,
            show = showParts,
            money = vse.prachy,
            busCount = allBuses.size,
            dispatchedBusCount = allBuses.count { it.value.line != null },
            buses = allBuses.values.toList(),
        )
    }

    private val allBusIDs = allBuses.mapState { buses ->
        buses.mapTo(mutableSetOf()) { it.value.id }
    }

    fun onEvent(e: FleetEvent): Unit = when (e) {
        is FleetEvent.UnselectAllBuses -> selectedBusIDs.value = emptySet()
        is FleetEvent.SelectAllBuses -> selectedBusIDs.value = allBusIDs.value
        is FleetEvent.SellSelected -> {
            val busesToSell = selectedBuses.value
            val price = massActionsState.value!!.sellPrice

            sellBuses(busesToSell, price)

            onEvent(FleetEvent.UnselectAllBuses)

            e.onComplete()
        }

        is FleetEvent.RemoveSelectedFromLines -> Unit
        is FleetEvent.PutSelectedOnLine -> {
            val busesToChange = selectedBuses.value
            menic.zmenitBusy {
                busesToChange.forEach { bus ->
                    val i = indexOfFirst { it.id == bus.id }
                    this[i] = this[i].placeOnStreetBeginning(
                        line = e.lineID,
                        directionOnLine = Smer.Pozitivni,
                        positionOnLine = 0,
                    )
                }

                val affectedLines = busesToChange.mapNotNull { it.line }.distinct()
                affectedLines.forEach { line ->
                    apply(dp.value!!.linka(line.id).rozmistitBusy)
                }
            }
            dosahni(Dosahlost.BusNaLince::class)
            onEvent(FleetEvent.UnselectAllBuses)
        }

        is FleetEvent.GoBack -> navigator.pop()
        is FleetEvent.GoToShop -> navigator.push(Route.Shop)
        is FleetEvent.ToggleBus -> selectedBusIDs.update { it.toggle(e.busID) }

        is FleetEvent.EditRegistrationNumber -> {
            val buses = allBuses.value
            val bus = buses[e.busID]!!

            if (e.number == bus.registrationNumber)
                return e.onComplete(EditNumberResult.Success)

            if (buses.any { it.value.registrationNumber == e.number })
                return e.onComplete(EditNumberResult.AlreadyExists)

            menic.zmenitBusy {
                val i = indexOfFirst { it.id == e.busID }
                this[i] = this[i].copy(
                    evCislo = e.number
                )
            }

            e.onComplete(EditNumberResult.Success)
        }

        is FleetEvent.RemoveFromLine -> {
            menic.zmenitBusy {
                val i = indexOfFirst { it.id == e.busID }
                this[i] = this[i].copy(
                    linka = null
                )
            }
        }

        is FleetEvent.PutOnLine -> {
            menic.zmenitBusy {
                val i = indexOfFirst { it.id == e.busID }
                this[i] = this[i].placeOnStreetBeginning(
                    line = e.lineID,
                    directionOnLine = Smer.Pozitivni,
                    positionOnLine = 0,
                )
            }
            dosahni(Dosahlost.BusNaLince::class)
        }

        is FleetEvent.Sell -> {
            val bus = allBuses.value[e.busID]!!

            sellBuses(setOf(bus), bus.sellPrice)

            e.onComplete()
        }
    }

    private fun sellBuses(
        busesToSell: Set<BusInFleet>,
        price: Peniz
    ) {
        menic.zmenitUlice {
            movePeopleOffBuses(busesToSell.sumOf { it.people })
        }
        menic.zmenitBusy {
            busesToSell.forEach { bus ->
                removeFirst { it.id == bus.id }
            }
        }
        menic.zmenitPrachy {
            it + price
        }
    }

    enum class EditNumberResult {
        Success, AlreadyExists
    }
}

private fun MutableList<Ulice>.movePeopleOffBuses(peopleToMove: Int) {
    var peopleLeftToMove = peopleToMove
    withIndex().shuffled().forEach { (index, street = value) ->
        val peopleToMoveToThisStreet = peopleLeftToMove.coerceAtMost(street.kapacita - street.cloveci)
        peopleLeftToMove -= peopleToMoveToThisStreet
        this[index] = street.copy(
            cloveci = street.cloveci + peopleToMoveToThisStreet
        )
    }
}
