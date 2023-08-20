package cz.jaro.dopravnipodniky

import cz.jaro.dopravnipodniky.jednotky.Peniz
import cz.jaro.dopravnipodniky.jednotky.penez
import kotlinx.serialization.Serializable
import java.time.LocalDate
import kotlin.reflect.KClass

@Serializable
sealed interface Dosahlost {

    @Serializable
    sealed interface NormalniDosahlost : Dosahlost {
        val jmeno: Int
        val popis: Int
        val odmena: Peniz get() = 0.penez

        val stav: Stav
        fun stav(stav: Stav): NormalniDosahlost
    }

    @Serializable
    sealed interface SkupinovaDosahlost : Dosahlost {
        val dosahlosti: List<KClass<out Pocetni>>

        @Serializable
        data object Bus : SkupinovaDosahlost {
            override val dosahlosti = listOf(
                Bus1::class,
                Bus2::class,
                Bus12::class,
                Bus60::class,
                Bus1024::class,
            )
        }

        @Serializable
        data object Penize : SkupinovaDosahlost {
            override val dosahlosti = listOf(
                Penize200000::class,
                Penize500000::class,
                Penize1000000::class,
                Penize2000000::class,
                Penize10000000::class,
            )
        }
    }

    @Serializable
    sealed interface Secret : NormalniDosahlost {
        val napoveda: Int? get() = null
    }

    @Serializable
    sealed interface NeSecret : NormalniDosahlost

    @Serializable
    sealed interface Pocetni : NormalniDosahlost {
        val cil: Int
    }

    @Serializable
    sealed interface Stav {
        @Serializable
        data class Splneno(
            @Serializable(with = LocalDateSerializer::class) val kdy: LocalDate,
        ) : Stav

        @Serializable
        data object Nesplneno : Stav

        @Serializable
        data class Pocetni(
            val pocet: Int,
        ) : Stav
    }

    @Serializable
    data class Linka1(
        override val stav: Stav = Stav.Nesplneno,
    ) : Pocetni {
        override fun stav(stav: Stav) = copy(stav = stav)
        override val cil: Int get() = 1

        override val jmeno get() = R.string.d_n_linka1
        override val popis get() = R.string.d_p_linka1
        override val odmena get() = 10_000.0.penez
    }

    @Serializable
    data class Kocka(
        override val stav: Stav = Stav.Nesplneno,
    ) : Secret {
        override fun stav(stav: Stav) = copy(stav = stav)
        override val jmeno get() = R.string.d_n_kocka1
        override val popis get() = R.string.d_p_kocka1
        override val odmena get() = 100_000.0.penez
    }

    @Serializable
    data class BusNaLince(
        override val stav: Stav = Stav.Nesplneno,
    ) : NeSecret {
        override fun stav(stav: Stav) = copy(stav = stav)
        override val jmeno get() = R.string.d_n_bus_na_lince
        override val popis get() = R.string.d_p_bus_na_lince
    }

    @Serializable
    data class BusNaZastavce(
        override val stav: Stav = Stav.Nesplneno,
    ) : NeSecret {
        override fun stav(stav: Stav) = copy(stav = stav)
        override val jmeno get() = R.string.d_n_bus_na_zastavce
        override val popis get() = R.string.d_p_bus_na_zastavce
    }

    @Serializable
    data class Bus1(
        override val stav: Stav = Stav.Nesplneno,
    ) : Pocetni {
        override fun stav(stav: Stav) = copy(stav = stav)
        override val cil: Int get() = 1
        override val jmeno get() = R.string.d_n_bus1
        override val popis get() = R.string.d_p_bus1
        override val odmena get() = 10_000.0.penez
    }

    @Serializable
    data class Bus2(
        override val stav: Stav = Stav.Nesplneno,
    ) : Pocetni {
        override fun stav(stav: Stav) = copy(stav = stav)
        override val cil: Int get() = 2
        override val jmeno get() = R.string.d_n_bus2
        override val popis get() = R.string.d_p_bus2
        override val odmena get() = 5_000.0.penez
    }

    @Serializable
    data class Bus12(
        override val stav: Stav = Stav.Pocetni(12),
    ) : Pocetni {
        override fun stav(stav: Stav) = copy(stav = stav)
        override val cil: Int get() = 12
        override val jmeno get() = R.string.d_n_bus12
        override val popis get() = R.string.d_p_bus12
        override val odmena get() = 12_000.0.penez
    }

    @Serializable
    data class Bus60(
        override val stav: Stav = Stav.Nesplneno,
    ) : Pocetni {
        override fun stav(stav: Stav) = copy(stav = stav)
        override val cil: Int get() = 60
        override val jmeno get() = R.string.d_n_bus60
        override val popis get() = R.string.d_p_bus60
        override val odmena get() = 60_000.0.penez
    }

