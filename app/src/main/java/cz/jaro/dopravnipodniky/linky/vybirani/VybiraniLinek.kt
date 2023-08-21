package cz.jaro.dopravnipodniky.linky.vybirani

val kliklyKrizovatky = mutableListOf<Pair<Int, Int>>() // (2 to 1) -> v Ulicovych blokach

/*fun DrawScope.pripravitNaVybiraniLinky() {

    // vytvorit novou linku
    kliklyKrizovatky.clear()

    val btnHotovo = activity.findViewById<Button>(R.id.btnHotovo)

    btnHotovo.setOnClickListener {

        val linkaDialogBinding = LinkaDialogBinding.inflate(LayoutInflater.from(context))

        var pocetKliknuti = 1

        linkaDialogBinding.btnBarvaLinky.setBackgroundColor(BARVICKY[pocetKliknuti])
        linkaDialogBinding.tvBarvaLinky.text = context.getString(R.string.barva_linky, NAZVYBARVICEK[pocetKliknuti])

        linkaDialogBinding.btnDopravaLinka.setOnClickListener {
            pocetKliknuti ++

            if (pocetKliknuti > BARVICKY.lastIndex) pocetKliknuti = 1

            linkaDialogBinding.btnBarvaLinky.setBackgroundColor(BARVICKY[pocetKliknuti])

            linkaDialogBinding.tvBarvaLinky.text = context.getString(R.string.barva_linky, NAZVYBARVICEK[pocetKliknuti])
        }
        linkaDialogBinding.btnDolevaLinka.setOnClickListener {
            pocetKliknuti --

            if (pocetKliknuti < 1) pocetKliknuti = BARVICKY.lastIndex

            linkaDialogBinding.btnBarvaLinky.setBackgroundColor(BARVICKY[pocetKliknuti])

            linkaDialogBinding.tvBarvaLinky.text = context.getString(R.string.barva_linky, NAZVYBARVICEK[pocetKliknuti])
        }

        MaterialAlertDialogBuilder(context).apply {
            setTitle(context.getString(R.string.vytvorit_linku))

            setView(linkaDialogBinding.clLinkaDIalog)

            setPositiveButton(R.string.vytvorit) { dialog, _ ->
                dialog.cancel()

                if (!linkaDialogBinding.etCisloLinky.text.toString().isDigitsOnly() || linkaDialogBinding.etCisloLinky.text.toString().isEmpty()) {

                    Toast.makeText(context, R.string.spatne_cislo_linky, Toast.LENGTH_LONG).show()
                    return@setPositiveButton
                }
                if (dp.linky.any { it.cislo == linkaDialogBinding.etCisloLinky.text.toString().toInt() }) {
                    Toast.makeText(context, R.string.linka_existuje, Toast.LENGTH_LONG).show()
                    return@setPositiveButton
                }
                if (kliklyKrizovatky.size < 2) {

                    Toast.makeText(context, R.string.linka_kratka, Toast.LENGTH_LONG).show()
                    return@setPositiveButton
                }

                val linka = Linka(
                    linkaDialogBinding.etCisloLinky.text.toString().toInt(),
                    kliklyKrizovatky, BARVICKY[pocetKliknuti], context
                )

                for (ulice in linka.seznamUlic) {
                    dp.ulice(ulice).pocetLinek++
                }

                context.dosahni("linka1", btnHotovo)

                if (dp.linky.any { it.seznamUlic == linka.seznamUlic }) context.dosahni("stejneLinky", btnHotovo)

                dp.linky.add(linka)

                activity.finish()

            }

            setNeutralButton(android.R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }

            activity.runOnUiThread {
                show()
            }

        }

    }

    val btnReset = activity.findViewById<Button>(R.id.btnReset)

    btnReset.setOnClickListener {
        kliklyKrizovatky.clear()
    }

}*/

/*fun DrawScope.poKliknutiPriVybiraniLinky() {

    for ((xKrizovatky, yKrizovatky) in seznamKrizovatek) {

        val zacatekXPx = velikostBloku * xKrizovatky * (velikostUlicovyhoBloku + sirkaUlice) // v Px
        val zacatekYPx = velikostBloku * yKrizovatky * (velikostUlicovyhoBloku + sirkaUlice) // v Px
        val konecXPx   = velikostBloku * xKrizovatky * (velikostUlicovyhoBloku + sirkaUlice) + velikostBloku * sirkaUlice // v Px
        val konecYPx   = velikostBloku * yKrizovatky * (velikostUlicovyhoBloku + sirkaUlice) + velikostBloku * sirkaUlice // v Px

        if (jeVObdelniku(
                mouseX,
                mouseY,
                zacatekXPx - 2 * sirkaUlice * velikostBloku + tx,
                zacatekYPx - 2 * sirkaUlice * velikostBloku + ty,
                konecXPx + 2 * sirkaUlice * velikostBloku + tx,
                konecYPx + 2 * sirkaUlice * velikostBloku + ty
            )
        ) {

            if (kliklyKrizovatky.isEmpty()) {
                kliklyKrizovatky += xKrizovatky to yKrizovatky
            } else if (kliklyKrizovatky.size > 1 && // odstranit posledni krizovatku
                xKrizovatky to yKrizovatky == kliklyKrizovatky[kliklyKrizovatky.lastIndex - 1]) {
                kliklyKrizovatky.removeAt(kliklyKrizovatky.lastIndex)
            } else if ( // vedlejsi krizovatka
                xKrizovatky - 1 to yKrizovatky == kliklyKrizovatky.last() ||
                xKrizovatky + 1 to yKrizovatky == kliklyKrizovatky.last() ||
                xKrizovatky to yKrizovatky - 1 == kliklyKrizovatky.last() ||
                xKrizovatky to yKrizovatky + 1 == kliklyKrizovatky.last()
            ) {
                if (dp.ulicove.any {
                        it.zacatek == xKrizovatky to yKrizovatky && it.konec   == kliklyKrizovatky.last() ||
                                it.konec   == xKrizovatky to yKrizovatky && it.zacatek == kliklyKrizovatky.last()
                    }) {
                    if (xKrizovatky to yKrizovatky !in kliklyKrizovatky) {
                        kliklyKrizovatky += xKrizovatky to yKrizovatky
                    } else if (BuildConfig.DEBUG) {
                        kliklyKrizovatky += xKrizovatky to yKrizovatky
                    }
                }
            }
        }
    }
}*/

