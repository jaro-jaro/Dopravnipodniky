package cz.jaro.dopravnipodniky.sketches

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
import cz.jaro.dopravnipodniky.Orientace.SVISLE
import cz.jaro.dopravnipodniky.Orientace.VODOROVNE
import cz.jaro.dopravnipodniky.TypBaraku
import cz.jaro.dopravnipodniky.classes.Barak
import cz.jaro.dopravnipodniky.classes.DopravniPodnik
import cz.jaro.dopravnipodniky.classes.Ulice
import cz.jaro.dopravnipodniky.jednotky.Pozice
import cz.jaro.dopravnipodniky.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.jednotky.bloku
import cz.jaro.dopravnipodniky.jednotky.blokuSUlicema
import cz.jaro.dopravnipodniky.jednotky.toDp
import cz.jaro.dopravnipodniky.odsazeniBaraku
import cz.jaro.dopravnipodniky.sedObrubniku
import cz.jaro.dopravnipodniky.sedUlice
import cz.jaro.dopravnipodniky.sirkaObrubniku
import cz.jaro.dopravnipodniky.sirkaUlice
import cz.jaro.dopravnipodniky.theme.Theme
import cz.jaro.dopravnipodniky.velikostBaraku
import cz.jaro.dopravnipodniky.velikostUlicovyhoBloku

context(DrawScope)
fun namalovatKrizovatku(
    dp: DopravniPodnik,
    krizovatka: Pozice<UlicovyBlok>,
) {
    val (x, y) = krizovatka

    val sousedi = dp.ulicove.filter {
        it.konec == krizovatka || it.zacatek == krizovatka
    }

    val zacatekX = x.blokuSUlicema.toDp(priblizeni)
    val zacatekY = y.blokuSUlicema.toDp(priblizeni)
    val konecX = (x.blokuSUlicema + sirkaUlice).toDp(priblizeni)
    val konecY = (y.blokuSUlicema + sirkaUlice).toDp(priblizeni)

    val velikost = sirkaUlice.toDp(priblizeni)

    drawRect(
        color = sedUlice,
        topLeft = Offset(zacatekX.toPx(), zacatekY.toPx()),
        bottomRight = Offset(konecX.toPx(), konecY.toPx()),
    )

    val obrubnik = sirkaObrubniku.toDp(priblizeni/*.coerceAtLeast(1F)*/)

    if (sousedi.none { it.konec == krizovatka && it.orietace == SVISLE }) drawRect(
        color = sedObrubniku,
        topLeft = Offset(zacatekX.toPx(), zacatekY.toPx() - obrubnik.toPx()),
        size = Size(velikost.toPx(), obrubnik.toPx())
    ) // nahore
    if (sousedi.none { it.konec == krizovatka && it.orietace == VODOROVNE }) drawRect(
        color = sedObrubniku,
        topLeft = Offset(zacatekX.toPx() - obrubnik.toPx(), zacatekY.toPx()),
        size = Size(obrubnik.toPx(), velikost.toPx())
    ) // vlevo
    if (sousedi.none { it.zacatek == krizovatka && it.orietace == SVISLE }) drawRect(
        color = sedObrubniku,
        topLeft = Offset(zacatekX.toPx(), konecY.toPx()),
        size = Size(velikost.toPx(), obrubnik.toPx())
    ) // dole
    if (sousedi.none { it.zacatek == krizovatka && it.orietace == VODOROVNE }) drawRect(
        color = sedObrubniku,
        topLeft = Offset(konecX.toPx(), zacatekY.toPx()),
        size = Size(obrubnik.toPx(), velikost.toPx())
    ) // vpravo
}

