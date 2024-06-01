package com.arkul.mychat.utilities.progressBar

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.arkul.mychat.R

fun Fragment.showProgressBar(id: Int = R.id.progressIndicator, isShowing: Boolean)  {
    try {
        val visibility = if (isShowing) View.VISIBLE else View.INVISIBLE
        val view = this.view?.findViewById<View>(id) ?: requireParentFragment().view?.findViewById(id)
        ?: throw Exception("ProgressBar with ID $id not found")
        view.visibility = visibility
    } catch (e: Exception) {
        Log.d("debug", e.message.toString())
    }
}