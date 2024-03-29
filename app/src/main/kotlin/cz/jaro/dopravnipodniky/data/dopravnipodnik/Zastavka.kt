package cz.jaro.dopravnipodniky.data.dopravnipodnik

import cz.jaro.dopravnipodniky.shared.nasobitelKapacityZastavky
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.roundToInt

@JvmInline
@Serializable
@SerialName("Zastavka")
value class Zastavka(
    val cloveci: Int = 0
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

fun Ulice.kapacitaZastavky() = (kapacita * nasobitelKapacityZastavky).roundToInt()