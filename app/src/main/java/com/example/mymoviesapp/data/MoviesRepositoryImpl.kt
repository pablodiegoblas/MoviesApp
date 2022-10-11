package com.example.mymoviesapp.data

import com.example.mymoviesapp.data.database.MoviesDbDataSource
import com.example.mymoviesapp.data.mappers.mapListFavourites
import com.example.mymoviesapp.data.server.ServerMoviesDatasource
import com.example.mymoviesapp.domain.models.MovieModel
import com.example.mymoviesapp.domain.MoviesRepository
import com.example.mymoviesapp.domain.models.GenreMovie
import com.example.mymoviesapp.extension.sortedByGenres
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val serverMoviesProvider: ServerMoviesDatasource,
    private val dbDataSource: MoviesDbDataSource
) : MoviesRepository {

    /**
     * Return the movies with favourites items
     */
    override suspend fun getAllPopularMovies(): List<MovieModel> {
        val apiPopularMovies = serverMoviesProvider.getAllPopularMoviesRequest()

        dbDataSource.insertAll(apiPopularMovies)

        val listWithFavorites = dbDataSource.getAllFavouritesMovies()
        val genresFavorites = dbDataSource.getSelectedGenres()

        return apiPopularMovies.mapListFavourites(listWithFavorites).sortedByGenres(genresFavorites.map { it.id })
    }

    override suspend fun getMoviesSearched(searchMovieTitle: String): List<MovieModel> {
        val searchedMovies = serverMoviesProvider.getMoviesSearched(searchMovieTitle)

        dbDataSource.insertAll(searchedMovies)

        return searchedMovies
    }

    override suspend fun getApiGenresMovies(): List<GenreMovie> = serverMoviesProvider.getGenreMovies()

    override suspend fun getAllFavouriteMovies(): List<MovieModel> {
        return dbDataSource.getAllFavouritesMovies()
    }

    override suspend fun updateFavourite(movieModel: MovieModel) {
        dbDataSource.updateFavourite(movieModel)
    }

    override suspend fun getMovieDatabase(id: Int): MovieModel {
        return dbDataSource.findMovie(id)
    }

    override suspend fun getSelectedGenres(): List<GenreMovie> {
        return dbDataSource.getSelectedGenres()
    }

    override suspend fun insertGenres(genres: List<GenreMovie>) {
        dbDataSource.deleteGenres()
        dbDataSource.insertSelectedGenres(genres = genres)
    }
}