package com.example.pet_kotlin_weatherforecastapp.presentation.features.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.pet_kotlin_weatherforecastapp.data.remote.WeatherResponse
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MainScreen(weather: WeatherResponse?) {
    if (weather == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val dateTime = SimpleDateFormat("EEE, MMM dd yyyy HH:mm", Locale.getDefault())
        .format(Date(weather.dt * 1000))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "${weather.name}, ${weather.sys.country}",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            // Заглушка для иконки
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.primary)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = weather.weather.firstOrNull()?.main ?: "-",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${weather.main.temp}°C (ощущается как ${weather.main.feels_like}°C)",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Дополнительные параметры
        InfoRow(label = "Описание", value = weather.weather.firstOrNull()?.description ?: "-")
        InfoRow(label = "Темп. мин/макс", value = "${weather.main.temp_min}°C / ${weather.main.temp_max}°C")
        InfoRow(label = "Давление", value = "${weather.main.pressure} гПа")
        InfoRow(label = "Влажность", value = "${weather.main.humidity}%")
        InfoRow(label = "Облачность", value = "${weather.clouds.all}%")
        InfoRow(label = "Видимость", value = "${weather.visibility / 1000} км")
        InfoRow(label = "Ветер", value = "${weather.wind.speed} м/с, порывы ${weather.wind.gust ?: "-"}")
        weather.rain?.`1h`?.let { InfoRow(label = "Дождь (1ч)", value = "$it мм") }

        Spacer(modifier = Modifier.height(16.dp))
        InfoRow(label = "Восход", value = unixToTime(weather.sys.sunrise))
        InfoRow(label = "Закат", value = unixToTime(weather.sys.sunset))

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Обновлено: $dateTime",
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}

private fun unixToTime(unix: Long): String =
    SimpleDateFormat("HH:mm", Locale.getDefault())
        .format(Date(unix * 1000))
