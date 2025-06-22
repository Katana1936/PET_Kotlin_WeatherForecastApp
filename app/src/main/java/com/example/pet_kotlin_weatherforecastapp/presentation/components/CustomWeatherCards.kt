package com.example.pet_kotlin_weatherforecastapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pet_kotlin_weatherforecastapp.R
import com.example.pet_kotlin_weatherforecastapp.ui.theme.Blue
import com.example.pet_kotlin_weatherforecastapp.ui.theme.PET_Kotlin_WeatherForecastAppTheme
import com.example.pet_kotlin_weatherforecastapp.ui.theme.White
import androidx.compose.material3.Text


@Composable
fun CustomWeatherCard(
    temperature: String,
    iconResId: Int,
    time: String
) {
    Box(
        modifier = Modifier
            .width(70.dp)
            .height(120.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Blue.copy(alpha = 0.9f), Blue.copy(alpha = 0.6f))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            Text(
                text = temperature,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(id = iconResId),
                contentDescription = "weather icon",
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = time,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun CustomWeatherCardPreview() {
    PET_Kotlin_WeatherForecastAppTheme {
        CustomWeatherCard(
            temperature = "21°",
            iconResId = R.drawable.ic_rain,
            time = "11:00"
        )
    }
}
