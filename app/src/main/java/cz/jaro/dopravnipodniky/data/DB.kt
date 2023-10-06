package cz.jaro.dopravnipodniky.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Barak
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Bus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.LinkaUliceCrossRef
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.serializers.Converters

@Database(
    entities = [Barak::class, Ulice::class, Linka::class, Bus::class, LinkaUliceCrossRef::class, DopravniPodnik::class, Vse::class],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class DB : RoomDatabase() {
    abstract fun dao(): Dao
}