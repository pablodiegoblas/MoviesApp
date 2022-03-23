package com.example.wembleymoviesapp.data

import com.example.wembleymoviesapp.data.database.DBMoviesDatasource
import com.example.wembleymoviesapp.data.database.MovieDB
import com.example.wembleymoviesapp.data.mappers.convertListToDomainMovieItem
import com.example.wembleymoviesapp.data.mappers.convertListToMovieDB
import com.example.wembleymoviesapp.data.mappers.mapListFavourites
import com.example.wembleymoviesapp.data.server.ServerMoviesDatasource
import com.example.wembleymoviesapp.domain.MovieItem
import com.example.wembleymoviesapp.domain.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val dbMoviesProvider: DBMoviesDatasource,
    private val serverMoviesProvider: ServerMoviesDatasource
) : MoviesRepository {

    override fun getAllPopularMovies(callback: (List<MovieItem>) -> Unit) {

        serverMoviesProvider.getAllPopularMoviesRequest(
            onSuccess = { responseServer ->
                val popularMoviesDBModel = responseServer.convertListToMovieDB()
                // Insert in DB all movies that returns server
                insertMoviesInDatabase(popularMoviesDBModel)

                val listMovieItems = responseServer.convertListToDomainMovieItem()

                dbMoviesProvider.getAllFavouritesMovies { moviesDB ->
                    val listFavModelMovieItem = moviesDB.convertListToDomainMovieItem()

                    callback(listMovieItems.mapListFavourites(listFavModelMovieItem))
                }
            },
            onError = {
                callback(emptyList())
            }
        )

    }

    override fun getMoviesSearched(text: String, callback: (List<MovieItem>) -> Unit) {
        serverMoviesProvider.returnMoviesSearched(text,
            onSuccess = { responseServer ->
                // Save in Database
                val searchMoviesModelDB = responseServer.convertListToMovieDB()
                insertMoviesInDatabase(searchMoviesModelDB)

                // Set the value data
                callback(responseServer.convertListToDomainMovieItem())
            },
            onError = {
                callback(emptyList())
            }
        )
    }

    override fun getAllFavouriteMovies(callback: (List<MovieItem>) -> Unit) {
        // Return the movies from database

        dbMoviesProvider.getAllFavouritesMovies { listMoviesDB ->
            // Convert the moviesDB to MovieItem model
            callback(listMoviesDB.convertListToDomainMovieItem())
        }
    }

    override fun insertMoviesInDatabase(listDB: List<MovieDB>) {
        dbMoviesProvider.insert(listDB)
    }

    override fun removeFavourite(id: Int) {
        dbMoviesProvider.removeFavourite(id)
    }

    override fun setFavourite(id: Int) {
        dbMoviesProvider.setFavourite(id)
    }

    override fun getMovieDatabase(id: Int, callback: (MovieDB) -> Unit) {
        //Check if exists this movie in database
        dbMoviesProvider.findMovie(
            id = id,
            onSuccess = {
                callback(it)
            }
        )
    }
}