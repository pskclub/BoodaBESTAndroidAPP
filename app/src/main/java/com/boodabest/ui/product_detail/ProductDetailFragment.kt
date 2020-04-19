package com.boodabest.ui.product_detail

import android.os.Bundle
import com.boodabest.BaseFragment
import com.boodabest.R

private const val PRODUCT_ID = "product_id"

class ProductDetailFragment : BaseFragment(R.layout.fragment_product_detail) {
    private var productId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productId = it.getString(PRODUCT_ID)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(productId: String) =
            ProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(PRODUCT_ID, productId)
                }
            }
    }
}
