package cz.jaro.dopravnipodniky.ui.fleet.shop

import cz.jaro.dopravnipodniky.data.dopravnipodnik.TypBusu
import cz.jaro.dopravnipodniky.data.shop_settings.ShopFilter
import cz.jaro.dopravnipodniky.data.shop_settings.ShopSortSetting

sealed interface ShopEvent {
    data object BackPressed : ShopEvent
    data object ShowTutorial : ShopEvent
    data object FilterButtonClicked : ShopEvent
    data object SortButtonClicked : ShopEvent
    data class ToggleFilter(val filter: ShopFilter) : ShopEvent
    data class SetSort(val setting: ShopSortSetting) : ShopEvent

    data class BuyBus(
        val busType: TypBusu,
        val callbacks: ShopViewModel.AskMoreCallbacks,
        val onComplete: (reason: ShopViewModel.BuyResult) -> Unit
    ) : ShopEvent
}