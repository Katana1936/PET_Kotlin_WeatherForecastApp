package com.example.pet_kotlin_weatherforecastapp.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

fun mapWeatherIconToVector(iconCode: String): ImageVector {
    return when {
        iconCode.contains("01") -> Icons.Default.WbSunny
        iconCode.contains("02") -> Icons.Default.CloudQueue
        iconCode.contains("03") || iconCode.contains("04") -> Icons.Default.Cloud
        iconCode.contains("09") || iconCode.contains("10") -> Icons.Default.Umbrella
        iconCode.contains("11") -> Icons.Default.FlashOn
        iconCode.contains("13") -> Icons.Default.AcUnit
        iconCode.contains("50") -> Icons.Default.Air
        else -> Icons.Default.HelpOutline
    }
}

@Composable
fun WeatherVectorIcon(
    iconCode: String,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = mapWeatherIconToVector(iconCode),
        contentDescription = null,
        modifier = modifier
    )
}
