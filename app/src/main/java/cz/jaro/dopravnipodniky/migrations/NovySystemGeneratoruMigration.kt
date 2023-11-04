package cz.jaro.dopravnipodniky.migrations

import androidx.datastore.core.DataMigration
import androidx.datastore.preferences.core.Preferences
import cz.jaro.dopravnipodniky.data.PreferencesDataSource
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DetailGenerace
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DetailGeneraceV1
import cz.jaro.dopravnipodniky.shared.jednotky.penez
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONObject

object NovySystemGeneratoruMigration : DataMigration<Preferences> {
    override suspend fun cleanUp() {}

    override suspend fun migrate(currentData: Preferences): Preferences {
        val data = currentData.toMutablePreferences()

        println(currentData[PreferencesDataSource.PreferenceKeys.VSE]!!)

        val vse = JSONObject(currentData[PreferencesDataSource.PreferenceKeys.VSE]!!.replace("Infinity", "${Double.MAX_VALUE}"))

        val podniky = vse.getJSONArray("podniky")

        val detailGenerace = DetailGeneraceV1(
            investice = 0.penez,
            nazevMestaSeed = 0,
            michaniSeed = 0,
            sanceSeed = 0,
            barakySeed = 0,
            panelakySeed = 0,
            stredovySeed = 0,
            kapacitaSeed = 0
        )

        val detailGeneraceJson = JSONObject(Json.encodeToString<DetailGenerace>(detailGenerace))

        repeat(podniky.length()) { i ->
            vse.getJSONArray("podniky").getJSONObject(i).getJSONObject("info").put("detailGenerace", detailGeneraceJson)
        }

        data[PreferencesDataSource.PreferenceKeys.VSE] = vse.toString()

        return data.toPreferences()
    }

    override suspend fun shouldMigrate(currentData: Preferences): Boolean {
        val data = currentData[PreferencesDataSource.PreferenceKeys.VSE] ?: return false
        val vse = JSONObject(data.replace("Infinity", "${Double.MAX_VALUE}"))
        val podniky = vse.getJSONArray("podniky") ?: return false
        val dp = podniky.getJSONObject(0) ?: return false
        val info = dp.getJSONObject("info") ?: return false
        val maDetailGenerace = info.has("detailGenerace")
        return !maDetailGenerace
    }
}
