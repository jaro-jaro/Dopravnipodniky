package cz.jaro.dopravnipodniky.ui.theme

import androidx.annotation.StringRes
import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import cz.jaro.dopravnipodniky.R

enum class Theme(
    val darkColorScheme: ColorScheme,
    @StringRes
    val label: Int,
    val mainColor: Color,
) {
    Red(
        darkColorScheme = redColors,
        label = R.string.cervene,
        mainColor = redA700,
    ),
    Pink(
        darkColorScheme = pinkColors,
        label = R.string.ruzove,
        mainColor = pinkA700,
    ),
    Purple(
        darkColorScheme = purpleColors,
        label = R.string.fialove,
        mainColor = purpleA700,
    ),
    DeepPurple(
        darkColorScheme = deepPurpleColors,
        label = R.string.syte_fialove,
        mainColor = deepPurpleA700,
    ),
    Indigo(
        darkColorScheme = indigoColors,
        label = R.string.indigove,
        mainColor = indigoA700,
    ),
    LightBlue(
        darkColorScheme = lightBlueColors,
        label = R.string.svetle_modre,
        mainColor = lightBlueA700,
    ),
    Cyan(
        darkColorScheme = cyanColors,
        label = R.string.tyrkysove,
        mainColor = cyanA700,
    ),
    Green(
        darkColorScheme = greenColors,
        label = R.string.zelene,
        mainColor = greenA700,
    ),
    LightGreen(
        darkColorScheme = lightGreenColors,
        label = R.string.svetle_zelene,
        mainColor = lightGreenA700,
    ),
    Lime(
        darkColorScheme = limeColors,
        label = R.string.limetkove,
        mainColor = limeA700,
    ),
    Yellow(
        darkColorScheme = yellowColors,
        label = R.string.zlute,
        mainColor = yellowA700,
    ),
    Amber(
        darkColorScheme = amberColors,
        label = R.string.jantarove,
        mainColor = amberA700,
    ),
    Orange(
        darkColorScheme = orangeColors,
        label = R.string.oranzove,
        mainColor = orangeA700,
    ),
}