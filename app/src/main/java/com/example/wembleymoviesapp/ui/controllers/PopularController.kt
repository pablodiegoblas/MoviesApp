package com.example.wembleymoviesapp.ui.controllers

import android.widget.ImageView
import com.example.wembleymoviesapp.R
import com.example.wembleymoviesapp.data.database.DBMoviesProvider
import com.example.wembleymoviesapp.data.database.MovieDB
import com.example.wembleymoviesapp.data.model.ResponseModel
import com.example.wembleymoviesapp.data.server.ServerMoviesProvider
import com.example.wembleymoviesapp.domain.MovieItem
import com.example.wembleymoviesapp.ui.view.fragments.PopularMoviesFragment

class PopularController(
    private val popularMoviesFragment: PopularMoviesFragment,
    private val serverMoviesProvider: ServerMoviesProvider = ServerMoviesProvider(),
    private val dbProvider: DBMoviesProvider = DBMoviesProvider(popularMoviesFragment.requireContext())
) {

    fun getPopularMovies() {
        serverMoviesProvider.getAllPopularMoviesRequest(this)
    }

    fun returnsCall(listMovieItems: List<MovieItem>) {
        if (listMovieItems.isEmpty()) {
            popularMoviesFragment.showNotMoviesText()
        } else {
            // Buscar de estas peliculas que retorna cuales son favoritas
            getListWithFavourites(listMovieItems)
            // Una vez que ya tenemos las favoritas ahora ya creo el adaptador
            popularMoviesFragment.createAdapter(listMovieItems)
        }
    }

    private fun getListWithFavourites(listMovieItems: List<MovieItem>) {
        listMovieItems.forEachIndexed { _, movieItem ->
            if (dbProvider.findMovie(movieItem.id)?.favourite == true) movieItem.favourite = true
        }
    }

    fun insertMoviesInDatabase(listDB: List<MovieDB>) {
        for (i in listDB) {
            dbProvider.insert(i)
        }
    }

    fun showErrorNetwork() = popularMoviesFragment.showErrorAPI()

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
}