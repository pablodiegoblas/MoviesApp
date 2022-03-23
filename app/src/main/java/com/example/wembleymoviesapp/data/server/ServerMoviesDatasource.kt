package com.example.wembleymoviesapp.data.server

import com.example.wembleymoviesapp.data.API.APIServices.MoviesService
import com.example.wembleymoviesapp.domain.GetMoviesServer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ServerMoviesDatasource @Inject constructor(
    private val moviesService: MoviesService
) {

    /**
     * Function that returns all popular movies
     */
    fun getAllPopularMoviesRequest(onSuccess : (ResponseModel) -> Unit, onError : () -> Unit) {
        val resultCall: Call<ResponseModel> = moviesService.getPopularMovies()

        resultCall.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                val popularMovies = response.body()

                if (popularMovies != null) {
                    onSuccess(popularMovies)
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                onError()
            }
        })
    }

    /**
     * Function that search movie by title in SearchBar for popular fragment
     */
    fun returnMoviesSearched(
        searchMovieTitle: String,
        onSuccess: (ResponseModel) -> Unit,
        onError: () -> Unit
    ) {
        val resultCall: Call<ResponseModel> = moviesService.getSearchMovie(searchMovieTitle)

        resultCall.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                val searchMovie = response.body()

                if (searchMovie != null) {
                    onSuccess(searchMovie)
                }

            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                onError()
            }

        })
    }
}