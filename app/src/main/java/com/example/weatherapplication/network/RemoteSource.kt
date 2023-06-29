package com.example.designpattern.network

import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RemoteSource {
    suspend fun getProductsFromNetwork(): Flow<Response<com.example.designpattern.model.Response>>

}