package com.example.wembleymoviesapp.ui.viewModel

import android.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.wembleymoviesapp.data.MoviesRepositoryImpl
import com.example.wembleymoviesapp.data.database.DBMoviesDatasource
import com.example.wembleymoviesapp.data.server.ServerMoviesDatasource
import com.example.wembleymoviesapp.domain.MovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val moviesRepositoryImpl : MoviesRepositoryImpl
) : ViewModel(), SwipeRefreshLayout.OnRefreshListener,
    SearchView.OnQueryTextListener,
    SearchView.OnCloseListener {

    val popularMovieModel = MutableLiveData<List<MovieItem>>()

    fun returnAllPopularMovies() {
        moviesRepositoryImpl.getAllPopularMovies {
            popularMovieModel.postValue(it)
        }
    }

    override fun onRefresh() {
        returnAllPopularMovies()
    }

    /**
     * Function that set this movieItem as Favourite or noFavourite
     */
    fun pressFavButton(movieItem: MovieItem) {

        val attrFav: Boolean

        if (movieItem.favourite) {
            attrFav = false

            // Remove favourite attribute of the movies database
            moviesRepositoryImpl.removeFavourite(movieItem.id)

        } else {
            attrFav = true

            // Include favourite attribute of the movies database
            moviesRepositoryImpl.setFavourite(movieItem.id)

        }

        //find movie that click movie and change the favourite attribute
        popularMovieModel.postValue(
            popularMovieModel.value?.map { if (it.id == movieItem.id) it.copy(favourite = attrFav) else it }
                ?.toMutableList()
        )

    }

    override fun onQueryTextSubmit(text: String?): Boolean {
        if (text != null) {
            moviesRepositoryImpl.getMoviesSearched(text) {
                popularMovieModel.postValue(it)
            }
        }
        return true
    }

    override fun onQueryTextChange(text: String?): Boolean {
        return false
    }

    override fun onClose(): Boolean {
        returnAllPopularMovies()
        return false
    }
}