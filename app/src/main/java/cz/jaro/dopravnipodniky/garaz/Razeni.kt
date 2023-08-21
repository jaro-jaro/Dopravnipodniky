package cz.jaro.dopravnipodniky.garaz

import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.shared.Text
import cz.jaro.dopravnipodniky.dopravnipodnik.TypBusu
import cz.jaro.dopravnipodniky.shared.toText
import kotlinx.serialization.Serializable

@Serializable
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
    data object Cena {
        @Serializable
        data object Vzestupne : Razeni {
            override val nazev = R.string.dle_ceny.toText()
            override val comparator: Comparator<TypBusu> = compareBy { it.cena }
        }
        @Serializable
        data object Sestupne : Razeni {
            override val nazev = R.string.dle_ceny.toText()
            override val comparator: Comparator<TypBusu> = compareByDescending { it.cena }
        }
    }
    @Serializable
    data object Naklady {
        @Serializable
        data object Vzestupne : Razeni {
            override val nazev = R.string.dle_nakladu.toText()
            override val comparator: Comparator<TypBusu> = compareBy { it.maxNaklady }
        }
        @Serializable
        data object Sestupne : Razeni {
            override val nazev = R.string.dle_nakladu.toText()
            override val comparator: Comparator<TypBusu> = compareByDescending { it.maxNaklady }
        }
    }
    @Serializable
    data object Kapacita {
        @Serializable
        data object Vzestupne : Razeni {
            override val nazev = R.string.dle_kapacity.toText()
            override val comparator: Comparator<TypBusu> = compareBy { it.kapacita }
        }
        @Serializable
        data object Sestupne : Razeni {
            override val nazev = R.string.dle_kapacity.toText()
            override val comparator: Comparator<TypBusu> = compareByDescending { it.kapacita }
        }
    }

    @Serializable
    data object Zadne: Razeni {
        override val nazev = "".toText()
        override val comparator: Comparator<TypBusu> = compareBy { 0 }
    }
}