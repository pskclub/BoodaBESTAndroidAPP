package com.boodabest.models

import com.google.gson.annotations.SerializedName

data class PageResponse<T>(
    @SerializedName("total") val total: Int = 0,
    @SerializedName("next") val nextPage: String = "",
    @SerializedName("current") val currentPage: String = "",
    @SerializedName("items") val items: List<T>
) {
}
