package cz.jaro.dopravnipodniky

import androidx.compose.ui.graphics.Color

enum class Barvicka(
    val barva: Color,
    val jmeno: Int,
) {
    Cervena(
        barva = Color(0xFFF44336),
        jmeno = R.string.cervena
    )
}
