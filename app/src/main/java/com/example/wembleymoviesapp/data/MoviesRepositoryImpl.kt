package com.example.wembleymoviesapp.data

import com.example.wembleymoviesapp.data.database.dao.MoviesDao
import com.example.wembleymoviesapp.data.database.entities.MovieEntity
import com.example.wembleymoviesapp.data.mappers.*
import com.example.wembleymoviesapp.data.server.ServerMoviesDatasource
import com.example.wembleymoviesapp.domain.MovieItem
import com.example.wembleymoviesapp.domain.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val serverMoviesProvider: ServerMoviesDatasource,
    private val moviesDao: MoviesDao
) : MoviesRepository {

    /**
     * Return the movies with favourites items
     */
    override fun getAllPopularMovies(callback: (List<MovieItem>) -> Unit) {

        serverMoviesProvider.getAllPopularMoviesRequest(
            onSuccess = { responseServer ->
                val popularMoviesDBModel = responseServer.convertListToMovieEntity()
                // Insert in DB all movies that returns server
                insertMoviesInDatabase(popularMoviesDBModel)

                val listMovieItems = responseServer.convertListToDomainMovieItem()
                val listMoviesWithFav = moviesDao.getAllFavouritesMovies().convertListMovieEntityToDomainMovieItem()
                callback(listMovieItems.mapListFavourites(listMoviesWithFav))
                /*dbMoviesProvider.getAllFavouritesMovies { moviesDB ->
                    val listFavModelMovieItem = moviesDB.convertListToDomainMovieItem()

                    callback(listMovieItems.mapListFavourites(listFavModelMovieItem))
                }*/
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
                val searchMoviesModelDB = responseServer.convertListToMovieEntity()
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
        val favouritesMovies = moviesDao.getAllFavouritesMovies()
        callback(favouritesMovies.convertListMovieEntityToDomainMovieItem())
        /*dbMoviesProvider.getAllFavouritesMovies { listMoviesDB ->
            // Convert the moviesDB to MovieItem model
            callback(listMoviesDB.convertListToDomainMovieItem())
        }*/
    }

    override fun insertMoviesInDatabase(listDB: List<MovieEntity>) {
        moviesDao.insertAll(listDB)
//        dbMoviesProvider.insert(listDB)
    }

    override fun updateFavourite(movieID: Int, fav: Int) {
        moviesDao.updateFavourite(movieID, fav)
    }

/*    override fun removeFavourite(movie: MovieEntity) {
//        dbMoviesProvider.removeFavourite(id)
        moviesDao.updateFavourite(movie)
    }

    override fun setFavourite(id:) {
//        dbMoviesProvider.setFavourite(id)
        moviesDao.updateFavourite()
    }*/

    override fun getMovieDatabase(id: Int, callback: (MovieEntity) -> Unit) {
        //Check if exists this movie in database
        /*dbMoviesProvider.findMovie(
            id = id,
            onSuccess = {
                callback(it)
            }
        )*/
        callback(moviesDao.findMovie(id))
    }
}