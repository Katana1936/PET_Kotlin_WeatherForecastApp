package com.example.pet_kotlin_weatherforecastapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import com.example.pet_kotlin_weatherforecastapp.ui.theme.Gray
import com.example.pet_kotlin_weatherforecastapp.ui.theme.PET_Kotlin_WeatherForecastAppTheme
import com.example.pet_kotlin_weatherforecastapp.ui.theme.Black
import androidx.compose.material3.Text

@Composable
fun CustomDayCard(
    day: String,
    weatherDescription: String,
    iconResId: Int,
    maxTemp: String,
    minTemp: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = day,
            fontSize = 16.sp,
            color = Gray,
            modifier = Modifier.weight(1f)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(2f)
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = "weather icon",
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 6.dp)
            )

            Text(
                text = weatherDescription,
                fontSize = 16.sp,
                color = Black
            )
        }

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.weight(2f)
        ) {
            Text(
                text = maxTemp,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Black,
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = minTemp,
                fontSize = 16.sp,
                color = Gray,
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomDayCardPreview() {
    PET_Kotlin_WeatherForecastAppTheme {
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            CustomDayCard(
                day = "Mon",
                weatherDescription = "Rainy",
                iconResId = R.drawable.ic_rain,
                maxTemp = "20°",
                minTemp = "14°"
            )
        }
    }
}