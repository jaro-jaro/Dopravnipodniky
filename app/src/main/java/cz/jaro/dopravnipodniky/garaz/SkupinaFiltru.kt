package cz.jaro.dopravnipodniky.garaz

import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.shared.Text
import cz.jaro.dopravnipodniky.dopravnipodnik.TypBusu
import cz.jaro.dopravnipodniky.dopravnipodnik.nakladyTextem
import cz.jaro.dopravnipodniky.shared.jednotky.getNakladyTextem
import cz.jaro.dopravnipodniky.shared.jednotky.penezZaMin
import cz.jaro.dopravnipodniky.shared.toText
import kotlinx.serialization.Serializable
import cz.jaro.dopravnipodniky.dopravnipodnik.Trakce as BusTrakce
import cz.jaro.dopravnipodniky.dopravnipodnik.Vyrobce as BusVyrobce

@Serializable
sealed interface SkupinaFiltru<T : SkupinaFiltru.Filtr> {
    val filtry: List<T>
    val nazev: Text

    companion object {
        fun <T : Filtr> Sequence<TypBusu>.filtrovat(filtry: List<T>) = filter { typBusu ->
            if (filtry.isEmpty()) true
            else filtry.any { it.filtrovat(typBusu) }
        }

        val skupinyFiltru = listOf(
            Trakce,
            Delka,
            Vyrobce,
            Naklady,
            Cena,
        )

        val pocatecniFiltry = listOf(
            Trakce.TrakceFiltr(trakce = BusTrakce.Autobus.Dieslovy),
            Trakce.TrakceFiltr(trakce = BusTrakce.Autobus.Zemeplynovy),
            Trakce.TrakceFiltr(trakce = BusTrakce.Autobus.Hybridni),
            Trakce.TrakceFiltr(trakce = BusTrakce.Autobus.Vodikovy),
            Trakce.TrakceFiltr(trakce = BusTrakce.Elektrobus),
            Naklady.NakladyFiltr(naklady = 0.penezZaMin.getNakladyTextem()),
            Naklady.NakladyFiltr(naklady = 50.penezZaMin.getNakladyTextem()),
            Naklady.NakladyFiltr(naklady = 60.penezZaMin.getNakladyTextem()),
            Naklady.NakladyFiltr(naklady = 65.penezZaMin.getNakladyTextem()),
            Naklady.NakladyFiltr(naklady = 70.penezZaMin.getNakladyTextem()),
            Naklady.NakladyFiltr(naklady = 75.penezZaMin.getNakladyTextem()),
            Cena.MamNaTo,
        )
    }

    @Serializable
    sealed interface Filtr {
        val nazevFiltru: Text
        fun filtrovat(typBusu: TypBusu): Boolean
    }

    @Serializable
    data object Trakce : SkupinaFiltru<Trakce.TrakceFiltr> {
        @Serializable
        data class TrakceFiltr(
            private val trakce: BusTrakce,
        ) : Filtr {
            override val nazevFiltru get() = trakce.jmeno.toText()
            override fun filtrovat(typBusu: TypBusu) = typBusu.trakce == trakce
        }

        override val nazev = R.string.dle_trakce.toText()
        override val filtry = cz.jaro.dopravnipodniky.dopravnipodnik.Trakce.vse.map {
            TrakceFiltr(trakce = it)
        }
    }

    @Serializable
    data object Delka : SkupinaFiltru<Delka.DelkaFiltr> {
        @Serializable
        data class DelkaFiltr(
            private val delkaMin: Double,
            private val delkaMax: Double,
            override val nazevFiltru: Text,
        ) : Filtr {
            override fun filtrovat(typBusu: TypBusu) = typBusu.delka.value.toFloat() in delkaMin..delkaMax
        }

