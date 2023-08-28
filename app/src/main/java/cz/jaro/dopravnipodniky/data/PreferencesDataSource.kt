package cz.jaro.dopravnipodniky.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.shared.replaceBy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import kotlin.time.Duration.Companion.seconds

@Single
class PreferencesDataSource(
    private val dataStore: DataStore<Preferences>,
    hodiny: Hodiny,
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    object PreferenceKeys {
        val VSE = stringPreferencesKey("vse")
    }

    object DefaultValues {
        val VSE = Vse(Generator.vygenerujMiPrvniMesto())
    }

    // POZOR!!! MÁME DVA ZDROJE PARVDY!!! NA _dp SE **NESMÍ** SAHAT ZVENČÍ!!!!

//    val vse = dataStore.data.map { preferences ->
//        (preferences[PreferenceKeys.VSE]?.let { Json.decodeFromString<Vse>(it) } ?: DefaultValues.VSE).also {
//            _dp.value = it.podniky[it.indexAktualnihoDp]
//        }
//    }//.stateIn(scope, SharingStarted.WhileSubscribed(5.seconds), DefaultValues.VSE)

    suspend fun zmenitVse(update: (Vse) -> Vse) = withContext(Dispatchers.IO) {
        _vse.update { update(it ?: return@withContext) }
    }

    private val _vse = MutableStateFlow(null as Vse?)

    val vse = _vse.filterNotNull()

    val dp = vse.map { it.aktualniDp }

    init {
        scope.launch {
            val vse = dataStore.data.first()[PreferenceKeys.VSE]?.let { Json.decodeFromString(it) } ?: DefaultValues.VSE
            _vse.value = vse
        }
    }

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
    suspend fun zmenitDp(update: (DopravniPodnik) -> DopravniPodnik) = withContext(Dispatchers.IO) {
        _vse.update { vse ->
            vse?.copy(
                podniky = vse.podniky.replaceBy(update(vse.aktualniDp)) { it.id },
                indexAktualnihoDp = 0
            )
        }
    }

    init {
        hodiny.registerListener(2.seconds) {
            dataStore.edit { preferences ->
                preferences[PreferenceKeys.VSE] = Json.encodeToString(_vse.value)
            }
        }
    }
}