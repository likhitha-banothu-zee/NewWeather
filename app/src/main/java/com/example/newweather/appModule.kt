package com.example.newweather

import com.example.newweather.model.interfaces.CityEndPoint
import com.example.newweather.model.interfaces.WeatherEndPoint
import com.example.newweather.model.repository.WeatherRepo
import com.example.newweather.model.repository.WeatherRepoInterface
import com.example.newweather.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val weather_baseurl = BuildConfig.BASE_URL
private val places_baseurl = BuildConfig.BASE_URL2

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(weather_baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherEndPoint::class.java)
    }

    single {
        Retrofit.Builder()
            .baseUrl(places_baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CityEndPoint::class.java)
    }

    single<WeatherRepoInterface>{
        WeatherRepo(get(),get())
    }

    viewModel{
        WeatherViewModel(get())
    }
}