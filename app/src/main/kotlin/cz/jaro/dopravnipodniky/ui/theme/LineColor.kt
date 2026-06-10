package cz.jaro.dopravnipodniky.ui.theme

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import cz.jaro.dopravnipodniky.R

enum class LineColor(
    val color: Color,
    @StringRes
    val label: Int,
) {
    Black(
        color = black,
        label = R.string.cerna
    ),
    Red(
        color = red500,
        label = R.string.cervena
    ),
    Green(
        color = green500,
        label = R.string.zelena
    ),
    Blue(
        color = blue500,
        label = R.string.modra
    ),
    Yellow(
        color = yellow500,
        label = R.string.zluta
    ),
    Cyan(
        color = cyan500,
        label = R.string.tyrkysova
    ),
    Purple(
        color = purple500,
        label = R.string.fialova
    ),
    DarkRed(
        color = red900,
        label = R.string.tmave_cervena
    ),
    DarkGreen(
        color = green900,
        label = R.string.tmave_zelena
    ),
    DarkBlue(
        color = blue900,
        label = R.string.tmave_modra
    ),
    DarkYellow(
        color = yellow900,
        label = R.string.tmave_zluta
    ),
    DarkCyan(
        color = cyan900,
        label = R.string.tmave_tyrkysova
    ),
    DarkPurple(
        color = purple900,
        label = R.string.tmave_fialova
    ),
    LightRed(
        color = red300,
        label = R.string.svetle_cervena
    ),
    LightGreen(
        color = lightGreen500,
        label = R.string.svetle_zelena
    ),
    LightBlue(
        color = lightBlue500,
        label = R.string.svetle_modra
    ),
    LightYellow(
        color = yellow300,
        label = R.string.svetle_zluta
    ),
    LightCyan(
        color = cyan300,
        label = R.string.svetle_tyrkysova
    ),
    LightPurple(
        color = purple300,
        label = R.string.svetle_fialova
    ),
    Pink(
        color = pink500,
        label = R.string.ruzova
    ),
    Orange(
        color = orange500,
        label = R.string.oranzova
    ),
    Brown(
        color = brown500,
        label = R.string.hneda
    ),
}
