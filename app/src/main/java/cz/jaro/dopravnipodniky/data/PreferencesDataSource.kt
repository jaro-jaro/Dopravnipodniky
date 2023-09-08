package cz.jaro.dopravnipodniky.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Bus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DPInfo
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz
import cz.jaro.dopravnipodniky.shared.mutate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
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

    val puvodniVse = flow { emit(Vse(Generator.vygenerujMiPrvniMesto())) }

    private val _busy = MutableStateFlow(null as List<Bus>?)
    val busy = _busy.filterNotNull()
    private val _linky = MutableStateFlow(null as List<Linka>?)
    val linky = _linky.filterNotNull()
    private val _ulice = MutableStateFlow(null as List<Ulice>?)
    val ulice = _ulice.filterNotNull()
    private val _dpInfo = MutableStateFlow(null as DPInfo?)
    val dpInfo = _dpInfo.filterNotNull()
    private val _prachy = MutableStateFlow(null as Peniz?)
    val prachy = _prachy.filterNotNull()
    private val _dosahlosti = MutableStateFlow(null as List<Dosahlost.NormalniDosahlost>?)
    val dosahlosti = _dosahlosti.filterNotNull()
    private val _tutorial = MutableStateFlow(null as StavTutorialu?)
    val tutorial = _tutorial.filterNotNull()
    private val _nastaveni = MutableStateFlow(null as Nastaveni?)
    val nastaveni = _nastaveni.filterNotNull()

    val dp = combine(
        busy,
        linky,
        ulice,
        dpInfo,
    ) {
            busy: List<Bus>,
            linky: List<Linka>,
            ulice: List<Ulice>,
            dpInfo: DPInfo,
        ->
        DopravniPodnik(
            linky = linky,
            busy = busy,
            ulice = ulice,
            info = dpInfo,
        )
    }

    val vse = combine(
        dp,
        prachy,
        dosahlosti,
        tutorial,
        nastaveni,
    ) {
            dp: DopravniPodnik,
            prachy: Peniz,
            dosahlosti: List<Dosahlost.NormalniDosahlost>,
            tutorial: StavTutorialu,
            nastaveni: Nastaveni,
        ->
        Vse(
            podniky = listOf(dp),
            indexAktualnihoDp = index,
            prachy = prachy,
            tutorial = tutorial,
            dosahlosti = dosahlosti,
            nastaveni = nastaveni,
        )
    }

    private var index: Int = 0

    private val json = Json {
        allowSpecialFloatingPointValues = true
    }

    init {
        scope.launch(Dispatchers.IO) {
            val vse = dataStore.data.first()[PreferenceKeys.VSE]?.let { json.decodeFromString<Vse?>(it) } ?: puvodniVse.first()

            index = vse.indexAktualnihoDp
            _prachy.value = vse.prachy
            _dosahlosti.value = vse.dosahlosti
            _tutorial.value = vse.tutorial
            _nastaveni.value = vse.nastaveni
            val dp = vse.aktualniDp
            _busy.value = dp.busy
            _linky.value = dp.linky
            _ulice.value = dp.ulice
            _dpInfo.value = dp.info
        }
    }

    init {
        hodiny.registerListener(2.seconds) {
            dataStore.edit { preferences ->
                preferences[PreferenceKeys.VSE] = json.encodeToString(
                    Vse(
                        podniky = listOf(
                            DopravniPodnik(
                                linky = _linky.value ?: return@edit,
                                busy = _busy.value ?: return@edit,
                                ulice = _ulice.value ?: return@edit,
                                info = _dpInfo.value ?: return@edit,
                            )
                        ),
                        indexAktualnihoDp = index,
                        prachy = _prachy.value ?: return@edit,
                        tutorial = _tutorial.value ?: return@edit,
                        dosahlosti = _dosahlosti.value ?: return@edit,
                        nastaveni = _nastaveni.value ?: return@edit,
                    )
                )
            }
        }
    }

    suspend fun upravitBusy(update: suspend MutableList<Bus>.() -> Unit) = withContext(Dispatchers.IO) {
        _busy.value = (_busy.value ?: return@withContext).mutate(update)
    }

    suspend fun upravitLinky(update: suspend MutableList<Linka>.() -> Unit) = withContext(Dispatchers.IO) {
        _linky.value = (_linky.value ?: return@withContext).mutate(update)
    }

    suspend fun upravitUlice(update: suspend MutableList<Ulice>.() -> Unit) = withContext(Dispatchers.IO) {
        _ulice.value = (_ulice.value ?: return@withContext).mutate(update)
    }

    suspend fun upravitDPInfo(update: suspend (DPInfo) -> DPInfo) = withContext(Dispatchers.IO) {
        _dpInfo.value = update(_dpInfo.value ?: return@withContext)
    }

    suspend fun upravitPrachy(update: suspend (Peniz) -> Peniz) = withContext(Dispatchers.IO) {
        _prachy.value = update(_prachy.value ?: return@withContext)
    }

    suspend fun upravitDosahlosti(update: suspend MutableList<Dosahlost.NormalniDosahlost>.() -> Unit) = withContext(Dispatchers.IO) {
        _dosahlosti.value = (_dosahlosti.value ?: return@withContext).mutate(update)
    }

    suspend fun upravitTutorial(update: suspend (StavTutorialu) -> StavTutorialu) = withContext(Dispatchers.IO) {
        _tutorial.value = update(_tutorial.value ?: return@withContext)
    }

    suspend fun upravitNastaveni(update: suspend (Nastaveni) -> Nastaveni) = withContext(Dispatchers.IO) {
        _nastaveni.value = update(_nastaveni.value ?: return@withContext)
    }
}