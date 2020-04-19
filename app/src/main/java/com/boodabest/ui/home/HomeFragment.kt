package com.boodabest.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.boodabest.BaseFragment
import com.boodabest.R
import com.boodabest.repositories.banner.BannerViewModel
import com.boodabest.repositories.brand.BrandViewModel
import com.boodabest.repositories.product.ProductViewModel
import com.boodabest.ui.BannerAdapter
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : BaseFragment() {

    private var brandAdapter = BrandAdapter()
    private var bannerAdapter = BannerAdapter(context)

    private val brandViewModel: BrandViewModel by viewModels {
        viewModelFactory
    }

    private val productLatestViewModel: ProductViewModel by viewModels {
        viewModelFactory
    }

    private val productBestSellerViewModel: ProductViewModel by viewModels {
        viewModelFactory
    }

    private val bannerViewModel: BannerViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productLatestAdapter = ProductAdapter(appExecutors) { product, cardView ->
            Log.w("product_click", product.title)
        }

        val productBestSellerAdapter = ProductAdapter(appExecutors) { product, cardView ->
            Log.w("product_click", product.title)
        }
        productListLatest.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = productLatestAdapter
        }

        productListBestSeller.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = productBestSellerAdapter
        }


        brandList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = brandAdapter
        }


        bannerList.setSliderAdapter(bannerAdapter)

        productLatestViewModel.items.observe(viewLifecycleOwner, Observer { product ->
            productLatestAdapter.submitList(product.data)
        })

        productBestSellerViewModel.items.observe(viewLifecycleOwner, Observer { product ->
            productBestSellerAdapter.submitList(product.data)
        })

        brandViewModel.items.observe(viewLifecycleOwner, Observer { brand ->
            brandAdapter.submitList(brand.data)
        })

        bannerViewModel.items.observe(viewLifecycleOwner, Observer { banner ->
            bannerAdapter.renewItems(banner.data)
        })


    }

}
