package cz.jaro.dopravnipodniky.sketches

import cz.jaro.dopravnipodniky.*
import cz.jaro.dopravnipodniky.other.PrefsHelper.dp
import cz.jaro.dopravnipodniky.other.PrefsHelper.vse
import processing.core.PApplet
import kotlin.math.roundToInt

class Sketch(
    var vykreslit_busy: Boolean = true,
    var vykreslit_linky: Boolean = false,
    val vybirani_linky: Boolean = false,
) : PApplet() {

    var velikostBloku = pocatecniPriblizeni // px

    override fun settings() {
        fullScreen(P2DX)
    }

    override fun setup() {
        frameRate = TPS.toFloat()

        if (vybirani_linky) pripravitNaVybiraniLinky()

    }

    override fun draw() {
        translate(tx, ty)
        background(sedPozadi)
        noStroke()

        if (dp.kalibrovat != 0) kalibrovat()

        if (!vybirani_linky) {
            vykreslit_linky = vse.zobrazitLinky
            vykreslit_busy = !vse.zobrazitLinky
        } else {
            vykreslit_linky = true
            vykreslit_busy = false
        }

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
           ulice.draw(this)
        }

        dp.baraky.forEach { barak ->
           barak.draw(this)
        }

        seznamKrizovatek.forEach { (x, y) ->
            namalovatKrizovatku(x, y)
        }

        if (vykreslit_busy) dp.busy.forEach { bus ->
            bus.draw(this)
        }

        if (vykreslit_linky) namalovatLinky()


        dp.ulicove.forEach { ulice ->
            if (ulice.trolej) ulice.nakreslitTroleje(this)
        }


        if (vybirani_linky) namalovatVybiraniLinky()


        ulozit()
    }

    override fun touchEnded() {
        super.touchEnded()

        dp.sledovanejBus = null

        s = 0F

        x1 = -1F
        y1 = -1F

        if (!vybirani_linky && editor) moznaChcesUdelatZastavku()

        ulozit()
    }

    val seznamKrizovatek = mutableListOf<Pair<Int, Int>>() // (2 to 1) -> v Ulicovych blokach


    fun jeVObdelniku(x: Int, y: Int, x1: Float, y1: Float, x2: Float, y2: Float): Boolean {
        return (x in x1.roundToInt()..x2.roundToInt()) && (y in y1.roundToInt()..y2.roundToInt())
    }

    fun ulozit() {

    }

    override fun touchMoved() {
        super.touchMoved()

        dp.sledovanejBus = null

        if (vybirani_linky) {
            if (touches.size == 1) poKliknutiPriVybiraniLinky()
            if (touches.size == 2) sunout()
            if (touches.size == 2) zachovavatPodobnost()
        } else {
            sunout()
            if (touches.size == 2) zachovavatPodobnost()
        }
        ulozit()
    }

    private var puvodniDx = 0F
    private var puvodniDy = 0F
    private var puvodniDz = 0F

    private fun kalibrovat() {

        if (dp.kalibrovat == -1) {
            dp.kalibrovat = dobaKalibraceT
            puvodniDz = velikostBloku - pocatecniPriblizeni
            puvodniDx = tx - pocatecniPosunutiX
            puvodniDy = ty - pocatecniPosunutiY
        }

        velikostBloku -= puvodniDz / dobaKalibraceT
        tx -= puvodniDx / dobaKalibraceT
        ty -= puvodniDy / dobaKalibraceT

        dp.kalibrovat--
    }
}
