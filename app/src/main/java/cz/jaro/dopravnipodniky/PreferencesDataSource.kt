package cz.jaro.dopravnipodniky

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import cz.jaro.dopravnipodniky.classes.DopravniPodnik
import cz.jaro.dopravnipodniky.other.Generator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single

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

    // POZOR!!! MÁME DVA ZDROJE PARVDY!!! NA _dp SE **NESMÍ** SAHAT ZVENČÍ!!!!

    val vse = dataStore.data.map { preferences ->
        (preferences[PreferenceKeys.VSE]?.let { Json.decodeFromString<Vse>(it) } ?: DefaultValues.VSE).also {
            _dp.value = it.podniky[it.indexAktualnihoDp]
        }
    }//.stateIn(scope, SharingStarted.WhileSubscribed(5.seconds), DefaultValues.VSE)

    suspend fun zmenitVse(update: (Vse) -> Vse) {
        dataStore.edit { preferences ->
            val lastValue = preferences[PreferenceKeys.VSE]?.let { Json.decodeFromString(it) } ?: DefaultValues.VSE
            preferences[PreferenceKeys.VSE] = Json.encodeToString(update(lastValue))
        }
    }

    private val _dp = MutableStateFlow(null as DopravniPodnik?)

    val dp = _dp.filterNotNull()

    /**
     * Má stejný efekt jako
     * ```
     * zmenitVse {
     *     val podniky = it.podniky.toMutableList()
     *     podniky[it.indexAktualnihoDp] = update(podniky[it.indexAktualnihoDp])
     *     it.copy(
     *         podniky = podniky
     *     )
     * }
     * ```
     * ale je mnohem efektivnější. Prosím, prefertujte použití této metody.
     */
    suspend fun zmenitDp(update: (DopravniPodnik) -> DopravniPodnik) {
        _dp.update { update(it ?: return) }
        dataStore.edit { preferences ->
            val lastValue = preferences[PreferenceKeys.VSE]?.let { Json.decodeFromString(it) } ?: DefaultValues.VSE
            val podniky = lastValue.podniky.toMutableList()
            podniky[lastValue.indexAktualnihoDp] = update(podniky[lastValue.indexAktualnihoDp])
            preferences[PreferenceKeys.VSE] = Json.encodeToString(
                lastValue.copy(
                    podniky = podniky
                )
            )
        }
    }

    init {
        scope.launch {
            zmenitVse {
                it
            }
        }
    }
}