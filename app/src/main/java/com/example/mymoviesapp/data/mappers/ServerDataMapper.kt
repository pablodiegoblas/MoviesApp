package com.example.mymoviesapp.data.mappers

import com.example.mymoviesapp.data.server.ApiGenreMovie
import com.example.mymoviesapp.data.server.ApiGenresMovies
import com.example.mymoviesapp.data.server.ApiMovie
import com.example.mymoviesapp.domain.models.GenreMovie
import com.example.mymoviesapp.domain.models.GenresMovies
import com.example.mymoviesapp.domain.models.MovieModel

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