package cz.jaro.dopravnipodniky.data.dosahlosti

import cz.jaro.dopravnipodniky.R
import cz.jaro.dopravnipodniky.data.serializers.LocalDateTimeSerializer
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz
import cz.jaro.dopravnipodniky.shared.jednotky.penez
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import kotlin.reflect.KClass

@Serializable
@SerialName("Dosahlost")
sealed interface Dosahlost {
    companion object {
        fun dosahlosti() = listOf(
            Linka1(),
            Kocka(),
            BusNaLince(),
            BusNaZastavce(),
            Bus1(),
            Bus2(),
            Bus12(),
            Bus60(),
            Bus1024(),
            Penize200000(),
            Penize500000(),
            Penize1000000(),
            Penize2000000(),
            Penize10000000(),
            ProjetZastavku(),
            StejneLinky(),
            Vecne1(),
            Vecne2(),
            JostoMesto(),
            Citer(),
        )
    }

    @Serializable
    @SerialName("NormalniDosahlost")
    sealed interface NormalniDosahlost : Dosahlost {
        val jmeno: Int
        val popis: Int
        val odmena: Peniz get() = 0.penez

        val stav: Stav
        fun kopirovat(stav: Stav): NormalniDosahlost
    }

    @Serializable
    @SerialName("SkupinovaDosahlost")
    sealed interface SkupinovaDosahlost : Dosahlost {
        val dosahlosti: List<KClass<out Pocetni>>

        @Serializable
        @SerialName("BusDosahlost")
        data object Bus : SkupinovaDosahlost {
            override val dosahlosti = listOf(
                Bus1024::class,
                Bus60::class,
                Bus12::class,
                Bus2::class,
                Bus1::class,
            )
        }

        @Serializable
        @SerialName("PenizeDosahlost")
        data object Penize : SkupinovaDosahlost {
            override val dosahlosti = listOf(
                Penize10000000::class,
                Penize2000000::class,
                Penize1000000::class,
                Penize500000::class,
                Penize200000::class,
            )
        }

        @Serializable
        @SerialName("LinkaDosahlost")
        data object Linka : SkupinovaDosahlost {
            override val dosahlosti = listOf(
                Linka1::class
            )
        }
    }

    @Serializable
    @SerialName("SecretDosahlost")
    sealed interface Secret : NormalniDosahlost {
        val napoveda: Int? get() = null
    }

    @Serializable
    @SerialName("NeSecretDosahlost")
    sealed interface NeSecret : NormalniDosahlost

    @Serializable
    @SerialName("PocetniDosahlost")
    sealed interface Pocetni : NormalniDosahlost {
        val cil: Int
    }

    @Serializable
    @SerialName("StavDosahlost")
    sealed interface Stav {
        @Serializable
        @SerialName("SplnenoDosahlostStav")
        data class Splneno(
            @Serializable(with = LocalDateTimeSerializer::class) val kdy: LocalDateTime,
        ) : Stav

        @Serializable
        @SerialName("NesplnenoDosahlostStav")
        data object Nesplneno : Stav

        @Serializable
        @SerialName("PocetniDosahlostStav")
        data class Pocetni(
            val pocet: Int,
        ) : Stav
    }

    @Serializable
    @SerialName("Linka1Dosahlost")
    data class Linka1(
        override val stav: Stav = Stav.Nesplneno,
    ) : Pocetni {
        override fun kopirovat(stav: Stav) = copy(stav = stav)
        override val cil: Int get() = 1

        override val jmeno get() = R.string.d_n_linka1
        override val popis get() = R.string.d_p_linka1
        override val odmena get() = 10_000.0.penez
    }

    @Serializable
    @SerialName("KockaDosahlost")
    data class Kocka(
        override val stav: Stav = Stav.Nesplneno,
    ) : Secret {
        override fun kopirovat(stav: Stav) = copy(stav = stav)
        override val jmeno get() = R.string.d_n_kocka1
        override val popis get() = R.string.d_p_kocka1
        override val odmena get() = 100_000.0.penez
    }

