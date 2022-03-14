package com.example.wembleymoviesapp.data.server

import com.example.wembleymoviesapp.data.API.API
import com.example.wembleymoviesapp.data.API.APIServices.MoviesService
import com.example.wembleymoviesapp.data.mappers.ServerDataMapper
import com.example.wembleymoviesapp.ui.controllers.PopularController
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
        controllerPopular: PopularController
    ) {
        val result: Call<ResponseModel> = service.getPopularMovies()

        result.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                val popularMovies = response.body()

                if (popularMovies != null) {
                    val popularMoviesModelDB = dataMapperServer.convertListToMovieDB(popularMovies)

                    // Insert in DB all movies that returns server
                    controllerPopular.insertMoviesInDatabase(popularMoviesModelDB)

                    val listMovieItems =
                        dataMapperServer.convertListToDomainMovieItem(popularMovies)

                    // Return to controller the model
                    controllerPopular.returnsCall(listMovieItems)
                }

            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                controllerPopular.showErrorNetwork()
            }

        })
    }

    /**
     * Function that search movie by title in SearchBar for popular fragment
     */
    fun getMoviesSearched(
        searchMovieTitle: String,
        controllerPopular: PopularController
    ) {
        val result: Call<ResponseModel> = service.getSearchMovie(searchMovieTitle)

        result.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                val searchMovie = response.body()

                if (searchMovie != null) {
                    // Save in Database
                    val searchMoviesModelDB = dataMapperServer.convertListToMovieDB(searchMovie)
                    controllerPopular.insertMoviesInDatabase(searchMoviesModelDB)

                    // Charge adapter
                    val searchMoviesModelItem = dataMapperServer.convertListToDomainMovieItem(searchMovie)
                    controllerPopular.returnsCall(searchMoviesModelItem)
                }

            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                controllerPopular.showErrorNetwork()
            }

        })
    }
}