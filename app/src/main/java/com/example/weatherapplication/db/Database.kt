package com.example.day1

import android.content.Context
import androidx.room.*
import com.example.designpattern.model.Product


@Database (entities = arrayOf(Product::class), version = 1)
@TypeConverters(Converters::class)

abstract class productDatabase :RoomDatabase() {
    abstract  fun getproductDao ():ProductDao
    companion object{
        @Volatile
        private  var Instance :productDatabase? =null
        fun getIntsance (ctx :Context): productDatabase{
            return Instance ?: synchronized(this){
                val instance  = Room.databaseBuilder(
                    ctx.applicationContext, productDatabase::class.java,"productdatabase"

                ).build()
                Instance = instance
                instance
            }

        }

    }
}