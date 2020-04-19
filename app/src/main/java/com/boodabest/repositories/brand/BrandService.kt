package com.boodabest.repositories.brand

import androidx.lifecycle.LiveData
import com.boodabest.database.Brand
import com.boodabest.models.PageResponse
import com.boodabest.network.ApiResponse
import retrofit2.http.GET

interface BrandService {

    @GET("brands")
    fun get(): LiveData<ApiResponse<PageResponse<Brand>>>
}