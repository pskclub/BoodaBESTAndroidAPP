package com.boodabest.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.boodabest.di.ViewModelFactory
import com.boodabest.di.ViewModelKey
import com.boodabest.repositories.product.ProductViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel::class)
    abstract fun bindProductViewModel(viewModel: ProductViewModel): ViewModel
}