package cz.jaro.dopravnipodniky.shared

import android.graphics.Paint
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.compose.ui.unit.toOffset
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Krizovatka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.TypKrizovatky
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import cz.jaro.dopravnipodniky.shared.jednotky.toDp
import cz.jaro.dopravnipodniky.shared.jednotky.toPx
import cz.jaro.dopravnipodniky.ui.main.DEBUG_MODE
import cz.jaro.dopravnipodniky.ui.theme.Theme
import kotlinx.coroutines.flow.Flow
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.roundToLong
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.time.Duration


/**
 * Zformátuje číslo, aby vypadalo hezky
 */
fun Double.formatovat(decimalPlaces: Int = 2): Text {
    if (this == Double.POSITIVE_INFINITY) return R.string.nekonecne_mnoho.toText()
    if (this == Double.NEGATIVE_INFINITY) return R.string.nekonecne_malo.toText()

    return this
        .zaokrouhlit(decimalPlaces = decimalPlaces)
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

fun Double.zaokrouhlit(decimalPlaces: Int = 0) = zaokrouhlit(na = 10F.pow(decimalPlaces))

fun Double.zaokrouhlit(na: Float = 1F) = this
    .takeUnless { it.isNaN() }
    ?.times(na)
    ?.roundToLong()
    ?.toDouble()
    ?.div(na)
    ?: this

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

fun DrawScope.translate(
    radius: Float,
    degrees: Float,
    pivot: Offset,
    isTurnRight: Boolean,
    block: DrawScope.() -> Unit,
) = translate(pivot) {
    val x = radius * sin(Math.toRadians(degrees.toDouble())).toFloat()
    val y = radius * cos(Math.toRadians(degrees.toDouble())).toFloat()
    translate(
        left = x * if (isTurnRight) 1 else -1,
        top = y * if (isTurnRight) -1 else 1,
        block = block,
    )
}

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
            iterableC.collectionSizeOrDefault(10)
        )
    )
    while (a.hasNext() && b.hasNext() && c.hasNext()) {
        list.add(transform(a.next(), b.next(), c.next()))
    }
    return list
}

fun <A, B, C, D, E> zip(
    iterableA: Iterable<A>,
    iterableB: Iterable<B>,
    iterableC: Iterable<C>,
    iterableD: Iterable<D>,
    iterableE: Iterable<E>,
) = zip(iterableA, iterableB, iterableC, iterableD, iterableE) { a, b, c, d, e -> Quintuple(a, b, c, d, e) }

