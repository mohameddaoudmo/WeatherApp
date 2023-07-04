package com.example.designpattern.db

import com.example.designpattern.model.Product
import kotlinx.coroutines.flow.Flow

interface LocalSource {
    suspend fun insert(product: Product)
    suspend fun insertAll(products : List<Product>)
    suspend fun delete(product: Product)
    suspend fun getAllProducts(): Flow<List<Product>>
}