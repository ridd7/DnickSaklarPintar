// app/src/main/java/com/example/dnicksaklarpintar/ui/theme/Theme.kt
package com.example.dnicksaklarpintar.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Blue40, // Warna primer gelap (biru yang lebih terang)
    onPrimary = Color.White,
    secondary = BlueGrey40, // Warna sekunder gelap
    tertiary = LightBlue40, // Warna tersier gelap
    background = DarkBackground, // Latar belakang gelap
    onBackground = Color.White,
    surface = DarkBackground, // Permukaan gelap
    onSurface = Color.White,
    error = Color(0xFFCF6679), // Error default
    onError = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = Blue40, // Warna primer terang (biru)
    onPrimary = Color.White,
    secondary = AccentBlue, // Warna sekunder terang (biru yang berbeda)
    tertiary = LightBlue40, // Warna tersier terang
    background = LightBackground, // Latar belakang terang
    onBackground = Color.Black,
    surface = Color.White, // Permukaan terang
    onSurface = Color.Black,
    error = Color(0xFFB00020), // Error default
    onError = Color.White
)

@Composable
fun DnickSaklarPintarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Set false agar warna konsisten
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Gunakan Typography default atau sesuaikan
        content = content
    )
}