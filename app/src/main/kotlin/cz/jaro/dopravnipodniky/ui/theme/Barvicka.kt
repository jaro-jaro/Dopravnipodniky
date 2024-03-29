package cz.jaro.dopravnipodniky.ui.theme

import androidx.compose.ui.graphics.Color
import cz.jaro.dopravnipodniky.R

enum class Barvicka(
    val barva: Color,
    val jmeno: Int,
    val jeSvetla: Boolean,
) {
    Cerna(
        barva = black,
        jmeno = R.string.cerna,
        jeSvetla = false
    ),
    Cervena(
        barva = red500,
        jmeno = R.string.cervena,
        jeSvetla = false
    ),
    Zelena(
        barva = green500,
        jmeno = R.string.zelena,
        jeSvetla = true
    ),
    Modra(
        barva = blue500,
        jmeno = R.string.modra,
        jeSvetla = false
    ),
    Zluta(
        barva = yellow500,
        jmeno = R.string.zluta,
        jeSvetla = true
    ),
    Tyrkysova(
        barva = cyan500,
        jmeno = R.string.tyrkysova,
        jeSvetla = true
    ),
    Fialova(
        barva = purple500,
        jmeno = R.string.fialova,
        jeSvetla = true
    ),
    TmaveCervena(
        barva = red900,
        jmeno = R.string.tmave_cervena,
        jeSvetla = false
    ),
    TmaveZelena(
        barva = green900,
        jmeno = R.string.tmave_zelena,
        jeSvetla = false
    ),
    TmaveModra(
        barva = blue900,
        jmeno = R.string.tmave_modra,
        jeSvetla = false
    ),
    TmaveZluta(
        barva = yellow900,
        jmeno = R.string.tmave_zluta,
        jeSvetla = false
    ),
    TmaveTyrkysova(
        barva = cyan900,
        jmeno = R.string.tmave_tyrkysova,
        jeSvetla = false
    ),
    TmaveFialova(
        barva = purple900,
        jmeno = R.string.tmave_fialova,
        jeSvetla = false
    ),
    SvetleCervena(
        barva = red300,
        jmeno = R.string.svetle_cervena,
        jeSvetla = true
    ),
    SvetleZelena(
        barva = lightGreen500,
        jmeno = R.string.svetle_zelena,
        jeSvetla = false
    ),
    SvetleModra(
        barva = lightBlue500,
        jmeno = R.string.svetle_modra,
        jeSvetla = true
    ),
    SvetleZluta(
        barva = yellow300,
        jmeno = R.string.svetle_zluta,
        jeSvetla = true
    ),
    SvetleTyrkysova(
        barva = cyan300,
        jmeno = R.string.svetle_tyrkysova,
        jeSvetla = true
    ),
    SvetleFialova(
        barva = purple300,
        jmeno = R.string.svetle_fialova,
        jeSvetla = true
    ),
    Ruzova(
        barva = pink500,
        jmeno = R.string.ruzova,
        jeSvetla = false
    ),
    Oranzova(
        barva = orange500,
        jmeno = R.string.oranzova,
        jeSvetla = false
    ),
    Hneda(
        barva = brown500,
        jmeno = R.string.hneda,
        jeSvetla = false
    ),
}
