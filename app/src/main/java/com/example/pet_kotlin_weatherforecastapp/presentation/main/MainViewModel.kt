package com.example.pet_kotlin_weatherforecastapp.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pet_kotlin_weatherforecastapp.data.remote.ApiService
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.WeatherResponse
import com.example.pet_kotlin_weatherforecastapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _weather = MutableStateFlow<WeatherResponse?>(null)
    val weather: StateFlow<WeatherResponse?> = _weather

    fun fetchWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val resp = repository.getCurrentWeather(city, apiKey)
                if (resp.isSuccessful) {
                    _weather.value = resp.body()
                } else {
                    Log.e("MainViewModel", "Ошибка ${resp.code()}: ${resp.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Exception при запросе погоды", e)
            }
        }
    }
}

