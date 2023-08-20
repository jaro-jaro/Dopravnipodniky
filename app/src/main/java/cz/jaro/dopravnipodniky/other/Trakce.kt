package cz.jaro.dopravnipodniky.other

import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.bonusoveVydajeZaNeekologickeBusy
import cz.jaro.dopravnipodniky.bonusoveVydajeZaPoloekologickeBusy
import cz.jaro.dopravnipodniky.jednotky.penezZaMin
import kotlinx.serialization.Serializable

@Serializable
sealed interface Trakce {
    val jmeno: Int

    companion object {
        val vse = listOf(
            Autobus.Dieslovy,
            Autobus.Zemeplynovy,
            Autobus.Hybridni,
            Autobus.Vodikovy,
            Trolejbus.Obycejny,
            Trolejbus.Parcialni,
            Elektrobus,
        )
    }

    @Serializable
    sealed class Autobus(
        override val jmeno: Int,
    ) : Trakce {
        @Serializable
        data object Dieslovy : Autobus(
            jmeno = R.string.dieselovy_autobus
        )
        @Serializable
        data object Zemeplynovy : Autobus(
            jmeno = R.string.zemeplynovy_autobus
        )
        @Serializable
        data object Hybridni : Autobus(
            jmeno = R.string.hybridni_autobus
        )
        @Serializable
        data object Vodikovy : Autobus(
            jmeno = R.string.vodikovy_autobus
        )
    }

    @Serializable
    sealed class Trolejbus(
        override val jmeno: Int,
    ) : Trakce {
        @Serializable
        data object Obycejny : Trolejbus(
            jmeno = R.string.trolejbus
        )
        @Serializable
        data object Parcialni : Trolejbus(
            jmeno = R.string.parcialni_trolejbus
        )
    }

    @Serializable
    data object Elektrobus : Trakce {
        override val jmeno = R.string.elektrobus
    }

    @Serializable
    data object SUSbus : Trakce {
        override val jmeno = R.string.susbus
    }
}

val Trakce.ikonka: Int
    get() = when(this) {
        Trakce.Autobus.Dieslovy -> R.drawable.diesel
        Trakce.Autobus.Zemeplynovy -> R.drawable.zemeplyn
        Trakce.Autobus.Hybridni -> R.drawable.hybrid
        Trakce.Autobus.Vodikovy -> R.drawable.vodik
        Trakce.Trolejbus.Obycejny -> R.drawable.trolej
        Trakce.Trolejbus.Parcialni -> R.drawable.parcial
        Trakce.Elektrobus -> R.drawable.elektro
        Trakce.SUSbus -> R.drawable.blbobus_69tr_urcite_to_neni_rickroll
    }

fun Trakce.bonusoveVydajeZaNeekologicnost() = when (this) {
    Trakce.Autobus.Vodikovy -> 0.penezZaMin
    Trakce.Autobus.Hybridni -> bonusoveVydajeZaPoloekologickeBusy
    Trakce.Autobus.Zemeplynovy -> bonusoveVydajeZaPoloekologickeBusy
    else -> bonusoveVydajeZaNeekologickeBusy
}