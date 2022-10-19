package com.example.mymoviesapp.domain

import com.example.mymoviesapp.domain.models.GenreMovie
import com.example.mymoviesapp.domain.models.MovieModel

interface MoviesRepository {

    suspend fun getAllPopularMovies(): List<MovieModel>

    suspend fun getMoviesSearched(searchMovieTitle: String): List<MovieModel>

    suspend fun getApiGenresMovies() : List<GenreMovie>

    suspend fun getAllFavouriteMovies(): List<MovieModel>

    suspend fun updateMovie(movieModel: MovieModel)

    suspend fun getMovieDatabase(id: Int) : MovieModel

    suspend fun getSelectedGenres(): List<GenreMovie>

    suspend fun insertGenres(genres: List<GenreMovie>)
}