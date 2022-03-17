package com.example.wembleymoviesapp.domain

data class MovieItem(
    val id: Int,
    val title: String,
    val poster: String? = null,
    val favourite: Boolean
)

data class MovieDetail(
    val title: String,
    val backdrop: String? = null,
    val overview: String,
    val valuation: Double? = null
)
