package com.example.newweather.model.interfaces

import com.example.newweather.model.remotedata.weather.WeatherInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherEndPoint {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city:String,
        @Query("units") units:String,
        @Query("appid") apiKey:String,
    ): Response<WeatherInfo>

}