    @Serializable
    data class Bus1024(
        override val stav: Stav = Stav.Nesplneno,
    ) : Pocetni {
        override fun stav(stav: Stav) = copy(stav = stav)
        override val cil: Int get() = 1024
        override val jmeno get() = R.string.d_n_bus1024
        override val popis get() = R.string.d_p_bus1024
        override val odmena get() = 1_024_000.0.penez
    }

    @Serializable
    data class Penize200000(
        override val stav: Stav = Stav.Nesplneno,
    ) : Pocetni {
        override fun stav(stav: Stav) = copy(stav = stav)
        override val cil: Int get() = 200_000
        override val jmeno get() = R.string.d_n_penize200000
        override val popis get() = R.string.d_p_penize200000
    }

    @Serializable
    data class Penize500000(
        override val stav: Stav = Stav.Nesplneno,
    ) : Pocetni {
        override fun stav(stav: Stav) = copy(stav = stav)
        override val cil: Int get() = 500_000
        override val jmeno get() = R.string.d_n_penize500000
        override val popis get() = R.string.d_p_penize500000
    }

    @Serializable
    data class Penize1000000(
        override val stav: Stav = Stav.Nesplneno,
    ) : Pocetni {
        override fun stav(stav: Stav) = copy(stav = stav)
        override val cil: Int get() = 1_000_000
        override val jmeno get() = R.string.d_n_penize1000000
        override val popis get() = R.string.d_p_penize1000000
    }

    @Serializable
    data class Penize2000000(
        override val stav: Stav = Stav.Nesplneno,
    ) : Pocetni {
        override fun stav(stav: Stav) = copy(stav = stav)
        override val cil: Int get() = 2_000_000
        override val jmeno get() = R.string.d_n_penize2000000
        override val popis get() = R.string.d_p_penize2000000
    }

    @Serializable
    data class Penize10000000(
        override val stav: Stav = Stav.Nesplneno,
    ) : Pocetni {
        override fun stav(stav: Stav) = copy(stav = stav)
        override val cil: Int get() = 10_000_000
        override val jmeno get() = R.string.d_n_penize10000000
        override val popis get() = R.string.d_p_penize10000000
    }
    //tak tady je doopravdy konec




























































    @Serializable
    data class ProjetZastavku(
        override val stav: Stav = Stav.Nesplneno,
    ) : Secret {
        override fun stav(stav: Stav) = copy(stav = stav)
        override val jmeno get() = R.string.d_n_projet_zastavku
        override val popis get() = R.string.d_p_projet_zastavku
        override val napoveda get() = R.string.nic
    }

    @Serializable
    data class StejneLinky(
        override val stav: Stav = Stav.Nesplneno,
    ) : Secret {
        override fun stav(stav: Stav) = copy(stav = stav)
        override val jmeno get() = R.string.d_n_stejne_linky
        override val popis get() = R.string.d_p_stejne_linky
        override val odmena get() = 100_000.0.penez
        override val napoveda get() = R.string.d_nap_stejne_linky
    }

    @Serializable
    data class Vecne1(
        override val stav: Stav = Stav.Nesplneno,
    ) : Secret {
        override fun stav(stav: Stav) = copy(stav = stav)
        override val jmeno get() = R.string.d_n_vecne1
        override val popis get() = R.string.d_p_vecne1
        override val odmena get() = 1_000_000.0.penez
    }

    @Serializable
    data class Vecne2(
        override val stav: Stav = Stav.Nesplneno,
    ) : Secret {
        override fun stav(stav: Stav) = copy(stav = stav)
        override val jmeno get() = R.string.d_n_vecne2
        override val popis get() = R.string.d_p_vecne2
        override val odmena get() = 1_000_000_000_000.0.penez
    }

    @Serializable
    data class JostoMesto(
        override val stav: Stav = Stav.Nesplneno,
    ) : Secret {
        override fun stav(stav: Stav) = copy(stav = stav)
        override val jmeno get() = R.string.d_n_jostomesto
        override val popis get() = R.string.d_p_jostomesto
        override val odmena get() = Double.NEGATIVE_INFINITY.penez
    }

    @Serializable
    data class Citer(
        override val stav: Stav = Stav.Nesplneno,
    ) : Secret {
        override fun stav(stav: Stav) = copy(stav = stav)
        override val jmeno get() = R.string.d_n_citer
        override val popis get() = R.string.d_p_citer
        override val odmena get() = Double.NEGATIVE_INFINITY.penez
    }
    // tady to končí doopravdy






































































































}
