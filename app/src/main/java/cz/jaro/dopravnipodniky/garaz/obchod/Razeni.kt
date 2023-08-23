package cz.jaro.dopravnipodniky.garaz.obchod

import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.dopravnipodnik.TypBusu
import cz.jaro.dopravnipodniky.dopravnipodnik.zrychleniOdebiraniPenez
import cz.jaro.dopravnipodniky.shared.Text
import cz.jaro.dopravnipodniky.shared.toText
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Razeni")
sealed interface Razeni {
    val nazev: Text
    val comparator: Comparator<TypBusu>

    companion object {
        val razeni = listOf(
            listOf(
                Cena.Sestupne,
                Cena.Vzestupne,
            ),
            listOf(
                Naklady.Sestupne,
                Naklady.Vzestupne,
            ),
            listOf(
                Kapacita.Sestupne,
                Kapacita.Vzestupne,
            ),
        )
    }

    @Serializable
    @SerialName("RazeniCena")
    data object Cena {
        @Serializable
        @SerialName("RazeniCenaVzestupne")
        data object Vzestupne : Razeni {
            override val nazev = R.string.dle_ceny.toText()
            override val comparator: Comparator<TypBusu> = compareBy { it.cena }
        }
        @Serializable
        @SerialName("RazeniCenaSestupne")
        data object Sestupne : Razeni {
            override val nazev = R.string.dle_ceny.toText()
            override val comparator: Comparator<TypBusu> = compareByDescending { it.cena }
        }
    }
    @Serializable
    @SerialName("RazeniNaklady")
    data object Naklady {
        @Serializable
        @SerialName("RazeniNakladyVzestupne")
        data object Vzestupne : Razeni {
            override val nazev = R.string.dle_nakladu.toText()
            override val comparator: Comparator<TypBusu> = compareBy { it.zrychleniOdebiraniPenez }
        }
        @Serializable
        @SerialName("RazeniNakladySestupne")
        data object Sestupne : Razeni {
            override val nazev = R.string.dle_nakladu.toText()
            override val comparator: Comparator<TypBusu> = compareByDescending { it.zrychleniOdebiraniPenez }
        }
    }
    @Serializable
    @SerialName("RazeniKapacita")
    data object Kapacita {
        @Serializable
        @SerialName("RazeniKapacitaVzestupne")
        data object Vzestupne : Razeni {
            override val nazev = R.string.dle_kapacity.toText()
            override val comparator: Comparator<TypBusu> = compareBy { it.kapacita }
        }
        @Serializable
        @SerialName("RazeniKapacitaSestupne")
        data object Sestupne : Razeni {
            override val nazev = R.string.dle_kapacity.toText()
            override val comparator: Comparator<TypBusu> = compareByDescending { it.kapacita }
        }
    }

    @Serializable
    @SerialName("RazeniZadne")
    data object Zadne: Razeni {
        override val nazev = "".toText()
        override val comparator: Comparator<TypBusu> = compareBy { 0 }
    }
}