package com.boodabest.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        Brand::class
    ],
    version = 1,
    exportSchema = false
)
abstract class BrandDb : RoomDatabase() {

    abstract fun brandDao(): BrandDao

}
