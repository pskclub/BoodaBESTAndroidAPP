package com.boodabest.ui.account

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.boodabest.R
import com.boodabest.core.BaseFragment
import com.boodabest.ui.auth.LoginFragment

class AccountOverviewFragment : BaseFragment(R.layout.fragment_account_overview) {
    companion object {
        @JvmStatic
        fun newInstance() = AccountOverviewFragment().apply {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        app.updateTitle(getString(R.string.menu_account))
        auth.isAuth.observe(viewLifecycleOwner, Observer { isAuth ->
            if (!isAuth) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance())
                    .commit()
            }
        })

    }

}
