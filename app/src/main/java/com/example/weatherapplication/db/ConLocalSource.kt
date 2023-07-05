package com.example.designpattern.db

import android.content.Context
import com.example.day1.ProductDao
import com.example.day1.productDatabase
import com.example.designpattern.model.Product
import com.example.weatherapplication.model.Favorite

class ConLocalSource (private val context: Context) : LocalSource {

    private val dao: ProductDao by lazy {
        val db: productDatabase = productDatabase.getIntsance(context)
        db.getproductDao()
    }
    override suspend fun getAlllocation(): kotlinx.coroutines.flow.Flow<List<Favorite>> {
        return dao.getAll()
    }
    override suspend fun insert(favorite: Favorite) {
        dao.insert(favorite)
    }

    override suspend fun insertAll(products: List<Favorite>) {
//        dao.insertAll(products)
    }

    override suspend fun delete(favorite: Favorite) {
        dao.delete(favorite)
    }


}