package com.example.pet_kotlin_weatherforecastapp.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.pet_kotlin_weatherforecastapp.presentation.components.*
import com.example.pet_kotlin_weatherforecastapp.ui.theme.Gray
import com.example.pet_kotlin_weatherforecastapp.ui.theme.PET_Kotlin_WeatherForecastAppTheme

@Composable
fun MainScreen(
    cityName: String,
    temperature: String
) {
    Scaffold(
        topBar = {
            CustomTopBar(textName = cityName)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_sun),
                contentDescription = "sun icon",
                modifier = Modifier
                    .size(180.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = temperature,
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Thunderclouds",
                fontSize = 18.sp,
                color = Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomWindCard(wind = "13")
                CustomHumidityCard(humidity = "24")
                CustomRainCard(rainPercentage = "87")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Today",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Gray,
                modifier = Modifier.padding(start = 8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 20.dp),
                        modifier = Modifier.fillMaxWidth()
            ) {
                items(4) { index ->
                    when (index) {
                        0 -> CustomWeatherCard(
                            temperature = "21°",
                            iconResId = R.drawable.ic_sun,
                            time = "11:00"
                        )

                        1 -> CustomWeatherCard(
                            temperature = "21°",
                            iconResId = R.drawable.ic_thunder,
                            time = "12:00"
                        )

                        2 -> CustomWeatherCard(
                            temperature = "22°",
                            iconResId = R.drawable.ic_thunder,
                            time = "13:00"
                        )

                        3 -> CustomWeatherCard(
                            temperature = "19°",
                            iconResId = R.drawable.ic_rain,
                            time = "14:00"
                        )
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    PET_Kotlin_WeatherForecastAppTheme {
        Surface {
            MainScreen(
                cityName = "Nancy",
                temperature = "30°"
            )
        }
    }
}
