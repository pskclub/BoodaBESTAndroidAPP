package com.boodabest.database

import androidx.room.Entity
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity(primaryKeys = ["query"], tableName = "products_search")
@TypeConverters(ProductSearch.Converter::class)

data class ProductSearch(
    val query: String,
    val ids: List<String>,
    val totalCount: Int,
    val next: String = ""
) {

    class Converter {
        @TypeConverter
        fun stringToStringList(data: String?): List<String>? {
            return data?.let { it ->
                it.split(",").map {
                    it
                }
            }
        }

        @TypeConverter
        fun stringListToString(string: List<String>?): String? {
            return string?.joinToString(",")
        }
    }
}
