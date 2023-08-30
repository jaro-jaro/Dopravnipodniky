package cz.jaro.dopravnipodniky.data.dopravnipodnik

import kotlin.random.Random

enum class TypBaraku {
    Normalni,
    Panelak,
    Stredovy,;
}

fun TypBaraku.getBarva() = when (this) {
    TypBaraku.Normalni -> Random.nextInt(-1, 1)
    TypBaraku.Panelak -> 2
    TypBaraku.Stredovy -> Random.nextInt(-1, 1)
}

fun TypBaraku.getKapacita() = when (this) {
    TypBaraku.Normalni -> Random.nextInt(5, 15)
    TypBaraku.Panelak -> Random.nextInt(50, 200)
    TypBaraku.Stredovy -> Random.nextInt(200, 500)
}