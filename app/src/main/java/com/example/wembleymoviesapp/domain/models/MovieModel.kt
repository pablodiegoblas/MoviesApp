package com.example.wembleymoviesapp.domain.models

data class MovieModel(
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String?,
    val backdrop: String?,
    val releaseDate: String,
    val valuation: Double?,
    val favourite: Boolean
)
