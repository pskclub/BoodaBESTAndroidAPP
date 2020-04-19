package com.boodabest.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun inserts(items: List<Product>)

    @Query("DELETE FROM products")
    fun deleteAll()

    @Query("SELECT * FROM products WHERE id = :id")
    abstract fun find(id: String): LiveData<Product>

    @Query("SELECT * FROM products")
    abstract fun get(): LiveData<List<Product>>
}
