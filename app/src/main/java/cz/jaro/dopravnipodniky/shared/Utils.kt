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
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.toOffset
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import cz.jaro.dopravnipodniky.shared.jednotky.toDp
import cz.jaro.dopravnipodniky.shared.jednotky.toPx
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

context(PointerInputScope)
fun <T> List<T>.najitObdelnikVeKteremJe(
    offset: Offset,
    tx: Float,
    ty: Float,
    priblizeni: Float,
    transform: ((T) -> DpRect),
) = najitObdelnikVeKteremJe(
    offset = offset,
    tx = tx,
    ty = ty,
    priblizeni = priblizeni,
    center = this@PointerInputScope.size.center.toOffset(),
    transform = transform,
)

//context(PointerInputScope)
//fun List<DpRect>.najitObdelnikVeKteremJe(
//    offset: Offset,
//    tx: Float,
//    ty: Float,
//    priblizeni: Float,
//) = najitObdelnikVeKteremJe(
//    offset = offset,
//    tx = tx,
//    ty = ty,
//    priblizeni = priblizeni,
//    center = this@PointerInputScope.size.center.toOffset(),
//    transform = { it },
//)
//
//context(Density)
//fun List<DpRect>.najitObdelnikVeKteremJe(
//    offset: Offset,
//    tx: Float,
//    ty: Float,
//    center: Offset,
//    priblizeni: Float,
//) = najitObdelnikVeKteremJe(
//    offset = offset,
//    tx = tx,
//    ty = ty,
//    priblizeni = priblizeni,
//    center = center,
//    transform = { it },
//)

context(Density)
fun <T> List<T>.najitObdelnikVeKteremJe(
    offset: Offset,
    tx: Float,
    ty: Float,
    priblizeni: Float,
    center: Offset,
    transform: ((T) -> DpRect),
) = offset
    .toDpSPosunutimAPriblizenim(tx = tx, ty = ty, priblizeni = priblizeni, center = center)
    .let { pozice ->
        val i = map(transform).indexOfFirst {
            it.contains(pozice)
        }
        this.getOrNull(i)
    }

context(Density)
fun Offset.toDpSPosunutimAPriblizenim(
    tx: Float,
    ty: Float,
    priblizeni: Float,
    center: Offset,
) = this
    .minus(center * priblizeni)
    .minus(Offset(tx, ty) * priblizeni)
    .minus(center)
    .div(priblizeni)
    .plus(center)
    .toDp()

context(PointerInputScope)
fun Offset.toDpSPosunutimAPriblizenim(
    tx: Float,
    ty: Float,
    priblizeni: Float,
) = toDpSPosunutimAPriblizenim(
    tx = tx, ty = ty, priblizeni = priblizeni,
    center = this@PointerInputScope.size.center.toOffset(),
)

context(Density)
fun Pozice<Dp>.toOffsetSPriblizenim(
    priblizeni: Float,
    center: Offset,
) = this
    .toPx()
    .minus(center)
    .times(priblizeni)
    .plus(center)
    .plus(
        center * priblizeni
    )

context(PointerInputScope)
fun Pozice<Dp>.toOffsetSPriblizenim(
    priblizeni: Float,
) = toOffsetSPriblizenim(
    priblizeni = priblizeni,
    center = this@PointerInputScope.size.center.toOffset(),
)

fun Offset.odNulaNula(priblizeni: Float) = -this / priblizeni

fun <A, B, C> zip(
    iterableA: Iterable<A>,
    iterableB: Iterable<B>,
    iterableC: Iterable<C>,
) = zip(iterableA, iterableB, iterableC) { a, b, c -> Triple(a, b, c) }

fun <A, B, C, Z> zip(
    iterableA: Iterable<A>,
    iterableB: Iterable<B>,
    iterableC: Iterable<C>,
    transform: (a: A, b: B, c: C) -> Z
): ArrayList<Z> {
    val a = iterableA.iterator()
    val b = iterableB.iterator()
    val c = iterableC.iterator()

    val list = ArrayList<Z>(
        minOf(
            iterableA.collectionSizeOrDefault(10),
            iterableB.collectionSizeOrDefault(10),
            iterableC.collectionSizeOrDefault(10),
        )
    )
    while (a.hasNext() && b.hasNext() && c.hasNext()) {
        list.add(transform(a.next(), b.next(), c.next()))
    }
    return list
}

fun <A, B, C, D> zip(
    iterableA: Iterable<A>,
    iterableB: Iterable<B>,
    iterableC: Iterable<C>,
    iterableD: Iterable<D>,
) = zip(iterableA, iterableB, iterableC, iterableD) { a, b, c, d -> Quadruple(a, b, c, d) }

fun <A, B, C, D, Z> zip(
    iterableA: Iterable<A>,
    iterableB: Iterable<B>,
    iterableC: Iterable<C>,
    iterableD: Iterable<D>,
    transform: (a: A, b: B, c: C, d: D) -> Z
): ArrayList<Z> {
    val a = iterableA.iterator()
    val b = iterableB.iterator()
    val c = iterableC.iterator()
    val d = iterableD.iterator()

    val list = ArrayList<Z>(
        minOf(
            iterableA.collectionSizeOrDefault(10),
            iterableB.collectionSizeOrDefault(10),
            iterableC.collectionSizeOrDefault(10),
            iterableD.collectionSizeOrDefault(10),
        )
    )
    while (a.hasNext() && b.hasNext() && c.hasNext() && d.hasNext()) {
        list.add(transform(a.next(), b.next(), c.next(), d.next()))
    }
    return list
}

fun <Z> zip(
    iterables: List<Iterable<Any?>>,
    transform: (List<Any?>) -> Z
): ArrayList<Z> {
    val iterators = iterables.map { it.iterator() }

    val list = ArrayList<Z>(iterables.minOf { it.collectionSizeOrDefault(10) })
    while (iterators.all { it.hasNext() }) {
        list.add(transform(iterators.map { it.next() }))
    }
    return list
}

fun <T> Iterable<T>.collectionSizeOrDefault(default: Int): Int = if (this is Collection<*>) this.size else default