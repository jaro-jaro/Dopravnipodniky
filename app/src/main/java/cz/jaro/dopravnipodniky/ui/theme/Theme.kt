package cz.jaro.dopravnipodniky.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import cz.jaro.dopravnipodniky.R

enum class Theme(
    val darkColorScheme: ColorScheme,
    val jmeno: Int,
    val barva: Color,
) {
//    Cerne(
//        darkColorScheme = colorsCerne,
//        jmeno = R.string.cerne,
//        barva = t0,
//    ),
    Cervene(
        darkColorScheme = colorsCervene,
        jmeno = R.string.cervene,
        barva = t1,
    ),
//    Ruzove(
//        darkColorScheme = colorsRuzove,
//        jmeno = R.string.ruzove,
//        barva = t2,
//    ),
    Fialove(
        darkColorScheme = colorsFialove,
        jmeno = R.string.fialove,
        barva = t3,
    ),
    SyteFialove(
        darkColorScheme = colorsSyteFialove,
        jmeno = R.string.syte_fialove,
        barva = t4,
    ),
    Indigove(
        darkColorScheme = colorsIndigove,
        jmeno = R.string.indigove,
        barva = t5,
    ),
//    Modre(
//        darkColorScheme = colorsModre,
//        jmeno = R.string.modre,
//        barva = t6,
//    ),
    SvetleModre(
        darkColorScheme = colorsSvetleModre,
        jmeno = R.string.svetle_modre,
        barva = t7,
    ),
    Tyrkysove(
        darkColorScheme = colorsTyrkysove,
        jmeno = R.string.tyrkysove,
        barva = t8,
    ),
//    Modrozelene(
//        darkColorScheme = colorsModrozelene,
//        jmeno = R.string.modrozelene,
//        barva = t9,
//    ),
    Zelene(
        darkColorScheme = colorsZelene,
        jmeno = R.string.zelene,
        barva = t10,
    ),
    SvetleZelene(
        darkColorScheme = colorsSvetleZelene,
        jmeno = R.string.svetle_zelene,
        barva = t11,
    ),
    Limetkove(
        darkColorScheme = colorsLimetkove,
        jmeno = R.string.limetkove,
        barva = t12,
    ),
    Zlute(
        darkColorScheme = colorsZlute,
        jmeno = R.string.zlute,
        barva = t13,
    ),
    Jantarove(
        darkColorScheme = colorsJantarove,
        jmeno = R.string.jantarove,
        barva = t14,
    ),
    Oranzove(
        darkColorScheme = colorsOranzove,
        jmeno = R.string.oranzove,
        barva = t15,
    ),
//    SyteOranzove(
//        darkColorScheme = colorsSyteOranzove,
//        jmeno = R.string.syte_cervene,
//        barva = t16,
//    ),
}