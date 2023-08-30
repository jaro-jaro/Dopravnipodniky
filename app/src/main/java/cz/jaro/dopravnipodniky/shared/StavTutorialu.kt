package cz.jaro.dopravnipodniky.shared

import cz.jaro.dopravnipodniky.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("StavTutorialu")
sealed interface StavTutorialu {
    @Serializable
    @SerialName("StavTutorialu.Nic")
    data object Nic : StavTutorialu

    @Serializable
    @SerialName("StavTutorialu.Odkliknuto")
    data class Odkliknuto(
        val co: StavTutorialu,
    ) : StavTutorialu

    @Serializable
    @SerialName("StavTutorialu.Tutorialujeme")
    sealed class Tutorialujeme(
        val text: Int,
    ) : StavTutorialu {
        @Serializable
        @SerialName("StavTutorialu.Tutorialujeme.Uvod")
        data object Uvod : Tutorialujeme(
            text = R.string.t1,
        )

        @Serializable
        @SerialName("StavTutorialu.Tutorialujeme.Linky")
        data object Linky : Tutorialujeme(
            text = R.string.t2,
        )

        @Serializable
        @SerialName("StavTutorialu.Tutorialujeme.Zastavky")
        data object Zastavky : Tutorialujeme(
            text = R.string.t3,
        )

        @Serializable
        @SerialName("StavTutorialu.Tutorialujeme.Garaz")
        data object Garaz : Tutorialujeme(
            text = R.string.t4,
        )

        @Serializable
        @SerialName("StavTutorialu.Tutorialujeme.Obchod")
        data object Obchod : Tutorialujeme(
            text = R.string.t5,
        )

        @Serializable
        @SerialName("StavTutorialu.Tutorialujeme.Vypraveni")
        data object Vypraveni : Tutorialujeme(
            text = R.string.t6,
        )

        @Serializable
        @SerialName("StavTutorialu.Tutorialujeme.NovejDp")
        data object NovejDp : Tutorialujeme(
            text = R.string.t7,
        )
    }
}

infix fun StavTutorialu.je(stav: StavTutorialu.Tutorialujeme) = this == stav || this is StavTutorialu.Odkliknuto && co == stav