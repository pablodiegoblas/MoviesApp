package com.example.wembleymoviesapp.ui.viewModel

import android.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wembleymoviesapp.data.database.DBMoviesProvider
import com.example.wembleymoviesapp.data.database.MovieDB
import com.example.wembleymoviesapp.data.mappers.DbDataMapper
import com.example.wembleymoviesapp.domain.MovieItem
import com.example.wembleymoviesapp.ui.controllers.GetMoviesDB
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor()
    : ViewModel(), SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    @Inject lateinit var dbProvider: DBMoviesProvider
    @Inject lateinit var dataMapper: DbDataMapper

    val favouritesMovieModel = MutableLiveData<List<MovieItem>>()

    fun returnFavouritesMovies() {
        // Return the movies from database
        dbProvider.getAllFavouritesMovies(object : GetMoviesDB {
            override fun onSuccess(moviesDB: List<MovieDB>) {
                // Convert the moviesDB to MovieItem model
                val listFavMoviesModelItem = dataMapper.convertListToDomainMovieItem(moviesDB)

                // Save the list
                favouritesMovieModel.postValue(listFavMoviesModelItem)
            }

            override fun onError() {
                favouritesMovieModel.postValue(emptyList())
            }

        })
    }

    //Database operations
    fun createDB() = dbProvider.openDB()
    fun destroyDB() = dbProvider.closeDatabase()

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
        // busco la pelicula que se ha hecho clic y le cambio el atributo favorito
        val newList = favouritesMovieModel.value?.dropWhile { it.id == movieItem.id }

        //Change value the view model
        favouritesMovieModel.postValue(newList ?: emptyList())
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