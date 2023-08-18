package cz.jaro.dopravnipodniky.other

import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.bonusoveVydajeZaNeekologickeBusy
import cz.jaro.dopravnipodniky.bonusoveVydajeZaPoloekologickeBusy
import cz.jaro.dopravnipodniky.jednotky.penezZaMin

sealed interface Trakce {
    val jmeno: Int

    sealed class Autobus(
        override val jmeno: Int,
    ) : Trakce {
        data object Obycejny : Autobus(
            jmeno = R.string.autobus
        )
        data object Dieslovy : Autobus(
            jmeno = R.string.dieselovy_autobus
        )
        data object Zemeplynovy : Autobus(
            jmeno = R.string.zemeplynovy_autobus
        )
        data object Hybridni : Autobus(
            jmeno = R.string.hybridni_autobus
        )
        data object Vodikovy : Autobus(
            jmeno = R.string.vodikovy_autobus
        )
    }

    sealed class Trolejbus(
        override val jmeno: Int,
    ) : Trakce {
        data object Obycejny : Trolejbus(
            jmeno = R.string.trolejbus
        )
        data object Parcialni : Trolejbus(
            jmeno = R.string.parcialni_trolejbus
        )
    }

    data object Elektrobus : Trakce {
        override val jmeno = R.string.elektrobus
    }

    data object SuSbus : Trakce {
        override val jmeno = R.string.susbus
    }
}

fun Trakce.bonusoveVydajeZaNeekologicnost() = when (this) {
    Trakce.Autobus.Vodikovy -> 0.penezZaMin
    Trakce.Autobus.Hybridni -> bonusoveVydajeZaPoloekologickeBusy
    Trakce.Autobus.Zemeplynovy -> bonusoveVydajeZaPoloekologickeBusy
    else -> bonusoveVydajeZaNeekologickeBusy
}