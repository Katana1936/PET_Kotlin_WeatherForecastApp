package com.example.pet_kotlin_weatherforecastapp.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

@Composable
fun WeatherIcon(
    iconCode: String,
    modifier: Modifier = Modifier
) {
    val url = "https://openweathermap.org/img/wn/${iconCode}@2x.png"
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = modifier
    )
}
