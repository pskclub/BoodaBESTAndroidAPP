package com.boodabest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.boodabest.R
import com.boodabest.database.Banner
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.banner_item.view.*


class BannerAdapter :
    ListAdapter<Banner, BannerAdapter.BannerViewHolder>(BannerDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.banner_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BannerDiffCallback : DiffUtil.ItemCallback<Banner>() {
        override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem.imageURL == newItem.imageURL
        }
    }

    class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageViewBackground = view.iv_auto_image_slider

        fun bind(banner: Banner) {

            Glide
                .with(this.itemView.context)
                .load(banner.imageURL)
                .centerCrop()
                .into(imageViewBackground);
        }
    }
}