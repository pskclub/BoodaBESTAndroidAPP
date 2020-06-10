package com.boodabest.services

import androidx.lifecycle.LiveData
import com.boodabest.database.Product
import com.boodabest.models.PageMeta
import com.boodabest.network.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {

    @GET("product/{id}")
    fun find(@Path("id") id: String): LiveData<ApiResponse<Product>>

    @GET("products?limit=16")
    fun get(
        @Query("page") page: String = "",
        @Query("keyword") query: String = "",
        @Query("alias") tag: String = ""
    ): LiveData<ApiResponse<PageMeta<Product>>>
}