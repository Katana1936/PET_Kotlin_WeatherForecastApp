package com.example.pet_kotlin_weatherforecastapp.data.remote

import com.example.pet_kotlin_weatherforecastapp.data.remote.model.ForecastResponse
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("data/2.5/weather")
    suspend fun getWeatherByCity(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ru"
    ): Response<WeatherResponse>

    @GET("data/2.5/onecall")
    suspend fun get7DayForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String = "minutely,hourly",
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ru",
        @Query("appid") apiKey: String
    ): Response<ForecastResponse>
}
