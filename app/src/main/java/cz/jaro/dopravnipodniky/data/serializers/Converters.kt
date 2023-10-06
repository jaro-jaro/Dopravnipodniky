package cz.jaro.dopravnipodniky.data.serializers

import androidx.room.TypeConverter
import cz.jaro.dopravnipodniky.data.dopravnipodnik.TypBusu
import cz.jaro.dopravnipodniky.shared.jednotky.Pozice
import cz.jaro.dopravnipodniky.shared.jednotky.UlicovyBlok
import cz.jaro.dopravnipodniky.ui.malovani.SerializableDp
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.time.Duration

class Converters {
    private inline fun <reified T> toSerializable(value: String): T = Json.decodeFromString(value)
    private inline fun <reified T> fromSerializable(value: T): String = Json.encodeToString(value)

    @TypeConverter
    fun toSerializableDp(value: String): SerializableDp = toSerializable(value)
    @TypeConverter
    fun fromSerializableDp(value: SerializableDp): String = fromSerializable(value)

    @TypeConverter
    fun toTypBusu(value: String): TypBusu = toSerializable(value)
    @TypeConverter
    fun fromTypBusu(value: TypBusu): String = fromSerializable(value)

    @TypeConverter
    fun toDuration(value: String): Duration = toSerializable(value)
    @TypeConverter
    fun fromDuration(value: Duration): String = fromSerializable(value)

    @TypeConverter
    fun toPoziceOfUlicovyBlok(value: String): Pozice<UlicovyBlok> = toSerializable(value)
    @TypeConverter
    fun fromPoziceOfUlicovyBlok(value: Pozice<UlicovyBlok>): String = fromSerializable(value)
}