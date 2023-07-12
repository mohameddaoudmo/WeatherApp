package com.example.designpattern.model

import com.example.designpattern.db.ConLocalSource
import com.example.designpattern.db.LocalSource
import com.example.designpattern.network.RemoteSource
import com.example.weatherapplication.model.Alert
import com.example.weatherapplication.model.Favorite
import com.example.weatherforecastapp.ui.home.model.Forecast
import kotlinx.coroutines.flow.Flow
import retrofit2.Response



class Repostiory(
    private val remoteSource: RemoteSource,
    private val concreteLocalSource: LocalSource
) : RepositioryInterface {
    var latitude :Double = 0.0
    var longitude :Double = 0.0
    var language :String =""
    var unit :String =""


    override suspend fun getFromNetwork(  lat: Double,
                                          lon: Double,
                                          lang: String,
                                          unit: String,):  Flow<Response<Forecast>>{

        return remoteSource.getProductsFromNetwork(lat,lon ,lang,unit)
    }

    override suspend fun getFromDatabase(): Flow<List<Favorite>> {

        return concreteLocalSource.getAlllocation()

    }

    override suspend fun saveProducts(favorites: List<Favorite>) {
        concreteLocalSource.insertAll(favorites)
    }

    override suspend fun addToFavorites(favorite: Favorite) {
        concreteLocalSource.insert(favorite)
    }

    override suspend fun removeFromFavorites(favorite: Favorite) {
        concreteLocalSource.delete(favorite)
    }

    override suspend fun getFromDatabaseAlart(): Flow<List<Alert>> {
        return concreteLocalSource.getAllAlart()
    }


    override suspend fun addToAlart(alart: Alert) {
        concreteLocalSource.insertAlart(alart)
    }

    override suspend fun removeFromAlart(alart: Alert) {
concreteLocalSource.deleteAlart(alart)    }

    override fun getCurrentWeather(
        
        lat: Double,
        lon: Double,
        lang: String,
        unit: String,
    ) {
        remoteSource.sendCurrentWeather(lat,lon,lang,unit)

    }


}