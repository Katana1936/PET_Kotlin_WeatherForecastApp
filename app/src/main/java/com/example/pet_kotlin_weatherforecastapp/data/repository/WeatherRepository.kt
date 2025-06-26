package com.example.pet_kotlin_weatherforecastapp.data.repository

import com.example.pet_kotlin_weatherforecastapp.data.remote.ApiService
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.ForecastResponse
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.WeatherResponse
import retrofit2.Response
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getCurrentWeather(city: String, apiKey: String): Response<WeatherResponse> {
        return apiService.getWeatherByCity(city, apiKey)
    }

    suspend fun get7DayForecast(
        lat: Double,
        lon: Double,
        apiKey: String
    ): Response<ForecastResponse> {
        return apiService.get7DayForecast(lat, lon, apiKey = apiKey)
    }
}