fun <A, B, C, D, E, Z> zip(
    iterableA: Iterable<A>,
    iterableB: Iterable<B>,
    iterableC: Iterable<C>,
    iterableD: Iterable<D>,
    iterableE: Iterable<E>,
    transform: (a: A, b: B, c: C, d: D, e: E) -> Z
): ArrayList<Z> {
    val a = iterableA.iterator()
    val b = iterableB.iterator()
    val c = iterableC.iterator()
    val d = iterableD.iterator()
    val e = iterableE.iterator()

    val list = ArrayList<Z>(
        minOf(
            iterableA.collectionSizeOrDefault(10),
            iterableB.collectionSizeOrDefault(10),
            iterableC.collectionSizeOrDefault(10),
            iterableD.collectionSizeOrDefault(10),
            iterableE.collectionSizeOrDefault(10),
        )
    )
    while (a.hasNext() && b.hasNext() && c.hasNext() && d.hasNext() && e.hasNext()) {
        list.add(transform(a.next(), b.next(), c.next(), d.next(), e.next()))
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

inline fun <T> Iterable<T>.sumOfDp(selector: (T) -> Dp): Dp {
    var sum = 0.dp
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

fun Color.toArgb2() = 0xFF000000 + ((red * 255) * 0x10000 + (green * 255) * 0x100 + (blue * 255)).toInt()

fun IntRange.barvaTematu(tema: Theme): Pair<Color, Color> {
    val indexZakladniBarvy = Theme.entries.indexOf(tema)
    val indexNoveBarvy1MoznaPretekly = indexZakladniBarvy + first
    val indexNoveBarvy2MoznaPretekly = indexZakladniBarvy + last
    val indexNoveBarvy1 = (indexNoveBarvy1MoznaPretekly + Theme.entries.size) % Theme.entries.size
    val indexNoveBarvy2 = (indexNoveBarvy2MoznaPretekly + Theme.entries.size) % Theme.entries.size
    val barvicka1 = Theme.entries[indexNoveBarvy1].barva
    val barvicka2 = Theme.entries[indexNoveBarvy2].barva
    return barvicka1 to barvicka2
}

val ClosedFloatingPointRange<Double>.size get() = endInclusive - start

fun IntRange.toClosedDoubleRange() = first.toDouble()..last.toDouble()
fun ClosedFloatingPointRange<Float>.toDoubleRange() = start.toDouble()..endInclusive.toDouble()

fun Int.map(
    from: IntRange,
    to: ClosedFloatingPointRange<Float>,
) = toDouble().map(
    from = from.toClosedDoubleRange(),
    to = to.toDoubleRange(),
).toFloat()

fun Dp.map(
    from: Pair<Dp, Dp>,
    to: ClosedFloatingPointRange<Float>,
) = value.toDouble().map(
    from = (from.first.value..from.second.value).toDoubleRange(),
    to = to.toDoubleRange(),
).toFloat()

fun Int.map(
    from: IntRange,
    to: Pair<Color, Color>,
): Color {
    val r = this@map.map(
        from = from,
        to = to.first.red..to.second.red,
    )
    val g = this@map.map(
        from = from,
        to = to.first.green..to.second.green,
    )
    val b = this@map.map(
        from = from,
        to = to.first.blue..to.second.blue,
    )
    val a = this@map.map(
        from = from,
        to = to.first.alpha..to.second.alpha,
    )
    return Color(r, g, b, a)
}

fun Double.map(
    from: ClosedFloatingPointRange<Double>,
    to: ClosedFloatingPointRange<Double>,
): Double {
    val localValue = this - from.start
    val relativeValue = (localValue / from.size)
    val newLocalValue = relativeValue * to.size
    return newLocalValue + to.start
}

fun TypZatoceni.delkaZatoceni(sirkaBusu: Dp) = delkyCastiZatoceni(sirkaBusu).sumOfDp { it }

fun TypZatoceni.delkyCastiZatoceni(sirkaBusu: Dp) = when (this) {
    TypZatoceni.Rovne -> listOf(predsazeniKrizovatky + sirkaUlice + predsazeniKrizovatky)
    TypZatoceni.Vpravo -> {
        val r = predsazeniKrizovatky + odsazeniBusu + sirkaBusu / 2
        val theta = Math.PI / 2
        listOf(theta * r)
    }

    TypZatoceni.Vlevo -> {
        val r =
            predsazeniKrizovatky + sirkaUlice - (odsazeniBusu + sirkaBusu / 2)
        val theta = Math.PI / 2
        listOf(theta * r)
    }

    is TypZatoceni.Kruhac -> {
        val delkaCtvrtnyKruhace = delkaCtvrtnyKruhace(sirkaBusu)
        val delkaNajezduNaKruhac = delkaNajezduNaKruhac(sirkaBusu)
        listOf(delkaNajezduNaKruhac, delkaCtvrtnyKruhace * ctvrtin, delkaNajezduNaKruhac)
    }

    TypZatoceni.Spatne -> listOf(0.dp)
}

fun delkaNajezduNaKruhac(sirkaBusu: Dp) = run {
    val r = predsazeniKrizovatky + odsazeniBusu + sirkaBusu / 2
    val theta = Math.PI / 4
    theta * r
}

fun delkaCtvrtnyKruhace(sirkaBusu: Dp) = run {
    val r = .5 * sqrt(2.0) * sirkaUlice + predsazeniKrizovatky * sqrt(2.0) - predsazeniKrizovatky - odsazeniBusu - sirkaBusu / 2
    val theta = Math.PI / 2
    theta * r
}

fun typZatoceni(
    krizovatka: Krizovatka?,
    ulice: Ulice,
    pristiUlice: Ulice?,
) = when {
    pristiUlice == null && krizovatka?.typ == TypKrizovatky.Kruhac -> TypZatoceni.KruhacOtocka
    pristiUlice == null -> TypZatoceni.Spatne
    pristiUlice.orientace == ulice.orientace && krizovatka?.typ == TypKrizovatky.Kruhac -> TypZatoceni.KruhacRovne
    pristiUlice.orientace == ulice.orientace -> TypZatoceni.Rovne
    else -> {
        val vpravoSvisle = when {
            ulice.zacatek == pristiUlice.konec -> false
            ulice.zacatek == pristiUlice.zacatek -> true
            ulice.konec == pristiUlice.konec -> true
            ulice.konec == pristiUlice.zacatek -> false
            else -> throw IllegalStateException("WTF")
        }

        val vpravo =
            if (ulice.orientace == Orientace.Svisle) vpravoSvisle else !vpravoSvisle

        when {
            vpravo && krizovatka?.typ == TypKrizovatky.Kruhac -> TypZatoceni.KruhacVpravo
            vpravo -> TypZatoceni.Vpravo
            krizovatka?.typ == TypKrizovatky.Kruhac -> TypZatoceni.KruhacVlevo
            else -> TypZatoceni.Vlevo
        }
    }
}

operator fun Dp.times(other: Double) = times(other.toFloat())

inline fun <T> Iterable<T>.sumOfIndexed(selector: (Int, T) -> Int): Int {
    var index = 0
    var sum = 0
    for (element in this) {
        sum += selector(index++, element)
    }
    return sum
}

fun all(vararg vec: Boolean) = vec.all { it }
fun DrawScope.drawText(
    text: String,
    position: Offset,
    fontSize: Float = 14.sp.toPx(),
    color: Color = Color.White,
) {
    if (DEBUG_MODE) drawIntoCanvas {
        it.nativeCanvas.drawText(
            text,
            position.x,
            position.y,
            Paint().apply {
                this.color = color.toArgb()
                this.textSize = fontSize
            }
        )
    }
}