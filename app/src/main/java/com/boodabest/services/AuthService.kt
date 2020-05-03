package com.boodabest.services

import androidx.lifecycle.LiveData
import com.boodabest.models.LoginResponse
import com.boodabest.network.ApiResponse
import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginBody(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)

interface AuthService {
    @POST("member/login")
    fun login(@Body data: LoginBody): LiveData<ApiResponse<LoginResponse>>
}