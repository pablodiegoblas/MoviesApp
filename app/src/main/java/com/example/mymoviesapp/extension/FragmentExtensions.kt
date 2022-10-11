package com.example.mymoviesapp.extension

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.showDialog(dialogFragment: DialogFragment) {
    if (!supportFragmentManager.isDestroyed)
        dialogFragment.show(supportFragmentManager, null)
}