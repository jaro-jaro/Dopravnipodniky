package cz.jaro.dopravnipodniky.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.runtime.serialization.NavBackStackSerializer
import androidx.navigation3.ui.NavDisplay
import cz.jaro.dopravnipodniky.ui.dopravnipodniky.DopravniPodnikyScreen
import cz.jaro.dopravnipodniky.ui.dopravnipodniky.novypodnik.NovyDopravniPodnikScreen
import cz.jaro.dopravnipodniky.ui.fleet.GarazScreen
import cz.jaro.dopravnipodniky.ui.fleet.shop.ShopScreen
import cz.jaro.dopravnipodniky.ui.linky.LinkyScreen
import cz.jaro.dopravnipodniky.ui.linky.vybirani.VytvareniLinkyScreen
import cz.jaro.dopravnipodniky.ui.main.MainScreen

@Composable
fun rememberNavBackStack(vararg elements: Route) =
    rememberSerializable(serializer = NavBackStackSerializer(elementSerializer = Route.serializer())) {
        NavBackStack(*elements)
    }

@Composable
fun NavDisplay(
    modifier: Modifier = Modifier,
) {
    val backStack = rememberNavBackStack(Route.Map)
    val navigator = rememberNavigator(backStack)

    NavDisplay(
        backStack = backStack,
        modifier,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberSharedViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            val metadata = SharedViewModelStoreNavEntryDecorator.parent("shared")
            entry<Route.Map>(metadata = metadata) { MainScreen(navigator) }
            entry<Route.Lines>(metadata = metadata) { LinkyScreen(navigator) }
            entry<Route.NewLine>(metadata = metadata) { VytvareniLinkyScreen(it.edit, navigator) }
            entry<Route.Fleet>(metadata = metadata) { GarazScreen(navigator) }
            entry<Route.Shop>(metadata = metadata) { ShopScreen(navigator) }
            entry<Route.TransportCompanies>(metadata = metadata) { DopravniPodnikyScreen(navigator) }
            entry<Route.NewTransportCompany>(metadata = metadata) { NovyDopravniPodnikScreen(navigator) }
        },
    )
}