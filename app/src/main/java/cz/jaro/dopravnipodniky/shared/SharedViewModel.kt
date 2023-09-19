package cz.jaro.dopravnipodniky.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.jaro.dopravnipodniky.data.Generator
import cz.jaro.dopravnipodniky.data.Nastaveni
import cz.jaro.dopravnipodniky.data.PreferencesDataSource
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Bus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DPInfo
import cz.jaro.dopravnipodniky.data.dopravnipodnik.DopravniPodnik
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Ulice
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlost
import cz.jaro.dopravnipodniky.data.dosahlosti.Dosahlovac
import cz.jaro.dopravnipodniky.shared.jednotky.Peniz
import cz.jaro.dopravnipodniky.ui.garaz.obchod.SkupinaFiltru
import cz.jaro.dopravnipodniky.ui.garaz.obchod.SkupinaFiltru.Companion.filtrovat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.annotation.KoinViewModel
import kotlin.reflect.KClass
import kotlin.time.Duration.Companion.seconds

private val _novePodniky = MutableStateFlow(null as Triple<DopravniPodnik, DopravniPodnik, DopravniPodnik>?)
val novePodniky = _novePodniky.asStateFlow()

@KoinViewModel
class SharedViewModel(
    private val preferencesDataSource: PreferencesDataSource,
    private val dosahlovac: Dosahlovac,
) : ViewModel() {
    val busy = preferencesDataSource.busy
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), null)
    val linky = preferencesDataSource.linky
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), null)
    val ulice = preferencesDataSource.ulice
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), null)
    val dpInfo = preferencesDataSource.dpInfo
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), null)
    val prachy = preferencesDataSource.prachy
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), null)
    val dosahlosti = preferencesDataSource.dosahlosti
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), null)
    val tutorial = preferencesDataSource.tutorial
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), null)
    val nastaveni = preferencesDataSource.nastaveni
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), null)
    val podniky = preferencesDataSource.podniky
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), null)

    fun zmenitBusy(update: MutableList<Bus>.() -> Unit) {
        viewModelScope.launch {
            preferencesDataSource.upravitBusy(update)
        }
    }

    fun zmenitLinky(update: MutableList<Linka>.() -> Unit) {
        viewModelScope.launch {
            preferencesDataSource.upravitLinky(update)
        }
    }

    fun zmenitUlice(update: MutableList<Ulice>.() -> Unit) {
        viewModelScope.launch {
            preferencesDataSource.upravitUlice(update)
        }
    }

    fun zmenitDPInfo(update: (DPInfo) -> DPInfo) {
        viewModelScope.launch {
            preferencesDataSource.upravitDPInfo(update)
        }
    }

    fun zmenitPrachy(update: (Peniz) -> Peniz) {
        viewModelScope.launch {
            preferencesDataSource.upravitPrachy(update)
        }
    }

    fun zmenitDosahlosti(update: MutableList<Dosahlost.NormalniDosahlost>.() -> Unit) {
        viewModelScope.launch {
            preferencesDataSource.upravitDosahlosti(update)
        }
    }

    fun zmenitTutorial(update: (StavTutorialu) -> StavTutorialu) {
        viewModelScope.launch {
            preferencesDataSource.upravitTutorial(update)
        }
    }

    fun zmenitNastaveni(update: (Nastaveni) -> Nastaveni) {
        viewModelScope.launch {
            preferencesDataSource.upravitNastaveni(update)
        }
    }

    suspend fun zmenitOstatniDopravnikyPodniky(update: suspend MutableList<DopravniPodnik>.() -> Unit) = withContext(Dispatchers.IO) {
        preferencesDataSource.upravitOstatniDopravniPodniky(update)
    }

    suspend fun zmenitDopravnikyPodnik(dpID: DPID) = withContext(Dispatchers.IO) {
        preferencesDataSource.zmenitDopravniPodnik(dpID)
    }

    val filtrovaneBusy =
        nastaveni.filterNotNull().combine(prachy.filterNotNull()) { nastaveni, prachy ->
            SkupinaFiltru.skupinyFiltru.fold(typyBusu.asSequence()) { filtrovaneTypy, skupina ->
                filtrovaneTypy.filtrovat(nastaveni.filtry.filter { it in skupina.filtry })
            }.filter {
                if (SkupinaFiltru.Cena.MamNaTo in nastaveni.filtry) it.cena <= prachy
                else true
            }.sortedWith(nastaveni.razeni.comparator)
        }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), emptySequence())

    val dosahni: (KClass<out Dosahlost>) -> Unit = {
        viewModelScope.launch {
            dosahlovac.dosahni(it)
        }
    }

    fun najit3NovaMesta(
        investice: Peniz,
        progress: (Float) -> Unit,
        hotovo: () -> Unit,
    ) {
        CoroutineScope(Dispatchers.IO).launch {

            zmenitPrachy {
                it - investice
            }

            _novePodniky.value = Triple(
                Generator(investice).vygenerujMiMestoAToHnedVykricnik {
                    progress(it)
                },
                Generator(investice).vygenerujMiMestoAToHnedVykricnik {
                    progress(1 + it)
                },
                Generator(investice).vygenerujMiMestoAToHnedVykricnik {
                    progress(2 + it)
                },
            )

            withContext(Dispatchers.Main) {
                hotovo()
            }
        }
    }

    fun vybratMesto(
        id: DPID,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val dp = _novePodniky.value?.toList()?.singleOrNull { it.info.id == id } ?: return@launch
            zmenitOstatniDopravnikyPodniky {
                add(dp)
            }
            zmenitDopravnikyPodnik(dp.info.id)
        }
    }

//    init {
//        println(
//            typyBusu.sortedBy { it.zrychleniOdebiraniPenez }.joinToString("\n") { it.model }
//        )
//        println(
//            typyBusu.sortedBy { it.zrychleniOdebiraniPenez }.joinToString("\n") { (it.zrychleniOdebiraniPenez.formatovat() as Text.Plain).value }
//        )
//    }
}