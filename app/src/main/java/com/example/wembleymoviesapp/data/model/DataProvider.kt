package com.example.wembleymoviesapp.data.model

import com.example.wembleymoviesapp.data.db.DBMoviesProvider
import com.example.wembleymoviesapp.ui.view.activities.DetailMovieActivity

class DataProvider(val activity: DetailMovieActivity) {

    private val dbProvider = DBMoviesProvider(activity)

    /**
     * Function search a movie, first search in DB and if it does not exist, a request is made to API
     */
    fun searchMovie(title: String) {
        val movieDB = dbProvider.findMovie(title)

        if (movieDB != null) activity.bindDetailMovie(movieDB)
        else NetworkMoviesProvider.searchMovieDetail(title, activity)
    }
}