package com.example.pet_kotlin_weatherforecastapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.DailyForecast
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.HourlyForecast
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.TempDaily
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.WeatherItem
import com.example.pet_kotlin_weatherforecastapp.ui.theme.Blue
import com.example.pet_kotlin_weatherforecastapp.ui.theme.Gray
import com.example.pet_kotlin_weatherforecastapp.ui.theme.White
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun CustomWeatherCard(
    forecast: HourlyForecast,
    onClick: () -> Unit = {}
) {
    val time = Instant.ofEpochSecond(forecast.timestamp)
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("HH:mm"))

    Card(
        modifier = Modifier
            .width(70.dp)
            .height(120.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Blue.copy(alpha = 0.7f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            Text(
                text = "${forecast.temp.toInt()}°",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = White
            )

            AsyncImage(
                model = "https://openweathermap.org/img/wn/${forecast.weather.first().icon}@2x.png",
                contentDescription = forecast.weather.first().description,
                modifier = Modifier.size(28.dp)
            )

            Text(
                text = time,
                fontSize = 14.sp,
                color = White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview
fun PreviewCards() {
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Подставьте тестовые данные
        CustomWeatherCard(
            forecast = HourlyForecast(
                timestamp = Instant.now().epochSecond,
                temp = 21.0,
                pop = 0.0,
                weather = listOf(WeatherItem("Ясно", "01d"))
            )
        )
    }
}
