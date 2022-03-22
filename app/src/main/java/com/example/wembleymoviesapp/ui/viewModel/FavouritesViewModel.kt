package com.example.wembleymoviesapp.ui.viewModel

import android.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wembleymoviesapp.data.database.DBMoviesProvider
import com.example.wembleymoviesapp.data.database.MovieDB
import com.example.wembleymoviesapp.data.mappers.convertListToDomainMovieItem
import com.example.wembleymoviesapp.domain.GetMoviesDB
import com.example.wembleymoviesapp.domain.MovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor() : ViewModel(), SearchView.OnQueryTextListener,
    SearchView.OnCloseListener {

    @Inject
    lateinit var dbProvider: DBMoviesProvider

    val favouritesMovieModel = MutableLiveData<List<MovieItem>>()

    fun returnFavouritesMovies() {
        // Return the movies from database
        dbProvider.getAllFavouritesMovies(object : GetMoviesDB {
            override fun onSuccess(moviesDB: List<MovieDB>) {
                // Convert the moviesDB to MovieItem model
                val listFavMoviesModelItem = moviesDB.convertListToDomainMovieItem()

                // Save the list
                favouritesMovieModel.postValue(listFavMoviesModelItem)
            }

            override fun onError() {
                favouritesMovieModel.postValue(emptyList())
            }

        })
    }

    /**
     * Function that set this movieItem as Favourite
     */
    fun pressFavButton(movieItem: MovieItem) {
        if (movieItem.favourite) {
            // Remove favourite attribute of the movies database
            dbProvider.removeFavourite(movieItem.id)
        } else {
            // Include favourite attribute of the movies database
            dbProvider.setFavourite(movieItem.id)
        }

        //Change value the view model returns all popular movies
        dbProvider.getAllFavouritesMovies(object : GetMoviesDB {
            override fun onSuccess(moviesDB: List<MovieDB>) {
                val newList = moviesDB.convertListToDomainMovieItem()
                favouritesMovieModel.postValue(newList)
            }

            override fun onError() {
                favouritesMovieModel.postValue(emptyList())
            }

        })
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