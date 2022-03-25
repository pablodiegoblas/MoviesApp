package com.example.wembleymoviesapp.ui.view.adapters

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.wembleymoviesapp.BuildConfig
import com.example.wembleymoviesapp.R
import com.example.wembleymoviesapp.databinding.ItemMovieBinding
import com.example.wembleymoviesapp.domain.models.MovieModel
import com.squareup.picasso.Picasso

class PopularMoviesAdapter(
    private val onMoreClick: (MovieModel) -> Unit,
    private val onFavouriteClick: (favourite: MovieModel) -> Unit,
    private val onSharedClick: (MovieModel) -> Unit
) : ListAdapter<MovieModel, PopularMoviesAdapter.ViewHolder>(DiffUtilCallBack()) {

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
    ) :
        RecyclerView.ViewHolder(binding.root) {

        //Charge directly the elements with view binding
        fun bindMovie(movieModelItem: MovieModel) {
            with(movieModelItem) {
                poster?.let { loadImage(it, binding.imageViewMovie) }
                binding.textViewTitleMovie.text = title
                //Change fav image
                likeAnimation(binding.imageViewFavourite, R.raw.favourite, favourite)
            }

            binding.buttonMore.setOnClickListener {
                onMoreClick(movieModelItem)
            }

            binding.imageViewFavourite.setOnClickListener {
                onFavouriteClick(movieModelItem)
            }

            binding.imageViewShared.setOnClickListener {
                onSharedClick(movieModelItem)
            }
        }

        private fun loadImage(url: String, imageView: ImageView) =
            Picasso.get().load("${BuildConfig.ApiImagesUrl}$url").fit().into(imageView)

        private fun likeAnimation(
            imageView: LottieAnimationView,
            animation: Int,
            favourite: Boolean
        ) {
            if (favourite) {
                imageView.setAnimation(animation)
                imageView.playAnimation()
            } else {
                imageView.animate()
                    .alpha(0f)
                    .setDuration(400)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            imageView.resources.openRawResource(R.raw.favourite)
                            imageView.alpha = 1f
                        }
                    })
//                imageView.resources.openRawResource(R.raw.favourite)
            }
        }
    }
}

private class DiffUtilCallBack : DiffUtil.ItemCallback<MovieModel>() {
    override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
        oldItem == newItem

}
