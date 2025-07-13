package com.example.pet_kotlin_weatherforecastapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.*
import com.example.pet_kotlin_weatherforecastapp.data.repository.WeatherRepository
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

    fun fetch(city: String, apiKey: String) = viewModelScope.launch {
        runCatching { repo.currentWeather(city, apiKey) }
            .onSuccess { resp ->
                if (resp.isSuccessful) resp.body()?.let { w ->
                    _weather.value = w

                    runCatching {
                        repo.forecast(
                            lat = w.coord.lat,
                            lon = w.coord.lon,
                            apiKey = apiKey
                        )
                    }.onSuccess { fResp ->
                        if (fResp.isSuccessful) {
                            val forecastList = fResp.body()?.list.orEmpty()

                            val grouped = forecastList.groupBy {
                                it.dateText.substringBefore(" ")
                            }

                            val dailyList = grouped.map { (date, items) ->
                                val min = items.minOf { it.main.tempMin }
                                val max = items.maxOf { it.main.tempMax }
                                val icon = items.firstOrNull()?.weather?.firstOrNull()?.icon ?: "01d"
                                val desc = items.firstOrNull()?.weather?.firstOrNull()?.description ?: ""
                                val popAvg = items.map { it.pop }.average()

                                DailyForecast(
                                    timestamp = LocalDate.parse(date)
                                        .atStartOfDay(ZoneId.systemDefault())
                                        .toEpochSecond(),
                                    temp = TempDaily(min = min, max = max),
                                    weather = listOf(WeatherItem(description = desc, icon = icon)),
                                    pop = popAvg
                                )
                            }

                            _forecast.value = OneCallResponse(
                                lat = w.coord.lat,
                                lon = w.coord.lon,
                                timezone = "generated",
                                hourly = emptyList(),
                                daily = dailyList
                            )
                        }
                    }
                }
            }
    }
}
