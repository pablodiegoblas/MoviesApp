package com.example.wembleymoviesapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wembleymoviesapp.data.MoviesRepositoryImpl
import com.example.wembleymoviesapp.domain.models.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val moviesRepositoryImpl: MoviesRepositoryImpl
) : ViewModel() {

    val detailMovieModel = MutableLiveData<MovieModel>()

    fun setMovie(id: Int) {
        //Find in repository the movie
        viewModelScope.launch {
            detailMovieModel.value = moviesRepositoryImpl.getMovieDatabase(id)
        }
    }

}
