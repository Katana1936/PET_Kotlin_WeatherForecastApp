package com.example.pet_kotlin_weatherforecastapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.pet_kotlin_weatherforecastapp.presentation.utils.mapWeatherIconToDrawable

@Composable
fun WeatherIcon(
    iconCode: String,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = mapWeatherIconToDrawable(iconCode)),
        contentDescription = null,
        modifier = modifier
    )
}

