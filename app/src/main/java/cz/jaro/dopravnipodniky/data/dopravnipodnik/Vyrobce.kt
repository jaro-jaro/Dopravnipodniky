package cz.jaro.dopravnipodniky.data.dopravnipodnik

import cz.jaro.dopravnipodniky.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Vyrobce")
enum class Vyrobce(
    val jmeno: Int
) {
    SOLARIS(
        jmeno = R.string.solaris
    ),
    KAROSA(
        jmeno = R.string.karosa
    ),
    VOLVO(
        jmeno = R.string.volvo
    ),
    RENAULT(
        jmeno = R.string.renalut
    ),
    IVECO(
        jmeno = R.string.iveco
    ),
    IRISBUS(
        jmeno = R.string.irisbus
    ),
    IKARUS(
        jmeno = R.string.ikarus
    ),
    HEULIEZ(
        jmeno = R.string.heuliez
    ),
    SKODA(
        jmeno = R.string.skoda
    ),
    SOR(
        jmeno = R.string.sor
    ),
    JELCZ(
        jmeno = R.string.jelcz
    ),
    PRAGA(
        jmeno = R.string.praga
    ),
    TATRA(
        jmeno = R.string.tatra
    ),
    EKOVA(
        jmeno = R.string.ekova
    ),
    HESS(
        jmeno = R.string.hess
    ),
    BOGDAN(
        jmeno = R.string.bogdan
    ),
}