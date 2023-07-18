package cz.jaro.dopravnipodniky.other

import cz.jaro.dopravnipodniky.R

@Target(AnnotationTarget.TYPE)
annotation class LinkaId

@Target(AnnotationTarget.TYPE)
annotation class BusId

@Target(AnnotationTarget.TYPE)
annotation class UliceId

@Target(AnnotationTarget.TYPE)
annotation class CisloDosahlosti

enum class Razeni {
    VZESTUPNE, SESTUPNE, NIJAK,
}

enum class Trakce {
    AUTOBUS {
        override fun toString() = App.res.getString(R.string.autobus)
    },
    TROLEJBUS {
        override fun toString() = App.res.getString(R.string.trolejbus)
    },
    ELEKTROBUS {
        override fun toString() = App.res.getString(R.string.elektrobus)
    },
    SUSBUS {
        override fun toString() = App.res.getString(R.string.susbus)
    },
}

enum class Podtyp {
    OBYCEJNY {
        override fun toString() = App.res.getString(R.string.obycejny)
    },
    DIESELOVY {
        override fun toString() = App.res.getString(R.string.dieselovy)
    },
    ZEMEPLYNOVY {
        override fun toString() = App.res.getString(R.string.zemeplynovy)
    },
    HYBRIDNI {
        override fun toString() = App.res.getString(R.string.hybridni)
    },
    VODIKOVY {
        override fun toString() = App.res.getString(R.string.vodikovy)
    },
    PARCIALNI {
        override fun toString() = App.res.getString(R.string.parcialni)
    },
}

enum class Vyrobce {
    SOLARIS {
        override fun toString() = App.res.getString(R.string.solaris)
    },
    KAROSA {
        override fun toString() = App.res.getString(R.string.karosa)
    },
    VOLVO {
        override fun toString() = App.res.getString(R.string.volvo)
    },
    RENAULT {
        override fun toString() = App.res.getString(R.string.renalut)
    },
    IVECO {
        override fun toString() = App.res.getString(R.string.iveco)
    },
    IRISBUS {
        override fun toString() = App.res.getString(R.string.irisbus)
    },
    IKARUS {
        override fun toString() = App.res.getString(R.string.ikarus)
    },
    HEULIEZ {
        override fun toString() = App.res.getString(R.string.heuliez)
    },
    SKODA {
        override fun toString() = App.res.getString(R.string.skoda)
    },
    SOR {
        override fun toString() = App.res.getString(R.string.sor)
    },
    JELCZ {
        override fun toString() = App.res.getString(R.string.jelcz)
    },
    PRAGA {
        override fun toString() = App.res.getString(R.string.praga)
    },
    TATRA {
        override fun toString() = App.res.getString(R.string.tatra)
    },
    EKOVA {
        override fun toString() = App.res.getString(R.string.ekova)
    },
    HESS {
        override fun toString() = App.res.getString(R.string.hess)
    },
    BOGDAN {
        override fun toString() = App.res.getString(R.string.bogdan)
    },
}

enum class Orientace {
    SVISLE, VODOROVNE,
}

enum class Smer {
    POZITIVNE, NEGATIVNE,
}

operator fun Smer.times(other: Smer): Smer {

    if (other == Smer.POZITIVNE) return this
    if (this == Smer.POZITIVNE) return other
    return Smer.POZITIVNE

}

fun Boolean.toSmer() = if (this) Smer.POZITIVNE else Smer.NEGATIVNE
