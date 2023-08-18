package cz.jaro.dopravnipodniky

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import cz.jaro.dopravnipodniky.other.Generator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import kotlin.time.Duration.Companion.seconds

@Single
class PreferencesDataSource(
    private val dataStore: DataStore<Preferences>,
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    object PreferenceKeys {
        val VSE = stringPreferencesKey("vse")
    }

    object DefaultValues {
        val VSE = Vse(Generator.vygenerujMiPrvniMesto())
    }

    val vse = dataStore.data.map { preferences ->
        preferences[PreferenceKeys.VSE]?.let { Json.decodeFromString<Vse>(it) } ?: DefaultValues.VSE
    }.stateIn(scope, SharingStarted.WhileSubscribed(5.seconds), DefaultValues.VSE)

    suspend fun zmenitVse(update: (Vse) -> Vse) {
        dataStore.edit { preferences ->
            val lastValue = preferences[PreferenceKeys.VSE]?.let { Json.decodeFromString(it) } ?: DefaultValues.VSE
            preferences[PreferenceKeys.VSE] = Json.encodeToString(update(lastValue))
        }
    }
}