    @Serializable
    @SerialName("BusNaLinceDosahlost")
    data class BusNaLince(
        override val stav: Stav = Stav.Nesplneno,
    ) : NeSecret {
        override fun kopirovat(stav: Stav) = copy(stav = stav)
        override val jmeno get() = R.string.d_n_bus_na_lince
        override val popis get() = R.string.d_p_bus_na_lince
    }

    @Serializable
    @SerialName("BusNaZastavceDosahlost")
    data class BusNaZastavce(
        override val stav: Stav = Stav.Nesplneno,
    ) : NeSecret {
        override fun kopirovat(stav: Stav) = copy(stav = stav)
        override val jmeno get() = R.string.d_n_bus_na_zastavce
        override val popis get() = R.string.d_p_bus_na_zastavce
    }

    @Serializable
    @SerialName("Bus1Dosahlost")
    data class Bus1(
        override val stav: Stav = Stav.Nesplneno,
    ) : Pocetni {
        override fun kopirovat(stav: Stav) = copy(stav = stav)
        override val cil: Int get() = 1
        override val jmeno get() = R.string.d_n_bus1
        override val popis get() = R.string.d_p_bus1
        override val odmena get() = 10_000.0.penez
    }

    @Serializable
    @SerialName("Bus2Dosahlost")
    data class Bus2(
        override val stav: Stav = Stav.Nesplneno,
    ) : Pocetni {
        override fun kopirovat(stav: Stav) = copy(stav = stav)
        override val cil: Int get() = 2
        override val jmeno get() = R.string.d_n_bus2
        override val popis get() = R.string.d_p_bus2
        override val odmena get() = 5_000.0.penez
    }

    @Serializable
    @SerialName("Bus12Dosahlost")
    data class Bus12(
        override val stav: Stav = Stav.Pocetni(12),
    ) : Pocetni {
        override fun kopirovat(stav: Stav) = copy(stav = stav)
        override val cil: Int get() = 12
        override val jmeno get() = R.string.d_n_bus12
        override val popis get() = R.string.d_p_bus12
        override val odmena get() = 12_000.0.penez
    }

    @Serializable
    @SerialName("Bus60Dosahlost")
    data class Bus60(
        override val stav: Stav = Stav.Nesplneno,
    ) : Pocetni {
        override fun kopirovat(stav: Stav) = copy(stav = stav)
        override val cil: Int get() = 60
        override val jmeno get() = R.string.d_n_bus60
        override val popis get() = R.string.d_p_bus60
        override val odmena get() = 60_000.0.penez
    }

    @Serializable
    @SerialName("Bus1024Dosahlost")
    data class Bus1024(
        override val stav: Stav = Stav.Nesplneno,
    ) : Pocetni {
        override fun kopirovat(stav: Stav) = copy(stav = stav)
        override val cil: Int get() = 1024
        override val jmeno get() = R.string.d_n_bus1024
        override val popis get() = R.string.d_p_bus1024
        override val odmena get() = 1_024_000.0.penez
    }

    @Serializable
    @SerialName("Penize200000Dosahlost")
    data class Penize200000(
        override val stav: Stav = Stav.Nesplneno,
    ) : Pocetni {
        override fun kopirovat(stav: Stav) = copy(stav = stav)
        override val cil: Int get() = 200_000
        override val jmeno get() = R.string.d_n_penize200000
        override val popis get() = R.string.d_p_penize200000
    }

    @Serializable
    @SerialName("Penize500000Dosahlost")
    data class Penize500000(
        override val stav: Stav = Stav.Nesplneno,
    ) : Pocetni {
        override fun kopirovat(stav: Stav) = copy(stav = stav)
        override val cil: Int get() = 500_000
        override val jmeno get() = R.string.d_n_penize500000
        override val popis get() = R.string.d_p_penize500000
    }

