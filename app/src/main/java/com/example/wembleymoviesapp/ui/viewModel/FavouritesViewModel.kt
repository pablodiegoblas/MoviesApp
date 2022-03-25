package com.example.wembleymoviesapp.ui.viewModel

import android.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wembleymoviesapp.data.MoviesRepositoryImpl
import com.example.wembleymoviesapp.domain.models.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val moviesRepositoryImpl: MoviesRepositoryImpl
) : ViewModel(), SearchView.OnQueryTextListener,
    SearchView.OnCloseListener {

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
        val attributeFavourite: Boolean = !movieModelItem.favourite

        viewModelScope.launch {
            moviesRepositoryImpl.updateFavourite((movieModelItem.copy(favourite = attributeFavourite)))

            returnFavouritesMovies()
        }
    }

    override fun onQueryTextSubmit(text: String?): Boolean {
        text?.let { textFind ->
            if (textFind != "") {
                // Filter the list by title
                val listFilter =
                    favouritesMovieModel.value?.filter { movieDB -> text in movieDB.title }

                listFilter?.let { favouritesMovieModel.postValue(it) }
            }
        }
        return true
    }

    override fun onQueryTextChange(text: String?): Boolean {
        return false
    }

    override fun onClose(): Boolean {
        returnFavouritesMovies()
        return false
    }
}