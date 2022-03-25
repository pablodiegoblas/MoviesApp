package com.example.wembleymoviesapp.data

import com.example.wembleymoviesapp.data.database.MoviesDbDataSource
import com.example.wembleymoviesapp.data.mappers.mapListFavourites
import com.example.wembleymoviesapp.data.server.ServerMoviesDatasource
import com.example.wembleymoviesapp.domain.models.MovieModel
import com.example.wembleymoviesapp.domain.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val serverMoviesProvider: ServerMoviesDatasource,
    private val dbDataSource: MoviesDbDataSource
) : MoviesRepository {

    /**
     * Return the movies with favourites items
     */
    override suspend fun getAllPopularMovies(): List<MovieModel> {
        val popularMovies = serverMoviesProvider.getAllPopularMoviesRequest()

        dbDataSource.insertAll(popularMovies)

        val listWithFav = dbDataSource.getAllFavouritesMovies()
        return popularMovies.mapListFavourites(listWithFav)
    }

    override suspend fun getMoviesSearched(searchMovieTitle: String): List<MovieModel> {
        val searchedMovies = serverMoviesProvider.returnMoviesSearched(searchMovieTitle)

        dbDataSource.insertAll(searchedMovies)

        return searchedMovies
    }

    override suspend fun getAllFavouriteMovies(): List<MovieModel> {
        return dbDataSource.getAllFavouritesMovies()
    }

    override suspend fun updateFavourite(movieModel: MovieModel) {
        dbDataSource.updateFavourite(movieModel)
    }

    override suspend fun getMovieDatabase(id: Int): MovieModel {
        return dbDataSource.findMovie(id)
    }
}