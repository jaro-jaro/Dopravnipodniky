package cz.jaro.dopravnipodniky.data.shop_settings

import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.shared.Text
import cz.jaro.dopravnipodniky.shared.toText
import kotlinx.serialization.Serializable

@Serializable
enum class SortDirection(
    val label: Text,
) {
    Ascending(
        label = R.string.vzestupne.toText()
    ),
    Descending(
        label = R.string.sestupne.toText(),
    ),
}