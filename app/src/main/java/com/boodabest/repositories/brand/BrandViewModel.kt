package com.boodabest.repositories.brand

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.boodabest.database.Brand
import com.boodabest.models.RepoOptions
import com.boodabest.network.Resource
import javax.inject.Inject

class BrandViewModel @Inject constructor(private val brandRepository: BrandRepository) :
    ViewModel() {
    private val _fetchItems: MutableLiveData<FetchItems> = MutableLiveData()
    val items: LiveData<Resource<List<Brand>>> = _fetchItems.switchMap { input ->
        input.ifExists { options ->
            brandRepository.get(options)
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
}