package com.boodabest.repositories.product

import androidx.lifecycle.LiveData
import com.boodabest.database.Product
import com.boodabest.network.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    @GET("todos/{id}")
    fun findProduct(@Path("id") id: String): LiveData<ApiResponse<Product>>

    @GET("todos")
    fun findProducts(): LiveData<ApiResponse<List<Product>>>
}