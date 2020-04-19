package com.boodabest

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.boodabest.di.Injectable
import javax.inject.Inject

abstract class BaseFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors
}