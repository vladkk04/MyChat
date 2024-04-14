package com.arkul.mychat.ui.fragments.customizeAvatar

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CustomizeAvatarViewModel: ViewModel() {

    private val _backgroundColor = MutableStateFlow(-13025722)
    val backgroundColor get() = _backgroundColor.asStateFlow()

    fun changeBackground(color: Int) {
        _backgroundColor.value = color
       // Log.d("debug", color.toString())
    }
}