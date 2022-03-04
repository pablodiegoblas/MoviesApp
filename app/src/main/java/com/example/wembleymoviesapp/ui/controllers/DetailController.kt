package com.example.wembleymoviesapp.ui.controllers

import com.example.wembleymoviesapp.data.database.DBMoviesProvider
import com.example.wembleymoviesapp.data.database.DbDataMapper
import com.example.wembleymoviesapp.ui.view.activities.DetailMovieActivity

class DetailController(
    private val detailMovieActivity: DetailMovieActivity,
    private val dbDataMapper: DbDataMapper = DbDataMapper()
) {

    //private val dataProvider: DataProvider = DataProvider(detailMovieActivity)
    private val dbMoviesProvider: DBMoviesProvider = DBMoviesProvider(detailMovieActivity)

    fun createDB() = dbMoviesProvider.openDB()
    fun destroyDB() = dbMoviesProvider.closeDatabase()

    fun findMovie(id: Int) {
        val movieSearched = dbMoviesProvider.findMovie(id)
        if (movieSearched != null) {
            val movieConvertDetailModel = dbDataMapper.convertToDomainMovieDetail(movieSearched)

            detailMovieActivity.bindDetailMovie(movieConvertDetailModel)
        }
    }
}