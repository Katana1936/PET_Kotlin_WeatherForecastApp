package com.example.pet_kotlin_weatherforecastapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.DailyForecast
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.OneCallResponse
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.TempDaily
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.WeatherItem
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.WeatherResponse
import com.example.pet_kotlin_weatherforecastapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repo: WeatherRepository
) : ViewModel() {

    private val _now = MutableStateFlow<WeatherResponse?>(null)
    val now: StateFlow<WeatherResponse?> = _now

    private val _daily = MutableStateFlow<OneCallResponse?>(null)
    val daily: StateFlow<OneCallResponse?> = _daily

    fun load(city: String, key: String) = viewModelScope.launch {
        val nowResponse = repo.currentWeather(city, key)
        if (nowResponse.isSuccessful) {
            val now = nowResponse.body()
            _now.value = now

            now?.coord?.let { coord ->
                val forecastResponse = repo.forecast(coord.lat, coord.lon, key)
                if (forecastResponse.isSuccessful) {
                    val forecast = forecastResponse.body()

                    val grouped = forecast?.list?.groupBy {
                        it.dateText.substringBefore(" ")
                    } ?: emptyMap()

                    val dailyList = grouped.map { (date, items) ->
                        val min = items.minOf { it.main.tempMin }
                        val max = items.maxOf { it.main.tempMax }
                        val icon = items.firstOrNull()?.weather?.firstOrNull()?.icon ?: "01d"
                        val desc = items.firstOrNull()?.weather?.firstOrNull()?.description ?: ""
                        val main = items.firstOrNull()?.weather?.firstOrNull()?.main ?: ""


                        val popAvg = items.map { it.pop }.average()

                        DailyForecast(
                            timestamp = LocalDate
                                .parse(date)
                                .atStartOfDay(ZoneId.systemDefault())
                                .toEpochSecond(),
                            temp = TempDaily(min = min, max = max),
                            weather = listOf(WeatherItem(main = main, description = desc, icon = icon)),
                            pop = popAvg
                        )
                    }

                    _daily.value = OneCallResponse(
                        lat = coord.lat,
                        lon = coord.lon,
                        timezone = "unknown",
                        hourly = emptyList(),
                        daily = dailyList
                    )
                }
            }
        }
    }
}



