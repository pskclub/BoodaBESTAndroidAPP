package com.boodabest.models

data class RepoOptions(
    val isNetworkOnly: Boolean = false
)

data class RepoPageOptions(
    val limit: Int = 16,
    val page: String?
)