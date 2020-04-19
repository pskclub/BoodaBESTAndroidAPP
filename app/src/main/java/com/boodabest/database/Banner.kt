package com.boodabest.database

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["id"], tableName = "banners")
data class Banner(
    @field:SerializedName("banner_id") val id: String,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("cover_image_url") val imageURL: String,
    @field:SerializedName("link_app_url") val link: String
)