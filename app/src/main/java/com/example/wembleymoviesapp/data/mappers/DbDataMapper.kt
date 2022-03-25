package com.example.wembleymoviesapp.data.mappers

import com.example.wembleymoviesapp.data.database.entities.MovieEntity
import com.example.wembleymoviesapp.domain.models.MovieModel

fun MovieEntity.toDomainModel(): MovieModel = with(this) {
    MovieModel(
        id = id,
        title = title,
        poster = poster,
        overview = overview,
        backdrop = backdrop,
        releaseDate = releaseDate,
        valuation = voteAverage,
        favourite = favourite
    )
}
fun MovieModel.toEntity() : MovieEntity = with(this) {
    MovieEntity(
        id = id,
        title = title,
        poster = poster,
        overview = overview,
        backdrop = backdrop,
        releaseDate = releaseDate,
        voteAverage = valuation,
        favourite = favourite
    )
}

