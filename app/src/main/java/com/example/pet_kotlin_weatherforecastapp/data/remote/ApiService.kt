package com.example.pet_kotlin_weatherforecastapp.data.remote

import com.example.pet_kotlin_weatherforecastapp.data.remote.model.ForecastResponse
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.OneCallResponse
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("q")     city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
        @Query("lang")  lang: String = "ru"
    ): Response<WeatherResponse>

    @GET("data/2.5/onecall")
    suspend fun getOneCall(
        @Query("lat")     lat: Double,
        @Query("lon")     lon: Double,
        @Query("exclude") exclude: String = "minutely,alerts",
        @Query("units")   units: String = "metric",
        @Query("lang")    lang: String = "ru",
        @Query("appid")   apiKey: String
    ): Response<OneCallResponse>

    @GET("data/2.5/forecast")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ru"
    ): Response<ForecastResponse>

}
