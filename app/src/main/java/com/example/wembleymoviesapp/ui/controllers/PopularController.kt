package com.example.wembleymoviesapp.ui.controllers

import com.example.wembleymoviesapp.data.database.DBMoviesProvider
import com.example.wembleymoviesapp.data.database.MovieDB
import com.example.wembleymoviesapp.data.server.ServerMoviesProvider
import com.example.wembleymoviesapp.domain.MovieItem
import com.example.wembleymoviesapp.ui.view.fragments.PopularMoviesFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PopularController(
    private val popularMoviesFragment: PopularMoviesFragment,
    private val serverMoviesProvider: ServerMoviesProvider = ServerMoviesProvider(),
    private val dbProvider: DBMoviesProvider = DBMoviesProvider(popularMoviesFragment.requireContext())
) {

    private var listPopular: MutableList<MovieItem> = mutableListOf()

    fun getPopularMovies() {
        serverMoviesProvider.getAllPopularMoviesRequest(this)
    }

    fun returnsCall(listCall: List<MovieItem>) {

        listPopular = listCall.toMutableList()

        if (listPopular.isEmpty()) {
            popularMoviesFragment.showNotMoviesText()
        } else {
            // Buscar de estas peliculas que retorna cuales son favoritas
            getListWithFavourites()
            // Una vez que ya tenemos las favoritas ahora ya modifico la lista del adaptador
            popularMoviesFragment.updatePopularMoviesAdapter(listPopular)
        }
    }

    private fun getListWithFavourites() {
        // FALLO AQUI NO LO REASIGNABA por lo que al volver a cargar las peliculas no se cargaban las que ya estaban como favoritas
        listPopular = listPopular.mapIndexed { _, movieItem ->
            if (dbProvider.findMovie(movieItem.id)?.favourite == true) movieItem.copy(favourite = true)
            else movieItem
        }.toMutableList()
    }

    fun insertMoviesInDatabase(listDB: List<MovieDB>) {
        for (i in listDB) {
            dbProvider.insert(i)
        }
    }

    fun showErrorNetwork() = popularMoviesFragment.showErrorAPI()

    fun createDB() = dbProvider.openDB()
    fun destroyDB() = dbProvider.closeDatabase()

    /**
     * Método que cambia la imagen de favoritos cuando se hace click sobre una pelicula
     */
    fun pressFavButton(movieItem: MovieItem) {
        if (movieItem.favourite) {
            listPopular = listPopular.map {
                if (it.id == movieItem.id) it.copy(
                    favourite = false
                ) else it
            }.toMutableList()

            // Remove favourite attribute of the movies database
            GlobalScope.launch {
                dbProvider.removeFavourite(movieItem.id)
            }

        } else {
            listPopular = listPopular.map {
                if (it.id == movieItem.id) it.copy(
                    favourite = true
                ) else it
            }.toMutableList()

            // Include favourite attribute of the movies database
            GlobalScope.launch {
                dbProvider.setFavourite(movieItem.id)
            }

        }

        popularMoviesFragment.updatePopularMoviesAdapter(listPopular)

    }
}