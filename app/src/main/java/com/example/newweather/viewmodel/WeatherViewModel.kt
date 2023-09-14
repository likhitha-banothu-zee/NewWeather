package com.example.newweather.viewmodel

import androidx.lifecycle.ViewModel
import com.example.newweather.model.remotedata.cities.Location
import com.example.newweather.model.remotedata.weather.WeatherInfo
import androidx.lifecycle.viewModelScope
import com.example.newweather.model.repository.Repo
import com.example.newweather.model.repository.WeatherRepoInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class WeatherViewModel(val weatherRepo : WeatherRepoInterface): ViewModel() {
    private val _weather = MutableStateFlow<Repo<WeatherInfo>>(Repo.Loading())
    val weather : StateFlow<Repo<WeatherInfo>> = _weather

    private val _cities =MutableStateFlow<ArrayList<Location>>(ArrayList())
    val cities : StateFlow<ArrayList<Location>> = _cities

    fun getWeather(city: String) {
        viewModelScope.launch{
            _weather.value= Repo.Loading()
            val response1 = weatherRepo.getWeather(city)
            _weather.value = handleWeatherResponse(response1)
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

    fun getCities(pattern: String) {
        viewModelScope.launch{
            val response3 = weatherRepo.getCity(pattern)
            val items = response3.body()?.items
            items?.let {
                val labels: ArrayList<Location> = ArrayList()
                for (i in items) {
                    i.address?.city?.let{
                        labels.add(Location(it))
                    }
                }
                _cities.value = labels
            }
        }
    }

}
