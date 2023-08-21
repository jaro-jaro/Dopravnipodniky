package cz.jaro.dopravnipodniky.dopravnipodnik

import kotlin.random.Random

enum class TypBaraku {
    Normalni,
    Panelak,
    Stredovy,;
}

fun TypBaraku.getKapacita() = when (this) {
    TypBaraku.Normalni -> Random.nextInt(5, 15)
    TypBaraku.Panelak -> Random.nextInt(50, 200)
    TypBaraku.Stredovy -> Random.nextInt(200, 500)
}