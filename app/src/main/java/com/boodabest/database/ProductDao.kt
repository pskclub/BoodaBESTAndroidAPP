package com.boodabest.database

import android.util.SparseIntArray
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(result: ProductSearch)

    @Query("DELETE FROM products")
    fun deleteAll()

    @Query("SELECT * FROM products WHERE id = :id")
    abstract fun find(id: String): LiveData<Product>

    @Query("SELECT * FROM products")
    abstract fun get(): LiveData<List<Product>>

    @Query("SELECT * FROM products WHERE id in (:ids)")
    abstract fun loadById(ids: List<String>): LiveData<List<Product>>

    @Query("SELECT * FROM products_search WHERE `query` = :query")
    abstract fun search(query: String): LiveData<ProductSearch?>

    fun load(ids: List<String>): LiveData<List<Product>> {
        val order = mutableMapOf<String, Int>()
        ids.withIndex().forEach {
            order[it.value] = it.index
        }
        return loadById(ids).map { repositories ->
            repositories.sortedWith(compareBy { order[it.id] })
        }
    }

}
