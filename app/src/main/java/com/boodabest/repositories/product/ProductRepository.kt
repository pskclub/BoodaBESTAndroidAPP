package com.boodabest.repositories.product

import androidx.lifecycle.LiveData
import com.boodabest.core.AppExecutors
import com.boodabest.database.Product
import com.boodabest.database.ProductDao
import com.boodabest.models.PageMeta
import com.boodabest.models.RepoOptions
import com.boodabest.network.ApiResponse
import com.boodabest.network.NetworkBoundResource
import com.boodabest.network.Resource
import com.boodabest.services.ProductService
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ProductRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val productService: ProductService,
    private val productDao: ProductDao
) {

    fun find(id: String, options: RepoOptions): LiveData<Resource<Product>> {
        return object : NetworkBoundResource<Product, Product>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<Product>> {
                return productService.find(id)
            }

            override fun loadFromDb() = productDao.find(id)

            override fun saveCallResult(item: Product) {
                productDao.insert(item)
            }

            override fun shouldFetch(data: Product?) = options.isNetworkOnly || data == null
        }.asLiveData()
    }

    fun get(
        page: String = "",
        query: String = "",
        options: RepoOptions = RepoOptions()
    ): LiveData<Resource<List<Product>>> {
        return object : NetworkBoundResource<List<Product>, PageMeta<Product>>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<PageMeta<Product>>> {
                return productService.get(page, query)
            }

            override fun loadFromDb() = productDao.get()

            override fun saveCallResult(item: PageMeta<Product>) {
                productDao.inserts(item.items)
            }

            override fun shouldFetch(data: List<Product>?) =
                options.isNetworkOnly || data.isNullOrEmpty()
        }.asLiveData()
    }
}