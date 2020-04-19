package com.boodabest.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.boodabest.R
import com.boodabest.database.Product
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.product_item.view.*


class ProductAdapter :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.title == newItem.title
        }
    }

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.title
        private val thumbnailURL = view.thumbnailURL
        private val brandTitle = view.brandTitle
        private val price = view.price
        private val pricePromotion = view.pricePromotion

        fun bind(product: Product) {
            title.text = product.title
            brandTitle.text = product.brand.title
            price.text = product.price
            pricePromotion.text = product.pricePromotion

            Glide
                .with(this.itemView.context)
                .load(product.thumbnailURL)
                .centerCrop()
                .into(thumbnailURL)
            title.text = product.title
        }
    }
}