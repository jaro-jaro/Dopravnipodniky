package cz.jaro.dopravnipodniky.ui.fleet

import cz.jaro.dopravnipodniky.data.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Trakce
import cz.jaro.dopravnipodniky.shared.BusID
import cz.jaro.dopravnipodniky.shared.LinkaID
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz
import cz.jaro.dopravnipodniky.shared.jednotky.PenizZaMinutu
import cz.jaro.dopravnipodniky.ui.theme.LineColor

data class FleetState(
    val massActions: FleetMassActionsState?,
    val show: ShowFleetParts,
    val money: Peniz,
    val busCount: Int,
    val dispatchedBusCount: Int,
    val buses: List<BusInFleet>,
)

data class ShowFleetParts(
    val money: Boolean,
    val dispatchedBuses: Boolean,
)

data class FleetMassActionsState(
    val selectedBuses: Set<BusID>,
    val selectedCount: Int,
    val sellPrice: Peniz,
    val availableLines: Set<Linka>,
)

data class BusInFleet(
    val id: BusID,
    val registrationNumber: Int,
    val line: LineOfBusInFleet?,
    val traction: Trakce,
    val model: String,
    val damage: Double,
    val runningCosts: PenizZaMinutu,
    val availableLines: Set<Linka>,
    val sellPrice: Peniz,
    val people: Int,
)

data class LineOfBusInFleet(
    val number: String,
    val color: LineColor,
    val id: LinkaID,
)