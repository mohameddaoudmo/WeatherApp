package com.example.designpattern.network

import android.content.Context
import com.example.weatherforecastapp.ui.home.model.Forecast
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RemoteSource {
     fun sendCurrentWeather(
        lat: Double,
        lon: Double,
        lang: String,
        unit: String,
    )
    suspend fun getProductsFromNetwork(  lat: Double,
                                         lon: Double,
                                         lang: String,
                                         unit: String,): Flow<Response<Forecast>>



}