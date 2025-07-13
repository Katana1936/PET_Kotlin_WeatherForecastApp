package com.example.pet_kotlin_weatherforecastapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.DailyForecast
import com.example.pet_kotlin_weatherforecastapp.ui.theme.Black
import com.example.pet_kotlin_weatherforecastapp.ui.theme.Gray
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

    val weather = forecast.weather.firstOrNull()
    val iconCode = weather?.icon ?: "01d"
    val description = weather?.main ?: weather?.description ?: "—"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = dayName,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(2f)
        ) {
            WeatherIcon(
                iconCode = iconCode,
                modifier = Modifier.size(24.dp)
            )

            Spacer(Modifier.width(6.dp))

            Text(
                text = description,
                fontSize = 14.sp,
                color = Gray
            )
        }

        Text(
            text = "+${forecast.temp.max.toInt()}°",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )

        Text(
            text = "+${forecast.temp.min.toInt()}°",
            fontSize = 14.sp,
            color = Gray,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }
}



