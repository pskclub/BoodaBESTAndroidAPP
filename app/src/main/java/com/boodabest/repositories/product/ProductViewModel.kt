package com.boodabest.repositories.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.boodabest.database.Product
import com.boodabest.models.RepoOptions
import com.boodabest.network.Resource
import com.boodabest.utils.AbsentLiveData
import javax.inject.Inject

class ProductViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {
    private val _productId: MutableLiveData<ProductId> = MutableLiveData()
    private val _fetchItems: MutableLiveData<FetchItems> = MutableLiveData()

    val item: LiveData<Resource<Product>> = _productId.switchMap { input ->
        input.ifExists { id, options ->
            productRepository.find(id, options)
        }
    }

    val items: LiveData<Resource<List<Product>>> = _fetchItems.switchMap { input ->
        input.ifExists { options ->
            productRepository.get(options = options)
        }
    }


    fun fetchItems() {
        val update = FetchItems(RepoOptions(isNetworkOnly = true))
        _fetchItems.value = update
    }


    data class FetchItems(val options: RepoOptions = RepoOptions()) {
        fun <T> ifExists(f: (RepoOptions) -> LiveData<T>): LiveData<T> {
            return f(options)
        }
    }

    fun setProductId(id: String, options: RepoOptions = RepoOptions()) {
        val update = ProductId(id, options)
        if (_productId.value == update) {
            return
        }
        _productId.value = update
    }

    data class ProductId(val id: String, val options: RepoOptions) {
        fun <T> ifExists(f: (String, options: RepoOptions) -> LiveData<T>): LiveData<T> {
            return if (id.isBlank()) {
                AbsentLiveData.create()
            } else {
                f(id, options)
            }
        }
    }
}