package cz.jaro.dopravnipodniky.sketches

import cz.jaro.dopravnipodniky.*
import cz.jaro.dopravnipodniky.classes.Barak
import cz.jaro.dopravnipodniky.classes.Ulice
import cz.jaro.dopravnipodniky.other.Orientace.SVISLE
import cz.jaro.dopravnipodniky.other.Orientace.VODOROVNE
import cz.jaro.dopravnipodniky.other.PrefsHelper.dp
import cz.jaro.dopravnipodniky.other.PrefsHelper.vse
import processing.core.PApplet
import processing.core.PConstants.*


fun Sketch.namalovatKrizovatku(x: Int, y: Int) {

    val sousedi = dp.ulicove.filter {
        it.konec == x to y || it.zacatek == x to y
    }

    val zacatekXPx = velikostBloku *  x * (velikostUlicovyhoBloku + sirkaUlice) // v px
    val zacatekYPx = velikostBloku *  y * (velikostUlicovyhoBloku + sirkaUlice) // v px
    val konecXPx   = velikostBloku * (x * (velikostUlicovyhoBloku + sirkaUlice) + sirkaUlice) // v px
    val konecYPx   = velikostBloku * (y * (velikostUlicovyhoBloku + sirkaUlice) + sirkaUlice) // v px

    fill(sedUlice)

    rectMode(PApplet.CORNERS)
    rect(zacatekXPx, zacatekYPx, konecXPx, konecYPx)

    fill(sedObrubniku)

    val obrubnik = if (velikostBloku < 1) sirkaObrubniku else sirkaObrubniku * velikostBloku

    if (sousedi.none { it.konecY == y && it.orietace == SVISLE })
        rect(zacatekXPx, zacatekYPx - obrubnik, konecXPx, zacatekYPx) // nahore
    if (sousedi.none { it.zacatekX == x && it.orietace == VODOROVNE })
        rect(konecXPx, zacatekYPx, konecXPx + obrubnik, konecYPx) // vpravo
    if (sousedi.none { it.zacatekY == y && it.orietace == SVISLE })
        rect(zacatekXPx, konecYPx, konecXPx, konecYPx + obrubnik) // dole
    if (sousedi.none { it.konecX == x && it.orietace == VODOROVNE })
        rect(zacatekXPx - obrubnik, zacatekYPx, zacatekXPx, konecYPx) // vlevo
}

context(Sketch)
fun Ulice.draw(sk: Sketch) {
    sk.apply {
        zastavka?.draw(this)

        val zacatekXPx = zacatekXBlokuu * velikostBloku // v px
        val zacatekYPx = zacatekYBlokuu * velikostBloku // v px
        val konecXPx = konecXBlokuu * velikostBloku // v px
        val konecYPx = konecYBlokuu * velikostBloku // v px

        //fill(BARVICKY[ulice.potencial])
        //fill(ulice.potencial * 20)
        fill(sedUlice)

        rectMode(PApplet.CORNERS)
        rect(zacatekXPx, zacatekYPx, konecXPx, konecYPx)

        fill(sedObrubniku)

        val obrubnik = if (velikostBloku < 1) sirkaObrubniku else sirkaObrubniku * velikostBloku

        rect(zacatekXPx, zacatekYPx - obrubnik, konecXPx, zacatekYPx) // nahore
        rect(konecXPx, zacatekYPx, konecXPx + obrubnik, konecYPx) // vpravo
        rect(zacatekXPx, konecYPx, konecXPx, konecYPx + obrubnik) // dole
        rect(zacatekXPx - obrubnik, zacatekYPx, zacatekXPx, konecYPx) // vlevo

    }
}

