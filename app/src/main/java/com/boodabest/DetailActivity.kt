package com.boodabest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log

private const val TYPE = "type"
private const val ID = "id"


class DetailActivity : BaseActivity(R.layout.activity_detail) {
    private var type: String? = null
    private var id: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.let {
            type = it.getString(TYPE)
            id = it.getString(ID)

            Log.w("type", type)
            Log.w("id", id)
        }
    }

    companion object {
        const val PRODUCT_TYPE = "product"
        const val BRAND_TYPE = "brand"

        fun newInstance(context: Context, type: String, id: String) =
            Intent(context, DetailActivity::class.java).apply {
                putExtra(TYPE, type)
                putExtra(ID, id)
            }
    }

}
