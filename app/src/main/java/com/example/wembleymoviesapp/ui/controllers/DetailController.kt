package com.example.wembleymoviesapp.ui.controllers

import com.example.wembleymoviesapp.data.model.DataProvider
import com.example.wembleymoviesapp.ui.view.activities.DetailMovieActivity

class DetailController(detailMovieActivity: DetailMovieActivity) {

    private val dataProvider: DataProvider = DataProvider(detailMovieActivity)

    fun findMovie(title: String) {
        dataProvider.searchMovie(title)
    }
}