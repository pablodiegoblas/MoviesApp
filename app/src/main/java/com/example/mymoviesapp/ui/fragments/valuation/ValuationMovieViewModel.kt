package com.example.mymoviesapp.ui.fragments.valuation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviesapp.data.MoviesRepositoryImpl
import com.example.mymoviesapp.domain.models.MovieModel
import com.example.mymoviesapp.handlers.PreferencesHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val minRate = 0.5
const val maxRate = 10.0

@HiltViewModel
class ValuationMovieViewModel @Inject constructor(
    private val moviesRepository: MoviesRepositoryImpl,
    private val preferencesHandler: PreferencesHandler
) : ViewModel() {

    fun rateMovie(movie: MovieModel, rateNumber: String, onSuccess: (String) -> Unit) {
        if (rateNumber.toDouble() in minRate..maxRate) {
            viewModelScope.launch {
                moviesRepository.ratingMovie(
                    movie = movie,
                    valuation = rateNumber.toDouble(),
                    guestSessionId = preferencesHandler.guestSessionId,
                )
                onSuccess(rateNumber)
            }
        }

    }
}