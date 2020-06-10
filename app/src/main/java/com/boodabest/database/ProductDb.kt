package com.boodabest.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        Product::class,
        ProductSearch::class
    ],
    version = 8,
    exportSchema = false
)
abstract class ProductDb : RoomDatabase() {

    abstract fun productDao(): ProductDao

}
