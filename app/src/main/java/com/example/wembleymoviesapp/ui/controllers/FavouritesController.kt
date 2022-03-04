package com.example.wembleymoviesapp.ui.controllers

import android.widget.SearchView
import com.example.wembleymoviesapp.data.database.DBMoviesProvider
import com.example.wembleymoviesapp.data.mappers.DbDataMapper
import com.example.wembleymoviesapp.domain.MovieItem
import com.example.wembleymoviesapp.ui.view.fragments.FavMoviesFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavouritesController(
    private val favMoviesFragment: FavMoviesFragment,
    private val dbProvider: DBMoviesProvider = DBMoviesProvider((favMoviesFragment.requireContext())),
    private val dataMapper: DbDataMapper = DbDataMapper()
) : SearchView.OnQueryTextListener {

    private var listFavourites: MutableList<MovieItem> = mutableListOf()

    fun getFavouritesMovies() {
        // Devuelvo las peliculas de la base de datos
        val listFavMoviesCall = dbProvider.getAllFavouritesMovies()

        // Convierto las peliculas al modelo de dominio MovieItem
        val listFavMoviesModelItem = dataMapper.convertListToDomainMovieItem(listFavMoviesCall)

        // Almaceno la lista convertida a la lista del controlador
        listFavourites = listFavMoviesModelItem.toMutableList()

        //Si la lista no es vacia la actualizo, si es vacia pongo el texto NO HAY PELICULAS FAVORITAS
        if (listFavourites
                .isNotEmpty()
        ) favMoviesFragment.updateFavouritesMoviesAdapter(
            listFavourites
        )
        else favMoviesFragment.showNotMoviesFavText()

        // Una vez que devuelvo los datos dejo de refrescar el swipe layout
        favMoviesFragment.swipeRefreshLayout?.isRefreshing = false
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
            GlobalScope.launch {
                dbProvider.removeFavourite(movieItem.id)
            }

        } else {
            listFavourites =
                listFavourites.map { if (it.id == movieItem.id) it.copy(favourite = true) else it }
                    .toMutableList()

            // Include favourite attribute of the movies database
            GlobalScope.launch {
                dbProvider.setFavourite(movieItem.id)
            }

        }

        favMoviesFragment.updateFavouritesMoviesAdapter(listFavourites)

    }

    override fun onQueryTextSubmit(text: String?): Boolean {
        text?.let { searchText ->
            if (searchText != "") {
                var listFilterDB = dbProvider.findMoviesFavouritesByTitle(text)

                if (listFilterDB.isNotEmpty()) {
                    //Convierto la lista de favoritos filtrada en el modelo de dominio item
                    var listFavFilterDomainItem =
                        dataMapper.convertListToDomainMovieItem(listFilterDB)

                    //Creo el adaptador con esas peliculas
                    favMoviesFragment.updateFavouritesMoviesAdapter(listFavFilterDomainItem)
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
