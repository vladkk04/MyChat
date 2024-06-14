package com.arkul.mychat.utilities.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.getDrawable(id: Int): Drawable? = ContextCompat.getDrawable(requireContext(), id)
fun Context.getDrawable(id: Int): Drawable? = AppCompatResources.getDrawable(this, id)