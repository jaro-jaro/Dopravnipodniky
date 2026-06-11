package cz.jaro.dopravnipodniky.data.shop_settings

import kotlinx.serialization.Serializable

@Serializable
data class ShopSettings(
    val sort: ShopSortSetting,
    val filters: List<ShopFilter>
)

