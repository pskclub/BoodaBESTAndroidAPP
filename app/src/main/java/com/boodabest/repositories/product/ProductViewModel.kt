package com.boodabest.repositories.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.boodabest.database.Product
import com.boodabest.network.Resource
import com.boodabest.utils.AbsentLiveData
import javax.inject.Inject

class ProductViewModel @Inject constructor(productRepository: ProductRepository) : ViewModel() {
    private val _productId: MutableLiveData<ProductId> = MutableLiveData()

    val item: LiveData<Resource<Product>> = _productId.switchMap { input ->
        input.ifExists { id ->
            productRepository.find(id)
        }
    }

    val items: LiveData<Resource<List<Product>>> = productRepository.get()

    fun setProductId(id: String) {
        val update = ProductId(id)
        if (_productId.value == update) {
            return
        }
        _productId.value = update
    }

    data class ProductId(val id: String) {
        fun <T> ifExists(f: (String) -> LiveData<T>): LiveData<T> {
            return if (id.isBlank()) {
                AbsentLiveData.create()
            } else {
                f(id)
            }
        }
    }
}