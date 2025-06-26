package com.example.pet_kotlin_weatherforecastapp.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pet_kotlin_weatherforecastapp.R
import com.example.pet_kotlin_weatherforecastapp.presentation.components.CustomHumidityCard
import com.example.pet_kotlin_weatherforecastapp.presentation.components.CustomRainCard
import com.example.pet_kotlin_weatherforecastapp.presentation.components.CustomTopBar
import com.example.pet_kotlin_weatherforecastapp.presentation.components.CustomWeatherCard
import com.example.pet_kotlin_weatherforecastapp.presentation.components.CustomWindCard
import com.example.pet_kotlin_weatherforecastapp.presentation.components.WeatherIcon
import com.example.pet_kotlin_weatherforecastapp.ui.theme.Gray

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val weatherState by viewModel.weather.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchWeather("Nancy", "c9a492ccf25c130201d59d9e85423eee")
    }

    val city = weatherState?.cityName ?: "…"
    val temp = weatherState?.main?.temp?.toInt()?.let { "$it°" } ?: "--°"
    val desc = weatherState?.weather?.firstOrNull()?.description?.capitalize() ?: "Загрузка…"

    Scaffold(
        topBar = { CustomTopBar(textName = city, showLocationDot = true) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(30.dp))
            weatherState?.weather?.firstOrNull()?.icon?.let { iconCode ->
                WeatherIcon(iconCode, Modifier.size(180.dp).align(Alignment.CenterHorizontally))
            }
            Spacer(Modifier.height(30.dp))
            Text(temp, fontSize = 64.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            Text(desc, fontSize = 18.sp, color = Gray, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(24.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                CustomWindCard(wind = weatherState?.wind?.speed?.toInt()?.toString() ?: "--")
                CustomHumidityCard(humidity = weatherState?.main?.humidity?.toString() ?: "--")
                CustomRainCard(rainPercentage = "—")
            }
            Spacer(Modifier.height(32.dp))
            Text("Today", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Gray, modifier = Modifier.padding(start = 8.dp))
            Spacer(Modifier.height(12.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(4) { idx ->
                    CustomWeatherCard(temperature = "21°", iconResId = R.drawable.ic_thunder, time = "${11 + idx}:00")
                }
            }
        }
    }
}
