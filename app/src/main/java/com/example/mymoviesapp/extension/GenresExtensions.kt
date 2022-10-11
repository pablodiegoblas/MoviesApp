package com.example.mymoviesapp.extension

import com.example.mymoviesapp.domain.models.MovieModel

fun List<MovieModel>.sortedByGenres(favoritesGenres: List<Int>): List<MovieModel> {
    val listMovies = this.toMutableList()
    val moviesWithGenresFav = mutableListOf<MovieModel>()

    this.forEach { movie ->
        movie.genreIds?.forEach { genre ->
            if (genre in favoritesGenres) {
                if (moviesWithGenresFav.contains(movie).not()) moviesWithGenresFav.add(movie)
            }
        }
    }

    listMovies.removeAll(moviesWithGenresFav)
    listMovies.addAll(0, moviesWithGenresFav)

    return listMovies.toList()
}