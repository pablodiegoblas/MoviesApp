package com.example.mymoviesapp.data.mappers

import com.example.mymoviesapp.data.database.entities.GenreEntity
import com.example.mymoviesapp.data.database.entities.MovieEntity
import com.example.mymoviesapp.domain.models.GenreMovie
import com.example.mymoviesapp.domain.models.MovieModel

fun MovieEntity.toDomainModel(): MovieModel =
    MovieModel(
        id = id,
        title = title,
        poster = poster,
        overview = overview,
        backdrop = backdrop,
        releaseDate = releaseDate,
        valuation = voteAverage,
        favourite = favourite,
        genreIds = null
    )
fun MovieModel.toEntity() : MovieEntity =
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

fun GenreMovie.toEntity() : GenreEntity =
    GenreEntity(
        id = id,
        name = name,
        selected = selected
    )
fun GenreEntity.toDomain() : GenreMovie =
    GenreMovie(
        id = id,
        name = name,
        selected = selected
    )
