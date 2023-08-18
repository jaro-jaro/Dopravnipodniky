package cz.jaro.dopravnipodniky.classes

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
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