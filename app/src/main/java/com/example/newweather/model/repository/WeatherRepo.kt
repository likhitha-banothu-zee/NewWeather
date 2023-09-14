package com.example.newweather.model.repository

import com.example.newweather.BuildConfig
import com.example.newweather.model.interfaces.CityEndPoint
import com.example.newweather.model.interfaces.WeatherEndPoint
import com.example.newweather.model.remotedata.cities.Cities
import com.example.newweather.model.remotedata.weather.WeatherInfo
import retrofit2.Response

private val city_ApiKey=BuildConfig.API_KEY2
private val weather_ApiKey=BuildConfig.API_KEY
class WeatherRepo (
    private val api_weather :WeatherEndPoint,
    private val api_places :CityEndPoint
) : WeatherRepoInterface {

    private val units: String = "metric"
    override suspend fun getCity(pattern: String): Response<Cities> =
        api_places.getCity(pattern, city_ApiKey)

    override suspend fun getWeather(city: String): Response<WeatherInfo> =
        api_weather.getWeather(city, units, weather_ApiKey)

}