package cz.jaro.dopravnipodniky.migrations

import androidx.datastore.core.DataMigration
import androidx.datastore.preferences.core.Preferences
import cz.jaro.dopravnipodniky.data.PreferencesDataSource
import org.json.JSONObject

object BarakyNemajiBarvuMigration : DataMigration<Preferences> {
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
            val ulicove = dp.getJSONArray("ulice")

            repeat(ulicove.length()) { j ->
                val ulice = ulicove.getJSONObject(j)
                val baraky = ulice.getJSONArray("baraky")

                repeat(baraky.length()) { k ->
                    val barak = baraky.getJSONObject(k)

                    barak.remove("barvicka")
                }
            }
        }

        data[PreferencesDataSource.PreferenceKeys.VSE] = vse.toString()

        return data.toPreferences()
    }

    override suspend fun shouldMigrate(currentData: Preferences): Boolean {
        val data = currentData[PreferencesDataSource.PreferenceKeys.VSE] ?: return false
        val vse = JSONObject(data.replace("Infinity", "${Double.MAX_VALUE}"))

        val podniky = vse.optJSONArray("podniky") ?: return false

        repeat(podniky.length()) { i ->
            val dp = podniky.optJSONObject(i) ?: return false
            val ulicove = dp.optJSONArray("ulice") ?: return false

            repeat(ulicove.length()) { j ->
                val ulice = ulicove.optJSONObject(j) ?: return false
                val baraky = ulice.optJSONArray("baraky") ?: return false

                repeat(baraky.length()) { k ->
                    val barak = baraky.optJSONObject(k) ?: return false

                    val maBarvu = barak.has("barvicka")
                    return maBarvu
                }
            }
        }

        return false
    }
}
