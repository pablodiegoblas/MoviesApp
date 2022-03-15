package com.example.wembleymoviesapp.ui.controllers

import android.widget.SearchView
import com.example.wembleymoviesapp.data.database.DBMoviesProvider
import com.example.wembleymoviesapp.data.mappers.DbDataMapper
import com.example.wembleymoviesapp.domain.MovieItem
import com.example.wembleymoviesapp.ui.view.fragments.FavMoviesFragment
import kotlin.concurrent.thread

class FavouritesController(
    private val favMoviesFragment: FavMoviesFragment,
    private val dbProvider: DBMoviesProvider = DBMoviesProvider((favMoviesFragment.requireContext())),
    private val dataMapper: DbDataMapper = DbDataMapper()
) : SearchView.OnQueryTextListener {

    private var listFavourites: MutableList<MovieItem> = mutableListOf()

    fun getFavouritesMovies() {
        thread {
            // Return the movies from database
            val listFavMoviesCall = dbProvider.getAllFavouritesMovies()

            // Convert the movies to MovieItem model
            val listFavMoviesModelItem = dataMapper.convertListToDomainMovieItem(listFavMoviesCall)

            // Store the list
            listFavourites = listFavMoviesModelItem.toMutableList()

            // If the list is not empty I update it else show the text
            if (listFavourites
                    .isNotEmpty()
            ) favMoviesFragment.updateFavouritesMoviesAdapter(
                listFavourites
            )
            else favMoviesFragment.showNotMoviesFavText()

            // Stop refreshing SwipeRefreshLayout Layout
            favMoviesFragment.swipeRefreshLayout?.isRefreshing = false
        }

    }

    fun createDB() = dbProvider.openDB()
    fun destroyDB() = dbProvider.closeDatabase()

    /**
     * Método que hace lo oportuno al presionar el boton de favorito de un item
     */
    fun pressFavButton(movieItem: MovieItem) {

        if (movieItem.favourite) {
            // busco la pelicula que se ha hecho clic y le cambio el atributo favorito
            listFavourites =
                listFavourites.map { if (it.id == movieItem.id) it.copy(favourite = false) else it }
                    .toMutableList()

            // Remove favourite attribute of the movies database
            thread {
                dbProvider.removeFavourite(movieItem.id)
            }

        } else {
            listFavourites =
                listFavourites.map { if (it.id == movieItem.id) it.copy(favourite = true) else it }
                    .toMutableList()

            // Include favourite attribute of the movies database
            thread {
                dbProvider.setFavourite(movieItem.id)
            }

        }

        favMoviesFragment.updateFavouritesMoviesAdapter(listFavourites)

    }

    override fun onQueryTextSubmit(text: String?): Boolean {
        text?.let { searchText ->
            if (searchText != "") {
                //Filtro lista de datos por el titulo
                val listFilter = listFavourites.filter { movieDB -> text in movieDB.title }

                if (listFilter.isNotEmpty()) {

                    //Creo el adaptador con esas peliculas
                    favMoviesFragment.updateFavouritesMoviesAdapter(listFilter)
                } else {
                    favMoviesFragment.showNotMoviesFavText()
                }
            }
        }
        return true
    }

    override fun onQueryTextChange(text: String?): Boolean {
        if (text == "") {
            getFavouritesMovies()
        }
        return true
    }
}
