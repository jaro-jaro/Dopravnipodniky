package cz.jaro.dopravnipodniky.shared

import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.shared.StavTroleji.MaTrolej
import cz.jaro.dopravnipodniky.shared.StavTroleji.Neexistuje
import cz.jaro.dopravnipodniky.shared.StavTroleji.NemaTrolej

enum class StavTroleji {
    Neexistuje,
    MaTrolej,
    NemaTrolej;
}

val Ulice?.stavTroleje
    get() = when {
        this == null -> Neexistuje
        maTrolej -> MaTrolej
        else -> NemaTrolej
    }

val StavTroleji.maTrolej get() = this == MaTrolej
val StavTroleji.nemaTrolej get() = this != MaTrolej
val StavTroleji.neexistuje get() = this == Neexistuje
val StavTroleji.existuje get() = this != Neexistuje
