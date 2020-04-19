package com.boodabest.repositories.banner

import androidx.lifecycle.LiveData
import com.boodabest.AppExecutors
import com.boodabest.database.Brand
import com.boodabest.database.BrandDao
import com.boodabest.models.PageResponse
import com.boodabest.network.ApiResponse
import com.boodabest.network.NetworkBoundResource
import com.boodabest.network.Resource
import com.boodabest.repositories.brand.BrandService
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BrandRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val brandService: BrandService,
    private val brandDao: BrandDao
) {


    fun get(): LiveData<Resource<List<Brand>>> {
        return object : NetworkBoundResource<List<Brand>, PageResponse<Brand>>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<PageResponse<Brand>>> {
                return brandService.get()
            }

            override fun loadFromDb() = brandDao.get()

            override fun saveCallResult(item: PageResponse<Brand>) {
                brandDao.inserts(item.items)
            }

            override fun shouldFetch(data: List<Brand>?) = data == null || data.isEmpty()
        }.asLiveData()
    }
}