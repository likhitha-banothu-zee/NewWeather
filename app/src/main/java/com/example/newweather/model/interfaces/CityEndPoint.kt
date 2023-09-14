package com.example.newweather.model.interfaces

import com.example.newweather.model.remotedata.cities.Cities
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CityEndPoint {
    @GET("autocomplete")
    suspend fun getCity(
        @Query("q") pattern: String,
        @Query("apiKey") city_apiKey: String
    ) : Response<Cities>

}