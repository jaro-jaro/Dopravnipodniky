package cz.jaro.dopravnipodniky.shared.helpers

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.FontScaling
import androidx.compose.ui.unit.TextUnit

/** Convert [Dp] to pixels. Pixels are used to paint to Canvas. */
@Stable
context(density: Density)
fun Dp.toPx() = with(density) { toPx() }

/** Convert [Dp] to [Int] by rounding */
@Stable
context(density: Density)
fun Dp.roundToPx() = with(density) { roundToPx() }

/**
 * Convert Sp to pixels. Pixels are used to paint to Canvas.
 *
 * @throws IllegalStateException if TextUnit other than SP unit is specified.
 */
@Stable
context(density: Density)
fun TextUnit.toPx() = with(density) { toPx() }

/** Convert Sp to [Int] by rounding */
@Stable
context(density: Density)
fun TextUnit.roundToPx() = with(density) { roundToPx() }

/** Convert an [Int] pixel value to [Dp]. */
@Stable
context(density: Density)
fun Int.toDp() = with(density) { toDp() }

/** Convert an [Int] pixel value to Sp. */
@Stable
context(density: Density)
fun Int.toSp() = with(density) { toSp() }

/** Convert a [Float] pixel value to a Dp */
@Stable
context(density: Density)
fun Float.toDp() = with(density) { toDp() }

/** Convert a [Float] pixel value to a Sp */
@Stable
context(density: Density)
fun Float.toSp() = with(density) { toSp() }

/** Convert a [DpRect] to a [Rect]. */
@Stable
context(density: Density)
fun DpRect.toRect() = with(density) { toRect() }

/** Convert [Dp] to Sp. Sp is used for font size, etc. */
@Stable
context(fontScaling: FontScaling)
fun Dp.toSp() = with(fontScaling) { toSp() }

/**
 * Convert Sp to [Dp].
 *
 * @throws IllegalStateException if TextUnit other than SP unit is specified.
 */
@Stable
context(fontScaling: FontScaling)
fun TextUnit.toDp() = with(fontScaling) { toDp() }