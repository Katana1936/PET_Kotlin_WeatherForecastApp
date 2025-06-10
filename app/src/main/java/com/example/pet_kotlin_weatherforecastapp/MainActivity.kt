package com.example.pet_kotlin_weatherforecastapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<WeatherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val weather by viewModel.weatherData.collectAsState()
                WeatherScreen(weather)
            }
        }

        viewModel.fetchWeather(
            apiKey = "c9a492ccf25c130201d59d9e85423eee", // твой ключ
            lat = 48.6921,
            lon = 6.1844
        )
    }
}
