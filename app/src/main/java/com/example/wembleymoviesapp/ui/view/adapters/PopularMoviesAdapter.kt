package com.example.wembleymoviesapp.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wembleymoviesapp.BuildConfig
import com.example.wembleymoviesapp.R
import com.example.wembleymoviesapp.databinding.ItemMovieBinding
import com.example.wembleymoviesapp.domain.MovieItem
import com.squareup.picasso.Picasso

class PopularMoviesAdapter(
    private val onMoreClick: (MovieItem) -> Unit,
    private val onFavouriteClick: (favourite: MovieItem) -> Unit,
    private val onSharedClick: (MovieItem) -> Unit
) : ListAdapter<MovieItem, PopularMoviesAdapter.ViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMovie(getItem(position))
    }

    // Inner -> para que la clase ViewHolder pueda acceder a las propiedades de la clase superior en la que se encuentra
    inner class ViewHolder(
        private val binding: ItemMovieBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        //Cargo los elementos directamente con view binding
        fun bindMovie(movieItem: MovieItem) {
            with(movieItem) {
                poster?.let { loadImage(it, binding.imageViewMovie) }
                binding.textViewTitleMovie.text = title
                //Cambiar imagen de fav
                if (favourite) {
                    binding.imageViewFavourite.setImageResource(R.drawable.ic_favourite)
                } else
                    binding.imageViewFavourite.setImageResource(R.drawable.ic_no_favourite_red)
            }

            binding.buttonMore.setOnClickListener {
                onMoreClick(movieItem)
            }

            binding.imageViewFavourite.setOnClickListener {
                onFavouriteClick(movieItem)
            }

            binding.imageViewShared.setOnClickListener {
                onSharedClick(movieItem)
            }
        }

        private fun loadImage(url: String, imageView: ImageView) =
            Picasso.get().load("${BuildConfig.ApiImagesUrl}$url").fit().into(imageView)
    }
}

private class DiffUtilCallBack : DiffUtil.ItemCallback<MovieItem>() {
    override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean =
        oldItem == newItem

}
