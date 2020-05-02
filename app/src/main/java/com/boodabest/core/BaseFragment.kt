package com.boodabest.core

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.boodabest.di.Injectable
import com.boodabest.repositories.AppViewModel
import javax.inject.Inject

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId),
    Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    protected lateinit var appViewModel: AppViewModel

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            appViewModel = ViewModelProvider(this).get(AppViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }
}