package com.arkul.mychat.utilities.progressBar

import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment

fun Fragment.showProgressBar(progressBar: ProgressBar, isShow: Boolean = false) {
    progressBar.visibility = if(isShow) View.VISIBLE else View.INVISIBLE
}