    @Serializable
    @SerialName("Penize1000000Dosahlost")
    data class Penize1000000(
        override val stav: Stav = Stav.Nesplneno,
    ) : Pocetni {
        override fun kopirovat(stav: Stav) = copy(stav = stav)
        override val cil: Int get() = 1_000_000
        override val jmeno get() = R.string.d_n_penize1000000
        override val popis get() = R.string.d_p_penize1000000
    }

    @Serializable
    @SerialName("Penize2000000Dosahlost")
    data class Penize2000000(
        override val stav: Stav = Stav.Nesplneno,
    ) : Pocetni {
        override fun kopirovat(stav: Stav) = copy(stav = stav)
        override val cil: Int get() = 2_000_000
        override val jmeno get() = R.string.d_n_penize2000000
        override val popis get() = R.string.d_p_penize2000000
    }

    @Serializable
    @SerialName("Penize10000000Dosahlost")
    data class Penize10000000(
        override val stav: Stav = Stav.Nesplneno,
    ) : Pocetni {
        override fun kopirovat(stav: Stav) = copy(stav = stav)
        override val cil: Int get() = 10_000_000
        override val jmeno get() = R.string.d_n_penize10000000
        override val popis get() = R.string.d_p_penize10000000
    }
    //tak tady je doopravdy konec




























































    @Serializable
    @SerialName("ProjetZastavkuDosahlost")
    data class ProjetZastavku(
        override val stav: Stav = Stav.Nesplneno,
    ) : Secret {
        override fun kopirovat(stav: Stav) = copy(stav = stav)
        override val jmeno get() = R.string.d_n_projet_zastavku
        override val popis get() = R.string.d_p_projet_zastavku
        override val napoveda get() = R.string.nic
    }

    @Serializable
    @SerialName("StejneLinkyDosahlost")
    data class StejneLinky(
        override val stav: Stav = Stav.Nesplneno,
    ) : Secret {
        override fun kopirovat(stav: Stav) = copy(stav = stav)
        override val napoveda get() = R.string.d_n_stejne_linky
        override val popis get() = R.string.d_p_stejne_linky
        override val odmena get() = 100_000.0.penez
        override val jmeno get() = R.string.d_nap_stejne_linky
    }

    @Serializable
    @SerialName("Vecne1Dosahlost")
    data class Vecne1(
        override val stav: Stav = Stav.Nesplneno,
    ) : Secret {
        override fun kopirovat(stav: Stav) = copy(stav = stav)
        override val jmeno get() = R.string.d_n_vecne1
        override val popis get() = R.string.d_p_vecne1
        override val odmena get() = 1_000_000.0.penez
    }

    @Serializable
    @SerialName("Vecne2Dosahlost")
    data class Vecne2(
        override val stav: Stav = Stav.Nesplneno,
    ) : Secret {
        override fun kopirovat(stav: Stav) = copy(stav = stav)
        override val jmeno get() = R.string.d_n_vecne2
        override val popis get() = R.string.d_p_vecne2
        override val odmena get() = 1_000_000_000_000.0.penez
    }

    @Serializable
    @SerialName("JostoMestoDosahlost")
    data class JostoMesto(
        override val stav: Stav = Stav.Nesplneno,
    ) : Secret {
        override fun kopirovat(stav: Stav) = copy(stav = stav)
        override val jmeno get() = R.string.d_n_jostomesto
        override val popis get() = R.string.d_p_jostomesto
        override val odmena get() = (-100_000).penez
    }

    @Serializable
    @SerialName("CiterDosahlost")
    data class Citer(
        override val stav: Stav = Stav.Nesplneno,
    ) : Secret {
        override fun kopirovat(stav: Stav) = copy(stav = stav)
        override val jmeno get() = R.string.d_n_citer
        override val popis get() = R.string.d_p_citer
        override val odmena get() = Double.NEGATIVE_INFINITY.penez
    }
    // tady to končí doopravdy


}
