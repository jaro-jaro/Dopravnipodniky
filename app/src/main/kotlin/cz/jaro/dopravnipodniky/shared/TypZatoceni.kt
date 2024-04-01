package cz.jaro.dopravnipodniky.shared

sealed interface TypZatoceni {
    data object Rovne : TypZatoceni
    data object Vpravo : TypZatoceni
    data object Vlevo : TypZatoceni
    data object Spatne : TypZatoceni
    sealed class Kruhac(val ctvrtin: Int) : TypZatoceni
    data object KruhacVpravo : Kruhac(ctvrtin = 0)
    data object KruhacRovne : Kruhac(ctvrtin = 1)
    data object KruhacVlevo : Kruhac(ctvrtin = 2)
    data object KruhacOtocka : Kruhac(ctvrtin = 3)
}