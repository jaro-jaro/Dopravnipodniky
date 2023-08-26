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
        barva = red_500,
        jmeno = R.string.cervena,
        jeSvetla = false
    ),
    Zelena(
        barva = green_500,
        jmeno = R.string.zelena,
        jeSvetla = true
    ),
    Modra(
        barva = blue_500,
        jmeno = R.string.modra,
        jeSvetla = false
    ),
    Zluta(
        barva = yellow_500,
        jmeno = R.string.zluta,
        jeSvetla = true
    ),
    Tyrkysova(
        barva = cyan_500,
        jmeno = R.string.tyrkysova,
        jeSvetla = true
    ),
    Fialova(
        barva = purple_500,
        jmeno = R.string.fialova,
        jeSvetla = true
    ),
    tmave_cervena(
        barva = red_900,
        jmeno = R.string.tmave_cervena,
        jeSvetla = false
    ),
    tmave_zelena(
        barva = green_900,
        jmeno = R.string.tmave_zelena,
        jeSvetla = false
    ),
    tmave_modra(
        barva = blue_900,
        jmeno = R.string.tmave_modra,
        jeSvetla = false
    ),
    tmave_zluta(
        barva = yellow_900,
        jmeno = R.string.tmave_zluta,
        jeSvetla = false
    ),
    tmave_tyrkysova(
        barva = cyan_900,
        jmeno = R.string.tmave_tyrkysova,
        jeSvetla = false
    ),
    tmave_fialova(
        barva = purple_900,
        jmeno = R.string.tmave_fialova,
        jeSvetla = false
    ),
    svetle_cervena(
        barva = red_300,
        jmeno = R.string.svetle_cervena,
        jeSvetla = true
    ),
    svetle_zelena(
        barva = lightGreen_500,
        jmeno = R.string.svetle_zelena,
        jeSvetla = false
    ),
    svetle_modra(
        barva = lightBlue_500,
        jmeno = R.string.svetle_modra,
        jeSvetla = true
    ),
    svetle_zluta(
        barva = yellow_300,
        jmeno = R.string.svetle_zluta,
        jeSvetla = true
    ),
    svetle_tyrkysova(
        barva = cyan_300,
        jmeno = R.string.svetle_tyrkysova,
        jeSvetla = true
    ),
    svetle_fialova(
        barva = purple_300,
        jmeno = R.string.svetle_fialova,
        jeSvetla = true
    ),
    Ruzova(
        barva = pink_500,
        jmeno = R.string.ruzova,
        jeSvetla = false
    ),
    Oranzova(
        barva = orange_500,
        jmeno = R.string.oranzova,
        jeSvetla = false
    ),
    Hneda(
        barva = brown_500,
        jmeno = R.string.hneda,
        jeSvetla = false
    ),
}
