package cz.jaro.dopravnipodniky

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

@Single
class Hodiny(

) {
    fun setup() {

    }

    fun update() {

    }

    companion object {
        private const val millisPerTik = 1000L / TPS
    }

    private val cas = flow {
        while (currentCoroutineContext().isActive) {
            emit(System.currentTimeMillis())
        }
    }
        .distinctUntilChanged()
        .filter { millis ->
            millis % millisPerTik == 0L
        }
        .map { }
        .flowOn(Dispatchers.IO)

    val scope =  CoroutineScope(Dispatchers.IO)

    init {
        setup()
        scope.launch {
            cas.collect {
                update()
            }
        }
    }
}