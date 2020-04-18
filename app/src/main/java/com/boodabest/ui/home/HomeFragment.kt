package com.boodabest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.boodabest.AppExecutors
import com.boodabest.BaseFragment
import com.boodabest.R
import com.boodabest.repositories.product.ProductViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import javax.inject.Inject


class HomeFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private var productAdapter = ProductListAdapter()

    private val productViewModel: ProductViewModel by viewModels {
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
            layoutManager = LinearLayoutManager(context)
            adapter = productAdapter
        }
        productViewModel.items.observe(viewLifecycleOwner, Observer { items ->
            productAdapter.submitList(items.data)
        })
    }

}
