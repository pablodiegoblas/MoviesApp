package com.example.mymoviesapp.ui.fragments.popular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mymoviesapp.data.MoviesRepositoryImpl
import com.example.mymoviesapp.domain.models.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val moviesRepository: MoviesRepositoryImpl
) : ViewModel(), SwipeRefreshLayout.OnRefreshListener {

    val popularMovieModel = MutableLiveData<List<MovieModel>>()

    fun returnAllPopularMovies() {
        viewModelScope.launch {
            popularMovieModel.value = moviesRepository.getAllPopularMovies()
        }
    }

    override fun onRefresh() {
        returnAllPopularMovies()
    }

    /**
     * Function that set this movieItem as Favourite or noFavourite
     */
    fun pressFavButton(movieModelItem: MovieModel) {

        val attributeFavourite = movieModelItem.favourite.not()

        viewModelScope.launch {
            moviesRepository.updateMovie(movieModelItem.copy(favourite = attributeFavourite))

            // No return all the popular films because if user is looking for a film I want the favourite image to change but I don't want the adapter to change to the popular films but to keep showing the same films.
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

    fun onQueryTextSubmit(text: String) {
        if (text.isEmpty().not()) {
            viewModelScope.launch {
                popularMovieModel.value = moviesRepository.getMoviesSearched(text)
            }
        }
    }
}