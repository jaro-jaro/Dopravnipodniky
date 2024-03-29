package cz.jaro.dopravnipodniky.data.dopravnipodnik

enum class TypBaraku {
    Normalni,
    Panelak,
    Stredovy,;
}

val TypBaraku.barva
    get() = when (this) {
        TypBaraku.Normalni -> -1..0
        TypBaraku.Panelak -> 1..3
        TypBaraku.Stredovy -> 3..4
    }

val TypBaraku.kapacita
    get() = when (this) {
        TypBaraku.Normalni -> 5..15
        TypBaraku.Panelak -> 50..200
        TypBaraku.Stredovy -> 200..500
    }