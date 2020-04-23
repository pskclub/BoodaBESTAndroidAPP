package com.boodabest.ui.product_detail

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.boodabest.BaseFragment
import com.boodabest.R
import com.boodabest.repositories.product.ProductViewModel
import com.boodabest.utils.toSpanned
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_product_detail.*

private const val PRODUCT_ID = "product_id"

class ProductDetailFragment : BaseFragment(R.layout.fragment_product_detail) {
    private var productId: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            productId = it.getString(PRODUCT_ID)
        }

        initProduct()
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

    private fun initProduct() {
        val productViewModel: ProductViewModel by viewModels {
            viewModelFactory
        }


        productViewModel.setProductId(productId!!)
        productViewModel.item.observe(viewLifecycleOwner, Observer { product ->
            if (product.data !== null) {
                Glide
                    .with(this)
                    .load(product.data.header?.coverImageURL)
                    .into(imgCover)

                Glide
                    .with(this)
                    .load(product.data.brand.thumbnailURL)
                    .into(imgBrandLogo)

                coverContainer.setBackgroundColor(Color.parseColor(product.data.brand.backgroundColor))
                txtHeaderTitle.apply {
                    text = product.data.header?.title?.toSpanned() ?: ""
                    typeface = Typeface.DEFAULT_BOLD
                    setTextColor(Color.parseColor(product.data.header?.titleColor))
                }


                txtHeaderCaption.apply {
                    text = product.data.header?.caption?.toSpanned() ?: ""
                    setTextColor(Color.parseColor(product.data.header?.titleColor))
                }

                val productThumbnailAdapter = ProductThumbnailAdapter(context)
                productImageSlider.setSliderAdapter(productThumbnailAdapter)
                productThumbnailAdapter.renewItems(product.data.galleries)
            }
        })
    }
}
