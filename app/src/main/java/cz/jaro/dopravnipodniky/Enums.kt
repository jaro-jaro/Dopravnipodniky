package cz.jaro.dopravnipodniky

enum class Razeni {
    VZESTUPNE, SESTUPNE, NIJAK,
}

enum class Orientace {
    SVISLE, VODOROVNE,
}

enum class Smer {
    POZITIVNE, NEGATIVNE,
}

operator fun Smer.times(other: Smer) = if (other == this) Smer.POZITIVNE else Smer.NEGATIVNE
