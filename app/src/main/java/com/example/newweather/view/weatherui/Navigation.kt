package com.example.newweather.view.weatherui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newweather.R
import com.example.newweather.viewmodel.WeatherViewModel

@Composable
fun Navigation(viewModel: WeatherViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.HomeScreen.route){
        composable(route = Screens.HomeScreen.route){
            HomeScreen(navController = navController)
        }
        composable(route = Screens.WeatherScreen.route){
            WeatherScreen(navController = navController, viewModel = viewModel)
        }
    }

}