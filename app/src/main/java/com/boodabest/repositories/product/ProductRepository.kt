package com.boodabest.repositories.product

import androidx.lifecycle.LiveData
import com.boodabest.AppExecutors
import com.boodabest.database.Product
import com.boodabest.database.ProductDao
import com.boodabest.models.PageResponse
import com.boodabest.network.ApiResponse
import com.boodabest.network.NetworkBoundResource
import com.boodabest.network.Resource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ProductRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val productService: ProductService,
    private val productDao: ProductDao
) {

    fun find(id: String): LiveData<Resource<Product>> {
        return object : NetworkBoundResource<Product, Product>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<Product>> {
                return productService.find(id)
            }

            override fun loadFromDb() = productDao.find(id)

            override fun saveCallResult(item: Product) {
                productDao.insert(item)
            }

            override fun shouldFetch(data: Product?) = data == null
        }.asLiveData()
    }

    fun get(): LiveData<Resource<List<Product>>> {
        return object : NetworkBoundResource<List<Product>, PageResponse<Product>>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<PageResponse<Product>>> {
                return productService.get()
            }

            override fun loadFromDb() = productDao.get()

            override fun saveCallResult(item: PageResponse<Product>) {
                productDao.inserts(item.items)
            }

            override fun shouldFetch(data: List<Product>?) = data == null || data.isEmpty()
        }.asLiveData()
    }
}