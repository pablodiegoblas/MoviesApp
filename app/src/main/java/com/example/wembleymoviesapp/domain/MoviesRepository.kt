package com.example.wembleymoviesapp.domain

import com.example.wembleymoviesapp.domain.models.MovieModel

interface MoviesRepository {

    suspend fun getAllPopularMovies(): List<MovieModel>

    suspend fun getMoviesSearched(searchMovieTitle: String): List<MovieModel>

    suspend fun getAllFavouriteMovies(): List<MovieModel>

    suspend fun updateFavourite(movieModel: MovieModel)

    suspend fun getMovieDatabase(id: Int) : MovieModel
}