package cz.jaro.dopravnipodniky.data

import androidx.lifecycle.Lifecycle
import cz.jaro.dopravnipodniky.lifecycleState
import cz.jaro.dopravnipodniky.shared.TPS
import cz.jaro.dopravnipodniky.shared.jednotky.Tik
import cz.jaro.dopravnipodniky.shared.jednotky.tiku
import cz.jaro.dopravnipodniky.shared.jednotky.toTiky
import cz.jaro.dopravnipodniky.shared.zrychlovacHry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class Hodiny {

    private val cas = flow {
        val start = System.currentTimeMillis()
        while (currentCoroutineContext().isActive) {
            emit(System.currentTimeMillis() - start)
            delay(0.seconds)
        }
    }
        .flowOn(Dispatchers.IO)
        .distinctUntilChanged()
        .map { millisFromStart ->
            val seconds = millisFromStart / 1_000.0
            val ticks = seconds * TPS
            ticks.toLong()
        }
        .distinctUntilChanged()
        .map { ticks ->
            ticks.tiku
        }
        .flowOn(Dispatchers.IO)

    private val scope = CoroutineScope(Dispatchers.IO)

    private val tickTimes: MutableList<Instant> = mutableListOf()
    private val tickListeners: MutableList<Pair<Tik, suspend CoroutineScope.(diff: Duration) -> Unit>> = mutableListOf()
    private val durationTimes: MutableList<Instant> = mutableListOf()
    private val durationListeners: MutableList<Pair<Duration, suspend CoroutineScope.(diff: Duration) -> Unit>> = mutableListOf()

    fun registerListener(
        every: Duration,
        listener: suspend CoroutineScope.(dt: Duration) -> Unit,
    ) {
        durationTimes += Clock.System.now()
        durationListeners += every to listener
    }

    fun registerListener(
        every: Tik,
        listener: suspend CoroutineScope.(dt: Duration) -> Unit,
    ) {
        tickTimes += Clock.System.now()
        tickListeners += every to listener
    }

    init {
        scope.launch(Dispatchers.IO) {
            cas.collect { tik ->
                if (lifecycleState?.value?.isAtLeast(Lifecycle.State.RESUMED)?.not() == true) return@collect
                val l1 = tickListeners.toList()
                l1
                    .mapIndexed { i, [every, listener] -> Triple(i, every, listener) }
                    .filter {  [i, every, _] ->
                        tik % every == 0.tiku
                    }
                    .forEach { [i, _, listener] ->
                        launch(Dispatchers.IO) {
                            val last = tickTimes[i]
                            val new = Clock.System.now()
                            val dt = new - last
//                            println(listOf(last, diff, new))
                            tickTimes[i] = new
                            listener(dt * zrychlovacHry.toDouble())
                        }
                    }
                val l2 = durationListeners.toList()
                l2
                    .mapIndexed { i, [every, listener] -> Triple(i, every, listener) }
                    .filter { [i, every, _] ->
                        tik % every.toTiky() == 0.tiku
                    }
                    .forEach { [i, _, listener] ->
                        launch(Dispatchers.IO) {
                            val last = durationTimes[i]
                            val new = Clock.System.now()
                            val dt = new - last
//                            println(listOf(last, diff, new))
                            durationTimes[i] = new
                            listener(dt * zrychlovacHry.toDouble())
                        }
                    }
            }
        }
    }
}