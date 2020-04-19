package com.boodabest.di

import com.boodabest.MainActivity
import com.boodabest.ui.product_detail.ProductDetailActivity
import com.boodabest.ui.product_detail.ProductDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeProductDetailActivity(): ProductDetailActivity
}