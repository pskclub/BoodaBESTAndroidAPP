package com.boodabest.repositories.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.boodabest.core.AppExecutors
import com.boodabest.database.Product
import com.boodabest.database.ProductDao
import com.boodabest.database.ProductDb
import com.boodabest.database.ProductSearch
import com.boodabest.models.PageMeta
import com.boodabest.models.RepoOptions
import com.boodabest.network.ApiResponse
import com.boodabest.network.NetworkBoundResource
import com.boodabest.network.Resource
import com.boodabest.services.ProductService
import com.boodabest.utils.AbsentLiveData
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ProductRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val productService: ProductService,
    private val productDao: ProductDao,
    private val productDb: ProductDb
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


    fun getByTag(
        tag: String,
        page: String = "",
        query: String = "",
        options: RepoOptions = RepoOptions()
    ): LiveData<Resource<List<Product>>> {
        val queryKey = (if (tag.isBlank()) "-" else tag) + (if (query.isBlank()) "-" else query)
        return object : NetworkBoundResource<List<Product>, PageMeta<Product>>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<PageMeta<Product>>> {
                return productService.get(page, query, tag)
            }

            override fun loadFromDb(): LiveData<List<Product>> {
                return productDao.search(queryKey).switchMap { searchData ->
                    Timber.w("load $queryKey")
                    if (searchData == null) {
                        AbsentLiveData.create()
                    } else {
                        productDao.load(searchData.ids)
                    }
                }
            }

            override fun saveCallResult(item: PageMeta<Product>) {
                val ids = item.items.map { it.id }
                val repoSearchResult = ProductSearch(
                    query = queryKey,
                    ids = ids,
                    totalCount = item.total
                )

                productDb.runInTransaction {
                    productDao.insert(repoSearchResult)
                    productDao.inserts(item.items)
                }
            }

            override fun shouldFetch(data: List<Product>?) =
                options.isNetworkOnly || data.isNullOrEmpty()
        }.asLiveData()
    }
}