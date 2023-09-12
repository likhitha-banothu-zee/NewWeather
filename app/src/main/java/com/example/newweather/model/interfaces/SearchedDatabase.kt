package com.example.newweather.model.interfaces

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.model.SearchedData

@Database(entities = [SearchedData::class], version = 1)

abstract class SearchedDatabase: RoomDatabase() {
    abstract fun SearchDao(): SearchDao
}