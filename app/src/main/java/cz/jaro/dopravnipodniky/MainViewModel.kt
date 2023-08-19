package cz.jaro.dopravnipodniky

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.jaro.dopravnipodniky.classes.DopravniPodnik
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import kotlin.time.Duration.Companion.seconds

@KoinViewModel
class MainViewModel(
    private val preferencesDataSource: PreferencesDataSource,
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
}