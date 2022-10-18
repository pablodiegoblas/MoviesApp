package com.example.mymoviesapp.ui.fragments.selectionFavourites

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviesapp.databinding.FilterChipItemBinding
import com.example.mymoviesapp.domain.models.GenreMovie


class ChipListAdapter(
    private val chipClicked: (GenreMovie) -> Unit
): RecyclerView.Adapter<ChipListAdapter.ViewHolder>() {

    private var items: List<GenreMovie> = emptyList()

    fun updateList(data: List<GenreMovie>) {
        items = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            FilterChipItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(
        private val binding: FilterChipItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: GenreMovie) {
            with (binding) {
                root.setOnClickListener { chipClicked(genre.copy(selected = genre.selected.not())) }
                root.text = genre.name
                root.isChecked = genre.selected
                root.typeface = if (genre.selected) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
            }
        }
    }
}