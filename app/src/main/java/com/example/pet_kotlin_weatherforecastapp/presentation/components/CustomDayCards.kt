package com.example.pet_kotlin_weatherforecastapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.DailyForecast
import com.example.pet_kotlin_weatherforecastapp.ui.theme.Gray
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

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

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
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
}




