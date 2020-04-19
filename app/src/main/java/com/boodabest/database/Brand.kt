package com.boodabest.database

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["id"], tableName = "brands")
data class Brand(
    @field:SerializedName("tag_id") val id: String,
    @field:SerializedName("slug") val slug: String,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("cover_image_url") val coverImageURL: String,
    @field:SerializedName("logo_image_url") val logoImageURL: String,
    @field:SerializedName("background_color") val bgColor: String
)