package com.boodabest.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity(primaryKeys = ["id"], tableName = "products")
@TypeConverters(Product.GalleryConverter::class)
data class Product(
    @field:SerializedName("product_id") val id: String,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("thumbnail_url") val thumbnailURL: String,
    @field:SerializedName("price") val price: String,
    @field:SerializedName("price_promotion") val pricePromotion: String,
    @field:Embedded(prefix = "brand_") @field:SerializedName("brand") val brand: Brand,
    @field:Embedded(prefix = "header_") @field:SerializedName("header") val header: Header?,
    @field:SerializedName("galleries") val galleries: List<Gallery>
) {
    data class Brand(
        @field:SerializedName("tag_id") val id: String,
        @field:SerializedName("title") val title: String,
        @field:SerializedName("logo_image_url") val thumbnailURL: String,
        @field:SerializedName("background_color") val backgroundColor: String
    )

    data class Gallery(
        @field:SerializedName("image_url") val imageURL: String
    )

    data class Header(
        @field:SerializedName("align") val align: String?,
        @field:SerializedName("background_color") val backgroundColor: String?,
        @field:SerializedName("caption") val caption: String?,
        @field:SerializedName("caption_color") val captionColor: String?,
        @field:SerializedName("cover_image_url") val coverImageURL: String?,
        @field:SerializedName("is_show_cover") val isShowCover: String?,
        @field:SerializedName("is_show_overlay") val isShowOverlay: String?,
        @field:SerializedName("title") val title: String?,
        @field:SerializedName("title_color") val titleColor: String?
    )

    class GalleryConverter {

        @TypeConverter
        fun fromCountryLangList(value: List<Gallery>): String {
            val gson = Gson()
            val type = object : TypeToken<List<Gallery>>() {}.type
            return gson.toJson(value, type)
        }

        @TypeConverter
        fun toCountryLangList(value: String): List<Gallery> {
            val gson = Gson()
            val type = object : TypeToken<List<Gallery>>() {}.type
            return gson.fromJson(value, type)
        }
    }
}