package com.boodabest.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("access_token") val accessToken: String,
    @field:SerializedName("access_token_expire") val accessTokenExpire: String,
    @field:SerializedName("refresh_token") val refreshToken: String,
    @field:SerializedName("refresh_token_expire") val refreshTokenExpire: String
)