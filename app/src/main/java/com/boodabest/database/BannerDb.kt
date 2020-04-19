package com.boodabest.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        Banner::class
    ],
    version = 1,
    exportSchema = false
)
abstract class BannerDb : RoomDatabase() {

    abstract fun bannerDao(): BannerDao

}
