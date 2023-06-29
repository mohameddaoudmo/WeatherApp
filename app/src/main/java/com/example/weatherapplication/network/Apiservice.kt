package com.example.designpattern.network


import com.example.designpattern.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Apiservice {

    @GET("weather")
    suspend fun getWeatherByLocation(
        @Query("lat")
        latitude:String,
        @Query("lon")
        longitude:String
    )
//    :Response<ResponseWeather>

    @GET("weather")
    suspend fun getWeatherByCityID(
        @Query("id")
        query:String
    )
//    :Response<ResponseWeather>
}