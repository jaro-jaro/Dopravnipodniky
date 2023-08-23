package cz.jaro.dopravnipodniky
/*
import cz.jaro.dopravnipodniky.*
import cz.jaro.dopravnipodniky.classes.DopravniPodnik
import cz.jaro.dopravnipodniky.classes.Ulice
import cz.jaro.dopravnipodniky.Orientace.SVISLE
import cz.jaro.dopravnipodniky.Orientace.VODOROVNE
import processing.core.PApplet

class MiniSketch(private val dp: DopravniPodnik) : PApplet() {

    private var velikostBloku = 1F // px

    private var miniTx = 0F
    private var miniTy = 0F

    override fun setup() {
        frameRate = TPS.toFloat()


        val velMestaZacatek = dp.velikostMesta.first.first * velikostUlicovyhoBloku * velikostBloku
        val velMestaKonec = dp.velikostMesta.second.first * velikostUlicovyhoBloku * velikostBloku

        val pomer = width / (velMestaKonec - velMestaZacatek)

        velikostBloku = pomer * .5F

        miniTx = (-dp.velikostMesta.first.first + 1) * velikostUlicovyhoBloku * velikostBloku
        miniTy = (-dp.velikostMesta.first.second + 1) * velikostUlicovyhoBloku * velikostBloku

    }

    override fun draw() {

        translate(miniTx, miniTy)

        background(sedPozadi)

        seznamKrizovatek.clear()
        for (x in dp.velikostMesta.first.first..dp.velikostMesta.second.first) {
            for (y in dp.velikostMesta.first.second..dp.velikostMesta.second.second) {

                val sousedi = dp.ulicove.filter {
                    it.konec == x to y || it.zacatek == x to y
                }

                if (sousedi.isEmpty()) {
                    continue
                }

                seznamKrizovatek += x to y
            }
        }

        dp.ulicove.forEach { ulice ->
           namalovatUlici(ulice)
        }

        seznamKrizovatek.forEach { (x, y) ->
            namalovatKrizovatku(x, y)
        }

    }

    private val seznamKrizovatek = mutableListOf<Pair<Int, Int>>() // (2 to 1) -> v Ulicovych blokach

    private fun namalovatKrizovatku(x: Int, y: Int) {

        val sousedi = dp.ulicove.filter {
            it.konec == x to y || it.zacatek == x to y
        }

        val zacatekXPx = velikostBloku *  x * (velikostUlicovyhoBloku + sirkaUlice) // v px
        val zacatekYPx = velikostBloku *  y * (velikostUlicovyhoBloku + sirkaUlice) // v px
        val konecXPx   = velikostBloku * (x * (velikostUlicovyhoBloku + sirkaUlice) + sirkaUlice) // v px
        val konecYPx   = velikostBloku * (y * (velikostUlicovyhoBloku + sirkaUlice) + sirkaUlice) // v px

        fill(sedUlice)
        noStroke()

        rectMode(CORNERS)
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

    private fun namalovatUlici(ulice: Ulice) {

        val zacatekXPx = ulice.zacatekXBlokuu * velikostBloku // v px
        val zacatekYPx = ulice.zacatekYBlokuu * velikostBloku // v px
        val konecXPx = ulice.konecXBlokuu * velikostBloku // v px
        val konecYPx = ulice.konecYBlokuu * velikostBloku // v px

        fill(sedUlice)
        noStroke()

        rectMode(CORNERS)
        rect(zacatekXPx, zacatekYPx, konecXPx, konecYPx)

        fill(sedObrubniku)

        val obrubnik = if (velikostBloku < 1) sirkaObrubniku else sirkaObrubniku * velikostBloku

        rect(zacatekXPx, zacatekYPx - obrubnik, konecXPx, zacatekYPx) // nahore
        rect(konecXPx, zacatekYPx, konecXPx + obrubnik, konecYPx) // vpravo
        rect(zacatekXPx, konecYPx, konecXPx, konecYPx + obrubnik) // dole
        rect(zacatekXPx - obrubnik, zacatekYPx, zacatekXPx, konecYPx) // vlevo


    }
}*/
