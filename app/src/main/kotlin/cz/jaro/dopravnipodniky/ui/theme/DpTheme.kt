package cz.jaro.dopravnipodniky.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun DpTheme(
    useDynamicColor: Boolean,
    theme: Theme,
    content: @Composable () -> Unit,
) {

    val colorScheme = when {
        useDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> dynamicDarkColorScheme(LocalContext.current)
        else -> theme.darkColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}
