package com.example.newweather.model.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weatherapp.model.SearchedData


@Dao
interface SearchDao {
        @Insert
        suspend fun insertCity(name:String)

        @Query("SELECT * FROM SearchedData")
        suspend fun getData():List<SearchedData>

        @Query("SELECT * FROM SearchedData WHERE Name LIKE :searchQuery ")
        suspend fun searchDatabase(searchQuery: String): List<SearchedData>

}