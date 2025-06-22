package com.example.pet_kotlin_weatherforecastapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pet_kotlin_weatherforecastapp.R
import com.example.pet_kotlin_weatherforecastapp.ui.theme.Black
import com.example.pet_kotlin_weatherforecastapp.ui.theme.Gray
import com.example.pet_kotlin_weatherforecastapp.ui.theme.PET_Kotlin_WeatherForecastAppTheme
import com.example.pet_kotlin_weatherforecastapp.ui.theme.White

@Composable
fun CustomWindCard(
    wind: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(80.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(White)
            .padding(vertical = 12.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_wind),
            contentDescription = "wind icon",
            modifier = Modifier.size(32.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "$wind km/h",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Black
        )

        Text(
            text = "Wind",
            fontSize = 14.sp,
            color = Gray,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun CustomWindCardPreview() {
    PET_Kotlin_WeatherForecastAppTheme {
        CustomWindCard(wind = "13")
    }
}
