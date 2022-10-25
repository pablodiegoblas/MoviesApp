package com.example.mymoviesapp.ui.fragments.selectionFavourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mymoviesapp.R
import com.example.mymoviesapp.databinding.AlertPreferencesMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreferencesMoviesDialog: DialogFragment() {

    private var binding: AlertPreferencesMoviesBinding? = null

    private val viewModel: PreferencesMoviesViewModel by viewModels()

    private val adapter: ChipListAdapter by lazy {
        ChipListAdapter {
            viewModel.checkFavoriteGenre(it)
        }
    }

    var onSave: () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = AlertPreferencesMoviesBinding.inflate(layoutInflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {
            with(it) {
                nextButton.setOnClickListener {
                    viewModel.saveFavoritesGenres()
                    dismiss()
                    onSave()
                }
                toolbar.setNavigationOnClickListener { dismiss() }
                chipList.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
                chipList.adapter = adapter
            }
        }

        viewModel.preferencesGenres.observe(viewLifecycleOwner) { genres ->
            if (genres.isNotEmpty()) adapter.updateList(genres)
        }

        viewModel.getMovieGenres()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance(
            onSave: () -> Unit
        ) = PreferencesMoviesDialog().apply {
            this.onSave = onSave
        }
    }

    override fun getTheme(): Int {
        return R.style.FullScreenDialog
    }
}