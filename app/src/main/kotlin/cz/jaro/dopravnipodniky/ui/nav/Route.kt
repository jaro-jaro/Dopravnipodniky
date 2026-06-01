package cz.jaro.dopravnipodniky.ui.nav

import androidx.navigation3.runtime.NavKey
import cz.jaro.dopravnipodniky.shared.LinkaID
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {
    @Serializable
    data object Map : Route
    @Serializable
    data object Lines : Route
    @Serializable
    data class NewLine(val edit: LinkaID? = null) : Route
    @Serializable
    data object Fleet : Route
    @Serializable
    data object Shop : Route
    @Serializable
    data object TransportCompanies : Route
    @Serializable
    data object NewTransportCompany : Route
}