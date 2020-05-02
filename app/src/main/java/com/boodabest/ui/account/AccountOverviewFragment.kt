package com.boodabest.ui.account

import android.os.Bundle
import android.view.View
import com.boodabest.R
import com.boodabest.core.BaseAuthFragment

class AccountOverviewFragment : BaseAuthFragment(R.layout.fragment_account_overview) {
    companion object {
        @JvmStatic
        fun newInstance() = AccountOverviewFragment().apply {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        app.updateTitle(getString(R.string.menu_account))
    }

}
