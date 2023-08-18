package cz.jaro.dopravnipodniky.sketches

/*context (DrawScope)
fun Bus.draw(dp: DopravniPodnik) {
    if (linka != null) {
        val linka = dp.linka(linka)

        val seznamUlic = if (smerNaLince == POZITIVNE) {
            linka.ulice.toList()
        } else {
            linka.ulice.reversed()
        }

        val ulice = dp.ulice(seznamUlic[poziceNaLince])

        *//**
         * x ulice vlevo nahore
         *//*
        val x = ulice.zacatekX.toDp(velikostBloku) // px

        *//**
         * y ulice vlevo nahore
         *//*
        val y = ulice.zacatekY.toDp(velikostBloku) // px


        val px2 = 2.bloku.toDp(velikostBloku) // px

        *//**
         * konec busu v ulici ve smeru jizdy v pixelech
         *//*
        val a = poziceVUlici.toDp(velikostBloku) // px

        *//**
         * velikostUlicovyhoBloku v pixelech
         *//*
        val b = 1.ulicovychBloku.bloku.toDp(velikostBloku) // px

        rectMode(PApplet.CORNER)
//        fill(linka.barvicka)

        val pululice = (sirkaUlice / 2 + 1.bloku).toDp(velikostBloku) // v px

        *//**
         * 1. zjistit levej horni roh ulice (dale jen zacatek)
         * 1,5. zjistit pravej dolni roh ulice (dale jen konec)
         * 2. porovnat s koncem / zacatkem ulice predchozí (pokud existuje)
         *    -> je - POZITIVNI, ne -  NEGATIVNI
         * 2,5. pokud ulice neexistuje, porovnavat konec ulice s koncem / zacatkem ulice dalsi
         *    -> je - POZITIVNI, ne -  NEGATIVNI
         * 3. pokud je na lince ulice sama, smer busu v ulici urcuje smer busu na lince, return
         *
         * 4. pokud jede bus na lince v negativnim smeru, obratit
         * *//*

        val z = ulice.zacatek
        val k = ulice.konec

        val smerBusuNaUlici = when {
            poziceNaLince != 0 -> { // existuje ulice pred
                val ulicePred = dp.ulice(seznamUlic[poziceNaLince - 1])
                if (z == ulicePred.zacatek || z == ulicePred.konec) POZITIVNE else NEGATIVNE
            }

            seznamUlic.size != 1 -> { // existuje ulice po
                val ulicePo = dp.ulice(seznamUlic[1])
                if (k == ulicePo.zacatek || k == ulicePo.konec) POZITIVNE else NEGATIVNE
            }

            else -> {
                smerNaLince
            }
        }

        when (smerBusuNaUlici) {
            POZITIVNE -> {
                when (ulice.orietace) {
                    VODOROVNE -> {
                        // bus jede doprava
                        rect(
                            x + a,
                            y + px2 + pululice,
                            typBusu.delka.toBloky().toDp(velikostBloku),
                            sirkaBusu.toDp(velikostBloku),
                            3F.toDp(velikostBloku)
                        )
                        pozice = x + a to y + px2 + pululice
                    }
                    SVISLE -> {
                        // bus jede dolu
                        rect(
                            x + px2,
                            y + a,
                            sirkaBusu.toDp(velikostBloku),
                            typBusu.delka.toBloky().toDp(velikostBloku),
                            3F.toDp(velikostBloku)
                        )
                        pozice = x + px2 to y + a
                    }
                }
            }
            NEGATIVNE -> {
                when (ulice.orietace) {
                    VODOROVNE -> {
                        // bus jede doleva
                        rect(
                            x + b - a,
                            y + px2,
                            -typBusu.delka.toBloky().toDp(velikostBloku),
                            sirkaBusu.toDp(velikostBloku),
                            3F.toDp(velikostBloku)
                        )
                        pozice = x + b - a to y + px2
                    }
                    SVISLE -> {
                        // bus jede nahoru
                        rect(
                            x + px2 + 16F.toDp(velikostBloku),
                            y + b - a,
                            sirkaBusu.toDp(velikostBloku),
                            -typBusu.delka.toBloky().toDp(velikostBloku),
                            3F.toDp(velikostBloku)
                        )
                        pozice = x + px2 + 16F.toDp(velikostBloku) to y + b - a
                    }
                }
            }
        }
        if (dp.sledovanejBus == this@draw) sleduj()
    }
}*/

/*
context (DrawScope)
fun Bus.sleduj() {
    tx = -pozice.first + pocatecniPosunutiX
    ty = -pozice.second + pocatecniPosunutiY
    if (velikostBloku <= 1.9) {
        velikostBloku *= (TPS + .1F) / TPS.toFloat()
    }
}
*/
