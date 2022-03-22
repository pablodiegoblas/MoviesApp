package com.example.wembleymoviesapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wembleymoviesapp.data.database.DBMoviesProvider
import com.example.wembleymoviesapp.data.mappers.convertToDomainMovieDetail
import com.example.wembleymoviesapp.domain.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor()
    : ViewModel() {

    @Inject lateinit var dbMoviesProvider: DBMoviesProvider

    val detailMovieModel = MutableLiveData<MovieDetail>()

    fun setMovie(id: Int) {
        val movieSearched = dbMoviesProvider.findMovie(id)
        if (movieSearched != null) {
            val movieConvertDetailModel = movieSearched.convertToDomainMovieDetail()

            detailMovieModel.postValue(movieConvertDetailModel)
        }
    }

}
