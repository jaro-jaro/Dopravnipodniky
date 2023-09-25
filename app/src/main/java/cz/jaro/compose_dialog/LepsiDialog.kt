package cz.jaro.compose_dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties


data class AlertDialogInfo(
    val confirmButton: @Composable () -> Unit,
    val modifier: Modifier = Modifier,
    val dismissButton: @Composable (() -> Unit)? = null,
    val onDismissed: (() -> Unit)? = null,
    val icon: @Composable (() -> Unit)? = null,
    val title: @Composable (() -> Unit)? = null,
    val text: @Composable (() -> Unit)? = null,
    val properties: DialogProperties = DialogProperties(),
)

class AlertDialogState {

    internal var infos: List<AlertDialogInfo> by mutableStateOf(emptyList())
        private set

    fun show(info: AlertDialogInfo) {
        this.infos += info
    }

    fun show(
        confirmButton: @Composable () -> Unit,
        modifier: Modifier = Modifier,
        dismissButton: @Composable (() -> Unit)? = null,
        onDismissed: (() -> Unit)? = null,
        icon: @Composable (() -> Unit)? = null,
        title: @Composable (() -> Unit)? = null,
        text: @Composable (() -> Unit)? = null,
        properties: DialogProperties = DialogProperties(),
    ) = show(
        AlertDialogInfo(
            confirmButton, modifier, dismissButton, onDismissed, icon, title, text, properties
        )
    )

    fun hideTopMost() {
        this.infos = this.infos.dropLast(1)
    }
}

@Composable
fun AlertDialog(
    state: AlertDialogState,
) {
    state.infos.forEach { info ->
        androidx.compose.material3.AlertDialog(
            onDismissRequest = {
                state.hideTopMost()
                info.onDismissed?.invoke()
            },
            confirmButton = info.confirmButton,
            modifier = info.modifier,
            dismissButton = info.dismissButton,
            icon = info.icon,
            title = info.title,
            text = info.text,
            properties = info.properties
        )
    }
}