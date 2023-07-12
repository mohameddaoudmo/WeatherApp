package com.example.weatherapplication.model

import com.example.designpattern.model.RepositioryInterface
import com.example.weatherforecastapp.ui.home.model.Forecast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Response


class FakeRepo ( var alart: MutableList<Alert>? = mutableListOf(),
                 var fav: MutableList<Favorite>? = mutableListOf(),var Forcast: Forecast): RepositioryInterface {

    override suspend fun getFromNetwork(
        lat: Double,
        lon: Double,
        lang: String,
        unit: String
    ): Flow<Response<Forecast>> {
        return flow {
            val response = Response.success(Forcast)
            emit(response)
        }    }

    override suspend fun getFromDatabase(): Flow<List<Favorite>> {
        return flowOf(fav!!.toMutableList())    }

    override suspend fun saveProducts(favorite: List<Favorite>) {
        TODO("Not yet implemented")
    }

    override suspend fun addToFavorites(favorite: Favorite) {
        fav?.add(favorite)
    }

    override suspend fun removeFromFavorites(favorite: Favorite) {
        fav?.remove(favorite)
    }

    override suspend fun getFromDatabaseAlart(): Flow<List<Alert>> {
        return flowOf(alart!!.toMutableList())
    }

    override suspend fun addToAlart(alart: Alert) {
        this.alart?.add(alart)
    }

    override suspend fun removeFromAlart(alart: Alert) {
        this.alart?.remove(alart)
    }

    override fun getCurrentWeather(lat: Double, lon: Double, lang: String, unit: String) {
        TODO("Not yet implemented")
    }

}