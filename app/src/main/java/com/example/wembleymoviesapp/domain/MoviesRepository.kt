package com.example.wembleymoviesapp.domain

import com.example.wembleymoviesapp.data.database.MovieDB

interface MoviesRepository {

    fun getAllPopularMovies(callback: (List<MovieItem>) -> Unit)

    fun getMoviesSearched(text: String, callback: (List<MovieItem>) -> Unit)

    fun getAllFavouriteMovies(callback: (List<MovieItem>) -> Unit)

    fun insertMoviesInDatabase(listDB: List<MovieDB>)

    fun removeFavourite(id: Int)

    fun setFavourite(id: Int)

    fun getMovieDatabase(id: Int, callback: (MovieDB) -> Unit)
}