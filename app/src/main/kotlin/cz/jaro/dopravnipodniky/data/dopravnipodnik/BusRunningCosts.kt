package cz.jaro.dopravnipodniky.data.dopravnipodnik

import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.shared.Text
import cz.jaro.dopravnipodniky.shared.toText
import kotlinx.serialization.Serializable

@Serializable
enum class BusRunningCosts(
    val costChangeSpeedRange: OpenEndRange<Double>,
    val label: Text,
) {
    ExtremelyLow(
        costChangeSpeedRange = .0..<.16,
        label = R.string.velmi_nizke.toText(),
    ),
    VeryLow(
        costChangeSpeedRange = .16..<.22,
        label = R.string.hodne_nizke.toText(),
    ),
    Low(
        costChangeSpeedRange = .22..<.25,
        label = R.string.nizke.toText(),
    ),
    QuiteLow(
        costChangeSpeedRange = .25..<.29,
        label = R.string.pomerne_nizke.toText(),
    ),
    Lowered(
        costChangeSpeedRange = .29..<.35,
        label = R.string.snizene.toText(),
    ),
    Normal(
        costChangeSpeedRange = .35..<.45,
        label = R.string.normalni.toText(),
    ),
    QuiteHigh(
        costChangeSpeedRange = .45..<.55,
        label = R.string.pomerne_vysoke.toText(),
    ),
    High(
        costChangeSpeedRange = .55..<.66,
        label = R.string.vysoke.toText(),
    ),
    VeryHigh(
        costChangeSpeedRange = .66..<1.4,
        label = R.string.hodne_vysoke.toText(),
    ),
    ExtremelyHigh(
        costChangeSpeedRange = 1.4..<3.0,
        label = R.string.velmi_vysoke.toText(),
    ),
    MuseumBus(
        costChangeSpeedRange = 3.0..<10.0,
        label = R.string.muzejni_bus.toText(),
    ),
    JostusRostus(
        costChangeSpeedRange = 10.0..<Double.POSITIVE_INFINITY,
        label = R.string.JOSTOVSKE.toText(),
    ),
}