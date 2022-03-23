package com.example.wembleymoviesapp.data.mappers

import com.example.wembleymoviesapp.data.database.entities.MovieEntity
import com.example.wembleymoviesapp.data.server.RequestMovie
import com.example.wembleymoviesapp.data.server.ResponseModel
import com.example.wembleymoviesapp.domain.MovieItem

// Convert the list model from API model to MovieItem Model
fun ResponseModel.convertListToDomainMovieItem() =
    this.results.mapIndexed { _, requestMovie ->
        requestMovie.convertToDomainMovieItem()
    }

private fun RequestMovie.convertToDomainMovieItem() = with(this) {
    MovieItem(id, originalTitle, posterPath, false)
}

// Convert the list model from API model to Database Model

fun ResponseModel.convertListToMovieEntity() =
    this.results.mapIndexed { _, it ->
        it.convertToMovieEntity()
    }

private fun RequestMovie.convertToMovieEntity() = with(this) {
    MovieEntity(
        id,
        title = originalTitle,
        overview,
        posterPath,
        backdropPath,
        releaseDate,
        voteAverage,
        false
    )
}