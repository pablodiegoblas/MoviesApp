package com.example.mymoviesapp.data.api.APIServices

import com.example.mymoviesapp.data.server.ApiGenresMovies
import com.example.mymoviesapp.data.server.ResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/popular")
    suspend fun getPopularMovies(): ResponseModel

    @GET("search/movie")
    suspend fun getSearchMovie(@Query("query") query: String): ResponseModel

    @GET("genre/movie/list")
    suspend fun getGenresMoviesList(): ApiGenresMovies

}