package com.example.newweather.model.repository

import com.example.newweather.model.interfaces.SearchDao
import com.example.newweather.model.interfaces.SearchedDatabase
import com.example.newweather.model.remotedata.cities.Cities
import com.example.newweather.model.remotedata.forecast.Forecast
import com.example.newweather.model.remotedata.weather.WeatherInfo
import com.example.weatherapp.model.SearchedData
import retrofit2.Response
import java.util.regex.Pattern

interface WeatherRepoInterface {

//    suspend fun insertCity(insertcity : SearchedData)
//    suspend fun searchDatabase(searchQuery: String): SearchedData
//    suspend fun getData(): List<SearchedData>
    suspend fun getWeather(city:String): Response<WeatherInfo>
    suspend fun getForecast(city: String): Response<Forecast>
    suspend fun getCity(pattern: String): Response<Cities>

}