fun Ulice.nakreslitTroleje(sk: Sketch) {

    sk.apply {

        val zacatekXPx = zacatekXBlokuu * velikostBloku // v px
        val zacatekYPx = zacatekYBlokuu * velikostBloku // v px
        val konecXPx = konecXBlokuu * velikostBloku // v px
        val konecYPx = konecYBlokuu * velikostBloku // v px

        val p = 5 * velikostBloku // P jako poslech

        stroke(sedTroleje)
        strokeWeight(if (velikostBloku > 1) velikostBloku else 1F)

        if (orietace == VODOROVNE) {
            line(zacatekXPx + p, zacatekYPx + 5  * velikostBloku, konecXPx - p, zacatekYPx + 5  * velikostBloku)
            line(zacatekXPx + p, zacatekYPx + 10 * velikostBloku, konecXPx - p, zacatekYPx + 10 * velikostBloku)
            line(zacatekXPx + p, zacatekYPx + 20 * velikostBloku, konecXPx - p, zacatekYPx + 20 * velikostBloku)
            line(zacatekXPx + p, zacatekYPx + 25 * velikostBloku, konecXPx - p, zacatekYPx + 25 * velikostBloku)
        } else {
            line(zacatekXPx + 5  * velikostBloku, zacatekYPx + p, zacatekXPx + 5  * velikostBloku, konecYPx - p)
            line(zacatekXPx + 10 * velikostBloku, zacatekYPx + p, zacatekXPx + 10 * velikostBloku, konecYPx - p)
            line(zacatekXPx + 20 * velikostBloku, zacatekYPx + p, zacatekXPx + 20 * velikostBloku, konecYPx - p)
            line(zacatekXPx + 25 * velikostBloku, zacatekYPx + p, zacatekXPx + 25 * velikostBloku, konecYPx - p)
        }

        // arc(x, y, w, h, start, stop)

        val ctyrkrizovatkaZacatek = dp.ulicove.filter { (it.zacatek == zacatek || it.konec == zacatek) && it.trolej }.size == 4
        val ctyrkrizovatkaKonec = dp.ulicove.filter { (it.zacatek == konec || it.konec == konec) && it.trolej }.size == 4

        for (dalsiUlice in dp.ulicove) {

            noFill()

            if (dalsiUlice.zacatek == zacatek && dalsiUlice.konec == konec) continue

            when {
                orietace == SVISLE && dalsiUlice.trolej && (dalsiUlice.konec == konec || dalsiUlice.zacatek == konec) -> {

                    // odshora dolu a pak...

                    when {
                        dalsiUlice.zacatekX < konecX && !ctyrkrizovatkaKonec -> {
                            // vlevo

                            arc(konecXPx - sirkaUlice * velikostBloku - p, konecYPx - p, 10 * velikostBloku + 2*p, 10 * velikostBloku + 2*p, 0F, HALF_PI)
                            arc(konecXPx - sirkaUlice * velikostBloku - p, konecYPx - p, 20 * velikostBloku + 2*p, 20 * velikostBloku + 2*p, 0F, HALF_PI)
                            arc(konecXPx - sirkaUlice * velikostBloku - p, konecYPx - p, 40 * velikostBloku + 2*p, 40 * velikostBloku + 2*p, 0F, HALF_PI)
                            arc(konecXPx - sirkaUlice * velikostBloku - p, konecYPx - p, 50 * velikostBloku + 2*p, 50 * velikostBloku + 2*p, 0F, HALF_PI)

                        }
                        dalsiUlice.orietace == SVISLE -> {
                            // dolu

                            line(zacatekXPx + 5  * velikostBloku, konecYPx - p, zacatekXPx + 5  * velikostBloku, konecYPx + sirkaUlice * velikostBloku + p)
                            line(zacatekXPx + 10 * velikostBloku, konecYPx - p, zacatekXPx + 10 * velikostBloku, konecYPx + sirkaUlice * velikostBloku + p)
                            line(zacatekXPx + 20 * velikostBloku, konecYPx - p, zacatekXPx + 20 * velikostBloku, konecYPx + sirkaUlice * velikostBloku + p)
                            line(zacatekXPx + 25 * velikostBloku, konecYPx - p, zacatekXPx + 25 * velikostBloku, konecYPx + sirkaUlice * velikostBloku + p)
                        }
                    }
                }
                orietace == VODOROVNE && dalsiUlice.trolej && (dalsiUlice.konec == konec || dalsiUlice.zacatek == konec) -> {

                    // zleva doprava a pak...

                    when {
                        dalsiUlice.konecY > konecY && !ctyrkrizovatkaKonec -> {
                            // dolu

                            arc(konecXPx - p, konecYPx + p, 10 * velikostBloku + 2*p, 10 * velikostBloku + 2*p, PI + HALF_PI, TWO_PI)
                            arc(konecXPx - p, konecYPx + p, 20 * velikostBloku + 2*p, 20 * velikostBloku + 2*p, PI + HALF_PI, TWO_PI)
                            arc(konecXPx - p, konecYPx + p, 40 * velikostBloku + 2*p, 40 * velikostBloku + 2*p, PI + HALF_PI, TWO_PI)
                            arc(konecXPx - p, konecYPx + p, 50 * velikostBloku + 2*p, 50 * velikostBloku + 2*p, PI + HALF_PI, TWO_PI)

                        }
                        dalsiUlice.orietace == VODOROVNE -> {
                            // doprava

                            line(konecXPx - p, zacatekYPx + 5  * velikostBloku, konecXPx + sirkaUlice * velikostBloku + p, zacatekYPx + 5  * velikostBloku)
                            line(konecXPx - p, zacatekYPx + 10 * velikostBloku, konecXPx + sirkaUlice * velikostBloku + p, zacatekYPx + 10 * velikostBloku)
                            line(konecXPx - p, zacatekYPx + 20 * velikostBloku, konecXPx + sirkaUlice * velikostBloku + p, zacatekYPx + 20 * velikostBloku)
                            line(konecXPx - p, zacatekYPx + 25 * velikostBloku, konecXPx + sirkaUlice * velikostBloku + p, zacatekYPx + 25 * velikostBloku)
                        }
                    }
                }
                orietace == SVISLE && dalsiUlice.trolej && (dalsiUlice.konec == zacatek || dalsiUlice.zacatek == zacatek) -> {
                    // odspoda nahoru a pak...

                    if (dalsiUlice.konecX > zacatekX && !ctyrkrizovatkaZacatek) {
                        // vpravo

                        arc(zacatekXPx + sirkaUlice * velikostBloku + p, zacatekYPx + p, 10 * velikostBloku + 2*p, 10 * velikostBloku + 2*p, PI, PI + HALF_PI)
                        arc(zacatekXPx + sirkaUlice * velikostBloku + p, zacatekYPx + p, 20 * velikostBloku + 2*p, 20 * velikostBloku + 2*p, PI, PI + HALF_PI)
                        arc(zacatekXPx + sirkaUlice * velikostBloku + p, zacatekYPx + p, 40 * velikostBloku + 2*p, 40 * velikostBloku + 2*p, PI, PI + HALF_PI)
                        arc(zacatekXPx + sirkaUlice * velikostBloku + p, zacatekYPx + p, 50 * velikostBloku + 2*p, 50 * velikostBloku + 2*p, PI, PI + HALF_PI)

                    }

                }
                orietace == VODOROVNE && dalsiUlice.trolej && (dalsiUlice.konec == zacatek || dalsiUlice.zacatek == zacatek) -> {

                    // zprava doleva a pak...

                    if (dalsiUlice.zacatekY < zacatekY && !ctyrkrizovatkaZacatek) {
                        // nahoru

                        arc(zacatekXPx + p, zacatekYPx - p, 10 * velikostBloku + 2*p, 10 * velikostBloku + 2*p, HALF_PI, PI)
                        arc(zacatekXPx + p, zacatekYPx - p, 20 * velikostBloku + 2*p, 20 * velikostBloku + 2*p, HALF_PI, PI)
                        arc(zacatekXPx + p, zacatekYPx - p, 40 * velikostBloku + 2*p, 40 * velikostBloku + 2*p, HALF_PI, PI)
                        arc(zacatekXPx + p, zacatekYPx - p, 50 * velikostBloku + 2*p, 50 * velikostBloku + 2*p, HALF_PI, PI)

                    }

                }
            }
        }
        noStroke()
    }
}

