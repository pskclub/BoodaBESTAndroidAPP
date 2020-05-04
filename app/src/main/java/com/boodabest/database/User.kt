package com.boodabest.database

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["refreshToken"], tableName = "user")
data class User(
    @field:SerializedName("member_id") val id: String = "",
    @field:SerializedName("mobile") val mobile: String = "",
    @field:SerializedName("email") val email: String = "",
    @field:SerializedName("contact_email") val contactEmail: String = "",
    @field:SerializedName("contact_mobile") val contactMobile: String = "",
    @field:SerializedName("firstname") val firstName: String = "",
    @field:SerializedName("lastname") val lastName: String = "",
    @field:SerializedName("profile_image_url") val profileImageURL: String = "",
    @field:SerializedName("language") val language: String = "",
    @field:SerializedName("gender") val gender: String = "",
    @field:SerializedName("registered_by") val registeredBy: String = "",
    @field:SerializedName("member_card_no") val memberCardNo: String = "",
    @field:SerializedName("member_point") val memberPoint: Int = 0,
    @field:SerializedName("notification_on") val isNotificationOn: Boolean = false,
    @field:SerializedName("access_token") val accessToken: String = "",
    @field:SerializedName("access_token_expire") val accessTokenExpire: String = "",
    @field:SerializedName("refresh_token") val refreshToken: String = "",
    @field:SerializedName("refresh_token_expire") val refreshTokenExpire: String = ""
)