        override val nazev = R.string.dle_delky.toText()
        override val filtry = listOf(
            DelkaFiltr(
                delkaMin = 0.0,
                delkaMax = 11.0,
                nazevFiltru = R.string.mene_nez_12_m.toText(),
            ),
            DelkaFiltr(
                delkaMin = 11.0,
                delkaMax = 14.0,
                nazevFiltru = R.string._12_m.toText(),
            ),
            DelkaFiltr(
                delkaMin = 14.0,
                delkaMax = 17.0,
                nazevFiltru = R.string._15_m.toText(),
            ),
            DelkaFiltr(
                delkaMin = 17.0,
                delkaMax = 19.0,
                nazevFiltru = R.string._18_m.toText(),
            ),
            DelkaFiltr(
                delkaMin = 19.0,
                delkaMax = 119.0,
                nazevFiltru = R.string.vice_nez_18_m.toText(),
            ),
        )
    }

    @Serializable
    data object Vyrobce : SkupinaFiltru<Vyrobce.VyrobceFiltr> {
        @Serializable
        data class VyrobceFiltr(
            private val vyrobce: BusVyrobce,
        ) : Filtr {
            override val nazevFiltru get() = vyrobce.jmeno.toText()
            override fun filtrovat(typBusu: TypBusu) = typBusu.vyrobce == vyrobce
        }

        override val nazev = R.string.dle_vyrobce.toText()
        override val filtry = BusVyrobce.entries.map {
            VyrobceFiltr(vyrobce = it)
        }
    }

    @Serializable
    data object Naklady : SkupinaFiltru<Naklady.NakladyFiltr> {
        @Serializable
        data class NakladyFiltr(
            private val naklady: Int,
        ) : Filtr {
            override val nazevFiltru = naklady.toText()
            override fun filtrovat(typBusu: TypBusu) = typBusu.nakladyTextem == naklady
        }

        override val nazev = R.string.dle_nakladu.toText()
        override val filtry = (0..499).map {
            NakladyFiltr(naklady = it.penezZaMin.getNakladyTextem())
        }.distinctBy { it.nazevFiltru }
//        override val filtry = listOf(
//            NakladyFiltr(naklady = 49.penezZaMin.getNakladyTextem()),
//            NakladyFiltr(naklady = 59.penezZaMin.getNakladyTextem()),
//            NakladyFiltr(naklady = 64.penezZaMin.getNakladyTextem()),
//            NakladyFiltr(naklady = 69.penezZaMin.getNakladyTextem()),
//            NakladyFiltr(naklady = 74.penezZaMin.getNakladyTextem()),
//            NakladyFiltr(naklady = 79.penezZaMin.getNakladyTextem()),
//            NakladyFiltr(naklady = 84.penezZaMin.getNakladyTextem()),
//            NakladyFiltr(naklady = 89.penezZaMin.getNakladyTextem()),
//            NakladyFiltr(naklady = 94.penezZaMin.getNakladyTextem()),
//            NakladyFiltr(naklady = 99.penezZaMin.getNakladyTextem()),
//            NakladyFiltr(naklady = 499.penezZaMin.getNakladyTextem()),
//        )
    }

    @Serializable
    data object Cena : SkupinaFiltru<Cena.CenaFiltr> {
        @Serializable
        sealed class CenaFiltr(
            override val nazevFiltru: Text,
            private val vyFiltrovat: (typBusu: TypBusu) -> Boolean
        ) : Filtr {
            override fun filtrovat(typBusu: TypBusu) = vyFiltrovat(typBusu)
        }

        override val nazev = R.string.dle_ceny.toText()
        override val filtry = listOf(
            LevnaTrida, StredniTrida, VyssiTrida
        )
        @Serializable
        data object LevnaTrida : CenaFiltr(
            nazevFiltru = R.string.levna_trida.toText(),
            vyFiltrovat = {
                it.cena.value in 0.0..100_000.0
            }
        )
        @Serializable
        data object StredniTrida : CenaFiltr(
            nazevFiltru = R.string.stredni_trida.toText(),
            vyFiltrovat = {
                it.cena.value in 100_000.0..200_000.0
            }
        )
        @Serializable
        data object VyssiTrida : CenaFiltr(
            nazevFiltru = R.string.vyssi_trida.toText(),
            vyFiltrovat = {
                it.cena.value in 200_000.0..900_000.0
            }
        )
        @Serializable
        data object MamNaTo : CenaFiltr(
            nazevFiltru = R.string.mam_na_to.toText(),
            vyFiltrovat = {
                true
            }
        )
    }
}