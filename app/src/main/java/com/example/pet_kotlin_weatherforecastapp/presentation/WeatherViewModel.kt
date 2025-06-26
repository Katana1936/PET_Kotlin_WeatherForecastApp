package com.example.pet_kotlin_weatherforecastapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.OneCallResponse
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.WeatherResponse
import com.example.pet_kotlin_weatherforecastapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repo: WeatherRepository
) : ViewModel() {

    val weather  = MutableStateFlow<WeatherResponse?>(null)
    val oneCall  = MutableStateFlow<OneCallResponse?>(null)

    fun loadAll(city: String, apiKey: String) = viewModelScope.launch {
        // 1) Текущее
        repo.getCurrentWeather(city, apiKey).let { resp ->
            if (resp.isSuccessful) weather.value = resp.body()
        }
        // 2) Почасовой+7-дневный
        weather.value?.let { w ->
            repo.getOneCall(w.coord.lat, w.coord.lon, apiKey).let { resp ->
                if (resp.isSuccessful) oneCall.value = resp.body()
            }
        }
    }
}

