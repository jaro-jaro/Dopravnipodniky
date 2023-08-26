package cz.jaro.dopravnipodniky.data

import cz.jaro.dopravnipodniky.shared.jednotky.Tik
import cz.jaro.dopravnipodniky.shared.jednotky.tiku
import cz.jaro.dopravnipodniky.shared.jednotky.toTiky
import cz.jaro.dopravnipodniky.shared.millisPerTik
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single
import kotlin.time.Duration

@Single
class Hodiny {

    val cas = flow {
        while (currentCoroutineContext().isActive) {
            emit(System.currentTimeMillis())
        }
    }
        .distinctUntilChanged()
        .filter { millis ->
            millis % millisPerTik == 0L
        }
        .map { millis ->
            (millis / millisPerTik).tiku
        }
        .flowOn(Dispatchers.IO)

    private val scope = CoroutineScope(Dispatchers.IO)

    private val listeners: MutableList<Pair<Tik, suspend CoroutineScope.() -> Unit>> = mutableListOf()

    fun registerListener(
        every: Duration,
        listener: suspend CoroutineScope.() -> Unit,
    ) {
        listeners += every.toTiky() to listener
    }

    fun registerListener(
        every: Tik,
        listener: suspend CoroutineScope.() -> Unit,
    ) {
        listeners += every to listener
    }

    init {
        scope.launch {
            cas.collect { tik ->
                listeners.forEach { (every, listener) ->
                    launch {
                        if (tik % every == 0.tiku) listener()
                    }
                }
            }
        }
    }
}