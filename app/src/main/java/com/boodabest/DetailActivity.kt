package com.boodabest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.boodabest.core.BaseActivity
import com.boodabest.ui.auth.LoginFragment
import com.boodabest.ui.product_single.ProductSingleFragment

private const val TYPE = "type"
private const val ID = "id"
private const val TITLE = "title"


class DetailActivity : BaseActivity(R.layout.activity_detail) {
    private var type: String? = null
    private var id: String? = null
    private var title: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.app_toolbar))


        intent.extras?.let {
            type = it.getString(TYPE)
            id = it.getString(ID)
            title = it.getString(TITLE)
        }

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = title
        }

        if (savedInstanceState == null) {
            when (type) {
                PRODUCT_TYPE -> initProduct()
                BRAND_TYPE -> initBrand()
                LOGIN_TYPE -> initLogin()
            }
        }

    }


    companion object {
        const val PRODUCT_TYPE = "product"
        const val BRAND_TYPE = "brand"
        const val LOGIN_TYPE = "login"

        fun newInstance(context: Context, type: String, id: String = "", title: String = "") =
            Intent(context, DetailActivity::class.java).apply {
                putExtra(TYPE, type)
                putExtra(ID, id)
                putExtra(TITLE, title)
            }
    }

    private fun initProduct() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ProductSingleFragment.newInstance(id!!))
            .commit()
    }

    private fun initLogin() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, LoginFragment.newInstance())
            .commit()
    }

    private fun initBrand() {

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