fun Barak.draw(sk: Sketch) {
    sk.apply {

        val ulice = dp.ulice(ulice)

        var i = ulice.baraky.indexOf(this@draw)

        val druha = i >= ulice.baraky.size / 2

        if (druha) i -= ulice.baraky.size / 2
        
        val zacatekUliceXPx = ulice.zacatekXBlokuu * velikostBloku // v px
        val zacatekUliceYPx = ulice.zacatekYBlokuu * velikostBloku // v px
        val konecUliceXPx = ulice.konecXBlokuu * velikostBloku // v px
        val konecUliceYPx = ulice.konecYBlokuu * velikostBloku // v px

        val o = odsazeniBaraku * velikostBloku // odsazení od okraje

        val zaklad = barvicka + BARVICKYTEMAT.indexOf(vse.barva)

        val barvicka = BARVICKYTEMAT[
            zaklad
            - (if (zaklad >= BARVICKYTEMAT.size) BARVICKYTEMAT.size else 0)
            + (if (zaklad < 1) BARVICKYTEMAT.lastIndex else 0)
        ]

        fill(barvicka) //(sedBaraku)
        rectMode(CORNER)

        val rohovy = (i == 0 && !druha) || (i == 3 && druha)

        var scitanecVysky = 0F

        val sirka = velikostBaraku * velikostBloku * if (stredovy) 2 else 1

        val vyska = sirka * when {
            ulice.zastavka != null && !rohovy -> {
                scitanecVysky = .3F * sirka
                .65F
            }
            else -> 1F
        }

        if (stredovy) {

            rectMode(CENTER)

            val plusX = if (ulice.orietace == SVISLE) sirkaUlice * velikostBloku else 0F
            val plusY = if (ulice.orietace == VODOROVNE) sirkaUlice * velikostBloku else 0F

            rect( // uprostřed
                zacatekUliceXPx + velikostUlicovyhoBloku * velikostBloku / 2 + plusX,
                zacatekUliceYPx + velikostUlicovyhoBloku * velikostBloku / 2 + plusY,
                sirka,
                sirka,
                20F * velikostBloku
            )
        } else {
            when (ulice.orietace to druha) {
                VODOROVNE to true -> rect( // dole
                    zacatekUliceXPx + o + o + sirka + o + i * (o + sirka + o),
                    konecUliceYPx + o + (sirka - vyska),
                    sirka,
                    vyska + scitanecVysky,
                    5F * velikostBloku * (sirka / vyska)
                )
                VODOROVNE to false -> rect( // nahore
                    zacatekUliceXPx + o + i * (o + sirka + o),
                    zacatekUliceYPx - o - sirka - scitanecVysky,
                    sirka,
                    vyska + scitanecVysky,
                    5F * velikostBloku * (sirka / vyska)
                )
                SVISLE to true -> rect( // vlevo
                    zacatekUliceXPx - o - sirka - scitanecVysky,
                    zacatekUliceYPx + o + sirka + o + o + i * (o + sirka + o),
                    vyska + scitanecVysky,
                    sirka,
                    5F * velikostBloku * (sirka / vyska)
                )
                SVISLE to false -> rect( // vpravo
                    konecUliceXPx + o + (sirka - vyska),
                    zacatekUliceYPx + o + i * (o + sirka + o),
                    vyska + scitanecVysky,
                    sirka,
                    5F * velikostBloku * (sirka / vyska)
                )
            }
        }
    }
}
