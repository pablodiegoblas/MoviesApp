package com.example.wembleymoviesapp.data.mappers

import com.example.wembleymoviesapp.data.database.MovieDB
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
fun ResponseModel.convertListToMovieDB() =
    this.results.mapIndexed { _, it ->
        it.convertToMovieDB()
    }

private fun RequestMovie.convertToMovieDB() = with(this) {
    MovieDB(id, originalTitle, overview, posterPath, backdropPath, releaseDate, voteAverage, false)
}