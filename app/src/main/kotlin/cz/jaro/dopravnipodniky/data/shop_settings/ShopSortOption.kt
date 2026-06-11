package cz.jaro.dopravnipodniky.data.shop_settings

import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.dopravnipodnik.TypBusu
import cz.jaro.dopravnipodniky.data.dopravnipodnik.costChangeSpeed
import cz.jaro.dopravnipodniky.shared.Text
import cz.jaro.dopravnipodniky.shared.toText
import kotlinx.serialization.Serializable

@Serializable
sealed class ShopSortOption(
    val name: Text,
    val selector: (TypBusu) -> Comparable<*>?,
) {
    companion object {
        val shopSortOptions = listOf(ByPrice, ByCosts, ByCapacity)
    }

    @Serializable
    data object ByPrice : ShopSortOption(
        name = R.string.dle_ceny.toText(),
        selector = { it.cena },
    )
    @Serializable
    data object ByCosts : ShopSortOption(
        name = R.string.dle_nakladu.toText(),
        selector = { it.costChangeSpeed },
    )
    @Serializable
    data object ByCapacity : ShopSortOption(
        name = R.string.dle_kapacity.toText(),
        selector = { it.kapacita },
    )
}