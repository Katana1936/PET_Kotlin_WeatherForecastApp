package com.example.pet_kotlin_weatherforecastapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ru"
    ): WeatherResponse
}

data class WeatherResponse(
    val name: String,
    val weather: List<WeatherDescription>,
    val main: Main,
    val wind: Wind,
    val clouds: Clouds,
    val rain: Rain?,
    val dt: Long,
    val sys: Sys,
    val visibility: Int
)

data class WeatherDescription(
    val main: String,
    val description: String
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int,
    val sea_level: Int?,
    val grnd_level: Int?
)

data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double?
)

data class Clouds(
    val all: Int
)

data class Rain(
    val `1h`: Double?
)

data class Sys(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

