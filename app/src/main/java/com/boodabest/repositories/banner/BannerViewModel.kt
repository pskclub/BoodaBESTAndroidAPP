package com.boodabest.repositories.banner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.boodabest.database.Banner
import com.boodabest.models.RepoOptions
import com.boodabest.network.Resource
import javax.inject.Inject

class BannerViewModel @Inject constructor(private val bannerRepository: BannerRepository) :
    ViewModel() {
    private val _fetchItems: MutableLiveData<FetchItem> = MutableLiveData()
    val items: LiveData<Resource<List<Banner>>> = _fetchItems.switchMap { input ->
        input.ifExists { options ->
            bannerRepository.get(options)
        }
    }

    fun fetchItems() {
        val update = FetchItem(RepoOptions(isNetworkOnly = true))
        _fetchItems.value = update
    }


    data class FetchItem(val options: RepoOptions = RepoOptions()) {
        fun <T> ifExists(f: (RepoOptions) -> LiveData<T>): LiveData<T> {
            return f(options)
        }
    }
}