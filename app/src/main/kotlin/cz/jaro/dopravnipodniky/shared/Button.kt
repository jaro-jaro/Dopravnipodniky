package cz.jaro.dopravnipodniky.shared

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LongPressButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onLongPress: () -> Unit = {},
    onDoubleClick: () -> Unit = {},
    content: @Composable RowScope.() -> Unit
) {
    CompositionLocalProvider(
        LocalContentColor provides MaterialTheme.colorScheme.onPrimary,
    ) {
        Box(
            modifier = modifier
                .semantics { role = Role.Button }
                .minimumInteractiveComponentSize()
                .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
                .clip(CircleShape)
                .combinedClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    enabled = true,
                    role = Role.Button,
                    onClick = onClick,
                    onDoubleClick = onDoubleClick,
                    onLongClick = onLongPress,
                ),
            propagateMinConstraints = true
        ) {
            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onPrimary) {
                ProvideTextStyle(value = MaterialTheme.typography.labelLarge) {
                    Row(
                        Modifier
                            .defaultMinSize(
                                minWidth = ButtonDefaults.MinWidth,
                                minHeight = ButtonDefaults.MinHeight
                            )
                            .padding(ButtonDefaults.ContentPadding),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        content = content
                    )
                }
            }
        }
    }
}