package com.boodabest.database

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["id"], tableName = "products")
data class Product(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("title") val title: String
)