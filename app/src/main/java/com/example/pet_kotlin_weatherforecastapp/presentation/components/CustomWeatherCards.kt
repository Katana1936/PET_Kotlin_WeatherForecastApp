package com.example.pet_kotlin_weatherforecastapp.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pet_kotlin_weatherforecastapp.data.remote.model.HourlyForecast
import com.example.pet_kotlin_weatherforecastapp.ui.theme.White
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomWeatherCard(
    item: HourlyForecast,
    modifier: Modifier = Modifier
) {
    val time = Instant.ofEpochSecond(item.timestamp)
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("HH:mm"))

    val shape = RoundedCornerShape(24.dp)

    Card(
        modifier = modifier
            .width(70.dp)
            .height(120.dp),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF56CCF2),
                            Color(0xFF2F80ED)
                        )
                    )
                )
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    text = "${item.temp.toInt()}°",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = White
                )

                AsyncImage(
                    model = "https://openweathermap.org/img/wn/${item.weather.first().icon}@2x.png",
                    contentDescription = item.weather.first().description,
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
}
