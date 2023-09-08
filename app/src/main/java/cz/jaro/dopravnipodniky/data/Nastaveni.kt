package cz.jaro.dopravnipodniky.data

import cz.jaro.dopravnipodniky.ui.garaz.obchod.Razeni
import cz.jaro.dopravnipodniky.ui.garaz.obchod.SkupinaFiltru
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Nastaveni")
data class Nastaveni(
    val automatickyUdelovatEvC: Boolean = true,
    val vicenasobnyKupovani: Boolean = false,
    val filtry: List<SkupinaFiltru.Filtr> = SkupinaFiltru.pocatecniFiltry,
    val razeni: Razeni = Razeni.Naklady.Vzestupne,
)
