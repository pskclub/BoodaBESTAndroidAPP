package com.boodabest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.boodabest.BaseFragment
import com.boodabest.R
import com.boodabest.repositories.banner.BannerViewModel
import com.boodabest.repositories.product.ProductViewModel
import com.boodabest.ui.BannerAdapter
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : BaseFragment() {
    private var productAdapter = ProductAdapter()
    private var bannerAdapter = BannerAdapter()

    private val productViewModel: ProductViewModel by viewModels {
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
        productList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = productAdapter
        }


//        bannerList.setSliderAdapter(bannerAdapter)

        productViewModel.items.observe(viewLifecycleOwner, Observer { product ->
            productAdapter.submitList(product.data)
        })

//        bannerViewModel.items.observe(viewLifecycleOwner, Observer { banner ->
//            bannerAdapter.submitList(banner.data)
//            bannerList.startAutoCycle()
//        })
    }

}
