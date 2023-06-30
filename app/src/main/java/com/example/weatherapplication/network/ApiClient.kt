package com.example.designpattern.network

import android.content.Context
import com.example.weatherforecastapp.ui.home.model.Forecast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.openweathermap.org/"

object ApiClient : RemoteSource {
    var lat :Double =0.0
    var lon :Double =0.0
    var language :String =""
    var unit :String =""


    override suspend fun getProductsFromNetwork(): Flow<Response<Forecast>> {
    val x = Api.apiService.getCurrentWeatherByLatAndLon(lat, lon)
     return flowOf(x)


    }

    override fun sendCurrentWeather(
        lat: Double,
        lon: Double,
        lang: String,
        unit: String,
    ) {
        this.language = lang
        this.unit = unit
        this.lat = lat
        this.lon = lon

    }
}

object RetrofitHelper {
    var gson: Gson = GsonBuilder().create()
    var retrofitInstance: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}

object Api {
    val apiService: Apiservice by lazy {
        RetrofitHelper.retrofitInstance.create(Apiservice::class.java)
    }
}