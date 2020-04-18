package com.boodabest.core

import com.google.gson.GsonBuilder
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


abstract class BaseEcomService<T> {
    private val apiClass: Class<T>? = null

    fun getBaseUrl(): String = "https://stg-ecom.pams.ai/api/"

    fun addConverter(): Converter.Factory? {
        return GsonConverterFactory.create(
            GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyy-MM-dd'T'HH:mm:ssZ")
                .create()
        )
    }

    fun getDefaultTimeout(): Long {
        return 10000
    }

    fun createApi(): T {
        return Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(apiClass)
    }

}