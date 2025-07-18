package com.example.pet_kotlin_weatherforecastapp.presentation.main

import android.content.Context
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
        if (saved == null) {
            val location = LocationProvider(context).getCurrentLocation()
            location?.let {
                LocationPrefs.saveLocation(context, it.latitude, it.longitude)
            }
        }
    }


    fun fetchBySavedLocation(context: Context, apiKey: String) = viewModelScope.launch {
        val location = LocationPrefs.getSavedLocation(context) ?: return@launch

        val lat = location.first
        val lon = location.second

        runCatching { repo.currentWeatherByCoords(lat, lon, apiKey) }
            .onSuccess { resp ->
                if (resp.isSuccessful) {
                    resp.body()?.let { weatherResponse ->
                        _weather.value = weatherResponse
                        fetchForecast(lat, lon, apiKey)
                    }
                }
            }
    }


    fun fetchByCity(city: String, apiKey: String) = viewModelScope.launch {
        runCatching { repo.currentWeatherByCity(city, apiKey) }
            .onSuccess { resp ->
                if (resp.isSuccessful) {
                    resp.body()?.let { weatherResponse ->
                        _weather.value = weatherResponse

                        val lat = weatherResponse.coord.lat
                        val lon = weatherResponse.coord.lon

                        fetchForecast(lat, lon, apiKey)
                    }
                }
            }
    }

    fun initLocationAndFetch(context: Context, apiKey: String) = viewModelScope.launch {
        val saved = LocationPrefs.getSavedLocation(context)
        val location = saved ?: LocationProvider(context).getCurrentLocation()?.also {
            LocationPrefs.saveLocation(context, it.latitude, it.longitude)
        }

        location?.let {
            fetchBySavedLocation(context, apiKey)
        }
    }



    private suspend fun fetchForecast(lat: Double, lon: Double, apiKey: String) {
        runCatching {
            repo.forecast(lat, lon, apiKey)
        }.onSuccess { fResp ->
            if (fResp.isSuccessful) {
                val forecastList = fResp.body()?.list.orEmpty()

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
            }
        }
    }
}
