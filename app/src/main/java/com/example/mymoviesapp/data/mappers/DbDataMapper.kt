package com.example.mymoviesapp.data.mappers

import com.example.mymoviesapp.data.database.entities.GenreEntity
import com.example.mymoviesapp.data.database.entities.MovieEntity
import com.example.mymoviesapp.domain.models.GenreMovie
import com.example.mymoviesapp.domain.models.MovieModel
import com.example.mymoviesapp.domain.models.MovieState

fun MovieEntity.toDomainModel(): MovieModel =
    MovieModel(
        id = id,
        title = title,
        poster = poster,
        overview = overview,
        backdrop = backdrop,
        releaseDate = releaseDate,
        valuation = voteAverage,
        personalValuation = personalValuation,
        favourite = favourite,
        genreIds = null,
        state = movieState.toDomainModel()
    )

private fun Int?.toDomainModel(): MovieState? =
    when(this) {
        1 -> MovieState.See
        2 -> MovieState.Pending
        3 -> MovieState.NotSee
        else -> MovieState.NotSelected
    }

fun MovieModel.toEntity() : MovieEntity =
    MovieEntity(
        id = id,
        title = title,
        poster = poster,
        overview = overview,
        backdrop = backdrop,
        releaseDate = releaseDate.orEmpty(),
        voteAverage = valuation,
        personalValuation = personalValuation,
        favourite = favourite,
        movieState = state.toEntity()
    )

private fun MovieState?.toEntity(): Int =
    when(this) {
        MovieState.See -> 1
        MovieState.Pending -> 2
        MovieState.NotSee -> 3
        else -> 0
    }

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
