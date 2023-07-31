package cz.jaro.dopravnipodniky.sketches

import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import cz.jaro.dopravnipodniky.BuildConfig
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.cenaTroleje
import cz.jaro.dopravnipodniky.cenaZastavky
import cz.jaro.dopravnipodniky.classes.Zastavka
import cz.jaro.dopravnipodniky.formatovat
import cz.jaro.dopravnipodniky.other.PrefsHelper.dp
import cz.jaro.dopravnipodniky.other.PrefsHelper.vse
import cz.jaro.dopravnipodniky.sedZastavky
import cz.jaro.dopravnipodniky.velikostZastavky
import processing.core.PApplet
import kotlin.math.roundToInt

fun Sketch.moznaChcesUdelatZastavku() {

    for (ulice in dp.ulicove) {

        val zacatekXPx = ulice.zacatekXBlokuu * velikostBloku + tx // v px
        val zacatekYPx = ulice.zacatekYBlokuu * velikostBloku + ty // v px
        val konecXPx   = ulice.konecXBlokuu   * velikostBloku + tx // v px
        val konecYPx   = ulice.konecYBlokuu   * velikostBloku + ty // v px

        if (jeVObdelniku(
            mouseX,
            mouseY,
            zacatekXPx,
            zacatekYPx,
            konecXPx  ,
            konecYPx  ,
        )) {
            runOnUiThread {

                MaterialAlertDialogBuilder(context).apply {
                    setTitle(R.string.upravit_ulici)

                    setIcon(R.drawable.ic_baseline_edit_road_24)
                    //setTheme(R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered)

                    val arr = mutableListOf<String>()

                    arr += when (ulice.zastavka) {
                        null -> context.getString(R.string.vytvorit_zastavku, cenaZastavky.formatovat())

                        else -> context.getString(R.string.odstranit_zastavku, (cenaZastavky * .2).roundToInt().formatovat())
                    }

                    if (vse.tutorial != 4 && vse.tutorial != 5) {
                        arr += if (!ulice.trolej) {
                            context.getString(R.string.postavit_troleje, cenaTroleje.formatovat())
                        } else {
                            context.getString(R.string.odstranit_troleje, (cenaTroleje * .2).roundToInt().formatovat())
                        }
                    }

                    setItems(arr.toTypedArray()) { dialog, pos ->

                        when {
                            pos == 0 && ulice.zastavka != null -> {
                                if (vse.prachy < cenaZastavky * .2) {
                                    Toast.makeText(context, R.string.malo_penez, Toast.LENGTH_LONG).show()
                                    return@setItems
                                }

                                val z = ulice.zastavka!!
                                ulice.zastavka = null

                                vse.prachy -= cenaZastavky * .2

                                z.zesebevrazdujSe(context)
                                dp.zastavky.remove(z)
                            }
                            pos == 0 && ulice.zastavka == null -> {
                                if (vse.prachy < cenaZastavky) {
                                    Toast.makeText(context, R.string.malo_penez, Toast.LENGTH_LONG).show()
                                    return@setItems
                                }

                                val z = Zastavka(ulice.id, context)

                                ulice.zastavka = z

                                vse.prachy -= cenaZastavky

                                dp.zastavky.add(z)

                            }
                            pos == 1 && ulice.trolej -> {
                                if (vse.prachy < cenaTroleje * .2) {
                                    Toast.makeText(context, R.string.malo_penez, Toast.LENGTH_LONG).show()
                                    return@setItems
                                }

                                ulice.trolej = false

                                vse.prachy -= .2 * cenaTroleje

                            }
                            pos == 1 && !ulice.trolej -> {
                                if (vse.prachy < cenaTroleje) {
                                    Toast.makeText(context, R.string.malo_penez, Toast.LENGTH_LONG).show()
                                    return@setItems
                                }

                                ulice.trolej = true

                                vse.prachy -= cenaTroleje
                            }
                        }

                        dialog.cancel()
                    }

                    setNegativeButton(R.string.zrusit) { dialog, _ -> dialog.cancel() }

                    if (BuildConfig.DEBUG) {
                        setPositiveButton("Info") {dialog, _ ->
                            dialog.cancel()

                            MaterialAlertDialogBuilder(context).apply {
                                setTitle("Info o ulici ${ulice.id.formatovat()}\n")

                                setMessage(
                                    "Počet domů: ${ulice.baraky.size}\n" +
                                    "Potenciál: ${ulice.potencial}\n" +
                                    "Počet lidí doma: ${ulice.cloveci}\n" +
                                    "Počet lidí na zastávce: ${ulice.zastavka?.cloveci ?: -1}\n" +
                                    "Kapacita baráků: ${ulice.kapacita}\n" +
                                    "Kapacita zastávky: ${ulice.zastavka?.kapacita ?: -1}"
                                )

                                show()
                            }
                        }
                    }

                    show()
                }

                ulozit()
            }
        }
    }
}

fun Zastavka.draw(sk: Sketch) {
    sk.apply {
        val ulice = dp.ulice(ulice)

        fill(sedZastavky)
        rectMode(PApplet.CENTER)

        val zacatekXPx = ulice.zacatekXBlokuu * velikostBloku
        val zacatekYPx = ulice.zacatekYBlokuu * velikostBloku
        val konecXPx = ulice.konecXBlokuu * velikostBloku
        val konecYPx = ulice.konecYBlokuu * velikostBloku

        rect(
            (zacatekXPx + konecXPx) / 2,
            (zacatekYPx + konecYPx) / 2,
            velikostZastavky * velikostBloku,
            velikostZastavky * velikostBloku,
            6F * velikostBloku
        )
    }
}
