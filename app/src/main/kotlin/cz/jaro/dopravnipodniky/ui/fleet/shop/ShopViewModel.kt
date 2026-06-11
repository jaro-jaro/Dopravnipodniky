package cz.jaro.dopravnipodniky.ui.fleet.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Bus
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Linka
import cz.jaro.dopravnipodniky.data.dopravnipodnik.Trakce
import cz.jaro.dopravnipodniky.data.dopravnipodnik.TypBusu
import cz.jaro.dopravnipodniky.data.dopravnipodnik.getStreets
import cz.jaro.dopravnipodniky.data.dopravnipodnik.jsouVsechnyZatrolejovane
import cz.jaro.dopravnipodniky.data.shop_settings.FilterGroup
import cz.jaro.dopravnipodniky.data.shop_settings.ShopFilter
import cz.jaro.dopravnipodniky.data.shop_settings.ShopSortSetting
import cz.jaro.dopravnipodniky.shared.LinkaID
import cz.jaro.dopravnipodniky.shared.SharedViewModel
import cz.jaro.dopravnipodniky.shared.StavTutorialu
import cz.jaro.dopravnipodniky.shared.helpers.combineStates
import cz.jaro.dopravnipodniky.shared.je
import cz.jaro.dopravnipodniky.shared.toggle
import cz.jaro.dopravnipodniky.shared.typyBusu
import cz.jaro.dopravnipodniky.ui.nav.Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.time.Duration.Companion.seconds

