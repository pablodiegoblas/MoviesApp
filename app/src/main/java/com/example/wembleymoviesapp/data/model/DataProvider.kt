package com.example.wembleymoviesapp.data.model

import android.content.Context
import com.example.wembleymoviesapp.data.db.DBMoviesProvider
import com.example.wembleymoviesapp.ui.view.activities.DetailMovieActivity

class DataProvider(val activity: DetailMovieActivity) {

    private val dbProvider = DBMoviesProvider(activity)

    /**
     * Function find a movie first in DB and if it does not exist, a request is made to API
     */
    fun getMovie(title: String) {
        val movieDB = dbProvider.findMovie(title)

        if (movieDB !=  null) activity.bindDetailMovie(movieDB)
        else NetworkMoviesProvider.searchMovieDetail(title, activity)
    }
}