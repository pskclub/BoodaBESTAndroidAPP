package com.boodabest.ui.account

import android.os.Bundle
import android.view.View
import com.boodabest.core.BaseFragment
import com.boodabest.R

class AccountOverviewFragment : BaseFragment(R.layout.fragment_account_overview) {
    companion object {
        @JvmStatic
        fun newInstance() = AccountOverviewFragment().apply {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appViewModel.updateTitle(getString(R.string.menu_account))
    }

}
