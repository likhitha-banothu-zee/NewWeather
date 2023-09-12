package com.example.newweather.model.repository

sealed class Repo<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T?) : Repo<T>(data)
    class Error<T>(message: String?,data: T? = null) : Repo<T>(data,message)
    class Loading<T> : Repo<T>()

}