package com.example.wembleymoviesapp.data.mappers

import com.example.wembleymoviesapp.data.database.entities.MovieEntity
import com.example.wembleymoviesapp.data.server.RequestMovie
import com.example.wembleymoviesapp.domain.models.MovieModel

fun RequestMovie.toDomainModel() = with(this) {
    MovieModel(
        id = id,
        title = originalTitle,
        overview = overview,
        poster = posterPath,
        backdrop = backdropPath,
        releaseDate = releaseDate,
        valuation = voteAverage,
        favourite = false
    )
}

fun RequestMovie.toEntity() = with(this) {
    MovieEntity(
        id = id,
        title = originalTitle,
        overview = overview,
        poster = posterPath,
        backdrop = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        favourite = false
    )
}