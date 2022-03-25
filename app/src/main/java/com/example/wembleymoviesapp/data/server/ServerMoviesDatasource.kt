package com.example.wembleymoviesapp.data.server

import com.example.wembleymoviesapp.data.API.APIServices.MoviesService
import com.example.wembleymoviesapp.data.mappers.toDomainModel
import com.example.wembleymoviesapp.domain.models.MovieModel
import javax.inject.Inject

class ServerMoviesDatasource @Inject constructor(
    private val moviesService: MoviesService
) {

    /**
     * Function that returns all popular movies
     */
    suspend fun getAllPopularMoviesRequest(): List<MovieModel> {
        return moviesService.getPopularMovies().results.map { it.toDomainModel() }
    }

    /**
     * Function that search movie by title in SearchBar for popular fragment
     */
    suspend fun returnMoviesSearched(
        searchMovieTitle: String
    ): List<MovieModel> {
        return moviesService.getSearchMovie(searchMovieTitle).results.map { it.toDomainModel() }
    }
}