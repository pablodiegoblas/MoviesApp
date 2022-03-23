package com.example.wembleymoviesapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wembleymoviesapp.data.MoviesRepositoryImpl
import com.example.wembleymoviesapp.data.mappers.convertToDomainMovieDetail
import com.example.wembleymoviesapp.domain.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val moviesRepositoryImpl: MoviesRepositoryImpl
) : ViewModel() {

    val detailMovieModel = MutableLiveData<MovieDetail>()

    fun setMovie(id: Int) {
        //Find in repository the movie
        moviesRepositoryImpl.getMovieDatabase(id) { movieDB ->
            val movieConvertedDetailModel = movieDB.convertToDomainMovieDetail()

            detailMovieModel.postValue(movieConvertedDetailModel)
        }
    }

}
