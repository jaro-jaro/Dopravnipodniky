package cz.jaro.dopravnipodniky.data.shop_settings

import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.dopravnipodnik.BusRunningCosts
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Trakce
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Vyrobce
import cz.jaro.dopravnipodniky.shared.Text
import cz.jaro.dopravnipodniky.shared.toText
import kotlinx.serialization.Serializable

@Serializable
enum class FilterGroup {
    TractionGroup,
    LengthGroup,
    ManufacturerGroup,
    RunningCostsGroup,
    PurchasePriceGroup,
    HaveEnoughMoneyGroup,
}

@Serializable
sealed class FilterDisplayOptionGroup(
    val name: Text,
    val filters: List<ShopFilter>,
) {
    companion object {
        val groups = listOf(
            TractionGroup,
            LengthGroup,
            ManufacturerGroup,
            RunningCostsGroup,
            PurchasePriceGroup,
        )
    }

    @Serializable
    data object TractionGroup : FilterDisplayOptionGroup(
        name = R.string.dle_trakce.toText(),
        filters = Trakce.Companion.vse.map {
            ShopFilter.ByTraction(traction = it)
        },
    )

    @Serializable
    data object LengthGroup : FilterDisplayOptionGroup(
        name = R.string.dle_delky.toText(),
        filters = listOf(
            ShopFilter.ByLength.LengthLessThan12m,
            ShopFilter.ByLength.Length12m,
            ShopFilter.ByLength.Length15m,
            ShopFilter.ByLength.Length18m,
            ShopFilter.ByLength.LengthMoreThan18m,
        ),
    )

    @Serializable
    data object ManufacturerGroup : FilterDisplayOptionGroup(
        name = R.string.dle_vyrobce.toText(),
        filters = Vyrobce.entries.map {
            ShopFilter.ByManufacturer(manufacturer = it)
        },
    )

    @Serializable
    data object RunningCostsGroup : FilterDisplayOptionGroup(
        name = R.string.dle_nakladu.toText(),
        filters = BusRunningCosts.entries.dropLast(1).map {
            ShopFilter.ByRunningCosts(runningCosts = it)
        },
    )

    @Serializable
    data object PurchasePriceGroup : FilterDisplayOptionGroup(
        name = R.string.dle_ceny.toText(),
        filters = listOf(
            ShopFilter.ByPurchasePrice.Low,
            ShopFilter.ByPurchasePrice.Medium,
            ShopFilter.ByPurchasePrice.High,
            ShopFilter.HaveEnoughMoney,
        )
    )
}