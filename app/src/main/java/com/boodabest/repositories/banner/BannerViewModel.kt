package com.boodabest.repositories.banner

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.boodabest.database.Banner
import com.boodabest.network.Resource
import javax.inject.Inject

class BannerViewModel @Inject constructor(bannerRepository: BannerRepository) : ViewModel() {

    val items: LiveData<Resource<List<Banner>>> = bannerRepository.get()
}