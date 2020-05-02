package com.boodabest.ui.home

import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.boodabest.BaseFragment
import com.boodabest.DetailActivity
import com.boodabest.R
import com.boodabest.database.Brand
import com.boodabest.database.Product
import com.boodabest.repositories.banner.BannerViewModel
import com.boodabest.repositories.brand.BrandViewModel
import com.boodabest.repositories.product.ProductViewModel
import com.boodabest.ui.BannerAdapter
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment(R.layout.fragment_home) {
    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment().apply {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initProductListLatest()
        initProductListBestSeller()
        initBannerList()
        initBrandList()
    }


    private fun initBannerList() {
        val bannerViewModel: BannerViewModel by viewModels {
            viewModelFactory
        }

        val bannerAdapter = BannerAdapter()
        bannerList.setSliderAdapter(bannerAdapter)
        bannerViewModel.items.observe(viewLifecycleOwner, Observer { banner ->
            bannerAdapter.renewItems(banner.data)
        })
    }

    private fun initBrandList() {
        val brandViewModel: BrandViewModel by viewModels {
            viewModelFactory
        }
        val brandAdapter = BrandAdapter(onBrandClick())
        brandList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = brandAdapter
        }

        brandViewModel.items.observe(viewLifecycleOwner, Observer { brand ->
            brandAdapter.submitList(brand.data)
        })
    }


    private fun initProductListBestSeller() {
        val productBestSellerViewModel: ProductViewModel by viewModels {
            viewModelFactory
        }

        val productBestSellerAdapter = ProductAdapter(appExecutors, onProductClick())

        productListBestSeller.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = productBestSellerAdapter
        }

        productBestSellerViewModel.items.observe(viewLifecycleOwner, Observer { product ->
            productBestSellerAdapter.submitList(product.data)
        })

    }

    private fun initProductListLatest() {
        val productLatestViewModel: ProductViewModel by viewModels {
            viewModelFactory
        }

        val productLatestAdapter = ProductAdapter(appExecutors, onProductClick())

        productListLatest.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = productLatestAdapter
        }

        productLatestViewModel.items.observe(viewLifecycleOwner, Observer { product ->
            productLatestAdapter.submitList(product.data)
        })
    }

    private fun onProductClick(): (Product, CardView) -> Unit {
        return { product, _ ->
            activity?.let {
                it.startActivity(context?.let { it1 ->
                    DetailActivity.newInstance(
                        it1,
                        DetailActivity.PRODUCT_TYPE,
                        product.id,
                        product.title
                    )
                })
            }
        }
    }

    private fun onBrandClick(): (Brand, CardView) -> Unit {
        return { brand, _ ->
            activity?.let {
                it.startActivity(context?.let { it1 ->
                    DetailActivity.newInstance(
                        it1,
                        DetailActivity.BRAND_TYPE,
                        brand.id,
                        brand.title
                    )
                })
            }
        }
    }
}
