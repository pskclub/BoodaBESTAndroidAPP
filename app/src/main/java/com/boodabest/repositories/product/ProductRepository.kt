package com.boodabest.repositories.product

import androidx.lifecycle.LiveData
import com.boodabest.AppExecutors
import com.boodabest.database.Product
import com.boodabest.database.ProductDao
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

    fun findProduct(id: String): LiveData<Resource<Product>> {
        return object : NetworkBoundResource<Product, Product>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<Product>> {
                return productService.findProduct(id)
            }

            override fun loadFromDb() = productDao.findProduct(id)

            override fun saveCallResult(item: Product) {
                productDao.insert(item)
            }

            override fun shouldFetch(data: Product?) = data == null
        }.asLiveData()
    }

    fun findProducts(): LiveData<Resource<List<Product>>> {
        return object : NetworkBoundResource<List<Product>, List<Product>>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<List<Product>>> {
                return productService.findProducts()
            }

            override fun loadFromDb() = productDao.findProducts()

            override fun saveCallResult(items: List<Product>) {
                productDao.insertProducts(items)
            }

            override fun shouldFetch(data: List<Product>?) = data == null || data.isEmpty()
        }.asLiveData()
    }
}