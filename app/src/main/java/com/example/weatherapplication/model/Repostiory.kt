package com.example.designpattern.model

import android.content.Context
import com.example.designpattern.db.ConLocalSource
import com.example.designpattern.network.RemoteSource
import com.example.weatherforecastapp.ui.home.model.Forecast
import kotlinx.coroutines.flow.Flow
import retrofit2.Response



class Repostiory(
    private val remoteSource: RemoteSource,
    private val concreteLocalSource: ConLocalSource
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

    override suspend fun getFromDatabase(): Flow<List<Product>> {

        return concreteLocalSource.getAllProducts()
    }

    override suspend fun saveProducts(products: List<Product>) {
//        concreteLocalSource.insertAll(products)
    }

    override suspend fun addToFavorites(product: Product) {
//        concreteLocalSource.insert(product)
    }

    override suspend fun removeFromFavorites(product: Product) {
//        concreteLocalSource.delete(product)
    }

    override fun getCurrentWeather(
        
        lat: Double,
        lon: Double,
        lang: String,
        unit: String,
    ) {
        remoteSource.sendCurrentWeather(lat,lon,lang,unit)

    }


}