package com.boodabest.repositories.brand

import androidx.lifecycle.LiveData
import com.boodabest.core.AppExecutors
import com.boodabest.database.Brand
import com.boodabest.database.BrandDao
import com.boodabest.models.PageMeta
import com.boodabest.models.RepoOptions
import com.boodabest.network.ApiResponse
import com.boodabest.network.NetworkBoundResource
import com.boodabest.network.Resource
import com.boodabest.services.BrandService
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BrandRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val brandService: BrandService,
    private val brandDao: BrandDao
) {
    fun get(options: RepoOptions = RepoOptions()): LiveData<Resource<List<Brand>>> {
        return object : NetworkBoundResource<List<Brand>, PageMeta<Brand>>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<PageMeta<Brand>>> {
                return brandService.get()
            }

            override fun loadFromDb() = brandDao.get()

            override fun saveCallResult(item: PageMeta<Brand>) {
                brandDao.inserts(item.items)
            }

            override fun shouldFetch(data: List<Brand>?) =
                options.isNetworkOnly || data.isNullOrEmpty()
        }.asLiveData()
    }
}