package com.boodabest.models

import com.google.gson.annotations.SerializedName

data class Empty(
    @SerializedName("status") val status: String = ""
)
