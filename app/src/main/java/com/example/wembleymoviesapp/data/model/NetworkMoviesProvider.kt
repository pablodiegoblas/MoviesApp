package com.example.wembleymoviesapp.data.model

import androidx.fragment.app.Fragment
import com.example.wembleymoviesapp.data.API.API
import com.example.wembleymoviesapp.data.API.APIServices.MoviesService
import com.example.wembleymoviesapp.ui.view.activities.DetailMovieActivity
import com.example.wembleymoviesapp.ui.view.fragments.FavMoviesFragment
import com.example.wembleymoviesapp.ui.view.fragments.PopularMoviesFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkMoviesProvider {

    companion object {
        private val service: MoviesService = API.retrofit.create(MoviesService::class.java)

        /**
         * Function that returns all popular movies
         */
        fun getAllPopularMoviesRequest(
            popularMoviesFragment: PopularMoviesFragment
        ) {
            val result: Call<ResponseModel> = service.getPopularMovies(API.API_KEY)

            result.enqueue(object : Callback<ResponseModel> {
                override fun onResponse(
                    call: Call<ResponseModel>,
                    response: Response<ResponseModel>
                ) {
                    val popularMovies = response.body()

                    popularMovies?.let { popularMoviesFragment.createAdapter(it.results) }
                }

                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    popularMoviesFragment.showErrorAPI()
                }

            })
        }

        /**
         * Function that search movie by title in SearchBar
         */
        fun getMoviesSearched(fragment: Fragment, searchMovieTitle: String) {
            val result: Call<ResponseModel> = service.getSearchMovie(API.API_KEY, searchMovieTitle)

            result.enqueue(object : Callback<ResponseModel> {
                override fun onResponse(
                    call: Call<ResponseModel>,
                    response: Response<ResponseModel>
                ) {
                    val searchMovie = response.body()

                    when (fragment) {
                        is PopularMoviesFragment -> searchMovie?.let { fragment.createAdapter(it.results) }
                        is FavMoviesFragment -> searchMovie?.let { fragment.createAdapter(it.results) }
                    }
                }

                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    when (fragment) {
                        is PopularMoviesFragment -> fragment.showErrorAPI()
                        is FavMoviesFragment -> fragment.showErrorAPI()
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
                    val movie = response.body()?.results?.get(0)
                    activity.bindDetailMovie(movie)
                }

                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    activity.showError()
                }

            })
        }
    }
}