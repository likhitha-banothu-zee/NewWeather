package com.example.newweather.model.interfaces

import com.example.newweather.model.remotedata.cities.Cities
import com.example.newweather.model.remotedata.forecast.Forecast
import com.example.newweather.model.remotedata.weather.WeatherInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface EndPoints {

    @GET("weather?")
    suspend fun getWeather(
        @Query("q") city:String,
        @Query("units") units:String,
        @Query("appid") apiKey:String,
    ): Response<WeatherInfo>

    @GET("forecast?")
    suspend fun getForecast(
        @Query("q") city:String,
        @Query("units") units:String,
        @Query("appid") apiKey:String,
    ): Response<Forecast>

    @GET("autocomplete?")
    suspend fun getCity(
        @Query("q") pattern: String,
        @Query("apiKey") apiKey: String
    ) : Response<Cities>

}