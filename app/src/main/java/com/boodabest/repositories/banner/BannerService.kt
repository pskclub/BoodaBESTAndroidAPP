package com.boodabest.repositories.banner

import androidx.lifecycle.LiveData
import com.boodabest.database.Banner
import com.boodabest.database.Product
import com.boodabest.models.PageResponse
import com.boodabest.network.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BannerService {

    @GET("banners")
    fun get(): LiveData<ApiResponse<PageResponse<Banner>>>
}