context (DrawScope)
fun Barak.draw(
    tema: Theme,
    ulice: Ulice,
) {
    val (i, barakJeNaDruheStraneUlice) = ulice.baraky.indexOf(this@draw).let { i ->
        val jeNaDruheStrane = i >= ulice.baraky.size / 2

        (if (jeNaDruheStrane) i - ulice.baraky.size / 2 else i) to jeNaDruheStrane
    }

    val zacatekUliceX = ulice.zacatekX.toDp(priblizeni).toPx()
    val zacatekUliceY = ulice.zacatekY.toDp(priblizeni).toPx()
    val konecUliceX = ulice.konecX.toDp(priblizeni).toPx()
    val konecUliceY = ulice.konecY.toDp(priblizeni).toPx()

    val odsazeni = odsazeniBaraku.toDp(priblizeni).toPx()

    val indexbarvy = Theme.entries.indexOf(tema)
    val indexSkoroNoveBarvy = indexbarvy + barvicka
    val indexNoveBarvy = indexSkoroNoveBarvy
        .minus(if (indexSkoroNoveBarvy >= Theme.entries.size) Theme.entries.size else 0)
        .plus(if (indexSkoroNoveBarvy < 1) Theme.entries.lastIndex else 0)
    val barvicka = Theme.entries[indexNoveBarvy].barva // todo je to cerny

//    val rohovy = (i == 0 && !barakJeNaDruheStraneUlice) || (i == 3 && barakJeNaDruheStraneUlice)

    val scitanecVysky = 0F

    val sirka = velikostBaraku.toDp(priblizeni).toPx()

    @Suppress("UnnecessaryVariable")
    val vyska = /*if (ulice.maZastavku && !rohovy) {
        scitanecVysky = .3F * sirka
        sirka * .65F
    } else*/ sirka

    if (typ == TypBaraku.Stredovy) {

        val pulUlicovyhoBloku = velikostUlicovyhoBloku.bloku.toDp(priblizeni).toPx() / 2
        translate(
            left = zacatekUliceX,
            top = zacatekUliceY,
        ) {
            val rohBloku = when (ulice.orietace) {
                SVISLE -> Offset(x = sirkaUlice.toDp(priblizeni).toPx())
                VODOROVNE -> Offset(y = sirkaUlice.toDp(priblizeni).toPx())
            }
            translate(
                offset = rohBloku
            ) {
                val stredBloku = Offset(
                    x = pulUlicovyhoBloku,
                    y = pulUlicovyhoBloku
                )
                translate(
                    offset = stredBloku - Offset(x = sirka, y = sirka)
                ) {
                    drawRoundRect(
                        color = barvicka,
                        size = Size(sirka, sirka) * 2F,
                        cornerRadius = CornerRadius(20.bloku.toDp(priblizeni).toPx())
                    )
                }
            }
        }
    } else {
        when (ulice.orietace to barakJeNaDruheStraneUlice) {
            VODOROVNE to true -> drawRoundRect( // dole
                color = barvicka,
                topLeft = Offset(
                    x = zacatekUliceX + odsazeni + sirka + odsazeni + i * (sirka + odsazeni),
                    y = konecUliceY + odsazeni + (sirka - vyska),
                ),
                size = Size(
                    width = sirka,
                    height = vyska + scitanecVysky,
                ),
                cornerRadius = CornerRadius(5.bloku.toDp(priblizeni).toPx() * (sirka / vyska))
            )

            VODOROVNE to false -> drawRoundRect( // nahore
                color = barvicka,
                topLeft = Offset(
                    x = zacatekUliceX + odsazeni + i * (sirka + odsazeni),
                    y = zacatekUliceY - odsazeni - sirka - scitanecVysky,
                ),
                size = Size(
                    width = sirka,
                    height = vyska + scitanecVysky,
                ),
                cornerRadius = CornerRadius(5.bloku.toDp(priblizeni).toPx() * (sirka / vyska))
            )

            SVISLE to true -> drawRoundRect( // vlevo
                color = barvicka,
                topLeft = Offset(
                    x = zacatekUliceX - odsazeni - sirka - scitanecVysky,
                    y = zacatekUliceY + sirka + odsazeni + odsazeni + i * (sirka + odsazeni),
                ),
                size = Size(
                    width = vyska + scitanecVysky,
                    height = sirka,
                ),
                cornerRadius = CornerRadius(5.bloku.toDp(priblizeni).toPx() * (sirka / vyska))
            )

            SVISLE to false -> drawRoundRect( // vpravo
                color = barvicka,
                topLeft = Offset(
                    x = konecUliceX + odsazeni + (sirka - vyska),
                    y = zacatekUliceY + odsazeni + i * (sirka + odsazeni),
                ),
                size = Size(
                    width = vyska + scitanecVysky,
                    height = sirka,
                ),
                cornerRadius = CornerRadius(5.bloku.toDp(priblizeni).toPx() * (sirka / vyska))
            )
        }
    }
}

context(DrawScope)
fun Ulice.draw() {
//    zastavka?.draw()

    val zacatekX = zacatekX.toDp(priblizeni)
    val zacatekY = zacatekY.toDp(priblizeni)
    val konecX = konecX.toDp(priblizeni)
    val konecY = konecY.toDp(priblizeni)

    //fill(BARVICKY[ulice.potencial])
    //fill(ulice.potencial * 20)

    drawRect(
        color = sedUlice,
        topLeft = Offset(zacatekX.toPx(), zacatekY.toPx()),
        bottomRight = Offset(konecX.toPx(), konecY.toPx()),
    )

    val obrubnik = sirkaObrubniku.toDp(priblizeni/*.coerceAtLeast(1F)*/)

    drawRect(
        color = sedObrubniku,
        topLeft = Offset(zacatekX.toPx(), zacatekY.toPx() - obrubnik.toPx()),
        bottomRight = Offset(konecX.toPx(), zacatekY.toPx()),
    ) // nahore
    drawRect(
        color = sedObrubniku,
        topLeft = Offset(zacatekX.toPx() - obrubnik.toPx(), zacatekY.toPx()),
        bottomRight = Offset(zacatekX.toPx(), konecY.toPx()),
    ) // vlevo
    drawRect(
        color = sedObrubniku,
        topLeft = Offset(zacatekX.toPx(), konecY.toPx()),
        bottomRight = Offset(konecX.toPx(), konecY.toPx() + obrubnik.toPx()),
    ) // dole
    drawRect(
        color = sedObrubniku,
        topLeft = Offset(konecX.toPx(), zacatekY.toPx()),
        bottomRight = Offset(konecX.toPx() + obrubnik.toPx(), konecY.toPx()),
    ) // vpravo
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

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
fun DrawScope.drawArc(
    color: Color,
    startAngle: Float,
    sweepAngle: Float,
    useCenter: Boolean,
    center: Offset,
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