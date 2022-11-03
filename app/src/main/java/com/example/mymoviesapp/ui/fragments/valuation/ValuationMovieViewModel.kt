package com.example.mymoviesapp.ui.fragments.valuation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviesapp.R
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

    fun rateMovie(movie: MovieModel, rateString: String, onSuccess: (Double) -> Unit, onFailure: (Int) -> Unit) {
        viewModelScope.launch {
            val rateNumber = rateString.toDouble()
            if (rateNumber in minRate..maxRate &&
                (rateNumber % 0.5 == 0.0 ||
                    rateNumber % 10 == 0.0)) {
                val ratingResponse = moviesRepository.ratingMovie(
                    movie = movie,
                    valuation = rateNumber,
                    guestSessionId = preferencesHandler.guestSessionId,
                )
                if (ratingResponse.success) onSuccess(rateNumber)
                else onFailure(R.string.error_alert_rate_movie)
            }
            else onFailure(R.string.error_alert_incorrect_valor)
        }

    }
}