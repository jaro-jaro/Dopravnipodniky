package cz.jaro.dopravnipodniky.migrations

import androidx.datastore.core.DataMigration
import androidx.datastore.preferences.core.Preferences
import cz.jaro.dopravnipodniky.data.PreferencesDataSource
import org.json.JSONArray
import org.json.JSONObject

object NoveKrizovatkyMigration : DataMigration<Preferences> {
    override suspend fun cleanUp() {}

    override suspend fun migrate(currentData: Preferences): Preferences {
        val data = currentData.toMutablePreferences()

        println(currentData[PreferencesDataSource.PreferenceKeys.VSE]!!)

        val vse = JSONObject(
            currentData[PreferencesDataSource.PreferenceKeys.VSE]!!.replace(
                "Infinity",
                "${Double.MAX_VALUE}"
            )
        )

        val podniky = vse.getJSONArray("podniky")

        repeat(podniky.length()) { i ->
            val dp = podniky.getJSONObject(i)
            dp.put("krizovatky", JSONArray())
            println(dp)
            println(dp["krizovatky"])
        }

        println(podniky)

        data[PreferencesDataSource.PreferenceKeys.VSE] = vse.toString()

        return data.toPreferences()
    }

    override suspend fun shouldMigrate(currentData: Preferences): Boolean {
        val data = currentData[PreferencesDataSource.PreferenceKeys.VSE] ?: return false
        val vse = JSONObject(data.replace("Infinity", "${Double.MAX_VALUE}"))

        val podniky = vse.optJSONArray("podniky") ?: return false

        repeat(podniky.length()) { i ->
            val dp = podniky.optJSONObject(i) ?: return false
            return !dp.has("krizovatky")
        }

        return false
    }
}
