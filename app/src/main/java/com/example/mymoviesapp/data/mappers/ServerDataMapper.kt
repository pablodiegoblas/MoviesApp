package com.example.mymoviesapp.data.mappers

import com.example.mymoviesapp.data.server.*
import com.example.mymoviesapp.domain.models.*

fun ApiMovie.toDomainModel() =
    MovieModel(
        id = id,
        title = originalTitle.orEmpty(),
        overview = overview,
        poster = posterPath,
        backdrop = backdropPath,
        releaseDate = releaseDate,
        valuation = voteAverage,
        favourite = false,
        genreIds = genreIds
    )

fun ApiGenresMovies.toDomainModel() =
    GenresMovies(
        genres = genres.map { it.toDomainModel() }
    )
fun ApiGenreMovie.toDomainModel() =
    GenreMovie(
        id = id,
        name = name,
        selected = false
    )

fun ApiGuestSession.toDomainModel() =
    GuestSession(
        success = success,
        guestSessionId = guestSessionId,
        expiresAt = expiresAt
    )

fun ApiRatingResponse.toDomainModel() =
    RatingResponse(
        success = success,
        statusMessage = message
    )