package com.example.wembleymoviesapp.ui.controllers

import com.example.wembleymoviesapp.data.database.DBMoviesProvider
import com.example.wembleymoviesapp.ui.view.activities.DetailMovieActivity

class DetailController(detailMovieActivity: DetailMovieActivity) {

    //private val dataProvider: DataProvider = DataProvider(detailMovieActivity)
    private val dbMoviesProvider: DBMoviesProvider = DBMoviesProvider(detailMovieActivity)

    fun findMovie(id: Int) {
        //No necesario porque directamente siempre que busco peliculas las guardo
        //dataProvider.searchMovie(id)
        dbMoviesProvider.findMovie(id)
    }
}