class ShopViewModel(
    sharedViewModel: SharedViewModel,
    private val navigator: Navigator,
) : ViewModel() {
    private val dp = sharedViewModel.dp
    private val vse = sharedViewModel.vse
    private val menic = sharedViewModel.menic

    init {
        menic.zmenitTutorial {
            if (it je StavTutorialu.Tutorialujeme.Garaz)
                StavTutorialu.Tutorialujeme.Obchod
            else it
        }
    }

    private val shopSettings = vse.filterNotNull().map { vse ->
        vse.nastaveni.shopSettings
    }
    private val money = vse.filterNotNull().map { vse ->
        vse.prachy
    }
    private val setFiltersByGroup = shopSettings.map { shopSettings ->
        shopSettings.filters.groupBy { it.group }
    }
    private val sortSetting = shopSettings.map { shopSettings ->
        shopSettings.sort
    }

    private val onlyFilteredBusTypes = combine(setFiltersByGroup, money) { setFiltersByGroup, money ->
        typyBusu.asSequence().filter { type ->
            setFiltersByGroup.all { [group, filters] ->
                filters.any { filter ->
                    filter.predicate(type)
                }
            }
        }.filter {
            if (
                FilterGroup.HaveEnoughMoneyGroup in setFiltersByGroup &&
                ShopFilter.HaveEnoughMoney in setFiltersByGroup[FilterGroup.HaveEnoughMoneyGroup]!!
            ) it.cena <= money else true
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), emptySequence())

    private val sortedFilteredBusTypes = combine(onlyFilteredBusTypes, sortSetting) { types, sortSetting ->
        types.sortedWith(sortSetting.comparator)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5.seconds), emptySequence())

    enum class Show {
        Filters, Sort, Buses,
    }

    private val showState = MutableStateFlow(Show.Buses)

    val state = combineStates(
        sortedFilteredBusTypes, vse, showState
    ) { sortedFilteredBusTypes, vse, showState ->
        ShopState(
            buses = sortedFilteredBusTypes.toList(),
            tutorialState = vse!!.tutorial,
            shopSettings = vse.nastaveni.shopSettings,
            money = vse.prachy,
            showState = showState,
        )
    }

    fun onEvent(e: ShopEvent) = when (e) {
        is ShopEvent.BackPressed -> when (showState.value) {
            Show.Filters -> showState.value = Show.Buses
            Show.Sort -> showState.value = Show.Buses
            Show.Buses -> navigator.pop()
        }

        is ShopEvent.ShowTutorial -> menic.zmenitTutorial {
            StavTutorialu.Tutorialujeme.Obchod
        }

        is ShopEvent.FilterButtonClicked -> showState.update {
            if (it == Show.Filters) Show.Buses else Show.Filters
        }

        is ShopEvent.SortButtonClicked -> showState.update {
            if (it == Show.Sort) Show.Buses else Show.Sort
        }

        is ShopEvent.ToggleFilter -> menic.zmenitNastaveni {
            it.copy(
                shopSettings = it.shopSettings.copy(
                    filters = it.shopSettings.filters.toggle(e.filter)
                ),
            )
        }

        is ShopEvent.SetSort -> menic.zmenitNastaveni {
            it.copy(
                shopSettings = it.shopSettings.copy(
                    sort = if (it.shopSettings.sort == e.setting) ShopSortSetting.Default else e.setting,
                ),
            )
        }

        is ShopEvent.BuyBus -> viewModelScope.launch(Dispatchers.IO) {
            e.onComplete(buyBus(e.busType, e.callbacks))
        }
    }

    private suspend fun buyBus(busType: TypBusu, callbacks: AskMoreCallbacks): BuyResult {
        suspend fun askForBusCount() = suspendCancellableCoroutine { continuation ->
            callbacks.askForBusCount(busType) { enteredText ->
                continuation.resume(
                    if (enteredText.isEmpty() || enteredText.toIntOrNull() == null || enteredText.toInt() < 1) null
                    else enteredText.toInt()
                )
            }
        }

        suspend fun askForLine() = suspendCancellableCoroutine { continuation ->
            val allLines = dp.value!!.linky
            val lines = when (busType.trakce) {
                is Trakce.Trolejbus ->
                    allLines.filter { dp.value!!.getStreets(it.ulice).jsouVsechnyZatrolejovane() }

                else -> allLines
            }

            if (lines.isEmpty()) continuation.resume(null)
            else callbacks.askForLine(
                busType = busType,
                lines = lines,
                callback = continuation::resume,
            )
        }

        suspend fun askForRegistrationNumber() = suspendCancellableCoroutine { continuation ->
            callbacks.askForRegistrationNumber(
                busType = busType,
                validate = { text -> text.toIntOrNull()?.takeIf { it >= 1 } },
                callback = continuation::resume,
            )
        }

        val settings = vse.value!!.nastaveni
        val buses = dp.value!!.busy
        val money = vse.value!!.prachy

        val busCount = if (settings.vicenasobnyKupovani) askForBusCount() else 1
        if (busCount == null) return BuyResult.CountNotValid
        if (busCount > 50) return BuyResult.TooManyBuses

        val finalPrice = busType.cena * busCount
        if (finalPrice > money) return BuyResult.NotEnoughMoney

        val firstNumber = if (settings.automatickyUdelovatEvC) askForRegistrationNumber() else 1
        val currentBusNumbers = buses.map { it.evCislo }.toSet()
        val freeNumbersSequence = generateSequence(firstNumber) { it + 1 }.filter { it !in currentBusNumbers }
        val registrationNumbers = freeNumbersSequence.take(busCount).toList()

        val putOnLine = if (settings.vicenasobnyKupovani) askForLine() else null

        val newBuses = registrationNumbers.map { number ->
            Bus(
                evCislo = number,
                typBusu = busType,
                linka = putOnLine,
            )
        }
        menic.zmenitPrachy {
            it - finalPrice
        }
        menic.zmenitBusy {
            addAll(newBuses)
        }
        return BuyResult.Success
    }

    enum class BuyResult {
        Success, NotEnoughMoney, CountNotValid, TooManyBuses,
    }

    interface AskMoreCallbacks {
        fun askForBusCount(
            busType: TypBusu,
            callback: (enteredText: String) -> Unit,
        )

        fun askForLine(
            busType: TypBusu,
            lines: List<Linka>,
            callback: (chosen: LinkaID?) -> Unit,
        )

        fun askForRegistrationNumber(
            busType: TypBusu,
            validate: (enteredText: String) -> Int?,
            callback: (number: Int) -> Unit,
        )
    }
}