package com.example.pet_kotlin_weatherforecastapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.pet_kotlin_weatherforecastapp.ui.theme.PET_Kotlin_WeatherForecastAppTheme
import dagger.hilt.android.AndroidEntryPoint
import com.example.pet_kotlin_weatherforecastapp.navigation.AppNavGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PET_Kotlin_WeatherForecastAppTheme {
                Surface(Modifier.fillMaxSize()) {
                    AppNavGraph()
                }
            }
        }
    }
}


