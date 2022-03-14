package com.example.wembleymoviesapp.data.API.APIServices

import com.example.wembleymoviesapp.data.server.ResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/popular")
    fun getPopularMovies(): Call<ResponseModel>

    @GET("search/movie")
    fun getSearchMovie(@Query("query") query: String): Call<ResponseModel>

}