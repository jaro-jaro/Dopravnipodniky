package cz.jaro.better_dialog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class AlertDialogManager {
    internal var dialogs: List<AlertDialogState<*, *>> by mutableStateOf(emptyList())
        private set

    fun <D, S : AlertDialogStyle<D>> createState(style: S, state: D) =
        object : AlertDialogState<D, S> {
            override fun hide() = if (isShown) dialogs -= this else Unit
            override fun show() = if (!isShown) dialogs += this else Unit
            override val isShown: Boolean get() = this in dialogs
            override var style: S by mutableStateOf(style)
            override var customState: D by mutableStateOf(state)
            override fun toString() =
                "AlertDialogState(style=${this.style}, customState=$customState, isShown=$isShown)"
        }

    companion object {
        private var managerField: AlertDialogManager? by mutableStateOf(null)
        val Global
            get() = managerField ?: AlertDialogManager().also { managerField = it }
    }
}