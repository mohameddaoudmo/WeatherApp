package com.example.designpattern.network

import android.content.Context
import com.example.weatherapplication.SharedViewModel
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

    var latitude :Double ?=null
    var longitude :Double ?= null
    var language :String =""
    var unit :String =""
    override  fun sendCurrentWeather(
        lat: Double,
        lon: Double,
        lang: String,
        unit: String,
    ) {
        this.language = lang
        this.unit = unit
        latitude = lat
        longitude = lon
        println("in remote source $latitude $longitude")
    }


    override suspend fun getProductsFromNetwork(): Flow<Response<Forecast>> {

            val x = Api.apiService.getCurrentWeatherByLatAndLon(latitude ?: 0.0 , longitude ?: 0.0)
            return flowOf(x)
        println("$latitude  $longitude mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm")




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