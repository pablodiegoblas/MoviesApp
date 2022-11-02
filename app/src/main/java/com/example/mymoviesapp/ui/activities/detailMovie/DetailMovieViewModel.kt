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
    private val moviesRepository: MoviesRepositoryImpl
) : ViewModel() {

    val detailMovieModel = MutableLiveData<MovieModel>()

    fun setMovie(id: Int) {
        //Find in repository the movie
        viewModelScope.launch {
            detailMovieModel.value = moviesRepository.getMovieDatabase(id)
        }
    }

    fun evaluateMovie(valuation: String) {
        val updatedMovie = detailMovieModel.value?.copy(personalValuation = valuation.toDouble())

        updatedMovie?.let {
            viewModelScope.launch {
                detailMovieModel.postValue(it)
            }
        }

    }

    fun changeStateMovie(state: MovieState) {
        var updatedMovie = detailMovieModel.value?.copy(state = state)
        if (state != MovieState.See) updatedMovie = updatedMovie?.copy(personalValuation = null)

        updatedMovie?.let {
            viewModelScope.launch {
                detailMovieModel.postValue(it)
                moviesRepository.updateMovie(it)
            }
        }
    }

    /**
     * Function that set this movieItem as Favourite or noFavourite
     */
    fun pressFavButton() {

        val attributeFavourite = detailMovieModel.value?.favourite?.not()
        val updatedMovie = detailMovieModel.value?.copy(favourite = attributeFavourite ?: false)

        updatedMovie?.let {
            viewModelScope.launch {
                detailMovieModel.postValue(it)
                moviesRepository.updateMovie(it)
            }
        }
    }
}
