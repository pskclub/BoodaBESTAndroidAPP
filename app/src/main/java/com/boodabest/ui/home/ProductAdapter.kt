package com.boodabest.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.boodabest.AppExecutors
import com.boodabest.R
import com.boodabest.database.Product
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.product_item.view.*


class ProductAdapter(
    appExecutors: AppExecutors,
    private val callback: ((Product, CardView) -> Unit)?
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position), callback)
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
        private val cardView = view.cardView
        private val title = view.title
        private val thumbnailURL = view.thumbnailURL
        private val brandTitle = view.brandTitle
        private val price = view.price
        private val pricePromotion = view.pricePromotion


        fun bind(product: Product, callback: ((Product, CardView) -> Unit)?) {
            title.text = product.title
            brandTitle.text = product.brand.title
            price.text = product.price
            pricePromotion.text = product.pricePromotion

            Glide
                .with(this.itemView.context)
                .load(product.thumbnailURL)
                .placeholder(R.drawable.product_thumbnail_placeholder)
                .into(thumbnailURL)

            this.itemView.setOnClickListener {
                product.let {
                    callback?.invoke(it, cardView)
                }
            }
        }
    }
}