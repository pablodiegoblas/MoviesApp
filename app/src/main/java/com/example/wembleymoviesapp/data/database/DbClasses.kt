package com.example.wembleymoviesapp.data.database

data class MovieDB(
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String? = null,
    val backdrop: String? = null,
    val releaseDate: String? = null,
    val voteAverage: Double? = null,
    val favourite: Boolean
)