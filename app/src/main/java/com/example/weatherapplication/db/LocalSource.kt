package com.example.designpattern.db

import com.example.designpattern.model.Product
import com.example.weatherapplication.model.Alert
import com.example.weatherapplication.model.Favorite
import kotlinx.coroutines.flow.Flow

interface LocalSource {
    suspend fun insert(favorite: Favorite)
    suspend fun insertAll(favorite : List<Favorite>)
    suspend fun delete(favorite: Favorite)
    suspend fun getAlllocation(): Flow<List<Favorite>>
    suspend fun insertAlart(alart: Alert)
    suspend fun deleteAlart(alart: Alert)
    suspend fun getAllAlart(): Flow<List<Alert>>



}