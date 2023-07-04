package com.example.day1

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromString(value: String?): ArrayList<String>? {
        return if (value == null) null else ArrayList(value.split(",").map { it.trim() })
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String>?): String? {
        return list?.joinToString(separator = ",")
    }
}