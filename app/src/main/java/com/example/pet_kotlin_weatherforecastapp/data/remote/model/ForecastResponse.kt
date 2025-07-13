package com.example.pet_kotlin_weatherforecastapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("list") val list: List<ForecastItem>
)

data class ForecastItem(
    @SerializedName("dt") val timestamp: Long,
    @SerializedName("dt_txt") val dateText: String, //
    @SerializedName("main") val main: ForecastMain,
    @SerializedName("pop") val pop: Double,
    @SerializedName("weather") val weather: List<WeatherItem>
)

data class ForecastMain(
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double
)
