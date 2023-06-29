package com.example.designpattern.model

import com.example.designpattern.db.ConLocalSource
import com.example.designpattern.network.RemoteSource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response



class Repostiory(
    private val remoteSource: RemoteSource,
    private val concreteLocalSource: ConLocalSource
) : RepositioryInterface {


    override suspend fun getFromNetwork():  Flow<Response<com.example.designpattern.model.Response>>{
        return remoteSource.getProductsFromNetwork()
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


}