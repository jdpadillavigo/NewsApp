package com.jdpadillavigo.newsapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    secondary = Secondary,
    tertiary = Tertiary,
    surface = Surface,
    surfaceVariant = SurfaceVariant,
    background = Background,
    onSurface = OnSurface,
    onSurfaceVariant = OnSurfaceVariant
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    secondary = SecondaryDark,
    tertiary = TertiaryDark,
    surface = SurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    background = BackgroundDark,
    onSurface = OnSurfaceDark,
    onSurfaceVariant = OnSurfaceVariantDark
)

@Composable
fun NewsAppTheme(
    content: @Composable () -> Unit
) {
    val theme = if(isSystemInDarkTheme()) {
        DarkColorScheme
    } else {
        LightColorScheme
    }
    MaterialTheme(
        colorScheme = theme,
        typography = Typography,
        content = content
    )
}