package com.boodabest.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BannerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg banner: Banner)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun inserts(items: List<Banner>)

    @Query("DELETE FROM banners")
    fun deleteAll()

    @Query("SELECT * FROM banners WHERE id = :id")
    abstract fun find(id: String): LiveData<Banner>

    @Query("SELECT * FROM banners")
    abstract fun get(): LiveData<List<Banner>>
}
