package com.example.mymoviesapp.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.onAfterTextChanged(onAfterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            onAfterTextChanged(s.toString())
        }
    })
}