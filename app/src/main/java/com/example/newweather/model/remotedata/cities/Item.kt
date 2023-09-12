package com.example.newweather.model.remotedata.cities

data class Item(
    val address: Address,
    val highlights: Highlights,
    val id: String,
    val language: String,
    val localityType: String,
    val resultType: String,
    val title: String
)