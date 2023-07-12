package com.example.weatherapplication.network

import com.example.designpattern.network.RemoteSource
import com.example.weatherforecastapp.ui.home.model.Forecast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class ApiClient(var Forcast: Forecast) :RemoteSource{
    override fun sendCurrentWeather(lat: Double, lon: Double, lang: String, unit: String) {
       }

    override suspend fun getProductsFromNetwork(
        lat: Double,
        lon: Double,
        lang: String,
        unit: String
    ): Flow<Response<Forecast>> {
        return flow {
            val response = Response.success(Forcast)
            emit(response)
        }   }
}