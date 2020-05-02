package com.boodabest.core

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.boodabest.R
import com.boodabest.di.Injectable
import com.boodabest.repositories.AppViewModel
import com.boodabest.repositories.AuthViewModel
import com.boodabest.ui.auth.LoginFragment
import javax.inject.Inject

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId),
    Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    protected lateinit var app: AppViewModel
    protected val auth: AuthViewModel by viewModels {
        viewModelFactory
    }


    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            app = ViewModelProvider(this).get(AppViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }
}