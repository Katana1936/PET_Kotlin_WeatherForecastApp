package com.example.pet_kotlin_weatherforecastapp.presentation.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pet_kotlin_weatherforecastapp.R
import com.example.pet_kotlin_weatherforecastapp.presentation.components.CustomHumidityCard
import com.example.pet_kotlin_weatherforecastapp.presentation.components.CustomRainCard
import com.example.pet_kotlin_weatherforecastapp.presentation.components.CustomTopBar
import com.example.pet_kotlin_weatherforecastapp.presentation.components.CustomWeatherCard
import com.example.pet_kotlin_weatherforecastapp.presentation.components.CustomWindCard
import com.example.pet_kotlin_weatherforecastapp.presentation.components.WeatherIcon
import com.example.pet_kotlin_weatherforecastapp.ui.theme.Gray


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    apiKey: String,
    onOpenDetails: () -> Unit,
    vm: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        vm.requestLocationIfNeeded(context)
        vm.initLocationAndFetch(context, apiKey)
    }

    val weather by vm.weather.collectAsState()
    val forecast by vm.forecast.collectAsState()


    val city = weather?.cityName ?: "—"
    val temp = weather?.main?.temp?.toInt()?.let { "$it°" } ?: "--°"
    val desc = weather?.weather?.firstOrNull()?.description
        ?.replaceFirstChar { it.uppercaseChar() } ?: "—"
    val wind = weather?.wind?.speed?.toInt()?.toString() ?: "--"
    val humidity = weather?.main?.humidity?.toString() ?: "--"
    val rain = forecast?.hourly?.firstOrNull()?.pop?.let { (it * 100).toInt().toString() } ?: "--"

    Scaffold(
        topBar = {
            CustomTopBar(
                textName = city,
                showLocationDot = true
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(Modifier.height(30.dp))

                forecast?.hourly?.firstOrNull()?.weather?.firstOrNull()?.icon?.let {
                    WeatherIcon(
                        iconCode = it,
                        modifier = Modifier
                            .size(120.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }

                Spacer(Modifier.height(30.dp))

                Text(
                    text = temp,
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = desc,
                    fontSize = 18.sp,
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

                Spacer(Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Сегодня",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Gray
                    )

                    Row(
                        modifier = Modifier.clickable(onClick = onOpenDetails),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "7 дней",
                            fontSize = 14.sp,
                            color = Gray
                        )
                        Spacer(Modifier.width(4.dp))

                        Image(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "open 7-day forecast",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))
            }

            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                forecast?.hourly
                    ?.take(12)
                    ?.let { list ->
                        items(list, key = { it.timestamp }) { hour ->
                            CustomWeatherCard(item = hour)
                        }
                    }
            }
        }
    }
}

