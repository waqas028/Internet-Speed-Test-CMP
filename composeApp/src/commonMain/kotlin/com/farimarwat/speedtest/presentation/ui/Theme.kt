package com.farimarwat.speedtest.presentation.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color



val LightColorTheme = darkColorScheme( // Still using darkColorScheme
    primary = Color(0xFF9C6BFF),       // Lighter violet
    onPrimary = Color.Black,

    secondary = Color(0xFF7986CB),     // Soft Indigo
    onSecondary = Color.White,

    tertiary = Color(0xFF18FFFF),      // Bright Cyan
    onTertiary = Color.Black,

    background = Color(0xFF181A25),    // Dim dark (not pure black)
    onBackground = Color(0xFFECEFF1),  // Light gray-blue for text

    surface = Color(0xFF252837),       // Raised surface with bluish tint
    onSurface = Color(0xFFCFD8DC),     // Lightened text on surface

    error = Color(0xFFFF8A65),         // Warm soft red-orange
    onError = Color.Black
)




val DarkColorTheme = darkColorScheme(
    primary = Color(0xFF8A56FF),       // Vibrant Violet
    onPrimary = Color.Black,

    secondary = Color(0xFF5C6BC0),     // Indigo
    onSecondary = Color.White,

    tertiary = Color(0xFF00E5FF),      // Neon Cyan
    onTertiary = Color.Black,

    background = Color(0xFF0B0F1C),    // True deep dark
    onBackground = Color(0xFFE0E0E0),  // Light gray

    surface = Color(0xFF1A1F2B),       // Dark surface with less contrast
    onSurface = Color(0xFFB0BEC5),     // Cool gray-blue

    error = Color(0xFFFF6F61),         // Coral red
    onError = Color.Black
)




@Composable
fun getColorScheme():ColorScheme{
    return if(isSystemInDarkTheme()) DarkColorTheme else LightColorTheme
}