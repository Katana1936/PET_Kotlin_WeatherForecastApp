package com.example.pet_kotlin_weatherforecastapp.presentation.utils

import com.example.pet_kotlin_weatherforecastapp.R

fun mapWeatherIconToDrawable(iconCode: String): Int {
    return when (iconCode) {
        "01d" -> R.drawable.ic_sun              // ясно (день)
        "01n" -> R.drawable.ic_moon             // ясно (ночь)
        "02d", "03d", "04d" -> R.drawable.ic_cloudy // облачно с солнцем
        "02n", "03n", "04n" -> R.drawable.ic_cloudy_moon // облачно с луной
        "09d", "09n" -> R.drawable.ic_rain      // дождь
        "10d", "10n" -> R.drawable.ic_rainp     // дождь с прояснением
        "11d", "11n" -> R.drawable.ic_thunder   // гроза
        "13d", "13n" -> R.drawable.ic_snow      // снег
        "50d", "50n" -> R.drawable.ic_wind      // туман
        else -> R.drawable.ic_cloudy
    }
}

