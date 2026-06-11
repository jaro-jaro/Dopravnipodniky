package cz.jaro.dopravnipodniky.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
fun AppTheme(
    useDynamicColor: Boolean,
    theme: Theme,
    content: @Composable () -> Unit,
) {

    val colorScheme = when {
        useDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> dynamicDarkColorScheme(LocalContext.current)
        else -> theme.darkColorScheme
    }

    CompositionLocalProvider(
        LocalTheme provides theme,
        LocalMainThemeColor provides theme.mainColor,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content,
        )
    }
}

val LocalTheme = staticCompositionLocalOf<Theme?> { error("CompositionLocal LocalTheme not present") }
val LocalMainThemeColor = staticCompositionLocalOf<Color> { error("CompositionLocal LocalMainThemeColor not present") }

val theme
    @Composable
    @ReadOnlyComposable
    get() = LocalTheme.current

val themeColor
    @Composable
    @ReadOnlyComposable
    get() = LocalMainThemeColor.current
