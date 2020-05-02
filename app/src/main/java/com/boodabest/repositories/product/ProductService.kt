package com.boodabest.repositories.product

import androidx.lifecycle.LiveData
import com.boodabest.database.Product
import com.boodabest.models.PageMeta
import com.boodabest.network.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    @GET("product/{id}")
    fun find(@Path("id") id: String): LiveData<ApiResponse<Product>>

    @GET("products")
    fun get(): LiveData<ApiResponse<PageMeta<Product>>>
}