package com.boodabest.di

import android.app.Application
import androidx.room.Room
import com.boodabest.database.*
import com.boodabest.repositories.banner.BannerService
import com.boodabest.repositories.brand.BrandService
import com.boodabest.repositories.product.ProductService
import com.boodabest.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

var baseAPI = "https://shop.zeedshop.com/api/"


@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideProductService(): ProductService {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val original = chain.request()

            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                .addHeader("language", "en")
                .addHeader("x-device", "android")
                .addHeader("x-timestamp", "2019-08-15 18:24:00")

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        val okHttpClient = httpClient.build()

        return Retrofit.Builder()
            .baseUrl(baseAPI)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(okHttpClient)
            .build()
            .create(ProductService::class.java)
    }


    @Singleton
    @Provides
    fun provideProductDb(app: Application): ProductDb {
        return Room
            .inMemoryDatabaseBuilder(app, ProductDb::class.java)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideProductDao(db: ProductDb): ProductDao {
        return db.productDao()
    }


    @Singleton
    @Provides
    fun provideBannerService(): BannerService {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val original = chain.request()

            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                .addHeader("language", "en")
                .addHeader("x-device", "android")
                .addHeader("x-timestamp", "2019-08-15 18:24:00")

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        val okHttpClient = httpClient.build()

        return Retrofit.Builder()
            .baseUrl(baseAPI)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(okHttpClient)
            .build()
            .create(BannerService::class.java)
    }


    @Singleton
    @Provides
    fun provideBannerDb(app: Application): BannerDb {
        return Room
            .inMemoryDatabaseBuilder(app, BannerDb::class.java)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideBannerDao(db: BannerDb): BannerDao {
        return db.bannerDao()
    }

    @Singleton
    @Provides
    fun provideBrandService(): BrandService {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val original = chain.request()

            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                .addHeader("language", "en")
                .addHeader("x-device", "android")
                .addHeader("x-timestamp", "2019-08-15 18:24:00")

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        val okHttpClient = httpClient.build()

        return Retrofit.Builder()
            .baseUrl(baseAPI)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(okHttpClient)
            .build()
            .create(BrandService::class.java)
    }

    @Singleton
    @Provides
    fun provideBrandDb(app: Application): BrandDb {
        return Room
            .inMemoryDatabaseBuilder(app, BrandDb::class.java)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideBrandDao(db: BrandDb): BrandDao {
        return db.brandDao()
    }

}
