package cz.jaro.dopravnipodniky.data

import cz.jaro.dopravnipodniky.shared.jednotky.Tik
import cz.jaro.dopravnipodniky.shared.jednotky.tiku
import cz.jaro.dopravnipodniky.shared.jednotky.toTiky
import cz.jaro.dopravnipodniky.shared.millisPerTik
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
import org.koin.core.annotation.Single
import kotlin.time.Duration

@Single
class Hodiny {

    private val cas = flow {
        while (currentCoroutineContext().isActive) {
            emit(System.currentTimeMillis())
            delay(10)
        }
    }
        .flowOn(Dispatchers.IO)
        .distinctUntilChanged()
        .map { millis ->
            millis / millisPerTik
        }
        .distinctUntilChanged()
        .map { tik ->
            tik.tiku
        }
        .flowOn(Dispatchers.IO)

    private val scope = CoroutineScope(Dispatchers.IO)

    private val listeners: MutableList<Pair<Tik, suspend CoroutineScope.(tik: Tik) -> Unit>> = mutableListOf()

    fun registerListener(
        every: Duration,
        listener: suspend CoroutineScope.(tik: Tik) -> Unit,
    ) {
        listeners += every.toTiky() to listener
    }

    fun registerListener(
        every: Tik,
        listener: suspend CoroutineScope.(tik: Tik) -> Unit,
    ) {
        listeners += every to listener
    }

    init {
        scope.launch(Dispatchers.IO) {
            cas.collect { tik ->
                listeners.forEach { (every, listener) ->
                    launch(Dispatchers.IO) {
                        if (tik % every == 0.tiku) listener(tik)
                    }
                }
            }
        }
    }
}