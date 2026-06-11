package cz.jaro.dopravnipodniky.shared.helpers

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import cz.jaro.dopravnipodniky.ui.theme.AppTheme
import cz.jaro.dopravnipodniky.ui.theme.LocalTheme
import cz.jaro.dopravnipodniky.ui.theme.Theme


@ExperimentalMaterial3Api
@Composable
fun IconWithTooltip(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tooltipText: String? = contentDescription,
    tint: Color = LocalContentColor.current,
) = if (tooltipText != null) TooltipBox(
    tooltip = {
        AppTheme(
            useDynamicColor = false,
            theme = LocalTheme.current ?: Theme.Default,
        ) { PlainTooltip { Text(text = tooltipText) } }
    },
    state = rememberTooltipState(),
    positionProvider = TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Above),
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint
    )
}
else
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint
    )