/*fun DrawScope.namalovatVybiraniLinky() {

    for ((i, krizovatka) in kliklyKrizovatky.withIndex()) {

        val (xKrizovatky, yKrizovatky) = krizovatka

        val zacatekXBloku = xKrizovatky * (velikostUlicovyhoBloku + sirkaUlice) + 5 // v Blocich
        val zacatekYBloku = yKrizovatky * (velikostUlicovyhoBloku + sirkaUlice) + 5 // v Blocich
        val konecXBloku =   xKrizovatky * (velikostUlicovyhoBloku + sirkaUlice) + sirkaUlice - 5 // v Blocich
        val konecYBloku =   yKrizovatky * (velikostUlicovyhoBloku + sirkaUlice) + sirkaUlice - 5 // v Blocich


        val zacatekXPxKrizovatky = (zacatekXBloku) * velikostBloku // Px
        val zacatekYPxKrizovatky = (zacatekYBloku) * velikostBloku // Px
        val konecXPxKrizovatky = (konecXBloku) * velikostBloku // Px
        val konecYPxKrizovatky = (konecYBloku) * velikostBloku // Px

        rectMode(PApplet.CORNERS)
        fill(Color.rgb(255 - vse.barva.red, 255 - vse.barva.green, 255 - vse.barva.blue))

        if (i == kliklyKrizovatky.lastIndex) {
            ellipseMode(CENTER)
            strokeWeight(3F)
            circle(
                (konecXPxKrizovatky + zacatekXPxKrizovatky) / 2,
                (konecYPxKrizovatky + zacatekYPxKrizovatky) / 2,
                sqrt(2F) * sirkaUlice * velikostBloku
            )
            noStroke()
        }

        if (i == 0) continue

        val minulaKrizovatka = kliklyKrizovatky[i - 1]

        val zacatekXBlokuMinulyKrizovatky = minulaKrizovatka.first  * (velikostUlicovyhoBloku + sirkaUlice) + 5 // v Blocich
        val zacatekYBlokuMinulyKrizovatky = minulaKrizovatka.second * (velikostUlicovyhoBloku + sirkaUlice) + 5 // v Blocich
        val konecXBlokuMinulyKrizovatky =   minulaKrizovatka.first  * (velikostUlicovyhoBloku + sirkaUlice) + sirkaUlice - 5 // v Blocich
        val konecYBlokuMinulyKrizovatky =   minulaKrizovatka.second * (velikostUlicovyhoBloku + sirkaUlice) + sirkaUlice - 5 // v Blocich

        val zacatekXPxMinulyKrizovatky = (zacatekXBlokuMinulyKrizovatky) * velikostBloku // Px
        val zacatekYPxMinulyKrizovatky = (zacatekYBlokuMinulyKrizovatky) * velikostBloku // Px
        val konecXPxMinulyKrizovatky =   (konecXBlokuMinulyKrizovatky)   * velikostBloku // Px
        val konecYPxMinulyKrizovatky =   (konecYBlokuMinulyKrizovatky)   * velikostBloku // Px


        if (
            (zacatekXBlokuMinulyKrizovatky < zacatekXBloku
                    && zacatekYBloku == zacatekYBlokuMinulyKrizovatky)
            ||
            (zacatekYBlokuMinulyKrizovatky < zacatekYBloku
                    && zacatekXBloku == zacatekXBlokuMinulyKrizovatky)
        ) {
            rect(
                zacatekXPxMinulyKrizovatky, zacatekYPxMinulyKrizovatky,
                konecXPxKrizovatky, konecYPxKrizovatky,
                (sirkaUlice - 10) * velikostBloku / 2
            )
        }
        else if (
            (zacatekXBlokuMinulyKrizovatky > zacatekXBloku
                    && zacatekYBloku == zacatekYBlokuMinulyKrizovatky)
            ||
            (zacatekYBlokuMinulyKrizovatky > zacatekYBloku
                    && zacatekXBloku == zacatekXBlokuMinulyKrizovatky)
        ) {
            rect(
                zacatekXPxKrizovatky, zacatekYPxKrizovatky,
                konecXPxMinulyKrizovatky, konecYPxMinulyKrizovatky,
                (sirkaUlice - 10) * velikostBloku / 2
            )
        }
    }

}*/
