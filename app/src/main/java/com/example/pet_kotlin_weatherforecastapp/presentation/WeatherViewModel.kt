package com.example.pet_kotlin_weatherforecastapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.ForecastResponse
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.WeatherResponse
import com.example.pet_kotlin_weatherforecastapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _weather = MutableStateFlow<WeatherResponse?>(null)
    val weather: StateFlow<WeatherResponse?> get() = _weather

    private val _forecast = MutableStateFlow<ForecastResponse?>(null)
    val forecast: StateFlow<ForecastResponse?> get() = _forecast

    fun loadWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            val response = repository.getCurrentWeather(city, apiKey)
            if (response.isSuccessful) {
                _weather.value = response.body()
            }
        }
    }

    fun loadForecast(lat: Double, lon: Double, apiKey: String) {
        viewModelScope.launch {
            val response = repository.get7DayForecast(lat, lon, apiKey)
            if (response.isSuccessful) {
                _forecast.value = response.body()
            }
        }
    }
}
