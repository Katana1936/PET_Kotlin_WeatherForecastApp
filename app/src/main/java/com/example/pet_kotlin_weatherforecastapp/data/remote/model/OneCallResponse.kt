package com.example.pet_kotlin_weatherforecastapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class OneCallResponse(
    @SerializedName("lat")       val lat: Double,
    @SerializedName("lon")       val lon: Double,

    @SerializedName("timezone")  val timezone: String,
    @SerializedName("hourly")    val hourly: List<HourlyForecast>,
    @SerializedName("daily")     val daily: List<DailyForecast>
)

data class HourlyForecast(
    @SerializedName("dt")      val timestamp: Long,
    @SerializedName("temp")    val temp: Double,
    @SerializedName("weather") val weather: List<WeatherItem>,
    @SerializedName("pop")     val pop: Double
)

data class DailyForecast(
    @SerializedName("dt")      val timestamp: Long,
    @SerializedName("temp")    val temp: TempDaily,
    @SerializedName("weather") val weather: List<WeatherItem>,
    @SerializedName("pop")     val pop: Double
)


data class TempDaily(
    @SerializedName("min") val min: Double,
    @SerializedName("max") val max: Double
)

data class WeatherItem(
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)

