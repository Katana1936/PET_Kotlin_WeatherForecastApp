package com.example.pet_kotlin_weatherforecastapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pet_kotlin_weatherforecastapp.data.remote.ApiService
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _weather = MutableStateFlow<WeatherResponse?>(null)
    val weather: StateFlow<WeatherResponse?> = _weather

    fun loadWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getWeatherByCity(city, apiKey)
                if (response.isSuccessful) {
                    _weather.value = response.body()
                }
            } catch (e: Exception) {
                // Обработка ошибок
            }
        }
    }
}
