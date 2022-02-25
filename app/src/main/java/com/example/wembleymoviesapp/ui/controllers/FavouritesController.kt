package com.example.wembleymoviesapp.ui.controllers

import android.widget.ImageView
import android.widget.SearchView
import com.example.wembleymoviesapp.R
import com.example.wembleymoviesapp.data.database.DBMoviesProvider
import com.example.wembleymoviesapp.data.database.DbDataMapper
import com.example.wembleymoviesapp.data.server.ServerMoviesProvider
import com.example.wembleymoviesapp.domain.MovieItem
import com.example.wembleymoviesapp.ui.view.fragments.FavMoviesFragment

class FavouritesController(
    private val favMoviesFragment: FavMoviesFragment,
    private val dbProvider: DBMoviesProvider = DBMoviesProvider((favMoviesFragment.requireContext())),
    private val serverProvider: ServerMoviesProvider = ServerMoviesProvider(),
    private val dataMapper: DbDataMapper = DbDataMapper()
) : SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    fun getFavouritesMovies() {
        val listFavMovies = dbProvider.getAllFavouritesMovies()
        val listFavMoviesModelItem = dataMapper.convertListToDomainMovieItem(listFavMovies)

        if (dbProvider.getAllFavouritesMovies().isNotEmpty()) favMoviesFragment.createAdapter(
            listFavMoviesModelItem
        )
        else favMoviesFragment.showNotMoviesFavText()
    }

    fun destroyDB() = dbProvider.closeDatabase()

    /**
     * Método que hace lo oportuno al presionar el boton de favorito de un item
     */
    fun pressFavButton(movieItem: MovieItem, imageView: ImageView) {
        val imagen: Int

        if (movieItem.favourite) {

            movieItem.favourite = false
            dbProvider.removeFavourite(movieItem.id)
            imagen = R.drawable.ic_favourite_border_red
        } else {
            movieItem.favourite = true
            dbProvider.setFavourite(movieItem.id)
            imagen = R.drawable.ic_favourite_background_red
        }

        imageView.setImageResource(imagen)
    }

    override fun onQueryTextSubmit(text: String?): Boolean {
        text?.let { searchText ->
            if (searchText != "") {
                serverProvider.getMoviesSearched(favMoviesFragment, text, null, this)
            }
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return false
    }

    override fun onClose(): Boolean {
        getFavouritesMovies()
        return true
    }
}
