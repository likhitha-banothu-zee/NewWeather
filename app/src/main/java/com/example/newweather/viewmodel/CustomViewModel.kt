package com.example.newweather.viewmodel

import androidx.lifecycle.ViewModel
import com.example.newweather.model.localdata.Location
import com.example.newweather.model.remotedata.forecast.Forecast
import com.example.newweather.model.remotedata.weather.WeatherInfo
import androidx.lifecycle.viewModelScope
import com.example.newweather.model.repository.Repo
import com.example.newweather.model.repository.WeatherRepoInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class CustomViewModel(val weatherRepo : WeatherRepoInterface): ViewModel() {
    private val result_1 = MutableStateFlow<Repo<WeatherInfo>>(Repo.Loading())
    val result1 : StateFlow<Repo<WeatherInfo>> = result_1

    private val data_2 =MutableStateFlow<ArrayList<Location>>(ArrayList())
    val data2 : StateFlow<ArrayList<Location>> = data_2

    private val result_3 = MutableStateFlow<Repo<Forecast>>(Repo.Loading())
    val result3 : StateFlow<Repo<Forecast>> = result_3

    fun getWeather(city: String) {
        viewModelScope.launch(Dispatchers.IO){
            result_1.value= Repo.Loading()
            val response1 = weatherRepo.getWeather(city)
            result_1.value = handleWeatherResponse(response1)
        }
    }

    private fun handleWeatherResponse(response: Response<WeatherInfo>):Repo<WeatherInfo> {
        if(response.isSuccessful){
            response.body()?.let { resultResponse->
                return Repo.Success(resultResponse)
            }

        }
        return Repo.Error(response.message())
    }

    fun getForecast(city: String) {
        viewModelScope.launch(Dispatchers.IO){
            result_3.value= Repo.Loading()
            val response2 = weatherRepo.getForecast(city)
            result_3.value = handleForecastResponse(response2)
        }
    }

    private fun handleForecastResponse(response: Response<Forecast>):Repo<Forecast> {
        if(response.isSuccessful){
            response.body()?.let { resultResponse->
                return Repo.Success(resultResponse)
            }

        }
        return Repo.Error(response.message())
    }

    fun getCities(pattern: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response3 = weatherRepo.getCity(pattern)
            val items = response3.body()?.items
            items?.let {
                val labels: ArrayList<Location> = ArrayList()
                for (i in items) {
                    labels.add(Location(i.address.city))
                }
                data_2.value = labels
            }
        }
    }

//    fun getCurrentWeather(lat:String,lon:String){
//        val key = "1fdb0737d2cb43f7d78d9b1ac8f7f53d"
//        var endPoint = "weather?lat=$lat&lon=$lon&appid=$key"
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val apiData = weatherRepo.getWeather(endPoint)
//                apiData?.body()?.let {
//                    result_1.value(it)
//                }
//                Log.d("check",apiData.toString())
//            } catch (e: Exception) {
//                Log.d("error",e.toString())
//            }
//        }
//    }

}
