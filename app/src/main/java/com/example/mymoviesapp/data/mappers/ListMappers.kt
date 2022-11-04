package com.example.mymoviesapp.data.mappers

import com.example.mymoviesapp.domain.models.GenreMovie
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

fun List<GenreMovie>.mapGenresSelected(genresSelected: List<GenreMovie>): List<GenreMovie> =
    map { genre ->
        genre.copy(selected = genresSelected.containsGenre(genre))
    }

fun List<GenreMovie>.containsGenre(genreItem: GenreMovie) : Boolean {
    for (genre in this) {
        if (genre.id == genreItem.id) return true
    }

    return false
}