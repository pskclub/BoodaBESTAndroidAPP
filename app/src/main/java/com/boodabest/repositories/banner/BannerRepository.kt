package com.boodabest.repositories.banner

import androidx.lifecycle.LiveData
import com.boodabest.AppExecutors
import com.boodabest.database.Banner
import com.boodabest.database.BannerDao
import com.boodabest.models.PageResponse
import com.boodabest.network.ApiResponse
import com.boodabest.network.NetworkBoundResource
import com.boodabest.network.Resource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BannerRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val bannerService: BannerService,
    private val bannerDao: BannerDao
) {


    fun get(): LiveData<Resource<List<Banner>>> {
        return object : NetworkBoundResource<List<Banner>, PageResponse<Banner>>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<PageResponse<Banner>>> {
                return bannerService.get()
            }

            override fun loadFromDb() = bannerDao.get()

            override fun saveCallResult(item: PageResponse<Banner>) {
                bannerDao.inserts(item.items)
            }

            override fun shouldFetch(data: List<Banner>?) = data == null || data.isEmpty()
        }.asLiveData()
    }
}