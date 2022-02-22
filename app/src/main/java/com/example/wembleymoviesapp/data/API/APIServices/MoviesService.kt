package com.example.wembleymoviesapp.data.API.APIServices

import com.example.wembleymoviesapp.data.model.ResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String): Call<ResponseModel>

    @GET("search/movie")
    fun getSearchMovie(@Query("api_key") apiKey: String, @Query("query") query: String): Call<ResponseModel>

}