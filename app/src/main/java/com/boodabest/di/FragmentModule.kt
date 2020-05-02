package com.boodabest.di

import com.boodabest.ui.account.AccountOverviewFragment
import com.boodabest.ui.home.HomeFragment
import com.boodabest.ui.product_single.ProductSingleFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentModule {
    // เดี๋ยวจะใส่ Fragment ทุกๆตัวไว้ในนี้เพื่อทำเป็น Dependency
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeProductDetailFragment(): ProductSingleFragment

    @ContributesAndroidInjector
    abstract fun contributeAccountOverviewFragment(): AccountOverviewFragment
}