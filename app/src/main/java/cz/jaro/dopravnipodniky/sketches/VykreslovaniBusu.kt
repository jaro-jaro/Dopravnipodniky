package cz.jaro.dopravnipodniky.sketches

import cz.jaro.dopravnipodniky.*
import cz.jaro.dopravnipodniky.classes.Bus
import cz.jaro.dopravnipodniky.other.Orientace.VODOROVNE
import cz.jaro.dopravnipodniky.other.PrefsHelper.dp
import cz.jaro.dopravnipodniky.other.Smer.POZITIVNE
import cz.jaro.dopravnipodniky.other.toSmer
import processing.core.PApplet

fun Bus.draw(sk: Sketch) {
    sk.apply {
        if (linka != -1L) {
            val linka = dp.linka(linka)

            val seznamUlic = if (smerNaLince == POZITIVNE) {
                linka.seznamUlic.toList()
            } else {
                linka.seznamUlic.reversed()
            }

            val ulice = dp.ulice(seznamUlic[poziceNaLince])

            /**
             * x ulice vlevo nahore
             */
            val x = ulice.zacatekXBlokuu * velikostBloku // px
            /**
             * y ulice vlevo nahore
             */
            val y = ulice.zacatekYBlokuu * velikostBloku // px


            val px2 = 2F * velikostBloku // px

            /**
             * konec busu v ulici ve smeru jizdy v pixelech
             */
            val a = poziceVUlici * velikostBloku // px
            /**
             * velikostUlicovyhoBloku v pixelech
             */
            val b = velikostUlicovyhoBloku * velikostBloku // px

            rectMode(PApplet.CORNER)
            fill(linka.barvicka)

            val pululice = (sirkaUlice / 2 + 1F) * velikostBloku // v px

            /**
            * 1. zjistit levej horni roh ulice (dale jen zacatek)
            * 1,5. zjistit pravej dolni roh ulice (dale jen konec)
            * 2. porovnat s koncem / zacatkem ulice predchozí (pokud existuje)
            *    -> je - POZITIVNI, ne -  NEGATIVNI
            * 2,5. pokud ulice neexistuje, porovnavat konec ulice s koncem / zacatkem ulice dalsi
            *    -> je - POZITIVNI, ne -  NEGATIVNI
            * 3. pokud je na lince ulice sama, smer busu v ulici urcuje smer busu na lince, return
            *
            * 4. pokud jede bus na lince v negativnim smeru, obratit
            * */

            val z = ulice.zacatek
            val k = ulice.konec

            val smerBusuNaUlici = when {
                poziceNaLince != 0 -> { // existuje ulice pred
                    val ulicePred = dp.ulice(seznamUlic[poziceNaLince - 1])
                    (z == ulicePred.zacatek || z == ulicePred.konec).toSmer()
                }
                poziceNaLince != seznamUlic.lastIndex -> { // existuje ulice po
                    val ulicePo = dp.ulice(seznamUlic[poziceNaLince + 1])
                    (k == ulicePo.zacatek || k == ulicePo.konec).toSmer()
                }
                else -> {
                    smerNaLince
                }
            }

            if (smerBusuNaUlici == POZITIVNE) {
                if (ulice.orietace == VODOROVNE) {
                    // bus jede doprava
                    rect(x + a, y + px2 + pululice, typBusu.delka * nasobitelDelkyBusu * velikostBloku, sirkaBusu * velikostBloku, 3F * velikostBloku)
                    pozice = x + a to y + px2 + pululice
                } else {
                    // bus jede dolu
                    rect(x + px2, y + a, sirkaBusu * velikostBloku, typBusu.delka * nasobitelDelkyBusu * velikostBloku, 3F * velikostBloku)
                    pozice = x + px2 to y + a
                }
            } else {
                if (ulice.orietace == VODOROVNE) {
                    // bus jede doleva
                    rect(x + b - a, y + px2, -typBusu.delka * nasobitelDelkyBusu * velikostBloku, sirkaBusu * velikostBloku, 3F * velikostBloku)
                    pozice = x + b - a to y + px2
                } else {
                    // bus jede nahoru
                    rect(x + px2 + 16F * velikostBloku, y + b - a, sirkaBusu * velikostBloku, -typBusu.delka * nasobitelDelkyBusu * velikostBloku, 3F * velikostBloku)
                    pozice = x + px2 + 16F * velikostBloku to y + b - a
                }
            }
            if (dp.sledovanejBus == this@draw) sleduj(this)
        }
    }
}

fun Bus.sleduj(sk: Sketch) {
    tx = -pozice.first + pocatecniPosunutiX
    ty = -pozice.second + pocatecniPosunutiY
    if (sk.velikostBloku <= 1.9) {
        sk.velikostBloku *= (TPS + .1F) / TPS.toFloat()
    }
}
