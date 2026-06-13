package cz.jaro.dopravnipodniky.ui.fleet

import cz.jaro.dopravnipodniky.shared.BusID
import cz.jaro.dopravnipodniky.shared.LinkaID

sealed interface FleetEvent {
    data object UnselectAllBuses : FleetEvent
    data object SelectAllBuses : FleetEvent
    data class SellSelected(val onComplete: () -> Unit) : FleetEvent
    data object RemoveSelectedFromLines : FleetEvent
    data class PutSelectedOnLine(val lineID: LinkaID) : FleetEvent
    data object GoBack : FleetEvent
    data object GoToShop : FleetEvent
    data class ToggleBus(val busID: BusID) : FleetEvent
    data class EditRegistrationNumber(
        val busID: BusID,
        val number: Int,
        val onComplete: (FleetViewModel.EditNumberResult) -> Unit
    ) : FleetEvent
    data class RemoveFromLine(val busID: BusID) : FleetEvent
    data class PutOnLine(val busID: BusID, val lineID: LinkaID) : FleetEvent
    data class Sell(val busID: BusID, val onComplete: () -> Unit) : FleetEvent
}