package cz.jaro.dopravnipodniky.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.jaro.dopravnipodniky.shared.DPID
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz
import cz.jaro.dopravnipodniky.shared.pocatecniObnosPenez
import kotlinx.serialization.SerialName

//@Serializable
@Entity
@SerialName("Vse")
data class Vse(
    val aktualniDPID: DPID, /*= podniky.first().info.id*/
    val prachy: Peniz = pocatecniObnosPenez,
    val tutorial: StavTutorialu = StavTutorialu.Tutorialujeme.Uvod,
//    val dosahlosti: List<Dosahlost.NormalniDosahlost> = listOf(),
    @Embedded val nastaveni: Nastaveni = Nastaveni(),
) {

    @PrimaryKey
    private val id = 1

//    constructor(
//        prvniDp: DopravniPodnik
//    ) : this(
//        podniky = listOf(prvniDp)
//    )
}

//val Vse.aktualniDp get() = podniky.singleOrNull { it.info.id == aktualniDPID } ?: podniky.first()