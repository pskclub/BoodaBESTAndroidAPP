package com.boodabest.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg user: User)

    @Update
    abstract fun update(vararg user: User)

    @Query("DELETE FROM User")
    fun delete()

    @Query("SELECT * FROM user LIMIT 1")
    abstract fun find(): LiveData<User>


    @Query("SELECT * FROM user LIMIT 1")
    abstract fun findResult(): User?
}
