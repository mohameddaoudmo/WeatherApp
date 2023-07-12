package com.example.weatherapplication.db

import com.example.designpattern.db.LocalSource
import com.example.weatherapplication.model.Alert
import com.example.weatherapplication.model.Favorite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ConLocalSource(
    var alart: MutableList<Alert>? = mutableListOf(),
    var fav: MutableList<Favorite>? = mutableListOf()
) : LocalSource {
    override suspend fun insert(favorite: Favorite) {

        fav?.add(favorite)
    }

    override suspend fun insertAll(favorite: List<Favorite>) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(favorite: Favorite) {
        fav?.remove(favorite)
    }

    override suspend fun getAlllocation(): Flow<List<Favorite>> {
        return flowOf(fav!!.toMutableList())

    }

    override suspend fun insertAlart(alart: Alert) {
        this.alart?.add(alart)
    }

    override suspend fun deleteAlart(alart: Alert) {
        this.alart?.remove(alart)
    }

    override suspend fun getAllAlart(): Flow<List<Alert>> {
        return flowOf(alart!!.toMutableList())
    }
}