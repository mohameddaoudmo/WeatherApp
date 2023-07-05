package com.example.designpattern.model

import android.content.Context
import com.example.weatherapplication.model.Favorite
import com.example.weatherforecastapp.ui.home.model.Forecast
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RepositioryInterface  {
    suspend fun getFromNetwork(  lat: Double,
                                 lon: Double,
                                 lang: String,
                                 unit: String,) : Flow<Response<Forecast>>

    suspend fun getFromDatabase() : Flow<List<Favorite>>
    suspend fun saveProducts(favorite : List<Favorite>)
    suspend fun addToFavorites(favorite: Favorite)
    suspend fun removeFromFavorites(favorite: Favorite)
    fun getCurrentWeather(

        lat: Double,
        lon: Double,
        lang: String ,
        unit: String
    )
}