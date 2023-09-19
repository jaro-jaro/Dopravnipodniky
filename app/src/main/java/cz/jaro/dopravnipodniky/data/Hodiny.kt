package cz.jaro.dopravnipodniky.data

import cz.jaro.dopravnipodniky.shared.jednotky.Tik
import cz.jaro.dopravnipodniky.shared.jednotky.tiku
import cz.jaro.dopravnipodniky.shared.jednotky.toTiky
import cz.jaro.dopravnipodniky.shared.millisPerTik
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
import org.koin.core.annotation.Single
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@Single
class Hodiny {

    private val cas = flow {
        while (currentCoroutineContext().isActive) {
            emit(System.currentTimeMillis())
            delay(0)
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

    private val casy: MutableList<Long> = mutableListOf()
    private val listeners: MutableList<Pair<Tik, suspend CoroutineScope.(diff: Duration) -> Unit>> = mutableListOf()

    fun registerListener(
        every: Duration,
        listener: suspend CoroutineScope.(diff: Duration) -> Unit,
    ) {
        casy += System.currentTimeMillis()
        listeners += every.toTiky() to listener
    }

    fun registerListener(
        every: Tik,
        listener: suspend CoroutineScope.(diff: Duration) -> Unit,
    ) {
        casy += System.currentTimeMillis()
        listeners += every to listener
    }

    init {
        scope.launch(Dispatchers.IO) {
            cas.collect { tik ->
                val l = listeners.toList()
                l
                    .mapIndexed { i, (every, listener) -> Triple(i, every, listener) }
                    .filter { (i, every, _) ->
                        tik % every == 0.tiku
                    }
                    .forEach { (i, _, listener) ->
                        launch(Dispatchers.IO) {
                            val last = casy[i]
                            val new = System.currentTimeMillis()
                            val diff = (new - last).milliseconds
//                            println(listOf(last, diff, new))
                            casy[i] = new
                            listener(diff * zrychlovacHry.toDouble())
                        }
                    }
            }
        }
    }
}