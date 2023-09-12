package com.example.newweather.model.repository

import com.example.newweather.model.interfaces.EndPoints
import com.example.newweather.model.interfaces.SearchDao
import com.example.weatherapp.model.SearchedData
import com.example.newweather.model.interfaces.SearchedDatabase
import com.example.newweather.model.remotedata.cities.Cities
import com.example.newweather.model.remotedata.forecast.DataForecast
import com.example.newweather.model.remotedata.forecast.Forecast
import com.example.newweather.model.remotedata.weather.WeatherInfo
import retrofit2.Response

class WeatherRepo (
    private val api :EndPoints
   /* private val search :SearchDao,*/

) : WeatherRepoInterface {

    override suspend fun getCity(pattern:String): Response<Cities> = api.getCity(pattern,"fKzVotCk8CmC2teZ3QEn0f9XoL_sZAV585wgTMKFoao")

    override suspend fun getForecast(city:String): Response<Forecast> = api.getForecast(city,"metric","1fdb0737d2cb43f7d78d9b1ac8f7f53d")

    override suspend fun getWeather(city:String): Response<WeatherInfo> = api.getWeather(city,"metric","1fdb0737d2cb43f7d78d9b1ac8f7f53d")

//    override suspend fun insertCity(insertcity: SearchedData) = search.insertCity(query)
//
//    override suspend fun getData(): List<SearchedData> = search.getData()
//
//    override suspend fun searchDatabase(searchQuery: String): SearchedData = search.searchDatabase(query)
}