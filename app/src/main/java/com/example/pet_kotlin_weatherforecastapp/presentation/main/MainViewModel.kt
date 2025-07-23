package com.example.pet_kotlin_weatherforecastapp.presentation.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pet_kotlin_weatherforecastapp.data.local.LocationPrefs
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.*
import com.example.pet_kotlin_weatherforecastapp.data.repository.WeatherRepository
import com.example.pet_kotlin_weatherforecastapp.location.LocationProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: WeatherRepository
) : ViewModel() {

    private val _weather = MutableStateFlow<WeatherResponse?>(null)
    val weather: StateFlow<WeatherResponse?> = _weather

    private val _forecast = MutableStateFlow<OneCallResponse?>(null)
    val forecast: StateFlow<OneCallResponse?> = _forecast

    fun requestLocationIfNeeded(context: Context) = viewModelScope.launch {
        val saved = LocationPrefs.getSavedLocation(context)
        Log.d("DEBUG", "Saved location: $saved")
        if (saved == null) {
            val location = LocationProvider(context).getCurrentLocation()
            location?.let {
                LocationPrefs.saveLocation(context, it.latitude, it.longitude)
                Log.d("DEBUG", "Saved new location: ${it.latitude}, ${it.longitude}")
            }
        }
    }

    fun initLocationAndFetch(context: Context, apiKey: String) = viewModelScope.launch {
        val saved = LocationPrefs.getSavedLocation(context)
        Log.d("DEBUG", "Saved location at init: $saved")

        val lat: Double
        val lon: Double

        if (saved != null) {
            lat = saved.first
            lon = saved.second
        } else {
            val loc = LocationProvider(context).getCurrentLocation()
            if (loc == null) {
                Log.e("MainViewModel", "Location is null. Can't fetch weather.")
                return@launch
            }

            lat = loc.latitude
            lon = loc.longitude
            LocationPrefs.saveLocation(context, lat, lon)
            Log.d("DEBUG", "Got current location: $lat, $lon")
        }

        fetchWeatherAndForecast(lat, lon, apiKey)
    }

    private fun fetchWeatherAndForecast(lat: Double, lon: Double, apiKey: String) = viewModelScope.launch {
        Log.d("DEBUG", "Fetching weather for: lat=$lat, lon=$lon")

        val weatherResp = repo.currentWeatherByCoords(lat, lon, apiKey)
        if (weatherResp.isSuccessful) {
            weatherResp.body()?.let { response ->
                _weather.value = response
                fetchForecast(lat, lon, apiKey)
            }
        } else {
            Log.e("MainViewModel", "Weather request failed: ${weatherResp.code()}")
        }
    }

    fun fetchByCity(city: String, apiKey: String) = viewModelScope.launch {
        val weatherResp = repo.currentWeatherByCity(city, apiKey)
        if (weatherResp.isSuccessful) {
            weatherResp.body()?.let { response ->
                _weather.value = response
                val lat = response.coord.lat
                val lon = response.coord.lon
                fetchForecast(lat, lon, apiKey)
            }
        } else {
            Log.e("MainViewModel", "Weather by city failed: ${weatherResp.code()}")
        }
    }

    private suspend fun fetchForecast(lat: Double, lon: Double, apiKey: String) {
        Log.d("DEBUG", "Fetching forecast for: lat=$lat, lon=$lon")

        val response = repo.forecast(lat, lon, apiKey)
        if (response.isSuccessful) {
            val forecastList = response.body()?.list.orEmpty()

            val hourlyList = forecastList
                .take(12)
                .map {
                    HourlyForecast(
                        timestamp = it.timestamp,
                        temp = it.main.tempMin,
                        weather = it.weather,
                        pop = it.pop
                    )
                }

            val grouped = forecastList.groupBy {
                it.dateText.substringBefore(" ")
            }

            val dailyList = grouped.map { (date, items) ->
                val min = items.minOf { it.main.tempMin }
                val max = items.maxOf { it.main.tempMax }
                val icon = items.firstOrNull()?.weather?.firstOrNull()?.icon ?: "01d"
                val desc = items.firstOrNull()?.weather?.firstOrNull()?.description ?: ""
                val main = items.firstOrNull()?.weather?.firstOrNull()?.main ?: ""
                val popAvg = items.map { it.pop }.average()

                DailyForecast(
                    timestamp = LocalDate.parse(date)
                        .atStartOfDay(ZoneId.systemDefault())
                        .toEpochSecond(),
                    temp = TempDaily(min = min, max = max),
                    weather = listOf(
                        WeatherItem(main = main, description = desc, icon = icon)
                    ),
                    pop = popAvg
                )
            }

            _forecast.value = OneCallResponse(
                lat = lat,
                lon = lon,
                timezone = "generated",
                hourly = hourlyList,
                daily = dailyList
            )
        } else {
            Log.e("MainViewModel", "Forecast request failed: ${response.code()}")
        }
    }
}
