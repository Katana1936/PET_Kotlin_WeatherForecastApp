package com.example.pet_kotlin_weatherforecastapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Blue,             // Основной акцент (например, активная погода)
    onPrimary = White,          // Текст на голубом фоне
    background = White,         // Основной фон
    onBackground = Black,       // Текст на фоне
    surface = White,            // Карточки, виджеты
    onSurface = Black,          // Текст на карточках
    secondary = Gray            // Иконки/второстепенный текст
)

private val DarkColorScheme = darkColorScheme(
    primary = Blue,
    onPrimary = Black,
    background = Black,
    onBackground = White,
    surface = Gray,
    onSurface = White,
    secondary = Blue
)

@Composable
fun PET_Kotlin_WeatherForecastAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
