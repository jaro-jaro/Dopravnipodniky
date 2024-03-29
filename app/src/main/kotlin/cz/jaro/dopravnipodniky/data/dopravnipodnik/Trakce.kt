package cz.jaro.dopravnipodniky.data.dopravnipodnik

import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.shared.bonusoveVydajeZaNeekologickeBusy
import cz.jaro.dopravnipodniky.shared.bonusoveVydajeZaPoloekologickeBusy
import cz.jaro.dopravnipodniky.shared.jednotky.penezZaMin
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Trakce")
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
    @SerialName("Autobus")
    sealed class Autobus(
        override val jmeno: Int,
    ) : Trakce {
        @Serializable
        @SerialName("Dieslovy")
        data object Dieslovy : Autobus(
            jmeno = R.string.dieselovy_autobus
        )
        @Serializable
        @SerialName("Zemeplynovy")
        data object Zemeplynovy : Autobus(
            jmeno = R.string.zemeplynovy_autobus
        )
        @Serializable
        @SerialName("Hybridni")
        data object Hybridni : Autobus(
            jmeno = R.string.hybridni_autobus
        )
        @Serializable
        @SerialName("Vodikovy")
        data object Vodikovy : Autobus(
            jmeno = R.string.vodikovy_autobus
        )
    }

    @Serializable
    @SerialName("Trolejbus")
    sealed class Trolejbus(
        override val jmeno: Int,
    ) : Trakce {
        @Serializable
        @SerialName("Obycejny")
        data object Obycejny : Trolejbus(
            jmeno = R.string.trolejbus
        )
        @Serializable
        @SerialName("Parcialni")
        data object Parcialni : Trolejbus(
            jmeno = R.string.parcialni_trolejbus
        )
    }

    @Serializable
    @SerialName("Elektrobus")
    data object Elektrobus : Trakce {
        override val jmeno = R.string.elektrobus
    }

    @Serializable
    @SerialName("SUSbus")
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
        Trakce.Trolejbus.Obycejny -> R.drawable.trolejbus
        Trakce.Trolejbus.Parcialni -> R.drawable.parcial
        Trakce.Elektrobus -> R.drawable.ebus
        Trakce.SUSbus -> R.drawable.blbobus_69tr_urcite_to_neni_rickroll
    }

fun Trakce.bonusoveVydajeZaNeekologicnost() = when (this) {
    is Trakce.Trolejbus -> 0.penezZaMin
    is Trakce.Elektrobus -> 0.penezZaMin
    Trakce.Autobus.Vodikovy -> 0.penezZaMin
    Trakce.Autobus.Hybridni -> bonusoveVydajeZaPoloekologickeBusy
    Trakce.Autobus.Zemeplynovy -> bonusoveVydajeZaPoloekologickeBusy
    Trakce.SUSbus -> (-1).penezZaMin
    else -> bonusoveVydajeZaNeekologickeBusy
}