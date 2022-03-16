package com.example.wembleymoviesapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wembleymoviesapp.data.database.DBMoviesProvider
import com.example.wembleymoviesapp.data.mappers.DbDataMapper
import com.example.wembleymoviesapp.domain.MovieDetail

class DetailMovieViewModel(
    private val dbDataMapper: DbDataMapper = DbDataMapper(),
    private val dbMoviesProvider: DBMoviesProvider = DBMoviesProvider()
) : ViewModel() {

    val detailMovieModel = MutableLiveData<MovieDetail>()

    fun setMovie(id: Int) {
        val movieSearched = dbMoviesProvider.findMovie(id)
        if (movieSearched != null) {
            val movieConvertDetailModel = dbDataMapper.convertToDomainMovieDetail(movieSearched)

            detailMovieModel.postValue(movieConvertDetailModel)
        }
    }

    // Database operations
    fun createDB() = dbMoviesProvider.openDB()
    fun destroyDB() = dbMoviesProvider.closeDatabase()
}
