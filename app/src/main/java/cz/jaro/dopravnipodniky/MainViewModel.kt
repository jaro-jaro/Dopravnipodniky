package cz.jaro.dopravnipodniky

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.jaro.dopravnipodniky.SkupinaFiltru.Companion.filtrovat
import cz.jaro.dopravnipodniky.classes.DopravniPodnik
import cz.jaro.dopravnipodniky.other.Dosahlovac
import cz.jaro.dopravnipodniky.other.typyBusu
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import kotlin.reflect.KClass
import kotlin.time.Duration.Companion.seconds

@KoinViewModel
class MainViewModel(
    private val preferencesDataSource: PreferencesDataSource,
    private val dosahlovac: Dosahlovac,
) : ViewModel() {
    val dp = preferencesDataSource.dp
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), null)
    val vse = preferencesDataSource.vse
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), null)

    fun zmenitDp(update: (DopravniPodnik) -> DopravniPodnik) {
        viewModelScope.launch {
            preferencesDataSource.zmenitDp(update)
        }
    }

    fun zmenitVse(update: (Vse) -> Vse) {
        viewModelScope.launch {
            preferencesDataSource.zmenitVse(update)
        }
    }

    val filtrovaneBusy = vse.filterNotNull().map { vse ->
        var filtrovaneTypy = typyBusu.asSequence()
        SkupinaFiltru.skupinyFiltru.forEach { skupina ->
            filtrovaneTypy = filtrovaneTypy.filtrovat(vse.filtry.filter { it in skupina.filtry })
        }
        if (SkupinaFiltru.Cena.MamNaTo in vse.filtry) filtrovaneTypy = filtrovaneTypy.filter {
            it.cena <= vse.prachy
        }
        filtrovaneTypy.sortedWith(vse.razeni.comparator)
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), emptySequence())

    val dosahni: suspend (KClass<out Dosahlost>) -> Unit = dosahlovac::dosahni
}