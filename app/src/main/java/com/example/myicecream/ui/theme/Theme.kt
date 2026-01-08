package com.example.myicecream.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme

import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.runtime.SideEffect


private val DarkColors = darkColorScheme(
    background = Black,
    surface = Black,
    onBackground = White,
    onSurface = White,
    primary = White
)

private val LightColors = lightColorScheme(
    primary = Color(0xFF5C4638),
    onPrimary = Color(0xFFFDFDFD),
    background = Color(0xFFFDFDFD),
    surface = Color(0xFFFDFDFD),
    onSurface = Color(0xFF5C4638)
)

@Composable
fun MyIceCreamTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.primary.toArgb()
            WindowCompat
                .getInsetsController(window, view)
                .isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )

}