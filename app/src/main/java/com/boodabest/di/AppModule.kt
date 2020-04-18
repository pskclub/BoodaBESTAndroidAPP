package com.boodabest.di

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.boodabest.database.ProductDao
import com.boodabest.database.ProductDb
import com.boodabest.repositories.product.ProductService
import com.boodabest.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideProductService(): ProductService {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(ProductService::class.java)
    }


    @Singleton
    @Provides
    fun provideProductDb(app: Application): ProductDb {
        return Room
            .databaseBuilder(app, ProductDb::class.java, "product.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideProductDao(db: ProductDb): ProductDao {
        return db.productDao()
    }

}