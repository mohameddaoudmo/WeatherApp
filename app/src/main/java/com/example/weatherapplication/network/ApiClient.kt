package com.example.designpattern.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://dummyjson.com/"

object ApiClient : RemoteSource {
    override suspend fun getProductsFromNetwork(): Flow<Response<com.example.designpattern.model.Response>> {
//        val x = Api.apiService
//        return flowOf(x)
        return flowOf()

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