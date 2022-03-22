package com.example.wembleymoviesapp.data.mappers

import com.example.wembleymoviesapp.domain.MovieItem

fun List<MovieItem>.mapListFavourites(listFavouritesModelItem: List<MovieItem>): List<MovieItem> {
    return this.mapIndexed { _, movieItem ->
        if (listFavouritesModelItem.containsMovie(movieItem)) movieItem.copy(favourite = true)
        else movieItem
    }
}
fun List<MovieItem>.containsMovie(movieItem: MovieItem) : Boolean {
    for (movie in this) {
        if (movie.id == movieItem.id) return true
    }

    return false
}