package com.example.pet_kotlin_weatherforecastapp.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pet_kotlin_weatherforecastapp.presentation.components.*
import com.example.pet_kotlin_weatherforecastapp.ui.theme.Gray

@Composable
fun DetailsScreen(
    onBack: () -> Unit,
    vm: MainViewModel = hiltViewModel()
) {
    val weather by vm.weather.collectAsState()
    val forecast by vm.forecast.collectAsState()

    val currentTemp = weather?.main?.temp?.toInt()?.let { "$it°" } ?: "--°"
    val minToday = forecast?.daily?.firstOrNull()?.temp?.min?.toInt()?.let { "$it°" } ?: "--°"
    val description = weather?.weather?.firstOrNull()?.description?.replaceFirstChar { it.uppercase() } ?: "—"
    val wind = weather?.wind?.speed?.toInt()?.toString() ?: "--"
    val humidity = weather?.main?.humidity?.toString() ?: "--"
    val rain = forecast?.daily?.firstOrNull()?.pop?.let { (it * 100).toInt().toString() } ?: "--"

    Scaffold(
        topBar = {
            CustomTopBar(
                textName = "7 days",
                showBackButton = true,
                onBackClick = onBack
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { pad ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(pad)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(20.dp))

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = currentTemp,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.width(12.dp))

                weather?.weather?.firstOrNull()?.icon?.let {
                    WeatherIcon(
                        iconCode = it,
                        modifier = Modifier.size(80.dp)
                    )
                }

                Spacer(Modifier.width(12.dp))

                Text(
                    text = minToday,
                    fontSize = 32.sp,
                    color = Gray
                )
            }

            Spacer(Modifier.height(8.dp))
            Text(
                text = description,
                fontSize = 16.sp,
                color = Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                CustomWindCard(wind)
                CustomHumidityCard(humidity)
                CustomRainCard(rain)
            }

            Spacer(Modifier.height(32.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                forecast?.daily?.let { list ->
                    items(list.take(7)) { day ->
                        CustomDayCard(forecast = day)
                    }
                }
            }
        }
    }
}
