package com.example.mymoviesapp.ui.activities.detailMovie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviesapp.data.MoviesRepositoryImpl
import com.example.mymoviesapp.domain.models.MovieModel
import com.example.mymoviesapp.domain.models.MovieState
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

    fun changeStateMovie(state: MovieState) {
        val updatedMovie = detailMovieModel.value?.copy(state = state)
        updatedMovie?.let {
            viewModelScope.launch {
                detailMovieModel.postValue(it)
                moviesRepositoryImpl.updateMovie(it)
            }
        }
    }
}
