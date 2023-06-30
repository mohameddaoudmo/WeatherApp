package com.example.designpattern.network

import android.content.Context
import com.example.weatherforecastapp.ui.home.model.Forecast
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RemoteSource {
    suspend fun getProductsFromNetwork(): Flow<Response<Forecast>>

     fun sendCurrentWeather(
        lat: Double,
        lon: Double,
        lang: String,
        unit: String,
    )

}