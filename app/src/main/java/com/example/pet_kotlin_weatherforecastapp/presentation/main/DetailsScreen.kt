package com.example.pet_kotlin_weatherforecastapp.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pet_kotlin_weatherforecastapp.presentation.WeatherViewModel
import com.example.pet_kotlin_weatherforecastapp.presentation.components.CustomDayCard
import com.example.pet_kotlin_weatherforecastapp.presentation.components.CustomHumidityCard
import com.example.pet_kotlin_weatherforecastapp.presentation.components.CustomRainCard
import com.example.pet_kotlin_weatherforecastapp.presentation.components.CustomTopBar
import com.example.pet_kotlin_weatherforecastapp.presentation.components.CustomWindCard
import com.example.pet_kotlin_weatherforecastapp.ui.theme.Gray
import androidx.compose.material.icons.rounded.*
import com.example.pet_kotlin_weatherforecastapp.presentation.components.WeatherIcon


@Composable
fun DetailsScreen(
    city: String,
    apiKey: String,
    onBack: () -> Unit,
    vm: WeatherViewModel = hiltViewModel()
) {
    LaunchedEffect(city) { vm.load(city, apiKey) }

    val now by vm.now.collectAsState()
    val daily by vm.daily.collectAsState()

    val currentTemp   = now?.main?.temp?.toInt()?.let { "$it°" } ?: "--°"
    val minToday      = daily?.daily?.firstOrNull()?.temp?.min?.toInt()?.let { "$it°" } ?: "--°"
    val description   = now?.weather?.firstOrNull()?.description?.replaceFirstChar { it.uppercase() } ?: "—"
    val wind          = now?.wind?.speed?.toInt()?.toString() ?: "--"
    val humidity      = now?.main?.humidity?.toString() ?: "--"
    val rain          = daily?.daily?.firstOrNull()?.pop?.let { (it * 100).toInt().toString() } ?: "--"

    Scaffold(
        topBar = {
            CustomTopBar(
                textName = "7 days",
                showBackButton = true,
                onBackClick = onBack
            )
        }
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

                now?.weather?.firstOrNull()?.icon?.let {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        WeatherIcon(
                            iconCode = it,
                            modifier = Modifier.size(80.dp)
                        )
                        Text(
                            text = description,
                            fontSize = 14.sp,
                            color = Gray,
                            textAlign = TextAlign.Center
                        )
                    }
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
                daily?.daily?.let { list ->
                    items(list.take(7)) { day ->
                        CustomDayCard(forecast = day)
                    }
                    }
                }
            }
        }
    }
