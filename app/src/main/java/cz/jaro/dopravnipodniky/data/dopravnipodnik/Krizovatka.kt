package cz.jaro.dopravnipodniky.data.dopravnipodnik

import cz.jaro.dopravnipodniky.shared.KrizovatkaID
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Krizovatka")
data class Krizovatka(
    val pos: Pozice<UlicovyBlok>,
    val id: KrizovatkaID = KrizovatkaID.randomUUID(),
    val typ: TypKrizovatky,
)