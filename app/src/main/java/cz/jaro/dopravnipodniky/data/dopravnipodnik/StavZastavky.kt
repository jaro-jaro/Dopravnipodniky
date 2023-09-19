package cz.jaro.dopravnipodniky.data.dopravnipodnik

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
@SerialName("StavZastavky")
sealed class StavZastavky {
    @Serializable
    @SerialName("StavZastavky.Pred")
    data object Pred : StavZastavky()
    @Serializable
    @SerialName("StavZastavky.Na")
    data class Na(
        val doba: Duration,
    ) : StavZastavky()
    @Serializable
    @SerialName("StavZastavky.Po")
    data object Po : StavZastavky()
}
