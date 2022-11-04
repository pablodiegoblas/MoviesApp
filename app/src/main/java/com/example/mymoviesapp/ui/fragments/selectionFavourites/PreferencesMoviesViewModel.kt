package com.example.mymoviesapp.ui.fragments.selectionFavourites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviesapp.data.MoviesRepositoryImpl
import com.example.mymoviesapp.domain.models.GenreMovie
import com.example.mymoviesapp.handlers.PreferencesHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesMoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepositoryImpl,
    private val preferencesHandler: PreferencesHandler
) : ViewModel() {

    val preferencesGenres = MutableLiveData<List<GenreMovie>>()

    fun getMovieGenres() {
        viewModelScope.launch {
            preferencesGenres.value = moviesRepository.getGenresMovies()
        }
    }

    fun saveFavoritesGenres() {
        viewModelScope.launch {
            val favoritesGenres = mutableListOf<GenreMovie>()
            preferencesGenres.value?.forEach { genre ->
                if (genre.selected) favoritesGenres.add(genre)
            }

            preferencesHandler.showChooseGenre = favoritesGenres.isEmpty()


            moviesRepository.insertGenres(favoritesGenres.toList())
        }
    }

    fun checkFavoriteGenre(genre: GenreMovie) {
        preferencesGenres.postValue(
            preferencesGenres.value?.map {
                if (it.id == genre.id) it.copy(selected = genre.selected) else it
            }
        )
    }
}