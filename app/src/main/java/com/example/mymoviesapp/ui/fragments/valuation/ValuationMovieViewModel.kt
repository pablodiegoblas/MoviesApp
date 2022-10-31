package com.example.mymoviesapp.ui.fragments.valuation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviesapp.data.MoviesRepositoryImpl
import com.example.mymoviesapp.handlers.PreferencesHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val minRate = 0.5
const val maxRate = 10

@HiltViewModel
class ValuationMovieViewModel @Inject constructor(
    private val moviesRepository: MoviesRepositoryImpl,
    private val preferencesHandler: PreferencesHandler
) : ViewModel() {

    fun rateMovie(movieId: Int, rateNumber: Double) = viewModelScope.launch {
        if (rateNumber in minRate..maxRate.toDouble()) {
            moviesRepository.ratingMovie(
                movie = movieId,
                valuation = rateNumber.toLong(),
                guestSessionId = preferencesHandler.guestSessionId
            )
        }
    }
}