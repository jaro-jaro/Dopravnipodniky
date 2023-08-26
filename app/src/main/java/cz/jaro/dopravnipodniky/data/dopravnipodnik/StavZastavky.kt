package cz.jaro.dopravnipodniky.data.dopravnipodnik

import cz.jaro.dopravnipodniky.shared.jednotky.Tik
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("StavZastavky")
sealed class StavZastavky {
    @Serializable
    @SerialName("StavZastavky.Pred")
    data object Pred : StavZastavky()
    @Serializable
    @SerialName("StavZastavky.Na")
    data class Na(
        val doba: Tik,
    ) : StavZastavky()
    @Serializable
    @SerialName("StavZastavky.Po")
    data object Po : StavZastavky()
}
