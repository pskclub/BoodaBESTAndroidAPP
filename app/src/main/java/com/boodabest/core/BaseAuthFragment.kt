package com.boodabest.core

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.lifecycle.Observer
import com.boodabest.R
import com.boodabest.di.Injectable
import com.boodabest.ui.auth.LoginFragment


abstract class BaseAuthFragment(@LayoutRes contentLayoutId: Int) : BaseFragment(contentLayoutId),
    Injectable {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth.isAuth.observe(viewLifecycleOwner, Observer { isAuth ->
            if (!isAuth) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance())
                    .commit()
            }
        })

    }
}