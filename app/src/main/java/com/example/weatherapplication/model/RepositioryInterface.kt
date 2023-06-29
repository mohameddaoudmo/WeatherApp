package com.example.designpattern.model

import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RepositioryInterface  {
    suspend fun getFromNetwork() : Flow<Response<com.example.designpattern.model.Response>>

    suspend fun getFromDatabase() : Flow<List<Product>>
    suspend fun saveProducts(products : List<Product>)
    suspend fun addToFavorites(product: Product)
    suspend fun removeFromFavorites(product: Product)
}