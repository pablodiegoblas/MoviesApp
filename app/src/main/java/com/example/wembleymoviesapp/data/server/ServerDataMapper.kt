package com.example.wembleymoviesapp.data.server

import com.example.wembleymoviesapp.data.database.MovieDB
import com.example.wembleymoviesapp.data.model.RequestMovie
import com.example.wembleymoviesapp.data.model.ResponseModel
import com.example.wembleymoviesapp.domain.MovieDetail
import com.example.wembleymoviesapp.domain.MovieItem

class ServerDataMapper {

    // Convertirlo del formato de la API al formato item para mostrarlo
    fun convertListToDomainMovieItem(responseModel: ResponseModel) =
        responseModel.results.mapIndexed { _, requestMovie ->
            convertToDomainMovieItem(requestMovie)
        }
    private fun convertToDomainMovieItem(requestMovie: RequestMovie) = with(requestMovie) {
        MovieItem(id, originalTitle, posterPath, false)
    }

    // Convertirlo de formato de la API al formato detalle de pelicula
    fun convertToDomainMovieDetail(requestMovie: RequestMovie) = with(requestMovie) {
        MovieDetail(originalTitle, backdropPath, overview, voteAverage)
    }

    // Convertir de formato servidor al formato data base para guardarlo en la base de datos
    fun convertListToMovieDB(responseModel: ResponseModel) =
        responseModel.results.mapIndexed{ _, it ->
            convertToMovieDB(it)
        }

    private fun convertToMovieDB(requestMovie: RequestMovie) = with(requestMovie) {
        MovieDB(id, originalTitle, overview, posterPath, backdropPath, releaseDate, voteAverage, false)
    }
}