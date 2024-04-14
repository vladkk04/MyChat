package com.arkul.mychat.ui.fragments.createProfile

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedProfileViewModel: ViewModel() {

    private val _isUserPickColor = MutableStateFlow(true)
    val isUserPickColor get() = _isUserPickColor.asStateFlow()

    fun changeUserPickColorState(value: Boolean) {
        _isUserPickColor.value = value
    }
}