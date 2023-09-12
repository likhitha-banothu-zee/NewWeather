package com.example.newweather

import com.example.newweather.model.interfaces.EndPoints
import com.example.newweather.model.repository.WeatherRepo
import com.example.newweather.model.repository.WeatherRepoInterface
import com.example.newweather.viewmodel.CustomViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EndPoints::class.java)
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://autocomplete.search.hereapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EndPoints::class.java)
    }

    single<WeatherRepoInterface>{
        WeatherRepo(get())
    }

    viewModel{
        CustomViewModel(get())
    }
}