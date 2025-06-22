package com.example.pet_kotlin_weatherforecastapp.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pet_kotlin_weatherforecastapp.R
import com.example.pet_kotlin_weatherforecastapp.presentation.components.CustomTopBar
import com.example.pet_kotlin_weatherforecastapp.ui.theme.PET_Kotlin_WeatherForecastAppTheme

@Composable
fun DetailsScreen(
    temperature: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        CustomTopBar(textName = temperature)

        Spacer(modifier = Modifier.height(30.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_sun),
            contentDescription = "sun icon",
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    PET_Kotlin_WeatherForecastAppTheme {
        Surface {
            DetailsScreen(
                temperature = "30°"
            )
        }
    }
}