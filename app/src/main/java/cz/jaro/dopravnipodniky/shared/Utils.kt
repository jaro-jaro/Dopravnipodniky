package cz.jaro.dopravnipodniky.shared

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpRect
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import kotlinx.coroutines.flow.Flow
import kotlin.math.pow
import kotlin.math.roundToLong
import kotlin.time.Duration


/**
 * Zformátuje číslo, aby vypadalo hezky
 */
fun Double.formatovat(decimalPlaces: Int = 2): Text {
    if (this == Double.POSITIVE_INFINITY) return R.string.nekonecne_mnoho.toText()
    if (this == Double.NEGATIVE_INFINITY) return R.string.nekonecne_malo.toText()

    return this
        .times(10F.pow(decimalPlaces))
        .roundToLong()
        .toDouble()
        .div(10F.pow(decimalPlaces))
        .toBigDecimal()
        .toPlainString()
        .split(".")
        .let {
            if (it.size == 1) it
            else if (it[1] == "0") it.dropLast(1)
            else if (it[1].length == 1) listOf(it[0], "${it[1]}0")
            else it
        }
        .mapIndexed { i, it ->
            if (i == 0) it.formatovatTrojice() else it
        }
        .joinToString(",")
        .toText()
}

private fun String.formatovatTrojice() = toList()
    .reversed()
    .flatMapIndexed { i, cislice ->
        val a = listOf(cislice)

        if ((i + 1) % 3 == 0) a + ' ' else a
    }
    .reversed()
    .joinToString("")
    .trim()

fun Float.formatovat(decimalPlaces: Int = 2) = toDouble().formatovat(decimalPlaces)
fun Long.formatovat(decimalPlaces: Int = 2) = toDouble().formatovat(decimalPlaces)
fun Int.formatovat(decimalPlaces: Int = 2) = toDouble().formatovat(decimalPlaces)


/**
 * Udělá ideální barvu pozadí cardviewu za daných kritérií
 *
 * 2 -> výrazně
 *
 * 4 -> normálně
 *
 * 6 -> tmavě
 */

//fun pozadi(delitel: Int): Int {
//    return Color.rgb(vse.barva.red / delitel, vse.barva.green / delitel, vse.barva.blue / delitel)
//}

val Duration.milliseconds get() = inWholeMicroseconds / 1_000.0
val Duration.seconds get() = milliseconds / 1_000.0
val Duration.minutes get() = seconds / 60.0
val Duration.hours get() = minutes / 60.0

fun DpRect.contains(pozice: Pozice<Dp>): Boolean {
    return pozice.x >= left && pozice.x < right && pozice.y >= top && pozice.y < bottom
}

inline fun <E> List<E>.mutate(crossinline mutator: MutableList<E>.() -> Unit): List<E> = buildList {
    addAll(this@mutate)
    mutator()
}
suspend fun <E> List<E>.mutate(mutator: suspend MutableList<E>.() -> Unit): List<E> = buildList {
    addAll(this@mutate)
    mutator()
}


fun DrawScope.drawRect(
    color: Color,
    topLeft: Offset,
    bottomRight: Offset,
    alpha: Float = 1.0f,
    style: DrawStyle = Fill,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode
) = drawRect(
    color = color,
    topLeft = topLeft,
    size = Size(
        width = bottomRight.x - topLeft.x,
        height = bottomRight.y - topLeft.y,
    ),
    alpha = alpha,
    style = style,
    colorFilter = colorFilter,
    blendMode = blendMode,
)

fun DrawScope.drawRoundRect(
    color: Color,
    topLeft: Offset,
    bottomRight: Offset,
    alpha: Float = 1.0f,
    cornerRadius: CornerRadius = CornerRadius.Zero,
    style: DrawStyle = Fill,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode
) = drawRoundRect(
    color = color,
    topLeft = topLeft,
    size = Size(
        width = bottomRight.x - topLeft.x,
        height = bottomRight.y - topLeft.y,
    ),
    alpha = alpha,
    cornerRadius = cornerRadius,
    style = style,
    colorFilter = colorFilter,
    blendMode = blendMode,
)

fun DrawScope.drawArc(
    color: Color,
    center: Offset,
    startAngle: Float,
    sweepAngle: Float,
    useCenter: Boolean,
    quadSize: Size,
    alpha: Float = 1.0f,
    style: DrawStyle = Fill,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode
) = drawArc(
    color = color,
    startAngle = startAngle,
    sweepAngle = sweepAngle,
    useCenter = useCenter,
    topLeft = Offset(center.x - quadSize.width, center.y - quadSize.height),
    size = quadSize * 2F,
    alpha = alpha,
    style = style,
    colorFilter = colorFilter,
    blendMode = blendMode,
)

inline fun DrawScope.translate(
    offset: Offset = Offset.Zero,
    block: DrawScope.() -> Unit
) = translate(
    left = offset.x,
    top = offset.y,
    block = block,
)

fun <T, K> MutableList<T>.replaceBy(newValue: T, selector: (T) -> K) {
    val i = indexOfFirst { selector(it) == selector(newValue) }
    this[i] = newValue
}

fun <T, K> List<T>.replaceBy(newValue: T, selector: (T) -> K) = (listOf(newValue) + this).distinctBy(selector)

@Suppress("UNCHECKED_CAST")
fun <T1, T2, T3, T4, T5, T6, T7, T8, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    flow8: Flow<T8>,
    transform: suspend (T1, T2, T3, T4, T5, T6, T7, T8) -> R
): Flow<R> = kotlinx.coroutines.flow.combine(flow, flow2, flow3, flow4, flow5, flow6, flow7, flow8) { args: Array<*> ->
    transform(
        args[0] as T1,
        args[1] as T2,
        args[2] as T3,
        args[3] as T4,
        args[4] as T5,
        args[5] as T6,
        args[6] as T7,
        args[7] as T8,
    )
}

context(Density)
fun DpOffset.toOffset() = Offset(x.toPx(), y.toPx())