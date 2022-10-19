package com.example.mymoviesapp.ui.fragments.favourites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviesapp.data.MoviesRepositoryImpl
import com.example.mymoviesapp.domain.models.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val moviesRepositoryImpl: MoviesRepositoryImpl
) : ViewModel() {

    val favouritesMovieModel = MutableLiveData<List<MovieModel>>()

    fun returnFavouritesMovies() {
        viewModelScope.launch {
            favouritesMovieModel.value = moviesRepositoryImpl.getAllFavouriteMovies()
        }
    }

    /**
     * Function that set this movieItem as Favourite
     */
    fun pressFavButton(movieModelItem: MovieModel) {
        val attributeFavourite: Boolean = movieModelItem.favourite.not()

        viewModelScope.launch {
            moviesRepositoryImpl.updateMovie((movieModelItem.copy(favourite = attributeFavourite)))

            returnFavouritesMovies()
        }
    }

    fun onQueryTextSubmit(text: String) {
        if (text.isNotEmpty()) {
            // Filter the list by title
            val listFilter =
                favouritesMovieModel.value?.filter { movieDB -> text in movieDB.title }

            listFilter?.let { favouritesMovieModel.postValue(it) }
        }
    }

}