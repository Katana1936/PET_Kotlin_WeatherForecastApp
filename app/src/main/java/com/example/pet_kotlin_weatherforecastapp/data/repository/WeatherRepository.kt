package com.example.pet_kotlin_weatherforecastapp.data.repository

import com.example.pet_kotlin_weatherforecastapp.data.remote.ApiService
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun currentWeather(city: String, apiKey: String) =
        api.getCurrentWeather(city, apiKey)

    suspend fun forecast(lat: Double, lon: Double, apiKey: String) =
        api.getForecast(lat = lat, lon = lon, apiKey = apiKey)
}

