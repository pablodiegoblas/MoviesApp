package com.example.wembleymoviesapp.ui.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.wembleymoviesapp.R
import com.example.wembleymoviesapp.data.API.API
import com.example.wembleymoviesapp.data.db.DBMoviesProvider
import com.example.wembleymoviesapp.data.model.MovieModel
import com.example.wembleymoviesapp.databinding.ItemMovieBinding
import com.example.wembleymoviesapp.ui.view.fragments.FavMoviesFragment
import com.squareup.picasso.Picasso

class FavMoviesAdapter(
    val favMovies: List<MovieModel>,
    val dbProvider: DBMoviesProvider
) :
    RecyclerView.Adapter<FavMoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, dbProvider)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMovie(favMovies[position])
    }

    override fun getItemCount(): Int = favMovies.size


    class ViewHolder(
        val binding: ItemMovieBinding,
        val dbProvider: DBMoviesProvider
    ) : RecyclerView.ViewHolder(binding.root) {

        private var selected: Boolean = false

        //Charge the elements directly with view binding
        fun bindMovie(movieModel: MovieModel) {
            with(movieModel) {
                posterPath?.let { loadImage(it, binding.imageViewMovie) }
                binding.textViewTitleMovie.text = title

                if (dbProvider.checkExist(this) && !selected) changeImageFav()

                binding.buttonMore.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(item: View?) {
                        item?.let {
                            if (movieModel.title != null) FavMoviesFragment.passDetailActivity(
                                movieModel.title
                            )
                        }
                    }

                })

                binding.imageViewFavourite.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(item: View?) {
                        item?.let {
                            if (selected) {
                                dbProvider.remove(movieModel.id)
                            } else dbProvider.insert(movieModel)
                        }
                        changeImageFav()
                    }
                })

                binding.imageViewShared.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(p0: View?) {
                        movieModel.title?.let { FavMoviesFragment.sharedInfo("¿Te apetece venir a ver conmigo la pelicula $it?") }
                    }

                })
            }

        }

        fun loadImage(url: String, imageView: ImageView) =
            Picasso.get().load("${API.IMG_URL}$url").fit().into(imageView)

        private fun changeImageFav() {
            val imagen: Int
            if (selected) {
                imagen = R.drawable.ic_favourite_border_red
            } else {
                imagen = R.drawable.ic_favourite_background_red
            }

            binding.imageViewFavourite.setImageResource(imagen)

            selected = !selected
        }
    }
}