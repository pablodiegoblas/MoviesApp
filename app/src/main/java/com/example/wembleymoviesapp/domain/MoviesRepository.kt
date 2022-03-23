package com.example.wembleymoviesapp.domain

import com.example.wembleymoviesapp.data.database.entities.MovieEntity

interface MoviesRepository {

    fun getAllPopularMovies(callback: (List<MovieItem>) -> Unit)

    fun getMoviesSearched(text: String, callback: (List<MovieItem>) -> Unit)

    fun getAllFavouriteMovies(callback: (List<MovieItem>) -> Unit)

    fun insertMoviesInDatabase(listDB: List<MovieEntity>)

    /*fun removeFavourite(id: Int)

    fun setFavourite(id: Int)*/

    //    fun updateFavourite(movie: MovieEntity)
    fun updateFavourite(movieID: Int, fav: Int)

    fun getMovieDatabase(id: Int, callback: (MovieEntity) -> Unit)
}