package com.example.pet_kotlin_weatherforecastapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("daily") val daily: List<DailyForecast>
)

data class DailyForecast(
    @SerializedName("dt") val date: Long,
    @SerializedName("temp") val temp: Temperature,
    @SerializedName("weather") val weather: List<Weather>
)

data class Temperature(
    @SerializedName("day") val day: Double,
    @SerializedName("min") val min: Double,
    @SerializedName("max") val max: Double
)

data class Weather(
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)
