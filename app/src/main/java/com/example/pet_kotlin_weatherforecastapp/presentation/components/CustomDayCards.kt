package com.example.pet_kotlin_weatherforecastapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pet_kotlin_weatherforecastapp.R
import com.example.pet_kotlin_weatherforecastapp.ui.theme.Gray
import com.example.pet_kotlin_weatherforecastapp.ui.theme.PET_Kotlin_WeatherForecastAppTheme
import com.example.pet_kotlin_weatherforecastapp.ui.theme.Black
import androidx.compose.material3.Text
import coil.compose.AsyncImage
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.DailyForecast
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.TempDaily
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.WeatherItem
import com.example.pet_kotlin_weatherforecastapp.ui.theme.White
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun CustomDayCard(
    forecast: DailyForecast,
    onClick: () -> Unit = {}
) {
    val dayName = Instant.ofEpochSecond(forecast.timestamp)
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("EEE"))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = dayName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )

            AsyncImage(
                model = "https://openweathermap.org/img/wn/${forecast.weather.first().icon}@2x.png",
                contentDescription = forecast.weather.first().description,
                modifier = Modifier.size(32.dp)
            )

            Spacer(Modifier.width(12.dp))

            val maxTemp = "+${forecast.temp.max.toInt()}°"
            val minTemp = "+${forecast.temp.min.toInt()}°"

            Text(
                text = maxTemp,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )

            Text(
                text = minTemp,
                fontSize = 14.sp,
                color = Gray,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
    }
}
