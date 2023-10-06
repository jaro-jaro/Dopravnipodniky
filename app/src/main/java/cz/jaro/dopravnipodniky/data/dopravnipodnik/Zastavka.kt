package cz.jaro.dopravnipodniky.data.dopravnipodnik

import androidx.room.ColumnInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
@SerialName("Zastavka")
value class Zastavka(
    @ColumnInfo(name = "cloveciNaZastavce") val cloveciNaZastavce: Int = 0
)
//        init {
//            ulice.baraky.forEach {
//                while (it.cloveci > 0 && cloveci < ulice.cloveci * 3/4) {
//
//                    cloveci ++
//                    it.cloveci--
//                }
//            }
//
//            Log.i("Vytvořena zastávka", "Byla vytvořena zastávka na ulici ${ulice.id}.")
//        }

//fun Ulice.kapacitaZastavky() = (kapacita * nasobitelMaxCloveku).roundToInt()