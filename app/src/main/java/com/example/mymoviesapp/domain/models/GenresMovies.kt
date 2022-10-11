package com.example.mymoviesapp.domain.models

data class GenresMovies(
    val genres: List<GenreMovie>
)
data class GenreMovie(
    val id: Int,
    val name: String,
    val selected: Boolean
)
