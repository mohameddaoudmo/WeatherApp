package com.example.day1

import androidx.room.*
import com.example.designpattern.model.Product
import com.example.weatherapplication.model.Alert
import com.example.weatherapplication.model.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM favorites")
     fun  getAll ():Flow<List<Favorite>>

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert (favorite: Favorite) :Long

    @Delete
    suspend fun delete (favorite: Favorite) :Int

    @Query("SELECT * FROM alert")
    fun  getAllAlart ():Flow<List<Alert>>

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlart (alart : Alert) :Long

    @Delete
    suspend fun deleteAlart (alart: Alert) :Int
}