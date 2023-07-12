package com.example.weatherapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorites")
data class Favorite(@PrimaryKey val place: String, val latitude: Double, val longitude: Double) :
    Serializable


