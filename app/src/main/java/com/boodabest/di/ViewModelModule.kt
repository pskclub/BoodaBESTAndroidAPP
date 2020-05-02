package com.boodabest.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.boodabest.BaseActivityViewModel
import com.boodabest.repositories.banner.BannerViewModel
import com.boodabest.repositories.brand.BrandViewModel
import com.boodabest.repositories.product.ProductViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel::class)
    abstract fun bindProductViewModel(viewModel: ProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BannerViewModel::class)
    abstract fun bindBannerViewModel(viewModel: BannerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BrandViewModel::class)
    abstract fun bindBrandViewModel(viewModel: BrandViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BaseActivityViewModel::class)
    abstract fun bindBaseActivityViewModel(viewModel: BaseActivityViewModel): ViewModel
}