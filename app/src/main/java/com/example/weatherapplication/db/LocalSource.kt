package com.example.designpattern.db

import com.example.designpattern.model.Product
import com.example.weatherapplication.model.Favorite
import kotlinx.coroutines.flow.Flow

interface LocalSource {
    suspend fun insert(product: Favorite)
    suspend fun insertAll(products : List<Favorite>)
    suspend fun delete(product: Favorite)
    suspend fun getAlllocation(): Flow<List<Favorite>>
}