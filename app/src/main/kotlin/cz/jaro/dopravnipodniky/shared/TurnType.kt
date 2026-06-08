package cz.jaro.dopravnipodniky.shared

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.times
import cz.jaro.dopravnipodniky.shared.jednotky.Angle
import cz.jaro.dopravnipodniky.shared.jednotky.deg
import cz.jaro.dopravnipodniky.shared.jednotky.degrees
import cz.jaro.dopravnipodniky.shared.jednotky.radians
import cz.jaro.dopravnipodniky.shared.jednotky.sin
import kotlin.math.sqrt

sealed interface TurnType {
    sealed interface Basic : TurnType
    data object Right : Basic
    data object Straight : Basic
    data object Left : Basic
    sealed class Roundabout(val quarters: Int) : TurnType
    data object RoundaboutRight : Roundabout(quarters = 0)
    data object RoundaboutStraight : Roundabout(quarters = 1)
    data object RoundaboutLeft : Roundabout(quarters = 2)
    data object RoundaboutReturn : Roundabout(quarters = 3)
}

fun TurnType.turnParts(busWidth: Dp) = when (this) {
    TurnType.Straight -> listOf()
    TurnType.Right, TurnType.RoundaboutRight -> listOf(
        TurnPart(radiusRight(busWidth), 90.deg, 0.deg),
    )

    TurnType.Left -> listOf(
        TurnPart(radiusLeft(busWidth), (-90).deg, 0.deg),
    )

    is TurnType.Roundabout -> listOf(
        TurnPart(radiusRight(busWidth), 45.deg, 0.deg),
        TurnPart(radiusRoundabout(busWidth), (-45).deg, -(90.deg * quarters - 45.deg)),
        TurnPart(radiusRight(busWidth), 45.deg, 0.deg),
    )
}

private fun radiusRight(busWidth: Dp): Dp = predsazeniKrizovatky + odsazeniBusu + busWidth / 2
private fun radiusLeft(busWidth: Dp): Dp = predsazeniKrizovatky + sirkaUlice - (odsazeniBusu + busWidth / 2)
private fun radiusRoundabout(busWidth: Dp): Dp =
    sirkaUlice * (sqrt(2.0) / 2) + predsazeniKrizovatky * (sqrt(2.0) - 1) - (odsazeniBusu + busWidth / 2)

data class TurnPart(
    val radius: Dp,
    val entryAndExitAngle: Angle,
    val arcAngle: Angle,
)

val TurnPart.entryAngle get() = entryAndExitAngle / 2
val TurnPart.signedEntryAndExitLength get() = sin(entryAndExitAngle) * radius * entryAndExitLengthCoefficient
val TurnPart.entryAndExitLength get() = signedEntryAndExitLength.absoluteValue
val TurnPart.entryLength get() = entryAndExitLength / 2
val TurnPart.arcLength get() = arcLength(arcAngle, radius)
val TurnPart.signedArcLength get() = arcAngle.radians * radius
val TurnPart.length get() = entryAndExitLength + arcLength

/**
 * @see <a href="https://www.wolframalpha.com/input?i2d=true&i=1/Integrate[Cos[(Pi/8) (3 x^2 - 2 x^3)], {x, 0, 1}]">WolframAlpha</a>
 * */
private val TurnPart.entryAndExitLengthCoefficient get() = when (entryAndExitAngle.absoluteValue) {
    22.5.deg -> 1.0292045922861321
    45.deg -> 1.1240921665304795
    90.deg -> 1.6525000895846305
    else -> error("Není předpočítané: ${entryAndExitAngle.radians} rad (${entryAndExitAngle.degrees} °)")
}

fun TurnType.Basic?.onRoundabout() = when (this) {
    is TurnType.Right -> TurnType.RoundaboutRight
    is TurnType.Straight -> TurnType.RoundaboutStraight
    is TurnType.Left -> TurnType.RoundaboutLeft
    null -> TurnType.RoundaboutReturn
}

fun TurnType.Basic?.onRoundaboutIf(isRoundabout: Boolean) =
    if (isRoundabout) onRoundabout() else this ?: TurnType.RoundaboutReturn//error("Otočka nejde")