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
        this.initProductListLatest()
        this.initProductListBestSeller()
        this.initBannerList()
        this.initBrandList()
    }

    private fun initBrandList() {
        val brandAdapter = BrandAdapter()
        brandList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = brandAdapter
        }

        brandViewModel.items.observe(viewLifecycleOwner, Observer { brand ->
            brandAdapter.submitList(brand.data)
        })
    }

    private fun initBannerList() {
        val bannerAdapter = BannerAdapter(context)
        bannerList.setSliderAdapter(bannerAdapter)
        bannerViewModel.items.observe(viewLifecycleOwner, Observer { banner ->
            bannerAdapter.renewItems(banner.data)
        })
    }

    private fun initProductListBestSeller() {
        val productBestSellerAdapter = ProductAdapter(appExecutors) { product, cardView ->
            Log.w("product_click", product.title)
        }

        productListBestSeller.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = productBestSellerAdapter
        }

        productBestSellerViewModel.items.observe(viewLifecycleOwner, Observer { product ->
            productBestSellerAdapter.submitList(product.data)
        })

    }

    private fun initProductListLatest() {
        val productLatestAdapter = ProductAdapter(appExecutors) { product, cardView ->
            Log.w("product_click", product.title)
        }

        productListLatest.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = productLatestAdapter
        }

        productLatestViewModel.items.observe(viewLifecycleOwner, Observer { product ->
            productLatestAdapter.submitList(product.data)
        })
    }
}
