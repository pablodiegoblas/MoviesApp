package com.example.wembleymoviesapp.ui.fragments.popular

import android.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.wembleymoviesapp.data.MoviesRepositoryImpl
import com.example.wembleymoviesapp.domain.models.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val moviesRepositoryImpl: MoviesRepositoryImpl
) : ViewModel(), SwipeRefreshLayout.OnRefreshListener,
    SearchView.OnQueryTextListener,
    SearchView.OnCloseListener {

    val popularMovieModel = MutableLiveData<List<MovieModel>>()

    fun returnAllPopularMovies() {
        viewModelScope.launch {
            popularMovieModel.value = moviesRepositoryImpl.getAllPopularMovies()
        }
    }

    override fun onRefresh() {
        returnAllPopularMovies()
    }

    /**
     * Function that set this movieItem as Favourite or noFavourite
     */
    fun pressFavButton(movieModelItem: MovieModel) {

        val attributeFavourite = !movieModelItem.favourite

        viewModelScope.launch {
            moviesRepositoryImpl.updateFavourite(movieModelItem.copy(favourite = attributeFavourite))

            //No return all the popular films because if I am looking for a film I want the favourite image to change but I don't want the adapter to change to the popular films but to keep showing the same films.
            changeListView(movieModelItem.id, attributeFavourite)
        }
    }

    /**
     * Find the movie that click movie and change the favourite attribute
     */
    private fun changeListView(idMovie: Int, fav: Boolean) {
        popularMovieModel.postValue(
            popularMovieModel.value?.map { if (it.id == idMovie) it.copy(favourite = fav) else it }
                ?.toMutableList()
        )
    }

    override fun onQueryTextSubmit(text: String?): Boolean {
        if (text != null) {
            viewModelScope.launch {
                popularMovieModel.value = moviesRepositoryImpl.getMoviesSearched(text)
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