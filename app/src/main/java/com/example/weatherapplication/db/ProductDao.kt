package com.example.day1

import androidx.room.*
import com.example.designpattern.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM product_Table")
     fun  getAll ():Flow<List<Product>>

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert (product: Product) :Long

    @Delete
    suspend fun delete (product: Product) :Int
}