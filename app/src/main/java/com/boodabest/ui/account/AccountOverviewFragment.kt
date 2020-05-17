package com.boodabest.ui.account

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.boodabest.R
import com.boodabest.core.BaseAuthFragment
import com.boodabest.network.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_account_overview.*
import kotlinx.android.synthetic.main.fragment_account_overview_header.*

class AccountOverviewFragment : BaseAuthFragment(R.layout.fragment_account_overview) {
    companion object {
        @JvmStatic
        fun newInstance() = AccountOverviewFragment().apply {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        app.updateTitle(getString(R.string.menu_account))

        vSwipeRefresh.setOnRefreshListener {
            auth.fetchMe()
        }

        btnLogout.setOnClickListener {
            auth.logout()
        }

        initAccount()
    }

    private fun initAccount() {
        auth.fetchMe()
        auth.me.observe(viewLifecycleOwner, Observer { user ->
            if (user.status == Status.SUCCESS) {
                vSwipeRefresh.isRefreshing = false
            }
            if (user.data != null) {
                txtFullName.text = user.data.fullName()
                txtEmail.text = user.data.contactEmail
                txtMobile.text = user.data.contactMobile
                Glide
                    .with(this)
                    .load(user.data.profileImageURL)
                    .apply(RequestOptions().circleCrop())
                    .into(ivProfile)
            }
        })

    }

}
