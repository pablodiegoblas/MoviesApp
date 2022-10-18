package com.example.mymoviesapp.ui.fragments.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviesapp.R
import com.example.mymoviesapp.databinding.ItemMovieBinding
import com.example.mymoviesapp.domain.models.MovieModel
import com.example.mymoviesapp.extension.loadImage

class FavMoviesAdapter(
    private val onMoreClick: (MovieModel) -> Unit,
    private val onFavouriteClick: (favourite: MovieModel) -> Unit,
    private val onSharedClick: (MovieModel) -> Unit
) : ListAdapter<MovieModel, FavMoviesAdapter.ViewHolder>(DiffUtilCallBackFavourite()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMovie(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        //Charge the elements directly with view binding
        fun bindMovie(movieModelItem: MovieModel) {
            with(binding) {
                movieModelItem.poster?.let { imageViewMovie.loadImage(it) }
                textViewTitleMovie.text = movieModelItem.title
                //Change fav image
                if (movieModelItem.favourite)
                    imageViewFavourite.setImageResource(R.drawable.ic_favourite_red)
                else imageViewFavourite.setImageResource(R.drawable.ic_no_favourite_red)
            }

            binding.root.setOnClickListener {
                onMoreClick(movieModelItem)
            }
            binding.imageViewFavourite.setOnClickListener {
                onFavouriteClick(movieModelItem)
            }
            binding.imageViewShared.setOnClickListener {
                onSharedClick(movieModelItem)
            }
        }
    }

}

private class DiffUtilCallBackFavourite : DiffUtil.ItemCallback<MovieModel>() {
    override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
        oldItem == newItem

}