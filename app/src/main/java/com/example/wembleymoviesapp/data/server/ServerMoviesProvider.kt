package com.example.wembleymoviesapp.data.server

import androidx.fragment.app.Fragment
import com.example.wembleymoviesapp.data.API.API
import com.example.wembleymoviesapp.data.API.APIServices.MoviesService
import com.example.wembleymoviesapp.data.database.DbDataMapper
import com.example.wembleymoviesapp.data.model.ResponseModel
import com.example.wembleymoviesapp.ui.controllers.FavouritesController
import com.example.wembleymoviesapp.ui.controllers.PopularController
import com.example.wembleymoviesapp.ui.view.activities.DetailMovieActivity
import com.example.wembleymoviesapp.ui.view.fragments.PopularMoviesFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServerMoviesProvider(
    private val dataMapperServer: ServerDataMapper = ServerDataMapper()
) {

    private val service: MoviesService = API.retrofit.create(MoviesService::class.java)

    /**
     * Function that returns all popular movies
     */
    fun getAllPopularMoviesRequest(
        controllerPopular: PopularController?,
        controllerFav: FavouritesController?
    ) {
        val result: Call<ResponseModel> = service.getPopularMovies(API.API_KEY)

        result.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                if (controllerPopular != null) {
                    val popularMovies = response.body()

                    if (popularMovies != null) {
                        val popularMoviesModelDB = dataMapperServer.convertListToMovieDB(popularMovies)

                        // Insert in DB all movies that returns server
                        controllerPopular.insertMoviesInDatabase(popularMoviesModelDB)

                        val listMovieItems = dataMapperServer.convertListToDomainMovieItem(popularMovies)

                        // Return to controller the model
                        controllerPopular.returnsCall(listMovieItems)
                    }

                }
                else if (controllerFav != null) {

                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                if (controllerPopular != null) {
                    controllerPopular.showErrorNetwork()
                }
                else if(controllerFav != null) {
                    controllerFav.showErrorNetwork()
                }
            }

        })
    }

    /**
     * Function that search movie by title in SearchBar
     * Dep
     */
    fun getMoviesSearched(
        fragment: Fragment,
        searchMovieTitle: String,
        controllerPopular: PopularController?,
        controllerFav: FavouritesController?
    ) {
        val result: Call<ResponseModel> = service.getSearchMovie(API.API_KEY, searchMovieTitle)

        result.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                val searchMovie = response.body()

                when (fragment) {
                    is PopularMoviesFragment -> {
                        // Save in Database
                        val searchMoviesModelDB = searchMovie?.let {
                            dataMapperServer.convertListToMovieDB(it)
                        }
                        searchMoviesModelDB?.let { controllerPopular?.insertMoviesInDatabase(it) }

                        // Charge adapter
                        val searchMoviesModelItem = searchMovie?.let {
                            dataMapperServer.convertListToDomainMovieItem(searchMovie)
                        }
                        searchMoviesModelItem?.let { controllerPopular?.returnsCall(it) }
                    }
                    //is FavMoviesFragment -> searchMovie?.let { fragment.createAdapter(it.results) }
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                when (fragment) {
                    is PopularMoviesFragment -> fragment.showErrorAPI()
                    //is FavMoviesFragment -> fragment.showErrorAPI()
                }
            }

        })
    }

    /**
     * Function that search a Movie by her original title
     */
    fun searchMovieDetail(title: String, activity: DetailMovieActivity) {
        val result: Call<ResponseModel> = service.getSearchMovie(API.API_KEY, title)

        result.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                val movieSearched = response.body()?.results?.get(0)
                if (movieSearched != null) {
                    activity.bindDetailMovie(dataMapperServer.convertToDomainMovieDetail(movieSearched))
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                activity.showError()
            }

        })
    }
}