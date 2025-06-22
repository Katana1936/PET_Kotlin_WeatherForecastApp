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
fun DetailsScreen(
    temperature: String,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CustomTopBar(
                textName = "7 days",
                showBackButton = true,
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "20°",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(12.dp))

                Image(
                    painter = painterResource(id = R.drawable.ic_sun),
                    contentDescription = "sun icon",
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "17°",
                    fontSize = 32.sp,
                    color = Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Rainy - Cloudy",
                fontSize = 16.sp,
                color = Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomWindCard(wind = "20")
                CustomHumidityCard(humidity = "22")
                CustomRainCard(rainPercentage = "94")
            }

            Spacer(modifier = Modifier.height(32.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    PET_Kotlin_WeatherForecastAppTheme {
        Surface {
            DetailsScreen(
                temperature = "30°",
                onBackClick = {}
            )
        }
    }
}
