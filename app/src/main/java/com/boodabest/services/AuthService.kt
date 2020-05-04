package com.boodabest.services

import androidx.lifecycle.LiveData
import com.boodabest.database.User
import com.boodabest.models.Empty
import com.boodabest.models.LoginResponse
import com.boodabest.network.ApiResponse
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.*

data class LoginBody(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)

interface AuthService {
    @POST("member/login")
    fun login(@Body data: LoginBody): LiveData<ApiResponse<LoginResponse>>

    @GET("member/myprofile")
    fun profile(@Header("Authorization") token: String): LiveData<ApiResponse<User>>

    @POST("member/logout")
    fun logout(@Header("Authorization") token: String): Call<Empty>
}