package com.example.vascs.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val Maroon700 = Color(0xFF520027)
val Maroon500 = Color(0xFF7A003C)
val Maroon300 = Color(0xFFB53C6E)
val GoldAccent = Color(0xFFD4AF37)
val SoftBackground = Color(0xFFF8F9FC)
val CardBorder = Color(0xFFE2E8F0)

private val LightColorScheme = lightColorScheme(
    primary = Maroon500,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFBE8F0),
    onPrimaryContainer = Maroon700,
    secondary = GoldAccent,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFFFF8E7),
    onSecondaryContainer = Color(0xFF5C4900),
    background = SoftBackground,
    onBackground = Color(0xFF1E293B),
    surface = Color.White,
    onSurface = Color(0xFF0F172A),
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = Color(0xFF475569),
    outline = CardBorder
)

private val DarkColorScheme = darkColorScheme(
    primary = Maroon300,
    onPrimary = Color.White,
    primaryContainer = Maroon700,
    onPrimaryContainer = Color(0xFFFBE8F0),
    secondary = GoldAccent,
    onSecondary = Color.Black,
    background = Color(0xFF0F172A),
    onBackground = Color(0xFFF8FAFC),
    surface = Color(0xFF1E293B),
    onSurface = Color(0xFFF8FAFC),
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color(0xFFCBD5E1)
)

@Composable
fun VASCSTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
