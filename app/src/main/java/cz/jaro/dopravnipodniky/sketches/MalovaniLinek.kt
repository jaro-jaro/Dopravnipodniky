package cz.jaro.dopravnipodniky.sketches

import android.graphics.Color
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.core.text.isDigitsOnly
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import cz.jaro.dopravnipodniky.*
import cz.jaro.dopravnipodniky.classes.Linka
import cz.jaro.dopravnipodniky.databinding.LinkaDialogBinding
import cz.jaro.dopravnipodniky.other.Dosahlosti.dosahni
import cz.jaro.dopravnipodniky.other.Orientace.SVISLE
import cz.jaro.dopravnipodniky.other.Orientace.VODOROVNE
import cz.jaro.dopravnipodniky.other.PrefsHelper.dp
import cz.jaro.dopravnipodniky.other.PrefsHelper.vse
import processing.core.PApplet
import processing.core.PConstants.CENTER
import kotlin.math.sqrt

val kliklyKrizovatky = mutableListOf<Pair<Int, Int>>() // (2 to 1) -> v Ulicovych blokach

fun Sketch.pripravitNaVybiraniLinky() {

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

}

fun Sketch.namalovatVybiraniLinky() {

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

}

fun Sketch.poKliknutiPriVybiraniLinky() {

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
}

fun Sketch.namalovatLinky() {
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
}
