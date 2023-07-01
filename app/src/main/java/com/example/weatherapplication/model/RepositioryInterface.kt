package com.example.designpattern.model

import android.content.Context
import com.example.weatherforecastapp.ui.home.model.Forecast
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RepositioryInterface  {
    suspend fun getFromNetwork(  lat: Double,
                                 lon: Double,
                                 lang: String,
                                 unit: String,) : Flow<Response<Forecast>>

    suspend fun getFromDatabase() : Flow<List<Product>>
    suspend fun saveProducts(products : List<Product>)
    suspend fun addToFavorites(product: Product)
    suspend fun removeFromFavorites(product: Product)
    fun getCurrentWeather(

        lat: Double,
        lon: Double,
        lang: String ,
        unit: String
    )
}