package com.boodabest.ui.account

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.boodabest.R
import com.boodabest.core.BaseAuthFragment
import com.boodabest.network.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_account_profile.*
import kotlinx.android.synthetic.main.fragment_account_profile_header.*

class AccountProfileFragment : BaseAuthFragment(R.layout.fragment_account_profile) {

    companion object {
        @JvmStatic
        fun newInstance() = AccountProfileFragment().apply {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        app.updateTitle(getString(R.string.menu_profile))
        app.updateBackAble(true)

        vSwipeRefresh.setOnRefreshListener {
            auth.fetchMe()
        }

        initHeader()
    }

    private fun initHeader() {
        auth.fetchMe()
        auth.me.observe(viewLifecycleOwner, Observer { user ->
            if (user.status == Status.LOADED) {
                vSwipeRefresh.isRefreshing = false
            }
            if (user.data != null) {
                Glide
                    .with(this)
                    .load(user.data.profileImageURL)
                    .apply(RequestOptions().circleCrop())
                    .into(ivProfile)
            }
        })

    }
}
