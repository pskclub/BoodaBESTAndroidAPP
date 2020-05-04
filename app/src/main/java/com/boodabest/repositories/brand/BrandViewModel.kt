package com.boodabest.repositories.brand

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.boodabest.database.Brand
import com.boodabest.network.Resource
import javax.inject.Inject

class BrandViewModel @Inject constructor(brandRepository: BrandRepository) : ViewModel() {
    val items: LiveData<Resource<List<Brand>>> = brandRepository.get()
}