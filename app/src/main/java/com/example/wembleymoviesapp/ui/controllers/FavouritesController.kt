package com.example.wembleymoviesapp.ui.controllers

import android.widget.ImageView
import android.widget.SearchView
import com.example.wembleymoviesapp.R
import com.example.wembleymoviesapp.data.database.DBMoviesProvider
import com.example.wembleymoviesapp.data.database.DbDataMapper
import com.example.wembleymoviesapp.domain.MovieItem
import com.example.wembleymoviesapp.ui.view.fragments.FavMoviesFragment

class FavouritesController(
    private val favMoviesFragment: FavMoviesFragment,
    private val dbProvider: DBMoviesProvider = DBMoviesProvider((favMoviesFragment.requireContext())),
    private val dataMapper: DbDataMapper = DbDataMapper()
) : SearchView.OnQueryTextListener {

    fun getFavouritesMovies() {
        val listFavMovies = dbProvider.getAllFavouritesMovies()
        val listFavMoviesModelItem = dataMapper.convertListToDomainMovieItem(listFavMovies)

        if (dbProvider.getAllFavouritesMovies().isNotEmpty()) favMoviesFragment.createAdapter(
            listFavMoviesModelItem
        )
        else favMoviesFragment.showNotMoviesFavText()

        // Una vez que devuelvo los datos dejo de refrescar el swipe layout
        favMoviesFragment.swipeRefreshLayout.isRefreshing = false
    }

    fun destroyDB() = dbProvider.closeDatabase()

    /**
     * Método que hace lo oportuno al presionar el boton de favorito de un item
     */
    fun pressFavButton(movieItem: MovieItem, imageView: ImageView) {
        val image: Int

        if (movieItem.favourite) {
            movieItem.favourite = false
            dbProvider.removeFavourite(movieItem.id)
            image = R.drawable.ic_favourite_border_red
        } else {
            movieItem.favourite = true
            dbProvider.setFavourite(movieItem.id)
            image = R.drawable.ic_favourite_background_red
        }

        imageView.setImageResource(image)
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
                    favMoviesFragment.createAdapter(listFavFilterDomainItem)
                }
                else {
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
