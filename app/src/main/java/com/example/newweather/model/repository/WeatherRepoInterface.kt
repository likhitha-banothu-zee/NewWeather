package com.example.newweather.model.repository

import com.example.newweather.model.remotedata.cities.Cities
import com.example.newweather.model.remotedata.weather.WeatherInfo
import retrofit2.Response

interface WeatherRepoInterface {
    suspend fun getWeather(city:String): Response<WeatherInfo>
    suspend fun getCity(pattern: String): Response<Cities>

}
