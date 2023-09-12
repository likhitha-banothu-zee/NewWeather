package com.example.newweather.model.remotedata.weather

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)