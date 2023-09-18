package com.example.newweather.view.weatherui

sealed class Screens(val route :String){
    object HomeScreen : Screens("home screen")
    object WeatherScreen : Screens("weather screen")
}
