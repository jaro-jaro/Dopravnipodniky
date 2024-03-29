package cz.jaro.dopravnipodniky.shared

enum class Orientace {
    Svisle, Vodorovne,
}

enum class Smer {
    Pozitivni, Negativni,
}

operator fun Smer.times(other: Smer) = if (other == this) Smer.Pozitivni else Smer.Negativni
