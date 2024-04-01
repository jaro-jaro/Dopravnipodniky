package cz.jaro.dopravnipodniky.shared

import kotlin.time.Duration

// rychlost (dp/ms) = 50_000 (dp/h) / (60*60*1000)
// Ujetá vzdálenost busu / tik = rychlost * (tps / 1000) * zrychleni
sealed class StavHry(
    val tps: Int,
    val zrychleni: Float,
) {
    data object Hra : StavHry(
        tps = 60,
        zrychleni = 1F,
    )

//    data class Dohaneni(
//        val zbyva: Duration
//    ) : StavHry(
//        tps = 90,
//        zrychleni = 0.01F,
//    )

    sealed interface Simulace {
        val zbyva: Duration
    }

    data class RychlaSimulace(
        override val zbyva: Duration
    ) : StavHry(
        tps = 1000,
        zrychleni = 2500F,
    ), Simulace

    data class PomalaSimulace(
        override val zbyva: Duration
    ) : StavHry(
        tps = 200,
        zrychleni = 50F,
    ), Simulace
}

fun StavHry.RychlaSimulace.zpomalit() = StavHry.PomalaSimulace(zbyva = zbyva)
