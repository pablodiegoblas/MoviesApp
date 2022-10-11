package com.example.mymoviesapp.domain.models

data class MovieModel(
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String?,
    val backdrop: String?,
    val releaseDate: String,
    val valuation: Double?,
    val genreIds: List<Int>?,
    val favourite: Boolean
)

