package com.boodabest.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.boodabest.R
import com.boodabest.core.BaseFragment
import com.boodabest.network.Status
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        app.updateTitle(getString(R.string.menu_login))
        app.updateBackAble(true)

        btnLogin.setOnClickListener {
            onBtnLoginClick(it)
        }
    }

    private fun onBtnLoginClick(view: View) {
        val username = evEmail.text.toString()
        val password = evPassword.text.toString()

        if (username.isEmpty() || password.isEmpty()) {
            return
        }

        view.isEnabled = false
        auth.setLogin(username, password)
        auth.loginItem.observe(viewLifecycleOwner, Observer { login ->
            when (login.status) {
                Status.LOADING -> {
                    Log.w("loading", "...")
                }
                Status.LOADED -> {
                    view.isEnabled = true
                }
                Status.SUCCESS -> {
                    login.data?.accessToken?.let { auth.fetchMe(login.data) }
                }
                Status.ERROR -> {
                    Log.w("error msg", login.message.toString())
                }
            }
        })

        auth.fetchMeItem.observe(viewLifecycleOwner, Observer {
            if (it.status === Status.SUCCESS) {
                requireActivity().supportFragmentManager.popBackStack()
            }
        })
    }
}
