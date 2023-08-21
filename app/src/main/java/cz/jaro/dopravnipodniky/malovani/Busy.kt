package cz.jaro.dopravnipodniky.malovani

/*fun DrawScope.namalovatLinky() {
    dp.ulicove.forEach { ulice ->
        val linky = dp.linky.filter { ulice.id in it.seznamUlic }

        val zacatekXPx = ulice.zacatekXBlokuu * velikostBloku // v px
        val zacatekYPx = ulice.zacatekYBlokuu * velikostBloku // v px
        //val konecXPx = ulice.konecXBlokuu * velikostBloku // v px
        //val konecYPx = ulice.konecYBlokuu * velikostBloku // v px

        val sirka = ulice.sirkaBlokuu!! * velikostBloku
        val delka = ulice.delkaBlokuu!! * velikostBloku


        linky.forEachIndexed { i, linka ->

            //val maxLinekVUlici = dp.ulicove.maxOf { it.pocetLinek }

            var jeSpodnejsiUliceStejneOrientovana: Boolean? = null
            var jeVrchnejsiUliceStejneOrientovana: Boolean? = null

            if (ulice.orietace == VODOROVNE) {
                jeVrchnejsiUliceStejneOrientovana = linka.seznamUlic.any {
                    dp.ulice(it).zacatekX == ulice.zacatekX - 1 &&
                    dp.ulice(it).konec == ulice.zacatek }
            }
            if (ulice.orietace == SVISLE) {
                jeVrchnejsiUliceStejneOrientovana = linka.seznamUlic.any {
                    dp.ulice(it).konec == ulice.zacatek &&
                    dp.ulice(it).zacatekY == ulice.zacatekY - 1 }
            }

            if (ulice.orietace == VODOROVNE) {
                jeSpodnejsiUliceStejneOrientovana = linka.seznamUlic.any {
                    dp.ulice(it).konecX == ulice.konecX + 1 &&
                    dp.ulice(it).zacatek == ulice.konec }
            }
            if (ulice.orietace == SVISLE) {
                jeSpodnejsiUliceStejneOrientovana = linka.seznamUlic.any {
                    dp.ulice(it).zacatek == ulice.konec &&
                    dp.ulice(it).konecY == ulice.konecY + 1 }
            }

            var mensitelZacatku = 0F // px
            var scitanecKonce = 0F // px

            if (jeVrchnejsiUliceStejneOrientovana == true) {
                mensitelZacatku = 1.5F * sirkaUlice * velikostBloku
            }
            if (jeSpodnejsiUliceStejneOrientovana == true) {
                scitanecKonce = 1.5F * sirkaUlice * velikostBloku
            }

            if (jeVrchnejsiUliceStejneOrientovana == false) {

                if (linka.seznamUlic.any {
                        dp.ulice(it).konec == ulice.zacatek ||
                        dp.ulice(it).zacatek == ulice.zacatek && dp.ulice(it) != ulice }) {
                    //val vrchnejsiUlice = dp.ulice(linka.seznamUlic.first {
                    //    dp.ulice(it).konec == ulice.zacatek ||
                     //   dp.ulice(it).zacatek == ulice.zacatek })
                    //val vrchnejsiLinky = dp.linky.filter { vrchnejsiUlice.id in it.seznamUlic }

                    mensitelZacatku = (1F - dp.linky.indexOf(linka) / dp.linky.size.toFloat()) * sirkaUlice * velikostBloku
                }
            }
            if (jeSpodnejsiUliceStejneOrientovana == false) {

                if (linka.seznamUlic.any {
                        dp.ulice(it).zacatek == ulice.konec ||
                        dp.ulice(it).konec == ulice.konec && dp.ulice(it) != ulice }) {
                    //val spodnejsiUlice = dp.ulice(linka.seznamUlic.first {
                    //    dp.ulice(it).zacatek == ulice.konec ||
                    //    dp.ulice(it).zacatek == ulice.zacatek && dp.ulice(it) == ulice })
                    //val spodnejsiLinky = dp.linky.filter { spodnejsiUlice.id in it.seznamUlic }

                    scitanecKonce = (dp.linky.indexOf(linka) + 1) / dp.linky.size.toFloat() * sirkaUlice * velikostBloku
                }
            }

            if (vybirani_linky) {
                val pomer2 = (dp.linky.indexOf(linka) + 1.0) / (dp.linky.size + 1.0)
                fill(Color.rgb((vse.barva.red * pomer2).toInt(), (vse.barva.green * pomer2).toInt(), (vse.barva.blue * pomer2).toInt()))

                //fill(linka.barvicka, 50F)
            } else {
                fill(linka.barvicka)
            }


            rectMode(PApplet.CORNER)

            when(ulice.orietace) {
                SVISLE -> rect(
                    zacatekXPx + dp.linky.indexOf(linka) * (sirka / dp.linky.size) + velikostBloku / 2, //zacatekXPx + i * (sirka / maxLinekVUlici) + velikostBloku / 2
                    zacatekYPx - mensitelZacatku + velikostBloku / 2,
                    sirka / dp.linky.size - velikostBloku / 2,
                    delka + scitanecKonce + mensitelZacatku - velikostBloku / 2,
                    sirka / linky.size / 2
                )
                VODOROVNE -> rect(
                    zacatekXPx - mensitelZacatku + velikostBloku / 2,
                    zacatekYPx + dp.linky.indexOf(linka) * (sirka / dp.linky.size) + velikostBloku / 2, //zacatekXPx + i * (sirka / maxLinekVUlici) + velikostBloku / 2
                    delka + scitanecKonce + mensitelZacatku - velikostBloku / 2,
                    sirka / dp.linky.size - velikostBloku / 2,
                    delka / linky.size / 2
                )
            }
        }

    }
}*/
