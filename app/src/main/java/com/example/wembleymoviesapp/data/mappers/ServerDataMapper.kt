package com.example.wembleymoviesapp.data.mappers

import com.example.wembleymoviesapp.data.database.MovieDB
import com.example.wembleymoviesapp.data.server.RequestMovie
import com.example.wembleymoviesapp.data.server.ResponseModel
import com.example.wembleymoviesapp.domain.MovieDetail
import com.example.wembleymoviesapp.domain.MovieItem
import javax.inject.Inject

class ServerDataMapper @Inject constructor() {

    // Convert the list model from API model to MovieItem Model
    fun convertListToDomainMovieItem(responseModel: ResponseModel) =
        responseModel.results.mapIndexed { _, requestMovie ->
            convertToDomainMovieItem(requestMovie)
        }
    private fun convertToDomainMovieItem(requestMovie: RequestMovie) = with(requestMovie) {
        MovieItem(id, originalTitle, posterPath, false)
    }

    // Convert the list model from API model to Database Model
    fun convertListToMovieDB(responseModel: ResponseModel) =
        responseModel.results.mapIndexed{ _, it ->
            convertToMovieDB(it)
        }

    private fun convertToMovieDB(requestMovie: RequestMovie) = with(requestMovie) {
        MovieDB(id, originalTitle, overview, posterPath, backdropPath, releaseDate, voteAverage, false)
    }
}