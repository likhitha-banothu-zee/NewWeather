package com.example.newweather.model.remotedata.forecast

data class Forecast(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<DataForecast>,
    val message: Int
)