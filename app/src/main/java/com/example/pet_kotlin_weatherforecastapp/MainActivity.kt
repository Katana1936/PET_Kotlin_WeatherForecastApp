package com.example.pet_kotlin_weatherforecastapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import com.example.pet_kotlin_weatherforecastapp.data.WeatherRepository
import com.example.pet_kotlin_weatherforecastapp.presentation.features.main.MainScreen

class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(WeatherRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val weather = viewModel.weatherData.collectAsState().value
                MainScreen(weather)
            }
        }

        viewModel.fetchWeather(
            apiKey = "c9a492ccf25c130201d59d9e85423eee",
            lat = 48.6921,
            lon = 6.1844
        )
    }
}
