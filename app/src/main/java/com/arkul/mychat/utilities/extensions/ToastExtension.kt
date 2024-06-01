package com.arkul.mychat.utilities.extensions

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.arkul.mychat.R
import com.google.android.material.progressindicator.LinearProgressIndicator

private var toast: Toast? = null
fun Fragment.showToast(message: String?, duration: Int = Toast.LENGTH_SHORT) {
    if(message.isNullOrEmpty()) return
    if(lifecycle.currentState == Lifecycle.State.CREATED) return
    if (toast != null) { toast?.cancel() }
    toast = Toast.makeText(requireContext(), message, duration)
    toast?.show()

}
