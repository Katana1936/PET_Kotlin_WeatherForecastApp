package com.example.pet_kotlin_weatherforecastapp.data.repository

import com.example.pet_kotlin_weatherforecastapp.data.remote.ApiService
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.WeatherResponse
import retrofit2.Response
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun currentWeatherByCity(city: String, apiKey: String) =
        api.getCurrentWeatherByCity(city, apiKey)

    suspend fun currentWeatherByCoords(lat: Double, lon: Double, apiKey: String) =
        api.getCurrentWeatherByCoords(lat = lat, lon = lon, apiKey = apiKey)

    suspend fun forecast(lat: Double, lon: Double, apiKey: String) =
        api.getForecast(lat = lat, lon = lon, apiKey = apiKey)
}


