package com.example.wembleymoviesapp.ui.viewModel

import android.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wembleymoviesapp.data.MoviesRepositoryImpl
import com.example.wembleymoviesapp.domain.MovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val moviesRepositoryImpl: MoviesRepositoryImpl
) : ViewModel(), SearchView.OnQueryTextListener,
    SearchView.OnCloseListener {

    val favouritesMovieModel = MutableLiveData<List<MovieItem>>()

    fun returnFavouritesMovies() {
        moviesRepositoryImpl.getAllFavouriteMovies {
            favouritesMovieModel.postValue(it)
        }
    }

    /**
     * Function that set this movieItem as Favourite
     */
    fun pressFavButton(movieItem: MovieItem) {
        if (movieItem.favourite) {
            // Remove favourite attribute of the movies database
            moviesRepositoryImpl.updateFavourite(movieItem.id, 0)
        } else {
            // Include favourite attribute of the movies database
            moviesRepositoryImpl.updateFavourite(movieItem.id, 1)
        }

        returnFavouritesMovies()

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