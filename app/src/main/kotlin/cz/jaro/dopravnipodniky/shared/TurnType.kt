package cz.jaro.dopravnipodniky.shared

sealed interface TurnType {
    sealed interface Basic : TurnType
    data object Right : Basic
    data object Straight : Basic
    data object Left : Basic
    data object Return180 : Basic
    sealed class Roundabout(val quadrants: Int) : TurnType
    data object RoundaboutRight : Roundabout(quadrants = 0)
    data object RoundaboutStraight : Roundabout(quadrants = 1)
    data object RoundaboutLeft : Roundabout(quadrants = 2)
    data object RoundaboutReturn : Roundabout(quadrants = 3)
}

fun TurnType.Basic.onRoundabout() = when (this) {
    is TurnType.Right -> TurnType.RoundaboutRight
    is TurnType.Straight -> TurnType.RoundaboutStraight
    is TurnType.Left -> TurnType.RoundaboutLeft
    is TurnType.Return180 -> TurnType.RoundaboutReturn
}

fun TurnType.Basic.onRoundaboutIf(isRoundabout: Boolean) =
    if (isRoundabout) onRoundabout() else this