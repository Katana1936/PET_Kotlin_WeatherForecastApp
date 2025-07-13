package com.example.pet_kotlin_weatherforecastapp.presentation.utils

import com.example.pet_kotlin_weatherforecastapp.R

fun mapWeatherIconToDrawable(iconCode: String): Int {
    return when {
        iconCode.contains("01") -> R.drawable.ic_sun           // ясно
        iconCode.contains("02") || iconCode.contains("03") || iconCode.contains("04") -> R.drawable.ic_cloudy // облачно
        iconCode.contains("09") || iconCode.contains("10") -> R.drawable.ic_rain     // дождь
        iconCode.contains("11") -> R.drawable.ic_thunder       // гроза
        iconCode.contains("13") -> R.drawable.ic_snow          // снег
        iconCode.contains("50") -> R.drawable.ic_wind          // туман / ветер
        else -> R.drawable.ic_sun
    }
}
