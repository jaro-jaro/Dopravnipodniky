package cz.jaro.dopravnipodniky.data.dopravnipodnik

import androidx.room.Entity
import cz.jaro.dopravnipodniky.shared.LinkaID
import cz.jaro.dopravnipodniky.shared.UliceID

@Entity(primaryKeys = ["linkaID", "uliceId"])
data class LinkaUliceCrossRef(
    val linkaID: LinkaID,
    val uliceId: UliceID
)