package com.boodabest.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.boodabest.R
import com.boodabest.database.Brand
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.brand_item.view.*


class BrandAdapter(
    private val onClick: (Brand, CardView) -> Unit
) :
    ListAdapter<Brand, BrandAdapter.BrandViewHolder>(BrandDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        return BrandViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.brand_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    class BrandDiffCallback : DiffUtil.ItemCallback<Brand>() {
        override fun areItemsTheSame(oldItem: Brand, newItem: Brand): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Brand, newItem: Brand): Boolean {
            return oldItem.title == newItem.title
        }
    }

    class BrandViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        private val cardView = view.cardView
        private val coverImage = view.coverImage
        private val logoImage = view.logoImage
        private val title = view.title

        fun bind(brand: Brand, onClick: ((Brand, CardView) -> Unit)?) {
            title.text = brand.title
            Glide
                .with(this.itemView.context)
                .load(brand.coverImageURL)
                .into(coverImage)

            Glide
                .with(this.itemView.context)
                .load(brand.logoImageURL)
                .into(logoImage)

            this.itemView.setOnClickListener {
                brand.let {
                    onClick?.invoke(it, cardView)
                }
            }
        }
    }
}