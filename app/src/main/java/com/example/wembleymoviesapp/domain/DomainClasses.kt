package com.example.wembleymoviesapp.domain

data class MoviesItemsList(val listItems: MutableList<MovieItem>)

data class MovieItem(
    val id: Int,
    val title: String,
    val poster: String? = null,
    var favourite: Boolean
)

data class MovieDetail(
    val title: String,
    val backdrop: String? = null,
    val overview: String,
    val valoration: Double? = null
)