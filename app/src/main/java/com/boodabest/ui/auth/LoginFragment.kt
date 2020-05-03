package com.boodabest.ui.auth

import android.os.Bundle
import android.view.View
import com.boodabest.R
import com.boodabest.core.BaseFragment

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        app.updateTitle(getString(R.string.menu_login))
        app.updateBackAble(true)
    }
}
