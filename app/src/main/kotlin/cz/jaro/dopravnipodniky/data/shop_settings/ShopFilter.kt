package cz.jaro.dopravnipodniky.data.shop_settings

import androidx.compose.ui.unit.dp
import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.dopravnipodnik.BusRunningCosts
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Trakce
import cz.jaro.dopravnipodniky.data.dopravnipodnik.TypBusu
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Vyrobce
import cz.jaro.dopravnipodniky.data.dopravnipodnik.runningCosts
import cz.jaro.dopravnipodniky.shared.Text
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz
import cz.jaro.dopravnipodniky.shared.jednotky.penez
import cz.jaro.dopravnipodniky.shared.toText
import cz.jaro.dopravnipodniky.ui.malovani.SerializableDp
import kotlinx.serialization.Serializable

@Serializable
sealed class ShopFilter(
    val name: Text,
    val predicate: (busType: TypBusu) -> Boolean,
    val group: FilterGroup,
) {
    @Serializable
    data class ByTraction(
        private val traction: Trakce,
    ) : ShopFilter(
        name = traction.jmeno.toText(),
        predicate = { it.trakce == traction },
        group = FilterGroup.TractionGroup,
    )

    @Serializable
    sealed class ByLength(
        private val allowedLengths: ClosedRange<SerializableDp>,
        val filterName: Text,
    ) : ShopFilter(
        name = filterName,
        predicate = { it.delka in allowedLengths },
        group = FilterGroup.LengthGroup,
    ) {
        @Serializable
        data object LengthLessThan12m : ByLength(
            allowedLengths = 0.dp..11.dp,
            filterName = R.string.mene_nez_12_m.toText(),
        )

        @Serializable
        data object Length12m : ByLength(
            allowedLengths = 11.dp..14.dp,
            filterName = R.string._12_m.toText(),
        )

        @Serializable
        data object Length15m : ByLength(
            allowedLengths = 14.dp..17.dp,
            filterName = R.string._15_m.toText(),
        )

        @Serializable
        data object Length18m : ByLength(
            allowedLengths = 17.dp..19.dp,
            filterName = R.string._18_m.toText(),
        )

        @Serializable
        data object LengthMoreThan18m : ByLength(
            allowedLengths = 19.dp..119.dp,
            filterName = R.string.vice_nez_18_m.toText(),
        )
    }

    @Serializable
    data class ByManufacturer(
        private val manufacturer: Vyrobce,
    ) : ShopFilter(
        name = manufacturer.jmeno.toText(),
        predicate = { it.vyrobce == manufacturer },
        group = FilterGroup.ManufacturerGroup,
    )

    @Serializable
    data class ByRunningCosts(
        private val runningCosts: BusRunningCosts,
    ) : ShopFilter(
        name = runningCosts.label,
        predicate = { it.runningCosts == runningCosts },
        group = FilterGroup.RunningCostsGroup,
    )

    @Serializable
    sealed class ByPurchasePrice(
        private val priceRange: OpenEndRange<Peniz>,
        val filterName: Text,
    ) : ShopFilter(
        name = filterName,
        predicate = { it.cena in priceRange },
        group = FilterGroup.PurchasePriceGroup,
    ) {
        @Serializable
        data object Low : ByPurchasePrice(
            priceRange = 0.penez..<100_000.penez,
            filterName = R.string.levna_trida.toText(),
        )

        @Serializable
        data object Medium : ByPurchasePrice(
            priceRange = 100_000.penez..<200_000.penez,
            filterName = R.string.stredni_trida.toText(),
        )

        @Serializable
        data object High : ByPurchasePrice(
            priceRange = 200_000.penez..<900_000.penez,
            filterName = R.string.vyssi_trida.toText(),
        )
    }


    @Serializable
    data object HaveEnoughMoney : ShopFilter(
        name = R.string.mam_na_to.toText(),
        predicate = { true },
        group = FilterGroup.HaveEnoughMoneyGroup,
    )
}

