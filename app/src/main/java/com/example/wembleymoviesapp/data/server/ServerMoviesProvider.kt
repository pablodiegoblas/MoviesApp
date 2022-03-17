package com.example.wembleymoviesapp.data.server

import com.example.wembleymoviesapp.data.API.APIServices.MoviesService
import com.example.wembleymoviesapp.ui.controllers.GetMoviesServer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ServerMoviesProvider @Inject constructor() {

    @Inject lateinit var moviesService: MoviesService

    /**
     * Function that returns all popular movies
     */
    fun getAllPopularMoviesRequest(result: GetMoviesServer) {
        val resultCall: Call<ResponseModel> = moviesService.getPopularMovies()

        resultCall.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                val popularMovies = response.body()

                if (popularMovies != null) {
                    return result.onSuccess(popularMovies)
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                result.onError()
            }
        })
    }

    /**
     * Function that search movie by title in SearchBar for popular fragment
     */
    fun getMoviesSearched(
        searchMovieTitle: String,
        result: GetMoviesServer
    ) {
        val resultCall: Call<ResponseModel> = moviesService.getSearchMovie(searchMovieTitle)

        resultCall.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                val searchMovie = response.body()

                if (searchMovie != null) {
                    return result.onSuccess(searchMovie)
                }

            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                result.onError()
            }

        })
    }
}