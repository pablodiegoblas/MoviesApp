package com.example.mymoviesapp.ui.commons.custom_views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import com.example.mymoviesapp.databinding.SearchBarBinding
import com.example.mymoviesapp.extension.onAfterTextChanged
import com.example.mymoviesapp.extension.viewBinding
import com.example.mymoviesapp.extension.visible

class SearchBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val binding: SearchBarBinding by viewBinding(SearchBarBinding::inflate)

    fun showAdditionalAction(visible: Boolean) {
        binding.additionalButton.visible(visible)
    }

    fun setOnClickAdditionalAction(click: () -> Unit) {
        binding.additionalButton.setOnClickListener { click() }
    }

    fun onAfterTextChanged(afterChanged: (String) -> Unit) {
        binding.etxSearch.onAfterTextChanged { afterChanged(it) }
        binding.etxSearch
    }

    fun setOnFocusChangeListener(changeFocus: (Boolean) -> Unit) {
        binding.etxSearch.setOnFocusChangeListener { _, hasFocus -> changeFocus(hasFocus) }
    }

    fun clear(){
        binding.etxSearch.text = null
    }
}