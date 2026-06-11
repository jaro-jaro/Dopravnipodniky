package cz.jaro.dopravnipodniky.ui.fleet.shop

import cz.jaro.dopravnipodniky.data.dopravnipodnik.TypBusu
import cz.jaro.dopravnipodniky.data.shop_settings.ShopSettings
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz

data class ShopState(
    val buses: List<TypBusu>,
    val tutorialState: StavTutorialu,
    val shopSettings: ShopSettings,
    val money: Peniz,
    val showState: ShopViewModel.Show,
)
