package com.example.designpattern.model

import android.content.Context
import com.example.weatherapplication.model.Alert
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
    suspend fun getFromDatabaseAlart() : Flow<List<Alert>>
    suspend fun addToAlart(alart: Alert)
    suspend fun removeFromAlart(alart: Alert)
    fun getCurrentWeather(

        lat: Double,
        lon: Double,
        lang: String ,
        unit: String
    )
}