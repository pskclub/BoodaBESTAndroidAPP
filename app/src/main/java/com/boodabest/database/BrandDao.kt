package com.boodabest.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BrandDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg brand: Brand)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun inserts(items: List<Brand>)

    @Query("DELETE FROM brands")
    fun deleteAll()

    @Query("SELECT * FROM brands WHERE id = :id")
    abstract fun find(id: String): LiveData<Brand>

    @Query("SELECT * FROM brands")
    abstract fun get(): LiveData<List<Brand>>
}
