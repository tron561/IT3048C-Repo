package com.newsnow.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Orange,
    primaryVariant = Black,
    secondary = LightGreyishBlue
)

private val LightColorPalette = lightColors(
    primary = Orange,
    primaryVariant = Black,
    secondary = LightGreyishBlue,
    background = LightGreyishBlue,
    surface = Black
)

@Composable
fun NewsNowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}