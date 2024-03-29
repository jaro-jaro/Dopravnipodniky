package cz.jaro.dopravnipodniky.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Bus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DPInfo
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Krizovatka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.data.generace.Generator
import cz.jaro.dopravnipodniky.shared.DPID
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.Text
import cz.jaro.dopravnipodniky.shared.formatovat
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz
import cz.jaro.dopravnipodniky.shared.mutate
import cz.jaro.dopravnipodniky.zobrazitLoading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import org.koin.core.time.measureDuration
import kotlin.time.Duration.Companion.seconds

@Single
class PreferencesDataSource(
    private val dataStore: DataStore<Preferences>,
    hodiny: Hodiny,
) {
    var LOCK = false

    private val scope = CoroutineScope(Dispatchers.IO)

    object PreferenceKeys {
        val VSE = stringPreferencesKey("vse")
    }

    private val puvodniVse = flow { emit(Vse(Generator.vygenerujMiPrvniMesto())) }

    private val _ostatniPodniky = MutableStateFlow(null as List<DopravniPodnik>?)
    private val ostatniPodniky = _ostatniPodniky.filterNotNull()

    private val _busy = MutableStateFlow(null as List<Bus>?)
    val busy = _busy.filterNotNull()
    private val _linky = MutableStateFlow(null as List<Linka>?)
    val linky = _linky.filterNotNull()
    private val _ulice = MutableStateFlow(null as List<Ulice>?)
    val ulice = _ulice.filterNotNull()
    private val _krizovatky = MutableStateFlow(null as List<Krizovatka>?)
    val krizovatky = _krizovatky.filterNotNull()
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
        krizovatky,
        dpInfo,
    ) {
            busy: List<Bus>,
            linky: List<Linka>,
            ulice: List<Ulice>,
            krizovatky: List<Krizovatka>,
            dpInfo: DPInfo,
        ->
        DopravniPodnik(
            linky = linky,
            busy = busy,
            ulice = ulice,
            krizovatky = krizovatky,
            info = dpInfo,
        )
    }.filter { !LOCK }

    val podniky = combine(
        dp,
        ostatniPodniky,
    ) { dp, ostatniPodniky ->
        listOf(dp) + ostatniPodniky
    }.filter { !LOCK }

    val vse = combine(
        podniky,
        prachy,
        dosahlosti,
        tutorial,
        nastaveni,
    ) {
            podniky: List<DopravniPodnik>,
            prachy: Peniz,
            dosahlosti: List<Dosahlost.NormalniDosahlost>,
            tutorial: StavTutorialu,
            nastaveni: Nastaveni,
        ->
        Vse(
            podniky = podniky,
            aktualniDPID = podniky.first().info.id,
            prachy = prachy,
            tutorial = tutorial,
            dosahlosti = dosahlosti,
            nastaveni = nastaveni,
        )
    }.filter { !LOCK }

    private val json = Json {
        allowSpecialFloatingPointValues = true
    }

    init {
        scope.launch(Dispatchers.IO) {
            val vse = dataStore.data.first()[PreferenceKeys.VSE]?.let { json.decodeFromString<Vse?>(it) } ?: puvodniVse.first()
            setup(vse)
        }
    }

    private fun setup(vse: Vse) {
        _prachy.value = vse.prachy
        _dosahlosti.value = vse.dosahlosti
        _tutorial.value = vse.tutorial
        _nastaveni.value = vse.nastaveni
        _ostatniPodniky.value = vse.podniky - vse.aktualniDp
        val dp = vse.aktualniDp
        _busy.value = dp.busy
        _linky.value = dp.linky
        _ulice.value = dp.ulice
        _krizovatky.value = dp.krizovatky
        _dpInfo.value = dp.info
    }

    init {
        hodiny.registerListener(2.seconds) {
            if (!LOCK) dataStore.edit { preferences ->
                preferences[PreferenceKeys.VSE] = json.encodeToString(
                    Vse(
                        podniky = listOf(
                            DopravniPodnik(
                                linky = _linky.value ?: return@edit,
                                busy = _busy.value ?: return@edit,
                                ulice = _ulice.value ?: return@edit,
                                krizovatky = _krizovatky.value ?: return@edit,
                                info = _dpInfo.value ?: return@edit,
                            )
                        ).plus(_ostatniPodniky.value ?: return@edit).distinctBy { it.info.id },
                        aktualniDPID = _dpInfo.value?.id ?: return@edit,
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
        if (!LOCK) _busy.value = (_busy.value ?: return@withContext).mutate(update)
    }

    suspend fun upravitLinky(update: suspend MutableList<Linka>.() -> Unit) = withContext(Dispatchers.IO) {
        if (!LOCK) _linky.value = (_linky.value ?: return@withContext).mutate(update)
    }

    suspend fun upravitUlice(update: suspend MutableList<Ulice>.() -> Unit) = withContext(Dispatchers.IO) {
        if (!LOCK) _ulice.value = (_ulice.value ?: return@withContext).mutate(update)
    }

    suspend fun upravitKrizovatky(update: suspend MutableList<Krizovatka>.() -> Unit) = withContext(Dispatchers.IO) {
        if (!LOCK) _krizovatky.value = (_krizovatky.value ?: return@withContext).mutate(update)
    }

    suspend fun upravitDPInfo(update: suspend (DPInfo) -> DPInfo) = withContext(Dispatchers.IO) {
        if (!LOCK) _dpInfo.value = update(_dpInfo.value ?: return@withContext)
    }

    suspend fun upravitPrachy(update: suspend (Peniz) -> Peniz) = withContext(Dispatchers.IO) {
        if (!LOCK) _prachy.value = update(_prachy.value ?: return@withContext)
    }

    suspend fun upravitDosahlosti(update: suspend MutableList<Dosahlost.NormalniDosahlost>.() -> Unit) = withContext(Dispatchers.IO) {
        if (!LOCK) _dosahlosti.value = (_dosahlosti.value ?: return@withContext).mutate(update)
    }

    suspend fun upravitTutorial(update: suspend (StavTutorialu) -> StavTutorialu) = withContext(Dispatchers.IO) {
        if (!LOCK) _tutorial.value = update(_tutorial.value ?: return@withContext)
    }

    suspend fun upravitNastaveni(update: suspend (Nastaveni) -> Nastaveni) = withContext(Dispatchers.IO) {
        if (!LOCK) _nastaveni.value = update(_nastaveni.value ?: return@withContext)
    }

    suspend fun upravitOstatniDopravniPodniky(update: suspend MutableList<DopravniPodnik>.() -> Unit) = withContext(Dispatchers.IO) {
        _ostatniPodniky.value = (_ostatniPodniky.value ?: emptyList()).mutate(update)
    }

    suspend fun zmenitDopravniPodnik(dpID: DPID) = withContext(Dispatchers.IO) {
        val aktualniVse = vse.first()
        LOCK = true
        zobrazitLoading = true
        delay(1.seconds)
        println("MĚNĚNÍ DOPRAVNÍHO PODNIKU!!! AKTUÁLNÍ DP: ${aktualniVse.aktualniDp.info.id}, NOVÉ DP: $dpID")
        val millis = measureDuration {
            setup(
                aktualniVse.copy(
                    aktualniDPID = dpID
                )
            )
        }
        println("MĚNĚNÍ DOPRAVNÍHO PODNIKU DOKONČENO ZA ${(millis.formatovat() as Text.Plain).value} ms")
        delay(1.seconds)
        LOCK = false
        zobrazitLoading = false
    }
}