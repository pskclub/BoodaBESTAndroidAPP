package com.boodabest.database

import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["id"], tableName = "products")
data class Product(
    @field:SerializedName("product_id") val id: String,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("thumbnail_url") val thumbnailURL: String,
    @field:SerializedName("price") val price: String,
    @field:SerializedName("price_promotion") val pricePromotion: String,
    @field:Embedded(prefix = "brand_") @field:SerializedName("brand") val brand: Brand
) {
    @Entity(primaryKeys = ["id"], tableName = "brands")
    data class Brand(
        @field:SerializedName("tag_id") val id: String,
        @field:SerializedName("title") val title: String,
        @field:SerializedName("logo_image_url") val thumbnailURL: String
    )
}