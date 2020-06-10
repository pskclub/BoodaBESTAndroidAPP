package com.boodabest.ui.home

import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.boodabest.DetailActivity
import com.boodabest.R
import com.boodabest.core.BaseFragment
import com.boodabest.database.Brand
import com.boodabest.database.Product
import com.boodabest.network.Status
import com.boodabest.repositories.banner.BannerViewModel
import com.boodabest.repositories.brand.BrandViewModel
import com.boodabest.repositories.product.ProductViewModel
import com.boodabest.ui.BannerAdapter
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private var isBrandLoaded = false
    private var isBannerLoaded = false
    private var isProductLatestLoaded = false
    private var isProductBestLoaded = false

    private val brandViewModel: BrandViewModel by viewModels {
        viewModelFactory
    }

    private val bannerViewModel: BannerViewModel by viewModels {
        viewModelFactory
    }

    private val productBestSellerViewModel: ProductViewModel by viewModels {
        viewModelFactory
    }

    private val productLatestViewModel: ProductViewModel by viewModels {
        viewModelFactory
    }


    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment().apply {}
    }

    private fun onRefreshLoaded() {
        if (
            isBrandLoaded
            && isBannerLoaded
            && isProductBestLoaded
            && isProductLatestLoaded
        ) {
            vSwipeRefresh.isRefreshing = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        app.updateTitle(getString(R.string.app_name))

        vSwipeRefresh.setOnRefreshListener {
            isBrandLoaded = false
            isBannerLoaded = false
            isProductBestLoaded = false
            isProductLatestLoaded = false

            brandViewModel.fetchItems()
            bannerViewModel.fetchItems()
            productBestSellerViewModel.fetchItems()
            productLatestViewModel.fetchItems()
        }

        initProductListLatest()
        initProductListBestSeller()
        initBannerList()
        initBrandList()
    }


    private fun initBannerList() {
        bannerViewModel.fetchItems()
        val bannerAdapter = BannerAdapter()
        bannerList.setSliderAdapter(bannerAdapter)
        bannerViewModel.items.observe(viewLifecycleOwner, Observer { banner ->
            if (banner.status == Status.LOADED) {
                isBannerLoaded = true
                onRefreshLoaded()
            }

            bannerAdapter.renewItems(banner.data)
        })
    }

    private fun initBrandList() {
        brandViewModel.fetchItems()
        val brandAdapter = BrandAdapter(onBrandClick())
        brandList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = brandAdapter
        }

        brandViewModel.items.observe(viewLifecycleOwner, Observer { brand ->
            if (brand.status == Status.LOADED) {
                isBrandLoaded = true
                onRefreshLoaded()
            }

            brandAdapter.submitList(brand.data)
        })
    }


    private fun initProductListBestSeller() {
        val productBestSellerAdapter = ProductAdapter(appExecutors, onProductClick())

        productBestSellerViewModel.fetchItems("best-seller")
        productBestSellerViewModel.items.observe(viewLifecycleOwner, Observer { product ->
            if (product.status == Status.LOADED) {
                isProductBestLoaded = true
                onRefreshLoaded()
            }
            productBestSellerAdapter.submitList(product.data)
        })

        productListBestSeller.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = productBestSellerAdapter
        }


    }

    private fun initProductListLatest() {
        val productLatestAdapter = ProductAdapter(appExecutors, onProductClick())
        productLatestViewModel.fetchItems("new-arrival")
        productLatestViewModel.items.observe(
            viewLifecycleOwner,
            Observer { product ->
                if (product.status == Status.LOADED) {
                    isProductLatestLoaded = true
                    onRefreshLoaded()
                }
                productLatestAdapter.submitList(product.data)
            })


        productListLatest.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = productLatestAdapter
        }
    }

    private fun onProductClick(): (Product, CardView) -> Unit {
        return { product, _ ->
            requireActivity().startActivity(
                DetailActivity.newInstance(
                    requireContext(),
                    DetailActivity.PRODUCT_TYPE,
                    product.id,
                    product.title
                )
            )

        }
    }

    private fun onBrandClick(): (Brand, CardView) -> Unit {
        return { brand, _ ->
            requireActivity().startActivity(
                DetailActivity.newInstance(
                    requireContext(),
                    DetailActivity.BRAND_TYPE,
                    brand.id,
                    brand.title
                )
            )
        }
    }
}
