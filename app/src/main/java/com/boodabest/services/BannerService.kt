package com.boodabest.services

import androidx.lifecycle.LiveData
import com.boodabest.database.Banner
import com.boodabest.models.PageMeta
import com.boodabest.network.ApiResponse
import retrofit2.http.GET

interface BannerService {

    @GET("banners")
    fun get(): LiveData<ApiResponse<PageMeta<Banner>>>
}