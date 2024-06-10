package com.arkul.mychat.utilities.dialogs

import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.dialog.MaterialDialogs

fun Fragment.createAlertDialog(
    title: String,
    message: String,
    positiveButton: String,
    negativeButton: String,
    onPositiveClick: (() -> Unit)? = null,
    onNegativeClick: (() -> Unit)? = null
) = MaterialAlertDialogBuilder(requireContext())
    .setTitle(title)
    .setMessage(message)
    .setPositiveButton(positiveButton) { _, _ -> onPositiveClick?.invoke()  }
    .setNegativeButton(negativeButton) { _, _ -> onNegativeClick?.invoke() }
    .create()