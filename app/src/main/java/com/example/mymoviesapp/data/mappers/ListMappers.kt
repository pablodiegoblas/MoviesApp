package com.example.mymoviesapp.data.mappers

import com.example.mymoviesapp.domain.models.MovieModel

fun List<MovieModel>.mapListFavourites(listFavouritesModelItem: List<MovieModel>): List<MovieModel> {
    return this.mapIndexed { _, movieItem ->
        if (listFavouritesModelItem.containsMovie(movieItem)) movieItem.copy(favourite = true)
        else movieItem
    }
}

fun List<MovieModel>.containsMovie(movieModelItem: MovieModel): Boolean {
    for (movie in this) {
        if (movie.id == movieModelItem.id) return true
    }

    return false
}