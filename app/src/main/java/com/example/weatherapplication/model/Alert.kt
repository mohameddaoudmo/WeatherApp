package com.example.weatherapplication.model

import androidx.room.*

@Entity(tableName = "alert")
data class Alert(
    @PrimaryKey()
    val startTime: String,
    val endTime: String,
    val land :String,
    val type :String
){


}