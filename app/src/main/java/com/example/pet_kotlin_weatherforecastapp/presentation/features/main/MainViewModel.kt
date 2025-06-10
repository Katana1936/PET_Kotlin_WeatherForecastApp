package com.example.pet_kotlin_weatherforecastapp.presentation.features.main

import androidx.lifecycle.ViewModel
import com.example.pet_kotlin_weatherforecastapp.data.remote.WeatherResponse
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: StateFlow<WeatherResponse?> = _weatherData

    fun fetchMain(apiKey: String, lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getCurrentWeather(
                    lat = lat,
                    lon = lon,
                    apiKey = apiKey
                )
                _weatherData.value = response
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Ошибка: ${e.localizedMessage}")
            }
        }
    }
}