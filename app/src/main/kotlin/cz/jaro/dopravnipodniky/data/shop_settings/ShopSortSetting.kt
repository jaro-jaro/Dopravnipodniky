package cz.jaro.dopravnipodniky.data.shop_settings

import cz.jaro.dopravnipodniky.data.dopravnipodnik.TypBusu
import kotlinx.serialization.Serializable

@Serializable
sealed interface ShopSortSetting {
    @Serializable
    data object Default : ShopSortSetting
    @Serializable
    data class Sort(
        val sortBy: ShopSortOption,
        val sortDirection: SortDirection,
    ) : ShopSortSetting

    val comparator: Comparator<TypBusu> get() = when (this) {
        is Default -> Comparator { _, _ -> 0 }
        is Sort -> when (sortDirection) {
            SortDirection.Ascending -> compareBy(sortBy.selector)
            SortDirection.Descending -> compareByDescending(sortBy.selector)
        }
    }
}