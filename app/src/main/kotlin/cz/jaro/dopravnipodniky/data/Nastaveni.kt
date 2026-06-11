package cz.jaro.dopravnipodniky.data

import cz.jaro.dopravnipodniky.data.dopravnipodnik.BusRunningCosts
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Trakce
import cz.jaro.dopravnipodniky.data.shop_settings.ShopFilter
import cz.jaro.dopravnipodniky.data.shop_settings.ShopSettings
import cz.jaro.dopravnipodniky.data.shop_settings.ShopSortOption
import cz.jaro.dopravnipodniky.data.shop_settings.ShopSortSetting
import cz.jaro.dopravnipodniky.data.shop_settings.SortDirection
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Nastaveni")
data class Nastaveni(
    val automatickyUdelovatEvC: Boolean = true,
    val vicenasobnyKupovani: Boolean = false,
    val shopSettings: ShopSettings = ShopSettings(
        sort = ShopSortSetting.Sort(
            sortBy = ShopSortOption.ByCosts,
            sortDirection = SortDirection.Ascending,
        ),
        filters = listOf(
            ShopFilter.ByTraction(traction = Trakce.Autobus.Dieslovy),
            ShopFilter.ByTraction(traction = Trakce.Autobus.Zemeplynovy),
            ShopFilter.ByTraction(traction = Trakce.Autobus.Hybridni),
            ShopFilter.ByTraction(traction = Trakce.Autobus.Vodikovy),
            ShopFilter.ByTraction(traction = Trakce.Elektrobus),
            ShopFilter.ByRunningCosts(runningCosts = BusRunningCosts.ExtremelyLow),
            ShopFilter.ByRunningCosts(runningCosts = BusRunningCosts.VeryLow),
            ShopFilter.ByRunningCosts(runningCosts = BusRunningCosts.Low),
            ShopFilter.ByRunningCosts(runningCosts = BusRunningCosts.QuiteLow),
            ShopFilter.ByRunningCosts(runningCosts = BusRunningCosts.Lowered),
            ShopFilter.ByRunningCosts(runningCosts = BusRunningCosts.Normal),
            ShopFilter.HaveEnoughMoney,
        ),
    ),
)
