package com.example.designpattern.network


import com.example.designpattern.model.Response
import com.example.weatherforecastapp.ui.home.model.Forecast
import retrofit2.http.GET
import retrofit2.http.Query

interface Apiservice {
    @GET("data/2.5/onecall?")
    suspend fun getCurrentWeatherByLatAndLon(
        @Query("lat") lat: Double ,
        @Query("lon") lon: Double ,
        @Query("lang") lang: String,
        @Query("units") units: String,
        @Query("exclude") exclude: String = "minutely",
        @Query("appid") appid: String = "52a9dabee5c82702cb8c32eb70429b55"
    ): retrofit2.Response<Forecast>


}