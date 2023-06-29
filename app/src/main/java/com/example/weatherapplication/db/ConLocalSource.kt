package com.example.designpattern.db

import android.content.Context
import com.example.day1.ProductDao
import com.example.day1.productDatabase
import com.example.designpattern.model.Product

class ConLocalSource (private val context: Context) : LocalSource {

    private val dao: ProductDao by lazy {
        val db: productDatabase = productDatabase.getIntsance(context)
        db.getproductDao()
    }
    override suspend fun getAllProducts(): kotlinx.coroutines.flow.Flow<List<Product>> {
        return dao.getAll()
    }
    override suspend fun insert(product: Product) {
        dao.insert(product)
    }

    override suspend fun insertAll(products: List<Product>) {
//        dao.insertAll(products)
    }

    override suspend fun delete(product: Product) {
        dao.delete(product)
    }


}