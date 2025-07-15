package com.example.pet_kotlin_weatherforecastapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pet_kotlin_weatherforecastapp.BuildConfig
import com.example.pet_kotlin_weatherforecastapp.presentation.main.DetailsScreen
import com.example.pet_kotlin_weatherforecastapp.presentation.main.MainScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(modifier: Modifier = Modifier) {
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = "main",
        modifier = modifier
    ) {

        composable("main") {
            MainScreen(
                apiKey = BuildConfig.OPEN_WEATHER_KEY,
                onOpenDetails = { nav.navigate("details") }
            )
        }

        composable("details") {
            DetailsScreen(
                onBack = { nav.popBackStack() }
            )
        }
    }
}
